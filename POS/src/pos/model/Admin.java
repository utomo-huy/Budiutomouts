package pos.model;

public class Admin extends Pengguna {
    public Admin(String id, String nama, String pin) { super(id, nama, pin); }

    @Override
    public String getPeran() { return "Admin"; }
}
