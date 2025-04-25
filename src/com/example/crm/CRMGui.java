package com.example.crm;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CRMGui extends JFrame {
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;
    
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Main components
    private JPanel homePanel;
    private JPanel customerPanel;
    private JPanel productPanel;
    private JPanel orderPanel;
    
    // Status bar
    private JLabel statusLabel;
    
    public CRMGui(CustomerService customerService, ProductService productService, OrderService orderService) {
        this.customerService = customerService;
        this.productService = productService;
        this.orderService = orderService;
        
        // Setup the main frame
        setTitle("CRM Management System");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create main panel with card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create status bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusPanel.add(statusLabel);
        
        // Create panels for different sections
        createHomePanel();
        createCustomerPanel();
        createProductPanel();
        createOrderPanel();
        
        // Add panels to main panel
        mainPanel.add(homePanel, "Home");
        mainPanel.add(customerPanel, "Customers");
        mainPanel.add(productPanel, "Products");
        mainPanel.add(orderPanel, "Orders");
        
        // Show home panel by default
        cardLayout.show(mainPanel, "Home");
        
        // Add main panel and status bar to frame
        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        
        // Set status based on data
        updateStatus();
    }
    
    private void updateStatus() {
        int customerCount = customerService.getAllCustomers().size();
        int productCount = productService.getAllProducts().size();
        int orderCount = orderService.getAllOrders().size();
        
        // Get total value of all orders
        double totalOrderValue = 0.0;
        for (Order order : orderService.getAllOrders()) {
            totalOrderValue += order.getTotalAmount();
        }
        
        // Format status with counts and total sales value
        String status = String.format("Status: Ready | Customers: %d | Products: %d | Orders: %d | Total Sales: $%.2f", 
                                     customerCount, productCount, orderCount, totalOrderValue);
        
        statusLabel.setText(status);
    }
    
    private void createHomePanel() {
        homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout(10, 10));
        homePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(100, 150, 200));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel("CRM Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Version label
        JLabel versionLabel = new JLabel("Version 1.0");
        versionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        versionLabel.setForeground(new Color(240, 240, 240));
        titlePanel.add(versionLabel);
        
        homePanel.add(titlePanel, BorderLayout.NORTH);
        
        // Button panel with improved styling
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        
        JButton customerButton = new JButton("Customer Management");
        customerButton.setFont(new Font("Arial", Font.BOLD, 16));
        customerButton.setBackground(new Color(46, 125, 50));
        customerButton.setForeground(Color.BLACK);
        customerButton.setFocusPainted(false);
        customerButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Customers");
            updateStatus();
        });
        
        JButton productButton = new JButton("Product Management");
        productButton.setFont(new Font("Arial", Font.BOLD, 16));
        productButton.setBackground(new Color(21, 101, 192));
        productButton.setForeground(Color.BLACK);
        productButton.setFocusPainted(false);
        productButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Products");
            updateStatus();
        });
        
        JButton orderButton = new JButton("Order Management");
        orderButton.setFont(new Font("Arial", Font.BOLD, 16));
        orderButton.setBackground(new Color(183, 28, 28));
        orderButton.setForeground(Color.BLACK);
        orderButton.setFocusPainted(false);
        orderButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Orders");
            updateStatus();
        });
        
        buttonPanel.add(customerButton);
        buttonPanel.add(productButton);
        buttonPanel.add(orderButton);
        
        homePanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Summary Panel
        JPanel summaryPanel = new JPanel(new BorderLayout(10, 10));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("System Statistics"));
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        
        JLabel customersLabel = new JLabel("Customers: " + customerService.getAllCustomers().size(), JLabel.CENTER);
        customersLabel.setFont(new Font("Arial", Font.BOLD, 14));
        customersLabel.setForeground(Color.BLACK);
        
        JLabel productsLabel = new JLabel("Products: " + productService.getAllProducts().size(), JLabel.CENTER);
        productsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        productsLabel.setForeground(Color.BLACK);
        
        JLabel ordersLabel = new JLabel("Orders: " + orderService.getAllOrders().size(), JLabel.CENTER);
        ordersLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ordersLabel.setForeground(Color.BLACK);
        
        statsPanel.add(customersLabel);
        statsPanel.add(productsLabel);
        statsPanel.add(ordersLabel);
        
        summaryPanel.add(statsPanel, BorderLayout.CENTER);
        homePanel.add(summaryPanel, BorderLayout.SOUTH);
    }
    
    private void createCustomerPanel() {
        customerPanel = new JPanel(new BorderLayout(10, 10));
        customerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(46, 125, 50));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Customer Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Back button
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Home");
            updateStatus();
        });
        titlePanel.add(backButton);
        
        customerPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Name", "Contact Person", "Phone", "Email"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make all cells non-editable
            }
        };
        
        JTable customerTable = new JTable(tableModel);
        customerTable.setRowHeight(25);
        customerTable.setGridColor(new Color(240, 240, 240));
        customerTable.setSelectionBackground(new Color(184, 207, 229));
        
        JScrollPane scrollPane = new JScrollPane(customerTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton addButton = new JButton("Add Customer");
        addButton.setBackground(new Color(46, 125, 50));
        addButton.setForeground(Color.BLACK);
        
        JButton viewButton = new JButton("View Customer");
        viewButton.setBackground(new Color(33, 150, 243));
        viewButton.setForeground(Color.BLACK);
        
        JButton updateButton = new JButton("Update Customer");
        updateButton.setBackground(new Color(255, 152, 0));
        updateButton.setForeground(Color.BLACK);
        
        JButton deleteButton = new JButton("Delete Customer");
        deleteButton.setBackground(new Color(211, 47, 47));
        deleteButton.setForeground(Color.BLACK);
        
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBackground(new Color(158, 158, 158));
        refreshButton.setForeground(Color.BLACK);
        
        addButton.addActionListener(e -> showAddCustomerDialog());
        
        viewButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) customerTable.getValueAt(selectedRow, 0);
                Customer customer = customerService.getCustomerById(id);
                if (customer != null) {
                    JOptionPane.showMessageDialog(this, 
                            formatCustomerDetails(customer), 
                            "Customer Details", 
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        updateButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) customerTable.getValueAt(selectedRow, 0);
                Customer customer = customerService.getCustomerById(id);
                if (customer != null) {
                    showUpdateCustomerDialog(customer);
                    refreshCustomerTable(tableModel);
                    updateStatus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        deleteButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) customerTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, 
                        "Are you sure you want to delete this customer?", 
                        "Confirm Delete", 
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    customerService.deleteCustomer(id);
                    refreshCustomerTable(tableModel);
                    updateStatus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        refreshButton.addActionListener(e -> {
            refreshCustomerTable(tableModel);
            updateStatus();
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        customerPanel.add(tablePanel, BorderLayout.CENTER);
        customerPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Initial load of customers
        refreshCustomerTable(tableModel);
    }
    
    private String formatCustomerDetails(Customer customer) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><div style='font-family: Arial; padding: 10px;'>");
        sb.append("<h2 style='color: #2E7D32;'>Customer Details</h2>");
        sb.append("<div style='margin: 10px 0;'>");
        sb.append("<p><b>ID:</b> ").append(customer.getId()).append("</p>");
        sb.append("<p><b>Company Name:</b> ").append(customer.getName()).append("</p>");
        sb.append("<p><b>Contact Person:</b> ").append(customer.getContactPerson()).append("</p>");
        sb.append("<p><b>Phone:</b> ").append(customer.getPhone()).append("</p>");
        sb.append("<p><b>Email:</b> ").append(customer.getEmail()).append("</p>");
        sb.append("</div></div></html>");
        return sb.toString();
    }
    
    private void refreshCustomerTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                customer.getId(),
                customer.getName(),
                customer.getContactPerson(),
                customer.getPhone(),
                customer.getEmail()
            });
        }
    }
    
    private void showAddCustomerDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel headerLabel = new JLabel("Add New Customer");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(new Color(46, 125, 50));
        
        JTextField nameField = new JTextField(20);
        JTextField contactField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        
        panel.add(headerLabel);
        panel.add(new JLabel("Company Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Contact Person:"));
        panel.add(contactField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("* Company name is required"));
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String contact = contactField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            
            if (!name.isEmpty()) {
                customerService.addCustomer(new Customer(0, name, contact, phone, email));
                refreshCustomerTable((DefaultTableModel) ((JTable) ((JScrollPane) customerPanel.getComponent(1)).getViewport().getView()).getModel());
                updateStatus();
                JOptionPane.showMessageDialog(this, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Company name cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showUpdateCustomerDialog(Customer customer) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel headerLabel = new JLabel("Update Customer");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(new Color(255, 152, 0));
        
        JTextField nameField = new JTextField(customer.getName(), 20);
        JTextField contactField = new JTextField(customer.getContactPerson(), 20);
        JTextField phoneField = new JTextField(customer.getPhone(), 20);
        JTextField emailField = new JTextField(customer.getEmail(), 20);
        
        panel.add(headerLabel);
        panel.add(new JLabel("Company Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Contact Person:"));
        panel.add(contactField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("* Company name is required"));
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Update Customer", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String contact = contactField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            
            if (!name.isEmpty()) {
                customerService.updateCustomer(customer.getId(), name, contact, phone, email);
                refreshCustomerTable((DefaultTableModel) ((JTable) ((JScrollPane) customerPanel.getComponent(1)).getViewport().getView()).getModel());
                updateStatus();
                JOptionPane.showMessageDialog(this, "Customer updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Company name cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void createProductPanel() {
        productPanel = new JPanel(new BorderLayout(10, 10));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(21, 101, 192));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Product Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Back button
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Home");
            updateStatus();
        });
        titlePanel.add(backButton);
        
        productPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Name", "Description", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make all cells non-editable
            }
        };
        
        JTable productTable = new JTable(tableModel);
        productTable.setRowHeight(25);
        productTable.setGridColor(new Color(240, 240, 240));
        productTable.setSelectionBackground(new Color(184, 207, 229));
        
        JScrollPane scrollPane = new JScrollPane(productTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton addButton = new JButton("Add Product");
        addButton.setBackground(new Color(46, 125, 50));
        addButton.setForeground(Color.BLACK);
        
        JButton viewButton = new JButton("View Product");
        viewButton.setBackground(new Color(33, 150, 243));
        viewButton.setForeground(Color.BLACK);
        
        JButton updateButton = new JButton("Update Product");
        updateButton.setBackground(new Color(255, 152, 0));
        updateButton.setForeground(Color.BLACK);
        
        JButton deleteButton = new JButton("Delete Product");
        deleteButton.setBackground(new Color(211, 47, 47));
        deleteButton.setForeground(Color.BLACK);
        
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBackground(new Color(158, 158, 158));
        refreshButton.setForeground(Color.BLACK);
        
        addButton.addActionListener(e -> showAddProductDialog());
        
        viewButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) productTable.getValueAt(selectedRow, 0);
                Product product = productService.getProductById(id);
                if (product != null) {
                    JOptionPane.showMessageDialog(this, 
                            formatProductDetails(product), 
                            "Product Details", 
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        updateButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) productTable.getValueAt(selectedRow, 0);
                Product product = productService.getProductById(id);
                if (product != null) {
                    showUpdateProductDialog(product);
                    refreshProductTable(tableModel);
                    updateStatus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        deleteButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) productTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, 
                        "Are you sure you want to delete this product?", 
                        "Confirm Delete", 
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    productService.deleteProduct(id);
                    refreshProductTable(tableModel);
                    updateStatus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        refreshButton.addActionListener(e -> {
            refreshProductTable(tableModel);
            updateStatus();
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        productPanel.add(tablePanel, BorderLayout.CENTER);
        productPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Initial load of products
        refreshProductTable(tableModel);
    }
    
    private String formatProductDetails(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><div style='font-family: Arial; padding: 10px;'>");
        sb.append("<h2 style='color: #1565C0;'>Product Details</h2>");
        sb.append("<div style='margin: 10px 0;'>");
        sb.append("<p><b>ID:</b> ").append(product.getId()).append("</p>");
        sb.append("<p><b>Name:</b> ").append(product.getName()).append("</p>");
        sb.append("<p><b>Description:</b> ").append(product.getDescription()).append("</p>");
        sb.append("<p><b>Price:</b> $").append(String.format("%.2f", product.getPrice())).append("</p>");
        sb.append("</div></div></html>");
        return sb.toString();
    }
    
    private void refreshProductTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                product.getId(),
                product.getName(),
                product.getDescription(),
                String.format("$%.2f", product.getPrice())
            });
        }
    }
    
    private void showAddProductDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel headerLabel = new JLabel("Add New Product");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(new Color(21, 101, 192));
        
        JTextField nameField = new JTextField(20);
        JTextField descField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        
        panel.add(headerLabel);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("* Name and price are required"));
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            String priceText = priceField.getText().trim();
            
            if (!name.isEmpty() && !priceText.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceText);
                    productService.addProduct(new Product(0, name, desc, price));
                    refreshProductTable((DefaultTableModel) ((JTable) ((JScrollPane) productPanel.getComponent(1)).getViewport().getView()).getModel());
                    updateStatus();
                    JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Price must be a valid number", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Name and price cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showUpdateProductDialog(Product product) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel headerLabel = new JLabel("Update Product");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(new Color(255, 152, 0));
        
        JTextField nameField = new JTextField(product.getName(), 20);
        JTextField descField = new JTextField(product.getDescription(), 20);
        JTextField priceField = new JTextField(String.valueOf(product.getPrice()), 20);
        
        panel.add(headerLabel);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("* Name and price are required"));
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Update Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            String priceText = priceField.getText().trim();
            
            if (!name.isEmpty() && !priceText.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceText);
                    productService.updateProduct(product.getId(), name, desc, price);
                    refreshProductTable((DefaultTableModel) ((JTable) ((JScrollPane) productPanel.getComponent(1)).getViewport().getView()).getModel());
                    updateStatus();
                    JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Price must be a valid number", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Name and price cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void createOrderPanel() {
        orderPanel = new JPanel(new BorderLayout(10, 10));
        orderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(183, 28, 28));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Order Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Back button
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Home");
            updateStatus();
        });
        titlePanel.add(backButton);
        
        orderPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Order Name", "Customer", "Total Amount"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make all cells non-editable
            }
        };
        
        JTable orderTable = new JTable(tableModel);
        orderTable.setRowHeight(25);
        orderTable.setGridColor(new Color(240, 240, 240));
        orderTable.setSelectionBackground(new Color(184, 207, 229));
        
        JScrollPane scrollPane = new JScrollPane(orderTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton createOrderButton = new JButton("Create Order");
        createOrderButton.setBackground(new Color(46, 125, 50));
        createOrderButton.setForeground(Color.BLACK);
        
        JButton addProductToOrderButton = new JButton("Add Product to Order");
        addProductToOrderButton.setBackground(new Color(33, 150, 243));
        addProductToOrderButton.setForeground(Color.BLACK);
        
        JButton viewOrderButton = new JButton("View Order Details");
        viewOrderButton.setBackground(new Color(255, 152, 0));
        viewOrderButton.setForeground(Color.BLACK);
        
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBackground(new Color(158, 158, 158));
        refreshButton.setForeground(Color.BLACK);
        
        createOrderButton.addActionListener(e -> showCreateOrderDialog());
        
        addProductToOrderButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) orderTable.getValueAt(selectedRow, 0);
                Order order = orderService.getOrderById(id);
                if (order != null) {
                    showAddProductToOrderDialog(order);
                    refreshOrderTable(tableModel);
                    updateStatus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an order", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        viewOrderButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) orderTable.getValueAt(selectedRow, 0);
                Order order = orderService.getOrderById(id);
                if (order != null) {
                    // Create a formatted display for order details
                    JTextArea textArea = new JTextArea(formatOrderDetails(order));
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    JScrollPane scrollPane2 = new JScrollPane(textArea);
                    scrollPane2.setPreferredSize(new Dimension(500, 300));
                    
                    JOptionPane.showMessageDialog(this, scrollPane2, "Order Details", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an order", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        refreshButton.addActionListener(e -> {
            refreshOrderTable(tableModel);
            updateStatus();
        });
        
        buttonPanel.add(createOrderButton);
        buttonPanel.add(addProductToOrderButton);
        buttonPanel.add(viewOrderButton);
        buttonPanel.add(refreshButton);
        
        orderPanel.add(tablePanel, BorderLayout.CENTER);
        orderPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Initial load of orders
        refreshOrderTable(tableModel);
    }
    
    private String formatOrderDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(order.getId()).append("\n");
        sb.append("Name: ").append(order.getName()).append("\n");
        sb.append("Customer: ").append(order.getCustomer().getName()).append("\n\n");
        
        sb.append("Order Items:\n");
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%-20s %-8s %-12s %-12s\n", "Product", "Qty", "Unit Price", "Subtotal"));
        sb.append("--------------------------------------------------\n");
        
        for (OrderItem item : order.getOrderItems()) {
            double unitPrice = item.getProduct().getPrice();
            double subtotal = item.getQuantity() * unitPrice;
            sb.append(String.format("%-20s %-8d $%-11.2f $%-12.2f\n", 
                    item.getProduct().getName(),
                    item.getQuantity(),
                    unitPrice,
                    subtotal));
        }
        
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%-42s $%.2f\n", "Total:", order.getTotalAmount()));
        
        return sb.toString();
    }
    
    private void refreshOrderTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            tableModel.addRow(new Object[]{
                order.getId(),
                order.getName(),
                order.getCustomer().getName(),
                String.format("$%.2f", order.getTotalAmount())
            });
        }
    }
    
    private void showCreateOrderDialog() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You need to add customers first", "No Customers", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel headerLabel = new JLabel("Create New Order");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(new Color(183, 28, 28));
        
        // Create a combobox with customer names
        DefaultComboBoxModel<Customer> model = new DefaultComboBoxModel<>();
        for (Customer customer : customers) {
            model.addElement(customer);
        }
        
        JComboBox<Customer> customerBox = new JComboBox<>(model);
        customerBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Customer) {
                    setText(((Customer) value).getName());
                }
                return this;
            }
        });
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(headerLabel);
        
        JPanel contentPanel = new JPanel(new BorderLayout(5, 10));
        contentPanel.add(new JLabel("Select Customer:"), BorderLayout.NORTH);
        contentPanel.add(customerBox, BorderLayout.CENTER);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Create Order", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Customer selectedCustomer = (Customer) customerBox.getSelectedItem();
            if (selectedCustomer != null) {
                orderService.createOrder(selectedCustomer.getId());
                refreshOrderTable((DefaultTableModel) ((JTable) ((JScrollPane) orderPanel.getComponent(1)).getViewport().getView()).getModel());
                updateStatus();
                JOptionPane.showMessageDialog(this, "Order created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void showAddProductToOrderDialog(Order order) {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You need to add products first", "No Products", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel headerLabel = new JLabel("Add Product to Order #" + order.getId());
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(new Color(33, 150, 243));
        
        // Create a combobox with product names
        DefaultComboBoxModel<Product> model = new DefaultComboBoxModel<>();
        for (Product product : products) {
            model.addElement(product);
        }
        
        JComboBox<Product> productBox = new JComboBox<>(model);
        productBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Product) {
                    setText(((Product) value).getName() + " - $" + String.format("%.2f", ((Product) value).getPrice()));
                }
                return this;
            }
        });
        
        // Create spinner for quantity
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
        JSpinner quantitySpinner = new JSpinner(spinnerModel);
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(headerLabel);
        
        JPanel formPanel = new JPanel(new GridLayout(0, 1, 5, 10));
        formPanel.add(new JLabel("Select Product:"));
        formPanel.add(productBox);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantitySpinner);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Product to Order", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Product selectedProduct = (Product) productBox.getSelectedItem();
            int quantity = (int) quantitySpinner.getValue();
            
            if (selectedProduct != null && quantity > 0) {
                orderService.addProductToOrder(order.getId(), selectedProduct.getId(), quantity);
                refreshOrderTable((DefaultTableModel) ((JTable) ((JScrollPane) orderPanel.getComponent(1)).getViewport().getView()).getModel());
                updateStatus();
                JOptionPane.showMessageDialog(this, "Product added to order successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
} 