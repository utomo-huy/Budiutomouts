package pos.pembayaran;

import java.util.Scanner;

/** ABSTRACTION: interface metode pembayaran. */
public interface Pembayaran {
    String getNama();

    /** Biaya tambahan di luar total belanja (admin fee, dll). */
    double hitungBiaya(double total);

    /** Proses bayar. Return kembalian, atau -1 jika gagal. */
    double bayar(double totalAkhir, Scanner sc);
}
