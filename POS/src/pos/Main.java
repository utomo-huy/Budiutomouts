package pos;

import java.util.Scanner;
import pos.model.*;
import pos.pembayaran.*;
import pos.service.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Inventori inventori = new Inventori();

    // Data pengguna (polymorphism: array bertipe Pengguna)
    private static final Pengguna[] daftarPengguna = {
        new Admin("A01", "Budi (Admin)", "1234"),
        new Kasir("K01", "Siti", "1111"),
        new Kasir("K02", "Andi", "2222")
    };

    public static void main(String[] args) {
        System.out.println("=== SISTEM POS TOKO SEJAHTERA ===");
        while (true) {
            Pengguna user = login();
            if (user == null) break;

            // Polymorphism: menu dipilih sesuai tipe objek
            if (user instanceof Admin) menuAdmin((Admin) user);
            else if (user instanceof Kasir) menuKasir((Kasir) user);
        }
        System.out.println("Program selesai.");
    }

    private static Pengguna login() {
        System.out.println("\n--- LOGIN (ketik 0 untuk keluar) ---");
        System.out.print("ID  : ");
        String id = sc.nextLine().trim();
        if (id.equals("0")) return null;
        System.out.print("PIN : ");
        String pin = sc.nextLine().trim();

        for (Pengguna p : daftarPengguna) {
            if (p.getId().equalsIgnoreCase(id) && p.cekPin(pin)) {
                System.out.println("Login berhasil. Halo, " + p.getNama() + " [" + p.getPeran() + "]");
                return p;
            }
        }
        System.out.println("ID atau PIN salah!");
        return login();
    }

    // ================= MENU ADMIN =================
    private static void menuAdmin(Admin admin) {
        while (true) {
            System.out.println("\n--- MENU ADMIN ---");
            System.out.println("1. Lihat produk");
            System.out.println("2. Tambah produk");
            System.out.println("0. Logout");
            int pilih = bacaInt("Pilih: ");
            switch (pilih) {
                case 1: inventori.tampilkan(); break;
                case 2: tambahProduk(); break;
                case 0: return;
                default: System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void tambahProduk() {
        System.out.println("Kategori: 1) Makanan  2) Minuman  3) Elektronik");
        int kat = bacaInt("Pilih kategori: ");
        if (kat < 1 || kat > 3) { System.out.println("Kategori tidak valid!"); return; }

        System.out.print("Kode  : "); String kode = sc.nextLine().trim();
        System.out.print("Nama  : "); String nama = sc.nextLine().trim();
        double harga = bacaInt("Harga : ");
        int stok = bacaInt("Stok  : ");

        try {
            Produk p;
            if (kat == 1) p = new Makanan(kode, nama, harga, stok);
            else if (kat == 2) p = new Minuman(kode, nama, harga, stok);
            else {
                int garansi = bacaInt("Garansi (bulan): ");
                p = new Elektronik(kode, nama, harga, stok, garansi);
            }
            inventori.tambah(p);
            System.out.println("Produk berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    // ================= MENU KASIR =================
    private static void menuKasir(Kasir kasir) {
        while (true) {
            System.out.println("\n--- MENU KASIR ---");
            System.out.println("1. Lihat produk");
            System.out.println("2. Transaksi baru");
            System.out.println("0. Logout");
            int pilih = bacaInt("Pilih: ");
            switch (pilih) {
                case 1: inventori.tampilkan(); break;
                case 2: transaksiBaru(kasir); break;
                case 0: return;
                default: System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void transaksiBaru(Kasir kasir) {
        Transaksi trx = new Transaksi(kasir);
        inventori.tampilkan();

        while (true) {
            System.out.print("\nKode produk (ketik 'selesai' untuk bayar, 'batal' untuk batal): ");
            String kode = sc.nextLine().trim();

            if (kode.equalsIgnoreCase("batal")) {
                System.out.println("Transaksi dibatalkan.");
                return;
            }
            if (kode.equalsIgnoreCase("selesai")) {
                if (trx.kosong()) { System.out.println("Keranjang masih kosong!"); continue; }
                break;
            }

            Produk p = inventori.cari(kode);
            if (p == null) { System.out.println("Produk tidak ditemukan!"); continue; }

            int qty = bacaInt("Jumlah: ");
            try {
                trx.tambahItem(p, qty);
                trx.tampilkanKeranjang();
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal: " + e.getMessage());
            }
        }

        // Pilih metode pembayaran (polymorphism lewat interface)
        System.out.println("\nMetode pembayaran: 1) Tunai  2) QRIS");
        int m = bacaInt("Pilih: ");
        Pembayaran metode = (m == 2) ? new PembayaranQRIS() : new PembayaranTunai();

        if (!trx.checkout(metode, sc)) {
            System.out.println("Transaksi gagal / belum dibayar.");
        }
    }

    // ================= UTIL =================
    private static int bacaInt(String label) {
        while (true) {
            System.out.print(label);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }
}
