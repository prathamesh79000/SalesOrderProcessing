package practise.CitiusTech;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Customer1 {
    int customerId;
    String customerPassword;
    double credit;
    String email;
    private static final List<Customer1> customerList = new ArrayList<Customer1>();

    public Customer1(int customerId, String customerPassword, double credit, String email) {
        this.customerId = customerId;
        this.customerPassword = customerPassword;
        this.credit = credit;
        this.email = email;
    }

    public static void addCustomer(Customer1 customer) {
        customerList.add(customer);
    }

    public static Customer1 getCustomerByEmail(String email) {
        for (Customer1 customer : customerList) {
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return customer;
            }
        }
        return null;
    }

    public static Customer1 getCustomerById(int customerId) {
        for (Customer1 customer : customerList) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    public static int generateCustomerId() {
        return customerList.size() + 101;
    }

    public double getCredit() {
        return credit;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public boolean validatePassword(String password) {
        return this.customerPassword.equals(password);
    }

    public void updateCredit(double totalCost) {
        if (credit >= totalCost) {
            credit -= totalCost;
        }
    }
}

class Product1 {
    int productNumber;
    double productPrice;
    int productStock;
    String orderName;
    private static final List<Product1> productList = new ArrayList<Product1>();

    public Product1(int productNumber, String orderName, double productPrice, int productStock) {
        this.productNumber = productNumber;
        this.orderName = orderName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        productList.add(this);
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public String getOrderName() {
        return orderName;
    }

    public void updateStock(int quantity) {
        if (productStock >= quantity) {
            productStock -= quantity;
        } else {
            throw new IllegalArgumentException("Insufficient Quantity");
        }
    }

    public void displayDetails() {
        System.out.println("Product ID: " + productNumber);
        System.out.println("Product Name: " + orderName);
        System.out.println("Product Price: " + productPrice);
        System.out.println("Product quantity available: " + productStock);
    }

    public static void displayAllProducts() {
        System.out.println("Available Products:");
        for (Product1 product : productList) {
            System.out.println("------------------------------------");
            product.displayDetails();
            System.out.println("------------------------------------");
        }
    }
}

class Order1 {
    private static int nextOrderNumber = 100; // Starting order number, can be any initial value
    private final int orderNumber;
    Date date;
    double totalCost;
    private Invoice1 invoice;

    public Order1() {
        this.orderNumber = nextOrderNumber++;
        this.date = new Date();
    }

    public Invoice1 getInvoice() {
        return invoice;
    }

    public void placeOrder(Customer1 cust, Product1 prd, int quantity) {
        totalCost = quantity * prd.getProductPrice();
        if (cust.getCredit() >= totalCost && prd.getProductStock() >= quantity) {
            prd.updateStock(quantity);
            cust.updateCredit(totalCost);
            System.out.println("Order placed successfully!");
            invoice = new Invoice1(orderNumber, new Date(), cust, prd, quantity, totalCost);
        } else {
            System.out.println("Order Unsuccessful");
        }
    }
}

class Invoice1 {
    private int orderNumber;
    private Date date;
    private Customer1 cust;
    private Product1 prd;
    private int quantity;
    private double totalAmount;
    private static final List<Invoice1> order1List = new ArrayList<Invoice1>();

    public Invoice1() {
    }

    public Invoice1(int orderNumber, Date date, Customer1 customer, Product1 product, int quantity, double totalAmount) {
        this.orderNumber = orderNumber;
        this.date = date;
        this.cust = customer;
        this.prd = product;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        order1List.add(this);
    }

    public void generateInvoice() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("------------------------------------");
        System.out.println("Invoice Details:");
        System.out.println("Order Number: " + orderNumber);
        System.out.println("Date: " + sdf.format(date));
        System.out.println("Customer ID: " + cust.getCustomerId());
        System.out.println("Customer Email: " + cust.getEmail());
        System.out.println("Product Number: " + prd.getProductNumber());
        System.out.println("Product Name: " + prd.getOrderName());
        System.out.println("Product Price: " + prd.getProductPrice());
        System.out.println("Quantity Ordered: " + quantity);
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("------------------------------------");
    }

    public static void displayAllInvoices() {
        System.out.println("Below are all the invoices of all successful orders: ");
        for (Invoice1 invoice : order1List) {
            System.out.println("------------------------------------");
            invoice.generateInvoice();
            System.out.println("------------------------------------");
        }
    }
}

class Citius {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Adding some initial products
        Product1 prd1 = new Product1(1, "Apple", 200, 50);
        Product1 prd2 = new Product1(2, "Mango", 300, 150);

        int userChoice;
        Order1 order = null; // Initialize order variable outside the loop

        while (true) {
            System.out.println("------------------------------------");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.println("Enter your choice: ");
            userChoice = sc.nextInt();
            sc.nextLine();

            switch (userChoice) {
                case 1:
                    System.out.println("Signup");
                    System.out.print("Enter your email address: ");
                    String newEmail = sc.nextLine();

                    if (Customer1.getCustomerByEmail(newEmail) != null) {
                        System.out.println("Email already registered.");
                        break;
                    }

                    System.out.print("Enter your password: ");
                    String newPassword = sc.nextLine();

                    int newCustomerId = Customer1.generateCustomerId();
                    Customer1 newCustomer = new Customer1(newCustomerId, newPassword, 2000, newEmail);
                    Customer1.addCustomer(newCustomer);

                    System.out.println("Signup successful! Your Customer ID is: " + newCustomerId);
                    break;

                case 2:
                    System.out.println("Login");
                    System.out.print("Enter your customer ID: ");
                    int customerId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter your password: ");
                    String password = sc.nextLine();

                    Customer1 customer = Customer1.getCustomerById(customerId);

                    if (customer != null && customer.validatePassword(password)) {
                        System.out.println("Login successful!");
                        int innerChoice;
                        do {
                            System.out.println("------------------------------------");
                            System.out.println("1. See available products");
                            System.out.println("2. Buy a product");
                            System.out.println("3. Print invoice");
                            System.out.println("4. Credit balance");
                            System.out.println("5. Previous invoice's");
                            System.out.println("0. Logout");
                            System.out.println("Enter your choice: ");
                            innerChoice = sc.nextInt();
                            sc.nextLine(); // Consume newline left-over

                            switch (innerChoice) {
                                case 1:
                                    Product1.displayAllProducts();
                                    break;
                                case 2:
                                    System.out.println("Select a product to order:");
                                    System.out.println("1. " + prd1.getOrderName());
                                    System.out.println("2. " + prd2.getOrderName());
                                    System.out.println("Enter your choice: ");
                                    int productChoice = sc.nextInt();
                                    sc.nextLine(); // Consume newline left-over
                                    Product1 selectedProduct;
                                    switch (productChoice) {
                                        case 1:
                                            selectedProduct = prd1;
                                            break;
                                        case 2:
                                            selectedProduct = prd2;
                                            break;
                                        default:
                                            System.out.println("Invalid product choice.");
                                            continue;
                                    }
                                    System.out.println("Enter quantity: ");
                                    int quantity = sc.nextInt();
                                    sc.nextLine(); // Consume newline left-over
                                    if (order == null) {
                                        order = new Order1(); // Create a new order instance
                                    }
                                    order.placeOrder(customer, selectedProduct, quantity);
                                    break;

                                case 3:
                                    if (order != null && order.getInvoice() != null) {
                                        order.getInvoice().generateInvoice();
                                    } else {
                                        System.out.println("No orders placed yet.");
                                    }
                                    break;

                                case 4:
                                    System.out.println("Your available balance is: "+ customer.getCredit());
                                    break;


                                case 5:
                                    Invoice1.displayAllInvoices();
                                    break;

                                case 0:
                                    System.out.println("Logging out");
                                    order = null; // Reset order on logout
                                    break;

                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        } while (innerChoice != 0);
                    } else {
                        System.out.println("Invalid customer ID or password.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}