import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Seller extends User {
    public Seller() {
        this.role = "seller";
    }

    public void addProduct(ProductService productService, Scanner scanner) {
        System.out.print("Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Product Price: ");
        double price = scanner.nextDouble();
        System.out.print("Product Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setSellerId(this.getId());

        try {
            productService.addProduct(product);
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(ProductService productService, Scanner scanner) {
        System.out.print("Product Name to update: ");
        String name = scanner.nextLine();
        try {
            Product product = productService.getProductByName(name);
            if (product != null && product.getSellerId() == this.getId()) {
                System.out.print("New Product Price: ");
                double price = scanner.nextDouble();
                System.out.print("New Product Quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                product.setPrice(price);
                product.setQuantity(quantity);

                productService.updateProduct(product);
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("Product not found or you do not have permission to update it.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(ProductService productService, Scanner scanner) {
        System.out.print("Product Name to delete: ");
        String name = scanner.nextLine();
        try {
            Product product = productService.getProductByName(name);
            if (product != null && product.getSellerId() == this.getId()) {
                productService.deleteProduct(product.getId(), this.getId());
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Product not found or you do not have permission to delete it.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listProducts(ProductService productService) {
        try {
            List<Product> products = productService.getProductsBySeller(this.getId());
            for (Product product : products) {
                System.out.println(product.getName() + " - " + product.getPrice() + " - " + product.getQuantity());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
