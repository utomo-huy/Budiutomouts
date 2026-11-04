package pos.service;

import java.util.ArrayList;
import java.util.List;
import pos.model.*;

public class Inventori {
    private final List<Produk> daftar = new ArrayList<>();

    public Inventori() {
        // Data awal (polymorphism: list bertipe Produk, isinya subclass)
        daftar.add(new Makanan("MK01", "Roti Bakar", 12000, 30));
        daftar.add(new Makanan("MK02", "Nasi Goreng", 20000, 20));
        daftar.add(new Minuman("MN01", "Es Teh", 5000, 50));
        daftar.add(new Minuman("MN02", "Kopi Susu", 15000, 40));
        daftar.add(new Elektronik("EL01", "Earphone", 85000, 10, 12));
        daftar.add(new Elektronik("EL02", "Charger 20W", 120000, 8, 6));
    }

    public void tambah(Produk p) {
        if (cari(p.getKode()) != null)
            throw new IllegalArgumentException("Kode produk sudah ada");
        daftar.add(p);
    }

    public Produk cari(String kode) {
        for (Produk p : daftar) {
            if (p.getKode().equalsIgnoreCase(kode)) return p;
        }
        return null;
    }

    public void tampilkan() {
        System.out.println("\n=========================== DAFTAR PRODUK ===========================");
        for (Produk p : daftar) System.out.println(p);
        System.out.println("=====================================================================");
    }
}
