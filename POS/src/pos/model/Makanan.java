package pos.model;

/** INHERITANCE: Makanan mewarisi Produk. Diskon 10% jika beli >= 5. */
public class Makanan extends Produk {

    public Makanan(String kode, String nama, double harga, int stok) {
        super(kode, nama, harga, stok);
    }

    @Override
    public String getKategori() { return "Makanan"; }

    @Override
    public double hitungSubtotal(int qty) {
        double total = super.hitungSubtotal(qty);
        return (qty >= 5) ? total * 0.90 : total;
    }

    @Override
    public String getKeterangan() { return "Diskon 10% beli >= 5"; }
}
