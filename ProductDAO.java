import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT p.id, p.name, p.price, p.quantity, p.seller_id, u.username, u.email " +
                "FROM Products p " +
                "JOIN Users u ON p.seller_id = u.id";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerId(rs.getInt("seller_id"));
                product.setSellerName(rs.getString("username"));
                product.setSellerEmail(rs.getString("email"));
                products.add(product);
            }
            return products;
        }
    }

    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO Products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getSellerId());
            stmt.executeUpdate();
        }
    }

    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE Products SET name = ?, price = ?, quantity = ? WHERE id = ? AND seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getId());
            stmt.setInt(5, product.getSellerId());
            stmt.executeUpdate();
        }
    }

    public void deleteProduct(int productId, int sellerId) throws SQLException {
        String sql = "DELETE FROM Products WHERE id = ? AND seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, sellerId);
            stmt.executeUpdate();
        }
    }

    public List<Product> getProductsBySeller(int sellerId) throws SQLException {
        String sql = "SELECT * FROM Products WHERE seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerId(rs.getInt("seller_id"));
                products.add(product);
            }
            return products;
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM Products";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerId(rs.getInt("seller_id"));
                products.add(product);
            }
            return products;
        }
    }

    public Product getProductByName(String name) throws SQLException {
        String sql = "SELECT * FROM Products WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerId(rs.getInt("seller_id"));
                return product;
            } else {
                return null;
            }
        }
    }
}
