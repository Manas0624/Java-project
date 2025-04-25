package com.example.crm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static final String DATA_DIR = "data";
    private static final String CUSTOMERS_FILE = DATA_DIR + "/customers.dat";
    private static final String PRODUCTS_FILE = DATA_DIR + "/products.dat";
    private static final String ORDERS_FILE = DATA_DIR + "/orders.dat";
    
    static {
        // Create data directory if it doesn't exist
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }
    
    // Create sample data if no data exists
    public static void initializeSampleData(CustomerService customerService, ProductService productService, OrderService orderService) {
        if (customerService.getAllCustomers().isEmpty()) {
            System.out.println("Initializing sample customers...");
            // Add 10 sample customers
            customerService.addCustomer(new Customer(0, "Acme Corporation", "John Doe", "555-123-4567", "john@acme.com"));
            customerService.addCustomer(new Customer(0, "Globex Industries", "Jane Smith", "555-987-6543", "jane@globex.com"));
            customerService.addCustomer(new Customer(0, "Stark Enterprises", "Tony Stark", "555-111-2222", "tony@stark.com"));
            customerService.addCustomer(new Customer(0, "Wayne Enterprises", "Bruce Wayne", "555-333-4444", "bruce@wayne.com"));
            customerService.addCustomer(new Customer(0, "LexCorp", "Lex Luthor", "555-555-5555", "lex@lexcorp.com"));
            customerService.addCustomer(new Customer(0, "Umbrella Corporation", "Albert Wesker", "555-666-7777", "wesker@umbrella.com"));
            customerService.addCustomer(new Customer(0, "Oscorp Industries", "Norman Osborn", "555-888-9999", "norman@oscorp.com"));
            customerService.addCustomer(new Customer(0, "Cyberdyne Systems", "Miles Dyson", "555-000-1111", "miles@cyberdyne.com"));
            customerService.addCustomer(new Customer(0, "Weyland-Yutani Corp", "Peter Weyland", "555-222-3333", "peter@weyland.com"));
            customerService.addCustomer(new Customer(0, "Tyrell Corporation", "Eldon Tyrell", "555-444-6666", "tyrell@tyrell.com"));
        }
        
        if (productService.getAllProducts().isEmpty()) {
            System.out.println("Initializing sample products...");
            // Add 10 sample products
            productService.addProduct(new Product(0, "Office Chair", "Ergonomic office chair with lumbar support", 149.99));
            productService.addProduct(new Product(0, "Desk Lamp", "LED desk lamp with adjustable brightness", 39.99));
            productService.addProduct(new Product(0, "Laptop", "15-inch laptop with 16GB RAM and 512GB SSD", 899.99));
            productService.addProduct(new Product(0, "Monitor", "27-inch 4K monitor with HDR", 349.99));
            productService.addProduct(new Product(0, "Keyboard", "Mechanical keyboard with RGB lighting", 79.99));
            productService.addProduct(new Product(0, "Mouse", "Wireless ergonomic mouse", 29.99));
            productService.addProduct(new Product(0, "Headphones", "Noise-cancelling headphones", 129.99));
            productService.addProduct(new Product(0, "Desk", "Height-adjustable standing desk", 299.99));
            productService.addProduct(new Product(0, "Webcam", "1080p HD webcam with microphone", 59.99));
            productService.addProduct(new Product(0, "Docking Station", "USB-C docking station with multiple ports", 89.99));
        }
        
        if (orderService.getAllOrders().isEmpty() && !customerService.getAllCustomers().isEmpty() && !productService.getAllProducts().isEmpty()) {
            System.out.println("Initializing sample orders...");
            List<Customer> customers = customerService.getAllCustomers();
            List<Product> products = productService.getAllProducts();
            
            // Create 10 sample orders for different customers with various products
            // Order 1
            orderService.createOrder(customers.get(0).getId());
            Order order1 = orderService.getAllOrders().get(0);
            orderService.addProductToOrder(order1.getId(), products.get(0).getId(), 2); // 2 chairs
            orderService.addProductToOrder(order1.getId(), products.get(1).getId(), 3); // 3 lamps
            
            // Order 2
            orderService.createOrder(customers.get(1).getId());
            Order order2 = orderService.getAllOrders().get(1);
            orderService.addProductToOrder(order2.getId(), products.get(2).getId(), 1); // 1 laptop
            orderService.addProductToOrder(order2.getId(), products.get(4).getId(), 1); // 1 keyboard
            orderService.addProductToOrder(order2.getId(), products.get(5).getId(), 1); // 1 mouse
            
            // Order 3
            orderService.createOrder(customers.get(2).getId());
            Order order3 = orderService.getAllOrders().get(2);
            orderService.addProductToOrder(order3.getId(), products.get(7).getId(), 1); // 1 desk
            orderService.addProductToOrder(order3.getId(), products.get(0).getId(), 1); // 1 chair
            
            // Order 4
            orderService.createOrder(customers.get(3).getId());
            Order order4 = orderService.getAllOrders().get(3);
            orderService.addProductToOrder(order4.getId(), products.get(2).getId(), 5); // 5 laptops
            orderService.addProductToOrder(order4.getId(), products.get(3).getId(), 5); // 5 monitors
            
            // Order 5
            orderService.createOrder(customers.get(4).getId());
            Order order5 = orderService.getAllOrders().get(4);
            orderService.addProductToOrder(order5.getId(), products.get(6).getId(), 10); // 10 headphones
            
            // Order 6
            orderService.createOrder(customers.get(5).getId());
            Order order6 = orderService.getAllOrders().get(5);
            orderService.addProductToOrder(order6.getId(), products.get(8).getId(), 15); // 15 webcams
            orderService.addProductToOrder(order6.getId(), products.get(9).getId(), 15); // 15 docking stations
            
            // Order 7
            orderService.createOrder(customers.get(6).getId());
            Order order7 = orderService.getAllOrders().get(6);
            orderService.addProductToOrder(order7.getId(), products.get(0).getId(), 20); // 20 chairs
            orderService.addProductToOrder(order7.getId(), products.get(7).getId(), 20); // 20 desks
            
            // Order 8
            orderService.createOrder(customers.get(7).getId());
            Order order8 = orderService.getAllOrders().get(7);
            orderService.addProductToOrder(order8.getId(), products.get(2).getId(), 3); // 3 laptops
            orderService.addProductToOrder(order8.getId(), products.get(3).getId(), 3); // 3 monitors
            orderService.addProductToOrder(order8.getId(), products.get(4).getId(), 3); // 3 keyboards
            orderService.addProductToOrder(order8.getId(), products.get(5).getId(), 3); // 3 mice
            
            // Order 9
            orderService.createOrder(customers.get(8).getId());
            Order order9 = orderService.getAllOrders().get(8);
            orderService.addProductToOrder(order9.getId(), products.get(9).getId(), 30); // 30 docking stations
            
            // Order 10
            orderService.createOrder(customers.get(9).getId());
            Order order10 = orderService.getAllOrders().get(9);
            for (int i = 0; i < products.size(); i++) {
                orderService.addProductToOrder(order10.getId(), products.get(i).getId(), 1); // 1 of each product
            }
        }
    }
    
    // Save customers
    public static void saveCustomers(List<Customer> customers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CUSTOMERS_FILE))) {
            oos.writeObject(customers);
            System.out.println("Customers saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }
    
    // Load customers
    @SuppressWarnings("unchecked")
    public static List<Customer> loadCustomers() {
        File file = new File(CUSTOMERS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CUSTOMERS_FILE))) {
            return (List<Customer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading customers: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Save products
    public static void saveProducts(List<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCTS_FILE))) {
            oos.writeObject(products);
            System.out.println("Products saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }
    
    // Load products
    @SuppressWarnings("unchecked")
    public static List<Product> loadProducts() {
        File file = new File(PRODUCTS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRODUCTS_FILE))) {
            return (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading products: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Save orders
    public static void saveOrders(List<Order> orders) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(orders);
            System.out.println("Orders saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving orders: " + e.getMessage());
        }
    }
    
    // Load orders
    @SuppressWarnings("unchecked")
    public static List<Order> loadOrders() {
        File file = new File(ORDERS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
            return (List<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading orders: " + e.getMessage());
            return new ArrayList<>();
        }
    }
} 