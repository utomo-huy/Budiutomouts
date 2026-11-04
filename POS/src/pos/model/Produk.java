package pos.model;

/**
 * ABSTRACTION: kelas abstrak, tidak bisa di-instantiate langsung.
 * ENCAPSULATION: atribut private, diakses lewat getter/setter.
 */
public abstract class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        setHarga(harga);
        setStok(stok);
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }

    public void setHarga(double harga) {
        if (harga < 0) throw new IllegalArgumentException("Harga tidak boleh negatif");
        this.harga = harga;
    }

    public void setStok(int stok) {
        if (stok < 0) throw new IllegalArgumentException("Stok tidak boleh negatif");
        this.stok = stok;
    }

    public void kurangiStok(int qty) {
        if (qty > stok) throw new IllegalArgumentException("Stok tidak cukup");
        this.stok -= qty;
    }

    // Method abstrak: wajib di-override oleh subclass
    public abstract String getKategori();

    // POLYMORPHISM: subclass boleh mengubah cara hitung subtotal
    public double hitungSubtotal(int qty) {
        return harga * qty;
    }

    public String getKeterangan() { return "-"; }

    @Override
    public String toString() {
        return String.format("%-6s | %-18s | %-10s | Rp %,10.0f | Stok: %3d | %s",
                kode, nama, getKategori(), harga, stok, getKeterangan());
    }
}
