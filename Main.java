import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:postgresql://localhost:5432/ecommerce";
        String user = "postgres"; // Replace with your PostgreSQL username
        String password = "Keyin2021"; // Replace with your PostgreSQL password
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            UserDAO userDAO = new UserDAO(connection);
            UserService userService = new UserService(userDAO);
            ProductDAO productDAO = new ProductDAO(connection);
            ProductService productService = new ProductService(productDAO);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Role (buyer/seller/admin): ");
                    String role = scanner.nextLine();

                    User user;
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

                    user.setUsername(username);
                    user.setPassword(password);
                    user.setEmail(email);
                    try {
                        userService.registerUser(user);
                        System.out.println("User registered successfully!");
                    } catch (SQLException e) {
                        System.out.println("Error registering user: " + e.getMessage());
                    }

                } else if (choice == 2) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    try {
                        User user = userService.loginUser(username, password);
                        if (user != null) {
                            System.out.println("Login successful!");
                            switch (user.getRole()) {
                                case "buyer":
                                    buyerMenu(scanner, (Buyer) user, productService);
                                    break;
                                case "seller":
                                    sellerMenu(scanner, (Seller) user, productService);
                                    break;
                                case "admin":
                                    adminMenu(scanner, (Admin) user, userService, productService);
                                    break;
                            }
                        } else {
                            System.out.println("Invalid credentials!");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error logging in: " + e.getMessage());
                    }

                } else if (choice == 3) {
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void buyerMenu(Scanner scanner, Buyer buyer, ProductService productService) {
        while (true) {
            System.out.println("Buyer Menu");
            System.out.println("1. Browse Products");
            System.out.println("2. Search Product");
            System.out.println("3. View Product Info");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                buyer.browseProducts(productService);
            } else if (choice == 2) {
                System.out.print("Enter product name to search: ");
                String productName = scanner.nextLine();
                buyer.searchProduct(productService, productName);
            } else if (choice == 3) {
                System.out.print("Enter product name to view info: ");
                String productName = scanner.nextLine();
                buyer.viewProductInfo(productService, productName);
            } else if (choice == 4) {
                break;
            }
        }
    }

    private static void sellerMenu(Scanner scanner, Seller seller, ProductService productService) {
        while (true) {
            System.out.println("Seller Menu");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. List Products");
            System.out.println("5. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                seller.addProduct(productService, scanner);
            } else if (choice == 2) {
                seller.updateProduct(productService, scanner);
            } else if (choice == 3) {
                seller.deleteProduct(productService, scanner);
            } else if (choice == 4) {
                seller.listProducts(productService);
            } else if (choice == 5) {
                break;
            }
        }
    }

    private static void adminMenu(Scanner scanner, Admin admin, UserService userService,
            ProductService productService) {
        while (true) {
            System.out.println("Admin Menu");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. View All Products");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                admin.viewAllUsers(userService);
            } else if (choice == 2) {
                System.out.print("Enter username to delete: ");
                String username = scanner.nextLine();
                admin.deleteUser(userService, username);
            } else if (choice == 3) {
                admin.viewAllProducts(productService);
            } else if (choice == 4) {
                break;
            }
        }
    }
}
