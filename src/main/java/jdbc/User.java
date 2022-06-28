package jdbc;

public class User {

    private int id;
    private String fname;
    private String lname;
    private String pw;
    private String phone;
    private String address;

    public User(int id, String fname, String lname, String pw, String phone, String address) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.pw = pw;
        this.phone = phone;
        this.address = address;
    }

    public User(String fname, String lname, String pw, String phone, String address) {
        this.fname = fname;
        this.lname = lname;
        this.pw = pw;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPw() {
        return pw;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
