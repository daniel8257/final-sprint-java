import java.sql.SQLException;
import java.util.List;

public class Admin extends User {
    public Admin() {
        this.role = "admin";
    }

    public void viewAllUsers(UserService userService) {
        try {
            List<User> users = userService.getAllUsers();
            for (User user : users) {
                System.out.println(user.getUsername() + " - " + user.getEmail() + " - " + user.getRole());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(UserService userService, String username) {
        try {
            userService.deleteUser(username);
            System.out.println("User deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewAllProducts(ProductService productService) {
        try {
            List<Product> products = productService.getAllProducts();
            for (Product product : products) {
                System.out.println(product.getName() + " - " + product.getPrice() + " - " + product.getQuantity()
                        + " - Seller Name: " + product.getSellerName() + " - Seller Email: "
                        + product.getSellerEmail());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
