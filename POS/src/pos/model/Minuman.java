package pos.model;

/** INHERITANCE: Minuman mewarisi Produk. Harga normal tanpa diskon. */
public class Minuman extends Produk {

    public Minuman(String kode, String nama, double harga, int stok) {
        super(kode, nama, harga, stok);
    }

    @Override
    public String getKategori() { return "Minuman"; }
}
