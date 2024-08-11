import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void registerUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user;
                String role = rs.getString("role");
                switch (role) {
                    case "buyer":
                        user = new Buyer();
                        break;
                    case "seller":
                        user = new Seller();
                        break;
                    case "admin":
                        user = new Admin();
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown role: " + role);
                }
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                return user;
            } else {
                return null;
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM Users";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user;
                String role = rs.getString("role");
                switch (role) {
                    case "buyer":
                        user = new Buyer();
                        break;
                    case "seller":
                        user = new Seller();
                        break;
                    case "admin":
                        user = new Admin();
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown role: " + role);
                }
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
            return users;
        }
    }

    public void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM Users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}
