package pos.pembayaran;

import java.util.Scanner;

public class PembayaranQRIS implements Pembayaran {

    @Override
    public String getNama() { return "QRIS"; }

    @Override
    public double hitungBiaya(double total) { return total * 0.007; } // 0.7%

    @Override
    public double bayar(double totalAkhir, Scanner sc) {
        System.out.println("Silakan scan QR untuk membayar.");
        System.out.print("Pembayaran berhasil? (y/n): ");
        String jawab = sc.nextLine().trim();
        return jawab.equalsIgnoreCase("y") ? 0 : -1;
    }
}
