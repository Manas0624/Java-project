//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// Define the package structure
package com.example.crm;

import java.io.Serializable;
import java.util.*;

// Base class for common attributes
abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;

    public BaseEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name;
    }
}

class Customer extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String contactPerson;
    private String phone;
    private String email;

    public Customer(int id, String name, String contactPerson, String phone, String email) {
        super(id, name);
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString() + ", Contact Person: " + contactPerson + ", Phone: " + phone + ", Email: " + email;
    }
}

class Product extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String description;
    private double price;

    public Product(int id, String name, String description, double price) {
        super(id, name);
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString() + ", Description: " + description + ", Price: $" + String.format("%.2f", price);
    }
}

class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Customer customer;
    private List<OrderItem> orderItems;
    private double totalAmount;

    public Order(int id, Customer customer) {
        super(id, "Order for " + customer.getName());
        this.customer = customer;
        this.orderItems = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        this.totalAmount += orderItem.getQuantity() * orderItem.getProduct().getPrice();
        setName("Order for " + customer.getName() + " (Total: $" + String.format("%.2f", totalAmount) + ")");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", Customer: ").append(customer.getName()).append("\n");
        sb.append("Order Items:\n");
        for (OrderItem item : orderItems) {
            sb.append("- ").append(item).append("\n");
        }
        sb.append("Total Amount: $").append(String.format("%.2f", totalAmount));
        return sb.toString();
    }
}

class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " (Quantity: " + quantity + ", Price per unit: $" + String.format("%.2f", product.getPrice()) + ", Subtotal: $" + String.format("%.2f", quantity * product.getPrice()) + ")";
    }
}

class CustomerService {
    private List<Customer> customers = new ArrayList<>();
    private int nextCustomerId = 1;

    public CustomerService() {
        loadData();
    }
    
    private void loadData() {
        customers = DataStorage.loadCustomers();
        if (!customers.isEmpty()) {
            nextCustomerId = customers.stream()
                    .mapToInt(Customer::getId)
                    .max()
                    .getAsInt() + 1;
        }
    }

    public void addCustomer(Customer customer) {
        customer.setId(nextCustomerId++);
        customers.add(customer);
        System.out.println("Customer added successfully.");
        DataStorage.saveCustomers(customers);
    }

    public Customer getCustomerById(int id) {
        return customers.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public void updateCustomer(int id, String name, String contactPerson, String phone, String email) {
        Customer customer = getCustomerById(id);
        if (customer != null) {
            customer.setName(name);
            customer.setContactPerson(contactPerson);
            customer.setPhone(phone);
            customer.setEmail(email);
            System.out.println("Customer updated successfully.");
            DataStorage.saveCustomers(customers);
        } else {
            System.out.println("Customer with ID " + id + " not found.");
        }
    }

    public void deleteCustomer(int id) {
        Customer customer = getCustomerById(id);
        if (customer != null) {
            customers.remove(customer);
            System.out.println("Customer deleted successfully.");
            DataStorage.saveCustomers(customers);
        } else {
            System.out.println("Customer with ID " + id + " not found.");
        }
    }
}

class ProductService {
    private List<Product> products = new ArrayList<>();
    private int nextProductId = 1;
    
    public ProductService() {
        loadData();
    }
    
    private void loadData() {
        products = DataStorage.loadProducts();
        if (!products.isEmpty()) {
            nextProductId = products.stream()
                    .mapToInt(Product::getId)
                    .max()
                    .getAsInt() + 1;
        }
    }

    public void addProduct(Product product) {
        product.setId(nextProductId++);
        products.add(product);
        System.out.println("Product added successfully.");
        DataStorage.saveProducts(products);
    }

    public Product getProductById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void updateProduct(int id, String name, String description, double price) {
        Product product = getProductById(id);
        if (product != null) {
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            System.out.println("Product updated successfully.");
            DataStorage.saveProducts(products);
        } else {
            System.out.println("Product with ID " + id + " not found.");
        }
    }

    public void deleteProduct(int id) {
        Product product = getProductById(id);
        if (product != null) {
            products.remove(product);
            System.out.println("Product deleted successfully.");
            DataStorage.saveProducts(products);
        } else {
            System.out.println("Product with ID " + id + " not found.");
        }
    }
}

class OrderService {
    private List<Order> orders = new ArrayList<>();
    private int nextOrderId = 1;
    private final CustomerService customerService;
    private final ProductService productService;

    public OrderService(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
        loadData();
    }
    
    private void loadData() {
        orders = DataStorage.loadOrders();
        if (!orders.isEmpty()) {
            nextOrderId = orders.stream()
                    .mapToInt(Order::getId)
                    .max()
                    .getAsInt() + 1;
        }
    }

    public void createOrder(int customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer != null) {
            Order order = new Order(nextOrderId++, customer);
            orders.add(order);
            System.out.println("Order created with ID: " + order.getId());
            DataStorage.saveOrders(orders);
        } else {
            System.out.println("Customer not found.");
        }
    }

