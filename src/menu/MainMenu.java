package menu;

import controller.PhoneController;
import controller.NotFoundProductException;
import phone.*;

import java.util.Scanner;

public class MainMenu implements MenuConstants {
    public void displayMainMenu() {
        PhoneController controller = new PhoneController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("----------PHONE MANAGEMENT PROGRAM-----------");
            System.out.println("Select function by number(to continue):");
            System.out.println(ADD_PHONE + ". Add");
            System.out.println(DELETE_PHONE + ". Delete");
            System.out.println(VIEW_PHONE_LIST + ". View phone list");
            System.out.println(SEARCH_PHONE + ". Search");
            System.out.println(EXIT + ". Exit");
            System.out.print("Select function: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case ADD_PHONE:
                        addPhone(scanner, controller);
                        break;
                    case DELETE_PHONE:
                        deletePhone(scanner, controller);
                        break;
                    case VIEW_PHONE_LIST:
                        controller.viewPhones();
                        break;
                    case SEARCH_PHONE:
                        searchPhones(scanner, controller);
                        break;
                    case EXIT:
                        controller.savePhones();
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NotFoundProductException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void addPhone(Scanner scanner, PhoneController controller) {
        System.out.println("Add New Phone");
        System.out.print("Enter phone name: ");
        String name = scanner.nextLine();
        if (!isValidName(name)) {
            System.out.println("Invalid name. It should not be empty.");
            return;
        }
        System.out.print("Enter price: ");
        String priceStr = scanner.nextLine();
        if (!isValidPrice(priceStr)) {
            System.out.println("Invalid price. It should be a valid number greater than 0.");
            return;
        }
        double price = Double.parseDouble(priceStr);
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        if (!isValidQuantity(quantity)) {
            System.out.println("Invalid quantity. It should be a positive number.");
            return;
        }
        scanner.nextLine();
        System.out.print("Enter manufacturer: ");
        String manufacturer = scanner.nextLine();
        if (!isValidManufacturer(manufacturer)) {
            System.out.println("Invalid manufacturer. It should not be empty.");
            return;
        }

        System.out.print("Is it an authentic phone? (yes/no): ");
        String type = scanner.nextLine();

        Phone phone;
        if (type.equalsIgnoreCase(AUTHENTIC_PHONE)) {
            System.out.print("Enter warranty period (days): ");
            int warrantyPeriod = scanner.nextInt();
            if (!isValidWarrantyPeriod(warrantyPeriod)) {
                System.out.println("Invalid warranty period. It should be a positive number not exceeding 730 days.");
                return;
            }
            scanner.nextLine();
            System.out.print("Enter warranty scope (inland/world): ");
            String warrantyScope = scanner.nextLine();
            if (!isValidWarrantyScope(warrantyScope)) {
                System.out.println("Invalid warranty scope. It should be 'inland' or 'world'.");
                return;
            }
            phone = new AuthenticPhone(controller.getNextId(), name, price, quantity, manufacturer, warrantyPeriod, warrantyScope);
        } else {
            System.out.print("Enter import country: ");
            String importCountry = scanner.nextLine();
            if (!isValidImportCountry(importCountry)) {
                System.out.println("Invalid import country. It should not be Vietnam.");
                return;
            }
            System.out.print("Enter usage status (repaired/likenew): ");
            String usageStatus = scanner.nextLine();
            if (!isValidUsageStatus(usageStatus)) {
                System.out.println("Invalid usage status. It should be 'repaired' or 'likenew'.");
                return;
            }
            phone = new ImportPhone(controller.getNextId(), name, price, quantity, manufacturer, importCountry, usageStatus);
        }
        controller.addPhone(phone);
        controller.savePhones();
        System.out.println("Phone added successfully.");
    }


    private static void deletePhone(Scanner scanner, PhoneController controller) throws NotFoundProductException {
        System.out.print("Enter the ID of the phone to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Are you sure you want to delete this phone? (y/n): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase(CONFIRM_YES)) {
            controller.deletePhone(id);
            System.out.println("Phone deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void searchPhones(Scanner scanner, PhoneController controller) {
        System.out.print("Enter phone ID or name to search: ");
        String keyword = scanner.nextLine();
        controller.searchPhones(keyword);
    }


    private static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    private static boolean isValidPrice(String priceStr) {
        try {
            double price = Double.parseDouble(priceStr);
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

    private static boolean isValidManufacturer(String manufacturer) {
        return manufacturer != null && !manufacturer.trim().isEmpty();
    }

    private static boolean isValidWarrantyPeriod(int warrantyPeriod) {
        return warrantyPeriod > 0 && warrantyPeriod <= 730;
    }

    private static boolean isValidWarrantyScope(String warrantyScope) {
        return "inland".equalsIgnoreCase(warrantyScope) || "world".equalsIgnoreCase(warrantyScope);
    }

    private static boolean isValidImportCountry(String importCountry) {
        return importCountry != null && !importCountry.trim().equalsIgnoreCase("Vietnam");
    }

    private static boolean isValidUsageStatus(String usageStatus) {
        return "repaired".equalsIgnoreCase(usageStatus) || "likenew".equalsIgnoreCase(usageStatus);
    }
}
