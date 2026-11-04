package pos.model;

public class ItemTransaksi {
    private final Produk produk;
    private int qty;

    public ItemTransaksi(Produk produk, int qty) {
        this.produk = produk;
        this.qty = qty;
    }

    public Produk getProduk() { return produk; }
    public int getQty() { return qty; }
    public void tambahQty(int tambahan) { this.qty += tambahan; }

    // Polymorphism: subtotal dihitung sesuai jenis produknya
    public double getSubtotal() { return produk.hitungSubtotal(qty); }
}
