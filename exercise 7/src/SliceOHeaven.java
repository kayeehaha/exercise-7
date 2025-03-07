import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class SliceOHeaven {
    private static final long BLACKLISTED_NUMBER = 12345678901234L;
    
    public static void processCardPayment(long cardNumber, String expiryDate, int cvv) {
        Scanner scanner = new Scanner(System.in);
        while (String.valueOf(cardNumber).length() != 14 || cardNumber == BLACKLISTED_NUMBER) {
            System.out.println("Invalid or blacklisted card. Please enter a valid 14-digit card number:");
            cardNumber = scanner.nextLong();
        }
        System.out.println("Card accepted");
    }
    
    public static void specialOfTheDay(String pizzaOfTheDay, String sideOfTheDay, String specialPrice) {
        System.out.println("Today's Special:\nPizza: " + pizzaOfTheDay + "\nSide: " + sideOfTheDay + "\nPrice: " + specialPrice);
    }
    
    public static void takeOrder() {
        Scanner scanner = new Scanner(System.in);
        int ingChoice1, ingChoice2, ingChoice3;
        String ing1 = "", ing2 = "", ing3 = "";
        
        while (true) {
            System.out.println("Please pick any three of the following ingredients:\n" +
                               "1. Mushroom\n2. Paprika\n3. Sun-dried tomatoes\n4. Chicken\n5. Pineapple\n" +
                               "Enter any three choices (1, 2, 3,…) separated by spaces:");
            ingChoice1 = scanner.nextInt();
            ingChoice2 = scanner.nextInt();
            ingChoice3 = scanner.nextInt();
            
            if (isValidChoice(ingChoice1, 1, 5) && isValidChoice(ingChoice2, 1, 5) && isValidChoice(ingChoice3, 1, 5)) {
                ing1 = getIngredient(ingChoice1);
                ing2 = getIngredient(ingChoice2);
                ing3 = getIngredient(ingChoice3);
                break;
            } else {
                System.out.println("Invalid choice(s). Please pick only from the given list:");
            }
        }

        System.out.println("What size should your pizza be?\n1. Large\n2. Medium\n3. Small\nEnter only one choice (1, 2, or 3):");
        String pizzaSize = getValidChoice(scanner, 1, 3, new String[]{"Large", "Medium", "Small"});

        System.out.println("Do you want extra cheese (Y/N):");
        String extraCheese = scanner.next();
        
        System.out.println("Following are the side dish options:\n1. Calzone\n2. Garlic bread\n3. Chicken puff\n4. Muffin\n5. Nothing for me\nPick one (1, 2, 3,…):");
        String sideDish = getValidChoice(scanner, 1, 5, new String[]{"Calzone", "Garlic bread", "Chicken puff", "Muffin", "Nothing"});
        
        System.out.println("Choose from one of the drinks below. We recommend Coca Cola:\n1. Coca Cola\n2. Cold coffee\n3. Cocoa Drink\n4. No drinks for me\nEnter your choice:");
        String drinks = getValidChoice(scanner, 1, 4, new String[]{"Coca Cola", "Cold coffee", "Cocoa Drink", "No drinks"});

        System.out.println("Would you like the chance to pay only half for your order? (Y/N):");
        String wantDiscount = scanner.next();
        
        if (wantDiscount.equalsIgnoreCase("Y")) {
            isItYourBirthday();
        } else {
            makeCardPayment();
        }
    }
    
    public static void isItYourBirthday() {
        Scanner scanner = new Scanner(System.in);
        LocalDate birthdate;
        
        while (true) {
            System.out.println("Enter your birthdate (YYYY-MM-DD):");
            String input = scanner.next();
            try {
                birthdate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int age = Period.between(birthdate, LocalDate.now()).getYears();
                
                if (age >= 5 && age <= 120) {
                    break;
                } else {
                    System.out.println("Invalid date. You are either too young or too old to order. Please enter a valid date:");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter a valid date (YYYY-MM-DD):");
            }
        }
        System.out.println("Congratulations! You pay only half the price for your order.");
    }
    
    public static void makeCardPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        long cardNumber = scanner.nextLong();
        
        String expiryDate;
        while (true) {
            System.out.println("Enter card expiry date (MM/YY):");
            expiryDate = scanner.next();
            if (isFutureExpiryDate(expiryDate)) {
                break;
            } else {
                System.out.println("Invalid expiry date. Please enter a valid future date:");
            }
        }
        
        System.out.println("Enter card CVV (3 digits):");
        int cvv = scanner.nextInt();
        processCardPayment(cardNumber, expiryDate, cvv);
    }
    
    private static boolean isValidChoice(int choice, int min, int max) {
        return choice >= min && choice <= max;
    }
    
    private static String getValidChoice(Scanner scanner, int min, int max, String[] options) {
        int choice;
        while (true) {
            try {
                choice = scanner.nextInt();
                if (isValidChoice(choice, min, max)) {
                    return options[choice - 1];
                } else {
                    System.out.println("Invalid choice. Please enter a valid number:");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number:");
                scanner.next(); // Clear the invalid input
            }
        }
    }
    
    private static String getIngredient(int choice) {
        switch (choice) {
            case 1: return "Mushroom";
            case 2: return "Paprika";
            case 3: return "Sun-dried tomatoes";
            case 4: return "Chicken";
            case 5: return "Pineapple";
            default: return "";
        }
    }
    
    private static boolean isFutureExpiryDate(String expiryDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            LocalDate expiry = LocalDate.parse("01/" + expiryDate, formatter);
            return expiry.isAfter(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Slice-O-Heaven Order Summary";
    }
    
    public static void main(String[] args) {
        takeOrder();
        specialOfTheDay("Margherita", "Garlic Bread", "$9.99");
    }
}