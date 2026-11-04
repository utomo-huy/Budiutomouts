package pos.model;

/** INHERITANCE: Elektronik mewarisi Produk. Ada biaya proteksi 2% + garansi. */
public class Elektronik extends Produk {
    private int garansiBulan;

    public Elektronik(String kode, String nama, double harga, int stok, int garansiBulan) {
        super(kode, nama, harga, stok);
        this.garansiBulan = garansiBulan;
    }

    public int getGaransiBulan() { return garansiBulan; }

    @Override
    public String getKategori() { return "Elektronik"; }

    @Override
    public double hitungSubtotal(int qty) {
        return super.hitungSubtotal(qty) * 1.02; // biaya proteksi 2%
    }

    @Override
    public String getKeterangan() {
        return "Garansi " + garansiBulan + " bln, +2% proteksi";
    }
}