    public Order getOrderById(int id) {
        return orders.stream().filter(o -> o.getId() == id).findFirst().orElse(null);
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public void addProductToOrder(int orderId, int productId, int quantity) {
        Order order = getOrderById(orderId);
        Product product = productService.getProductById(productId);

        if (order != null && product != null) {
            order.addOrderItem(new OrderItem(product, quantity));
            System.out.println("Product added to order.");
            DataStorage.saveOrders(orders);
        } else {
            System.out.println("Order or Product not found.");
        }
    }

    public void displayOrderDetails(int orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            System.out.println(order);
        } else {
            System.out.println("Order not found.");
        }
    }
}

class CRMApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerService customerService = new CustomerService();
    private static final ProductService productService = new ProductService();
    private static final OrderService orderService = new OrderService(customerService, productService);

    public static void main(String[] args) {
        // Initialize sample data if storage is empty
        DataStorage.initializeSampleData(customerService, productService, orderService);
        
        runConsoleApp();
    }

    private static void runConsoleApp() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- CRM Management System ---");
            System.out.println("1. Add Customer\n2. View Customer\n3. View All Customers\n4. Update Customer\n5. Delete Customer");
            System.out.println("6. Add Product\n7. View Product\n8. View All Products\n9. Update Product\n10. Delete Product");
            System.out.println("11. Create Order\n12. Add Product to Order\n13. View Order Details\n14. View All Orders\n0. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next();
                continue;
            }
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> viewCustomer();
                case 3 -> viewAllCustomers();
                case 4 -> updateCustomer();
                case 5 -> deleteCustomer();
                case 6 -> addProduct();
                case 7 -> viewProduct();
                case 8 -> viewAllProducts();
                case 9 -> updateProduct();
                case 10 -> deleteProduct();
                case 11 -> createOrder();
                case 12 -> addProductToOrder();
                case 13 -> viewOrderDetails();
                case 14 -> viewAllOrders();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addCustomer() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Contact person: ");
        String contact = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        customerService.addCustomer(new Customer(0, name, contact, phone, email));
    }

    private static void viewCustomer() {
        System.out.print("Enter customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Customer c = customerService.getCustomerById(id);
        System.out.println(c != null ? c : "Not found.");
    }

    private static void viewAllCustomers() {
        customerService.getAllCustomers().forEach(System.out::println);
    }

    private static void updateCustomer() {
        System.out.print("Customer ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        Customer c = customerService.getCustomerById(id);
        if (c == null) {
            System.out.println("Not found."); return;
        }
        System.out.print("New name (" + c.getName() + "): ");
        String name = scanner.nextLine();
        System.out.print("New contact (" + c.getContactPerson() + "): ");
        String contact = scanner.nextLine();
        System.out.print("New phone (" + c.getPhone() + "): ");
        String phone = scanner.nextLine();
        System.out.print("New email (" + c.getEmail() + "): ");
        String email = scanner.nextLine();
        customerService.updateCustomer(id, name.isEmpty() ? c.getName() : name,
                contact.isEmpty() ? c.getContactPerson() : contact,
                phone.isEmpty() ? c.getPhone() : phone,
                email.isEmpty() ? c.getEmail() : email);
    }

    private static void deleteCustomer() {
        System.out.print("Enter customer ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        customerService.deleteCustomer(id);
    }

    private static void addProduct() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Price: ");
        double price = scanner.nextDouble(); scanner.nextLine();
        productService.addProduct(new Product(0, name, desc, price));
    }

    private static void viewProduct() {
        System.out.print("Product ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        Product p = productService.getProductById(id);
        System.out.println(p != null ? p : "Not found.");
    }

    private static void viewAllProducts() {
        productService.getAllProducts().forEach(System.out::println);
    }

    private static void updateProduct() {
        System.out.print("Product ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        Product p = productService.getProductById(id);
        if (p == null) { System.out.println("Not found."); return; }
        System.out.print("New name (" + p.getName() + "): ");
        String name = scanner.nextLine();
        System.out.print("New description (" + p.getDescription() + "): ");
        String desc = scanner.nextLine();
        System.out.print("New price (" + p.getPrice() + "): ");
        double price = scanner.nextDouble(); scanner.nextLine();
        productService.updateProduct(id, name.isEmpty() ? p.getName() : name,
                desc.isEmpty() ? p.getDescription() : desc,
                price);
    }

    private static void deleteProduct() {
        System.out.print("Product ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        productService.deleteProduct(id);
    }

    private static void createOrder() {
        System.out.print("Customer ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        orderService.createOrder(id);
    }

    private static void addProductToOrder() {
        System.out.print("Order ID: ");
        int orderId = scanner.nextInt();
        System.out.print("Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Quantity: ");
        int qty = scanner.nextInt(); scanner.nextLine();
        orderService.addProductToOrder(orderId, productId, qty);
    }

    private static void viewOrderDetails() {
        System.out.print("Order ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        orderService.displayOrderDetails(id);
    }

    private static void viewAllOrders() {
        orderService.getAllOrders().forEach(System.out::println);
    }
}
