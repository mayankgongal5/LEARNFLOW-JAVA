import java.util.*;
class User {
    String username;
    String password;
    ShoppingCartItem cart;

    public User() {
        this.cart = null;
    }
}
class ShoppingCartItem {
    Product product;
    ShoppingCartItem next;

    public ShoppingCartItem(Product product) {
        this.product = product;
        this.next = null;
    }
}
class Product {
    String productName;
    int productQuantity;
    float productPrice;

    public Product(String productName, int productQuantity, float productPrice) {
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }
}
public class Main {
    static void freeCart(ShoppingCartItem cart) {
        while (cart != null) {
            cart = cart.next;
        }
    }
    static void registerUser(User newUser) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a username: ");
        newUser.username = scanner.nextLine();
        System.out.print("Enter a password: ");
        newUser.password = scanner.nextLine();
    }
    static boolean loginUser(User existingUser) {
        Scanner scanner = new Scanner(System.in);
        User userInput = new User();
        System.out.print("Enter your username: ");
        userInput.username = scanner.nextLine();
        System.out.print("Enter your password: ");
        userInput.password = scanner.nextLine();
        return existingUser.username.equals(userInput.username) && existingUser.password.equals(userInput.password);
    }

    static boolean addToCart(User user, Product product) {
        ShoppingCartItem item = new ShoppingCartItem(product);
        item.next = user.cart;
        user.cart = item;
        System.out.println(product.productName + " added to the cart.");
        return true;
    }
    static void displayProductMenu(String section, String[] products, int[] prices) {
        System.out.println("Welcome to the " + section + " Section");
        for (int i = 0; i < products.length; ++i) {
            System.out.println((i + 1) + ") " + products[i] + " -> Rs." + prices[i]);
        }
    }
    static int portalDisplay(User user) {
        String[] categories = {"Western", "Indian", "IndoWestern"};
        String[] westernProducts = {"Jeans", "Tshirt", "Shirts", "One Piece", "Trousers"};
        String[] indianProducts = {"Kurti", "Skirt", "Lehenga", "Dress", "Ghagra"};
        String[] indoWesternProducts = {"Pants", "Tshirt", "Shirts", "The One Piece is real", "Short Kurti"};
        int[] prices = {900, 500, 700, 1000, 800};
        Scanner scanner = new Scanner(System.in);
        int category, selected, quantity;
        System.out.println("Welcome to the Shopping Portal");
        while (true) {
            System.out.println("\n 1) Western \n 2) Indian \n 3) IndoWestern");
            System.out.print("Enter the category you want to buy and 0 to exit: ");
            category = scanner.nextInt();
            if (category == 0) {
                return 1;
            }
            String[] products;
            switch (category) {
                case 1: products = westernProducts;
                    break;
                case 2: products = indianProducts;
                    break;
                case 3: products = indoWesternProducts;
                    break;
                default: System.out.println("Invalid category. Please try again.");
                    continue;
            }
            displayProductMenu(categories[category - 1], products, prices);
            System.out.print("Enter the product you want to purchase: ");
            selected = scanner.nextInt();
            if (selected < 1 || selected > products.length) {
                System.out.println("Invalid product selection. Please try again.");
                continue;
            }
            System.out.print("Enter the product quantity: ");
            quantity = scanner.nextInt();
            Product product = new Product(products[selected - 1], quantity, prices[selected - 1]);
            if (!addToCart(user, product)) {
                System.out.println("Failed to add product to the cart. Exiting.");
                return 1;
            }
            System.out.println("Product added to cart: ");
            System.out.println("Name: " + product.productName);
            System.out.println("Price: " + product.productPrice * product.productQuantity);
            System.out.println("Quantity: " + product.productQuantity);
        }
    }
    static void displayCart(User user) {
        System.out.println("Shopping Cart:");
        ShoppingCartItem current = user.cart;
        while (current != null) {
            System.out.println("Name: " + current.product.productName +
                    ", Price: " + current.product.productPrice * current.product.productQuantity +
                    ", Quantity: " + current.product.productQuantity);
            current = current.next;
        }
    }
    static void calculateTotal(User user) {
        float total = 0.0f;
        ShoppingCartItem current = user.cart;
        while (current != null) {
            total += current.product.productPrice * current.product.productQuantity;
            current = current.next;
        }
        if (total > 1000) {
            System.out.println("You have got a discount of 10%");
            total -= (float) (total * 0.10);
        } else {
            System.out.println("No discount available");
        }
        System.out.println("**********************************");
        System.out.println("Adding 5% GST");
        float gstAmount = total * 0.05f;
        total += gstAmount;
        System.out.println("**********************************");
        System.out.println("The total invoice including GST: Rs." + total);
    }
    public static void main(String[] args) {
        char startValue;
        User newUser = new User();
        newUser.cart = null;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter 's' to start or 'q' to quit: ");
        startValue = scanner.next().charAt(0);
        if (startValue == 's' || startValue == 'S') {
            registerUser(newUser);
            boolean loggedIn = loginUser(newUser);
            if (loggedIn) {
                int exitPortal = 0;
                while (exitPortal != 1) {
                    exitPortal = portalDisplay(newUser);
                }
                displayCart(newUser);
                calculateTotal(newUser);
                System.out.println("*****Thank you for shopping*******");
                freeCart(newUser.cart);
            } else {
                System.out.println("Login failed. Exiting the program.");
            }
        } else {
            System.out.println("You have entered the wrong option. Please press 's' to start.");
        }
    }
}
