package pos.model;

public class Kasir extends Pengguna {
    public Kasir(String id, String nama, String pin) { super(id, nama, pin); }

    @Override
    public String getPeran() { return "Kasir"; }
}
