import java.util.*;

public class Customer {
    private static final Scanner sc = new Scanner(System.in);
    private static final HashMap<Integer, Integer> cart = new HashMap<>();
    private static double discount = 0.0;

    public static void customerMenu() {
        while (true) {
            System.out.println("\n====== Customer Menu ======");
            System.out.println("1. Show Menu");
            System.out.println("2. Place Order");
            System.out.println("3. Pay Bill");
            System.out.println("4. Add Coupon Code");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    Menu.showMenu();
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    payBill();
                    break;
                case 4:
                    addCoupon();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void placeOrder() {
        while (true) {
            Menu.showMenu();
            System.out.print("Enter Item ID to order: ");
            int id = sc.nextInt();

            if (Menu.containsItem(id)) {
                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();

                cart.put(id, cart.getOrDefault(id, 0) + qty);
                System.out.println(qty + " x " + Menu.getItem(id).name + " added to cart.");
            } else {
                System.out.println("Invalid Item ID!");
            }

            System.out.print("Add more items? (yes/no): ");
            String more = sc.next();
            if (more.equalsIgnoreCase("no")) break;
        }
    }

    private static void payBill() {
        if (cart.isEmpty()) {
            System.out.println("No items in cart!");
            return;
        }

        double total = 0;
        System.out.println("\n====== Your Order ======");
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            int id = entry.getKey();
            int qty = entry.getValue();
            MenuItem item = Menu.getItem(id);

            double cost = item.price * qty;
            System.out.println(item.name + " x " + qty + " = Rs." + cost);
            total += cost;
        }

        if (discount > 0) {
            System.out.println("Discount Applied: " + (discount * 100) + "%");
            total = total - (total * discount);
        }

        System.out.println("Total Bill: Rs." + total);
        System.out.println("Payment Successful! Thank you.");
        cart.clear();
        discount = 0.0;
    }

    private static void addCoupon() {
        System.out.print("Enter Coupon Code: ");
        String code = sc.next();

        if (code.equalsIgnoreCase("DISC10")) {
            discount = 0.10;
            System.out.println("10% Discount Applied!");
        } else if (code.equalsIgnoreCase("DISC20")) {
            discount = 0.20;
            System.out.println("20% Discount Applied!");
        } else {
            System.out.println("Invalid Coupon Code!");
        }
    }
}
