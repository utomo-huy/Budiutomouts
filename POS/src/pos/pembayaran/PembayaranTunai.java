package pos.pembayaran;

import java.util.Scanner;

public class PembayaranTunai implements Pembayaran {

    @Override
    public String getNama() { return "Tunai"; }

    @Override
    public double hitungBiaya(double total) { return 0; }

    @Override
    public double bayar(double totalAkhir, Scanner sc) {
        System.out.print("Uang diterima : Rp ");
        try {
            double uang = Double.parseDouble(sc.nextLine().trim());
            if (uang < totalAkhir) {
                System.out.println("Uang kurang! Pembayaran gagal.");
                return -1;
            }
            return uang - totalAkhir;
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid!");
            return -1;
        }
    }
}
