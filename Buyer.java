import java.sql.SQLException;
import java.util.List;

public class Buyer extends User {
    public Buyer() {
        this.role = "buyer";
    }

    public void browseProducts(ProductService productService) {
        try {
            List<Product> products = productService.getAllProducts();
            for (Product product : products) {
                System.out.println(product.getName() + " - " + product.getPrice() + " - " + product.getQuantity());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchProduct(ProductService productService, String productName) {
        try {
            Product product = productService.getProductByName(productName);
            if (product != null) {
                System.out.println("Product found: " + product.getName() + " - " + product.getPrice() + " - "
                        + product.getQuantity());
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewProductInfo(ProductService productService, String productName) {
        try {
            Product product = productService.getProductByName(productName);
            if (product != null) {
                System.out.println("Product Info:");
                System.out.println("Name: " + product.getName());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Quantity: " + product.getQuantity());
                System.out.println("Seller ID: " + product.getSellerId());
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
