package pos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import pos.model.*;
import pos.pembayaran.Pembayaran;

public class Transaksi {
    private static final Locale ID = Locale.forLanguageTag("id-ID");
    private static int counter = 0;

    private final String noTransaksi;
    private final Kasir kasir;
    private final List<ItemTransaksi> items = new ArrayList<>();

    public Transaksi(Kasir kasir) {
        this.kasir = kasir;
        this.noTransaksi = String.format("TRX%04d", ++counter);
    }

    public void tambahItem(Produk produk, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Jumlah harus > 0");

        // Hitung qty yang sudah ada di keranjang agar stok tidak terlewati
        ItemTransaksi existing = cariItem(produk);
        int sudahAda = (existing == null) ? 0 : existing.getQty();
        if (sudahAda + qty > produk.getStok())
            throw new IllegalArgumentException("Stok tidak cukup (tersedia " + produk.getStok() + ")");

        if (existing != null) existing.tambahQty(qty);
        else items.add(new ItemTransaksi(produk, qty));
    }

    private ItemTransaksi cariItem(Produk p) {
        for (ItemTransaksi it : items) {
            if (it.getProduk() == p) return it;
        }
        return null;
    }

    public boolean kosong() { return items.isEmpty(); }

    public double hitungTotal() {
        double total = 0;
        for (ItemTransaksi it : items) total += it.getSubtotal();
        return total;
    }

    public void tampilkanKeranjang() {
        System.out.println("\n--- Keranjang ---");
        if (items.isEmpty()) {
            System.out.println("(kosong)");
            return;
        }
        int no = 1;
        for (ItemTransaksi it : items) {
            System.out.printf(ID, "%d. %-18s x%-3d Rp %,.0f%n",
                    no++, it.getProduk().getNama(), it.getQty(), it.getSubtotal());
        }
        System.out.printf(ID, "TOTAL: Rp %,.0f%n", hitungTotal());
    }

    /** Proses pembayaran, kurangi stok, cetak struk. Return true jika sukses. */
    public boolean checkout(Pembayaran metode, Scanner sc) {
        double total = hitungTotal();
        double biaya = metode.hitungBiaya(total);
        double totalAkhir = total + biaya;

        System.out.printf(ID, "Total belanja  : Rp %,.0f%n", total);
        if (biaya > 0)
            System.out.printf(ID, "Biaya %-8s : Rp %,.0f%n", metode.getNama(), biaya);
        System.out.printf(ID, "Total bayar    : Rp %,.0f%n", totalAkhir);

        double kembalian = metode.bayar(totalAkhir, sc);
        if (kembalian < 0) return false;

        for (ItemTransaksi it : items) it.getProduk().kurangiStok(it.getQty());
        cetakStruk(metode, total, biaya, totalAkhir, kembalian);
        return true;
    }

    private void cetakStruk(Pembayaran m, double total, double biaya, double totalAkhir, double kembalian) {
        System.out.println("\n==============================================");
        System.out.println("               TOKO SEJAHTERA");
        System.out.println("==============================================");
        System.out.println("No     : " + noTransaksi);
        System.out.println("Kasir  : " + kasir.getNama());
        System.out.println("----------------------------------------------");
        for (ItemTransaksi it : items) {
            System.out.printf(ID, "%-18s x%-3d %,12.0f%n",
                    it.getProduk().getNama(), it.getQty(), it.getSubtotal());
        }
        System.out.println("----------------------------------------------");
        System.out.printf(ID, "%-24s %,12.0f%n", "Total", total);
        if (biaya > 0) System.out.printf(ID, "%-24s %,12.0f%n", "Biaya " + m.getNama(), biaya);
        System.out.printf(ID, "%-24s %,12.0f%n", "Total Bayar", totalAkhir);
        System.out.printf(ID, "%-24s %,12.0f%n", "Dibayar (" + m.getNama() + ")", totalAkhir + kembalian);
        System.out.printf(ID, "%-24s %,12.0f%n", "Kembalian", kembalian);
        System.out.println("==============================================");
        System.out.println("          Terima kasih, datang lagi!");
        System.out.println("==============================================\n");
    }
}
