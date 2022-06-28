package jdbc;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataMapperTest {

    public Connection con;
    public DataMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DataMapper();

        try {
            con = DBConnector.connection();

            String createTable =
                    "CREATE TABLE IF NOT EXISTS `startcode_test`.`usertable` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `fname` VARCHAR(45) NULL,\n" +
                    "  `lname` VARCHAR(45) NULL,\n" +
                    "  `pw` VARCHAR(45) NULL,\n" +
                    "  `phone` VARCHAR(45) NULL,\n" +
                    "  `address` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`id`));";

            con.prepareStatement(createTable).executeUpdate();

            String SQL = "INSERT INTO startcode_test.usertable (fname, lname, pw, phone, address) VALUES (?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "Hans");
            ps.setString(2, "Hansen");
            ps.setString(3, "Hemmelig123");
            ps.setString(4, "40404040");
            ps.setString(5,"Rolighedsvej 3");
            ps.executeUpdate();
            ps.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            con = DBConnector.connection();
            String dropTable = "DROP TABLE IF EXISTS startcode_test.usertable";
            con.prepareStatement(dropTable).executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createUser() {
        //Arrange
        User user = new User("Steve", "Taylor", "123456", "secret", "Hollywood-Boulevard");
        //Act
        var id = mapper.createUser(user);
        System.out.println(id);
        //Assert
        assertEquals(2, id);
    }

    @Test
    void getAllUsers() {
        //Arrange
        List<User> users = mapper.getAllUsers();
        //Act
        var length = 1;
        //Assert
        assertEquals(1, users.size());
    }

    @Test
    void updateUser() {
        //Arrange
        User user = new User("Steve", "Taylor", "123456", "secret", "Hollywood-Boulevard");
        var phoneNumber = "40404040";
        //Act
        var rowsAffected = mapper.updateUser(phoneNumber, user);
        //Assert
        assertEquals(1, rowsAffected);
    }

    @Test
    void getUserByPhoneTest() {
        //Arrange
        var phoneNumber = "40404040";
        //Act
        var user = mapper.getUserByPhone(phoneNumber);
        //Assert
        assertEquals(phoneNumber, user.getPhone());
    }
}