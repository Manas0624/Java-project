package com.example.crm;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService(customerService, productService);
        
        // Initialize sample data if storage is empty
        DataStorage.initializeSampleData(customerService, productService, orderService);
        
        // Check if we should run in console mode
        if (args.length > 0 && args[0].equals("--console")) {
            System.out.println("Starting CRM in console mode...");
            CRMApp.main(args); // Run the console app
        } else {
            System.out.println("Starting CRM with graphical interface...");
            runGuiApp(customerService, productService, orderService);
        }
    }
    
    private static void runGuiApp(CustomerService customerService, ProductService productService, OrderService orderService) {
        try {
            // Set system look and feel
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set system look and feel: " + e.getMessage());
        }
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            CRMGui gui = new CRMGui(customerService, productService, orderService);
            gui.setVisible(true);
        });
    }
} 