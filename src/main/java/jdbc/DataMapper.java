package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataMapper {

    public int createUser(User u) {
        try {
            Connection con = DBConnector.connection();
            String sql = "INSERT INTO usertable (fname, lname, pw, phone, address) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, u.getFname());
            ps.setString(2, u.getLname());
            ps.setString(3, u.getPw());
            ps.setString(4, u.getAddress());
            ps.setString(5, u.getPhone());
            ps.executeUpdate();

            // Get generated ID from user
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            u.setId(id);

            return id;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> getAllUsers() {
        List<User> persons = new ArrayList();
        try {
            Connection con = DBConnector.connection();
            String SQL = "SELECT id, fname, lname, pw, phone, address FROM usertable";
            PreparedStatement ps = con.prepareStatement(SQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String pw = rs.getString("pw");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int id = (int) rs.getInt("id");
                persons.add(new User(id, fname, lname, pw, phone, address));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return persons;
    }

    public User getUserByPhone(String phoneNumber) {
        
        User user = null;
        
        try {
            Connection con = DBConnector.connection();
            String SQL = "SELECT * FROM usertable WHERE phone = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, phoneNumber);

            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String pw = rs.getString("pw");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int id = (int) rs.getInt("id");

                user = new User(id, fname, lname, pw, phone, address);
            }
            return user;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updateUser(String phoneNumber, User user) {

        try {
            Connection con = DBConnector.connection();
            String SQL =
                    "UPDATE usertable " +
                    "SET fname = ?, lname = ?, pw = ?, phone = ?, address = ?" +
                    "WHERE phone = ? ";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1,user.getFname());
            ps.setString(2,user.getLname());
            ps.setString(3,user.getPw());
            ps.setString(4,user.getPhone());
            ps.setString(5,user.getAddress());
            ps.setString(6, phoneNumber);

            return ps.executeUpdate();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
