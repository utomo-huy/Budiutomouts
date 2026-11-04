package pos.model;

/** ABSTRACTION + ENCAPSULATION: induk untuk Kasir dan Admin. */
public abstract class Pengguna {
    private String id;
    private String nama;
    private String pin; // private, tidak ada getter

    public Pengguna(String id, String nama, String pin) {
        this.id = id;
        this.nama = nama;
        this.pin = pin;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }

    public boolean cekPin(String input) { return pin.equals(input); }

    public abstract String getPeran();
}
