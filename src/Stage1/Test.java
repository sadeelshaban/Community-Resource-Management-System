/* package Stage1;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        AidManagement crms = new AidManagement();
        Scanner scanner = new Scanner(System.in);

        crms.registerBeneficiary(new Beneficiary("B001", "Ahmed Family", "Gaza", "0599111222", 5));
        crms.registerUser(new Volunteer("V01", "Mohammed Ali", "Rafah", "0599333444", "South Sector"));
        crms.registerAidItem(new FoodPackage("F01", "Flour Bags", 50, 8, LocalDate.of(2025, 12, 31), false));
        crms.registerAidItem(new ClothingPackage("C01", "Winter Jackets", 100, 7, "Adult-L", true));

        int choice = 0;
        while (choice != 8) {
            System.out.println("\n========= COMMUNITY RESOURCE MANAGEMENT =========");
            System.out.println("1. Register a Beneficiary");
            System.out.println("2. Register a Volunteer or Organization Staff");
            System.out.println("3. Add an Aid Item");
            System.out.println("4. Show Available Aid Items");
            System.out.println("5. Search for Aid");
            System.out.println("6. Assign Aid");
            System.out.println("7. View Distribution Report");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.print("Enter Beneficiary ID: ");
                        String bId = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String bName = scanner.nextLine();
                        System.out.print("Enter Location: ");
                        String bLocation = scanner.nextLine();
                        System.out.print("Enter Phone: ");
                        String bPhone = scanner.nextLine();
                        System.out.print("Enter Family Size: ");
                        int fSize = scanner.nextInt();
                        scanner.nextLine();
                        crms.registerBeneficiary(new Beneficiary(bId, bName, bLocation, bPhone, fSize));
                        break;

                    case 2:
                        System.out.print("Enter Volunteer ID: ");
                        String vId = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String vName = scanner.nextLine();
                        System.out.print("Enter Location: ");
                        String vLocation = scanner.nextLine();
                        System.out.print("Enter Phone: ");
                        String vPhone = scanner.nextLine();
                        System.out.print("Enter Assigned Sector: ");
                        String sector = scanner.nextLine();
                        crms.registerUser(new Volunteer(vId, vName, vLocation, vPhone, sector));
                        break;

                    case 3:
                        System.out.print("Enter Aid Item ID: ");
                        String aId = scanner.nextLine();
                        System.out.print("Enter Name (e.g., Rice Bags): ");
                        String aName = scanner.nextLine();
                        System.out.print("Enter Quantity: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Priority Level (1-10): ");
                        int priority = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
                        LocalDate expiry = LocalDate.parse(scanner.nextLine());
                        crms.registerAidItem(new FoodPackage(aId, aName, qty, priority, expiry, false));
                        break;

                    case 4:
                        crms.showAvailableAid();
                        break;

                    case 5:
                        System.out.print("Enter search keyword: ");
                        String keyword = scanner.nextLine();
                        crms.searchAid(keyword);
                        break;

                    case 6:
                        System.out.print("Enter Beneficiary ID to assign aid to: ");
                        String beneficiaryId = scanner.nextLine();
                        Beneficiary beneficiary = crms.findBeneficiaryById(beneficiaryId);

                        if (beneficiary == null) {
                            System.out.println("Beneficiary not found!");
                            break;
                        }

                        System.out.print("Enter Aid Item ID to assign: ");
                        String aidId = scanner.nextLine();
                        AidItem aidItem = crms.findAidItemById(aidId);

                        if (aidItem == null) {
                            System.out.println("Aid item not found!");
                            break;
                        }
                        
                        crms.assignTo(aidItem, beneficiary);
                        break;

                    case 7:
                        crms.viewDistributionReport();
                        break;

                    case 8:
                        System.out.println("Exiting the system. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number for the menu choice.");
                scanner.nextLine();   
                choice = 0; 
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        scanner.close(); 
    }
}
*/
