package Main;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
/**
 * The {@code Main} class provides the main menu and methods for managing competitors
 * in the Competitor Management System. It supports adding, updating, deleting,
 * and viewing competitors, as well as generating summaries and tables.
 * This class interacts with the {@link Manager} and {@link MRCompetitor} classes.
 * 
 * @version 1.0
 * @since 2024
 */
public class main {
	/**
     * Main entry point for the application. Displays a menu-driven interface
     * for managing competitors.
     * 
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();
        CompetitorDB competitorDB = new CompetitorDB();

        boolean running = true;

        while (running) {
            // Menu options
            System.out.println("\nWelcome to Competitor Management System");
            System.out.println("Please select an option:");
            System.out.println("1) Add Competitor");
            System.out.println("2) Update Competitor");
            System.out.println("3) Get Competitor by ID");
            System.out.println("4) Display All Competitors");
            System.out.println("5) Get Full Details of a Competitor");
            System.out.println("6) Get Short Details of a Competitor");
            System.out.println("7) DisplayTopPerformer / Winner");
            System.out.println("8) Delete Competitor");
            System.out.println("9) Generate Competitor Table");
            System.out.println("10) Display Statistical Summary");
            System.out.println("11) Exit");
            
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
               
                    addCompetitor(scanner, manager);
                    break;

                case 2:
                    // Update Competitor
                    updateCompetitor(scanner, manager);
                    break;

                case 3:
                    // Get Competitor by ID
                    getCompetitorById(scanner, manager);
                    break;

                case 4:
                    // Display All Competitors
                    displayAllCompetitors(manager);
                    break;
                    
                case 5:
                    getFullDetails(scanner, manager);
                    break;
                    
                case 6:
                    getShortDetails(scanner, manager);
                    break;
   
                case 7:
                    displayTopPerformer(manager);
                    break;
     
                case 8:
                    // Delete Competitor
                    deleteCompetitor(scanner, manager);
                    break;    
                case 9:
                    generateCompetitorTable(manager);
                    break;
                case 10:
                	 manager.displayStatisticalSummary();;
                    break;

                case 11:
                    // Exit the application
                    running = false;
                    System.out.println("Exiting... Goodbye, have a good day!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
    /**
     * Retrieves full details of a competitor by ID and displays them.
     * 
     * @param scanner the {@link Scanner} object for user input
     * @param manager the {@link Manager} object managing competitors
     */
    private static void getFullDetails(Scanner scanner, Manager manager) {
        System.out.println("Enter Competitor ID:");
        int competitorID = scanner.nextInt();

        MRCompetitor competitor = manager.getCompetitorsById(competitorID);

        if (competitor != null) {
            System.out.println("Full Details:");
            System.out.println(competitor.getFullDetails());
        } else {
            System.out.println("Competitor not found.");
        }
    }

    /**
     * Retrieves short details of a competitor by ID and displays them.
     * 
     * @param scanner the {@link Scanner} object for user input
     * @param manager the {@link Manager} object managing competitors
     */
    private static void getShortDetails(Scanner scanner, Manager manager) {
        System.out.println("Enter Competitor ID:");
        int competitorID = scanner.nextInt();

        MRCompetitor competitor = manager.getCompetitorsById(competitorID);

        if (competitor != null) {
            System.out.println("Short Details:");
            System.out.println(competitor.getShortDetails());
        } else {
            System.out.println("Competitor not found.");
        }
    }

    /**
     * Validates and ensures a non-empty string input from the user.
     * 
     * @param scanner Scanner object for user input.
     * @param prompt Prompt message for the user.
     * @return A validated non-empty string.
     */
    private static String validateNonEmptyString(Scanner scanner, String prompt) {
        String input = "";
        while (input.trim().isEmpty()) {
            System.out.println(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
        return input;
    }
    /**
     * Adds a new competitor to the system.
     * 
     * @param scanner the {@link Scanner} object for user input
     * @param manager the {@link Manager} object managing competitors
     */
    private static void addCompetitor(Scanner scanner, Manager manager) {
        System.out.println("Adding New Competitor...");

        int competitorID = CompetitorDB.fetchMaxCompetitorID() + 1;
        System.out.println("Generated Competitor ID: " + competitorID);

        String firstName = validateNonEmptyString(scanner, "Enter First Name (cannot be empty):");
        System.out.println("Enter Middle Name:");
        String middleName = scanner.nextLine();
        String lastName = validateNonEmptyString(scanner, "Enter Last Name (cannot be empty):");
        String competitionLevel = validateCompetitionLevel(scanner);
        String gender = validateGender(scanner);
        String beltColor = selectBeltColor(scanner);
        int[] scores = validateScores(scanner);

        // Create Name and MRCompetitor objects
        Name name = new Name(firstName, middleName, lastName);
        MRCompetitor competitor = new MRCompetitor(competitorID, name, competitionLevel, gender, beltColor, scores);

        // Add to manager
        manager.addCompetitor(competitor);
        System.out.println("Competitor added successfully!");
    }

    /**
     * Validates the competition level input (1-5).
     * 
     * @param scanner the {@link Scanner} object for user input
     * @return a string representing the valid competition level
     */
    private static String validateCompetitionLevel(Scanner scanner) {
        int level = 0;
        while (true) {
            System.out.println("Enter Competition Level (1 to 5):");
            if (scanner.hasNextInt()) {
                level = scanner.nextInt();
                scanner.nextLine();
                if (level >= 0 && level <= 5) {
                    return Integer.toString(level);
                }
            }
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            scanner.nextLine(); 
        }
    }

    /**
     * Validates the gender input (Male/Female).
     * 
     * @param scanner the {@link Scanner} object for user input
     * @return a string representing the valid gender
     */
    private static String validateGender(Scanner scanner) {
        while (true) {
            System.out.println("Enter Gender (Male or Female):");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Male") || input.equalsIgnoreCase("Female")) {
                return input;
            }
            System.out.println("Invalid input. Please enter 'Male' or 'Female'.");
        }
    }

    /**
     * Allows the user to select a belt color from a predefined list.
     * 
     * @param scanner the {@link Scanner} object for user input
     * @return a string representing the selected belt color
     */
    private static String selectBeltColor(Scanner scanner) {
        String[] beltColors = {"White", "Yellow", "Blue", "Purple", "Brown", "Black"};
        System.out.println("Choose Belt Color:");
        for (int i = 0; i < beltColors.length; i++) {
            System.out.println((i + 1) + ". " + beltColors[i]);
        }

        while (true) {
            System.out.print("Enter the number corresponding to competitor belt color: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= beltColors.length) {
                    return beltColors[choice - 1];
                }
            }
            System.out.println("Invalid input! Please enter a valid number.");
            scanner.nextLine(); 
        }
    }

    /**
     * Validates and collects 5 score inputs from the user.
     * 
     * @param scanner the {@link Scanner} object for user input
     * @return an array of 5 integers representing the scores
     */
    private static int[] validateScores(Scanner scanner) {
        int[] scores = new int[5];
        System.out.println("Enter 5 Scores (Numbers Only):");

        for (int i = 0; i < scores.length; i++) {
            while (true) {
                System.out.print("Score " + (i + 1) + ": ");
                
                
                if (scanner.hasNextInt()) {
                    int input = scanner.nextInt();
                    if (input > 0) { // Validate that the input is positive
                        scores[i] = input;
                        break;
                    } else {
                        System.out.println("Score must be a positive number. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a valid positive number.");
                    scanner.next(); // Clear the invalid input
                }
            }
        }
                

        scanner.nextLine(); 
        return scores;
    }


    /**
     * Updates the details of an existing competitor.
     * 
     * @param scanner the {@link Scanner} object for user input
     * @param manager the {@link Manager} object managing competitors
     */
    private static void updateCompetitor(Scanner scanner, Manager manager) {
        System.out.println("Enter Competitor ID to Update:");
        int competitorID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        MRCompetitor competitor = manager.getCompetitorsById(competitorID);
        if (competitor == null) {
            System.out.println("Competitor not found. Please check the ID.");
            return;
        }

        boolean updating = true;

        while (updating) {
            // Display current values with numbered options
            System.out.println("\nCurrent Competitor Details:");
            System.out.println("1. First Name: " + competitor.getName().getFirstName());
            System.out.println("2. Middle Name: " + competitor.getName().getMiddleName());
            System.out.println("3. Last Name: " + competitor.getName().getLastName());
            System.out.println("4. Competition Level: " + competitor.getCompetitionLevel());
            System.out.println("5. Gender: " + competitor.getgender());
            System.out.println("6. Belt Color: " + competitor.getBelbeltColor());
            System.out.println("7. Scores: " + Arrays.toString(competitor.getScores()));
            System.out.println("8. Finish Updating");

            // Prompt user to select a field
            System.out.println("\nSelect a field to update (1-8):");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Update First Name
                    String firstName = validateNonEmptyString(scanner, "Enter Updated First Name (cannot be empty):");
                    competitor.getName().setFirstName(firstName);
                    System.out.println("First Name updated successfully!");
                    break;

                case 2:
                    // Update Middle Name
                    System.out.println("Enter Updated Middle Name:");
                    String middleName = scanner.nextLine();
                    competitor.getName().setMiddleName(middleName);
                    System.out.println("Middle Name updated successfully!");
                    break;

                case 3:
                    // Update Last Name
                    String lastName = validateNonEmptyString(scanner, "Enter Updated Last Name (cannot be empty):");
                    competitor.getName().setLastName(lastName);
                    System.out.println("Last Name updated successfully!");
                    break;

                case 4:
                    // Update Competition Level
                    String competitionLevel = validateCompetitionLevel(scanner);
                    competitor.setCompetitionLevel(competitionLevel);
                    System.out.println("Competition Level updated successfully!");
                    break;

                case 5:
                    // Update Gender
                    String gender = validateGender(scanner);
                    competitor.setgender(gender);
                    System.out.println("Gender updated successfully!");
                    break;

                case 6:
                    // Update Belt Color
                    String beltColor = selectBeltColor(scanner);
                    competitor.setBelbeltColort(beltColor);
                    System.out.println("Belt Color updated successfully!");
                    break;

                case 7:
                    // Update Scores
                    int[] scores = validateScores(scanner);
                    competitor.setScores(scores);
                    System.out.println("Scores updated successfully!");
                    break;

                case 8:
                    // Finish Updating
                    updating = false;
                    System.out.println("Update process completed successfully!");
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option (1-8).");
                    break;
            }
        }

        
        manager.updateCompetitor(competitor);
        System.out.println("All changes have been saved.");
    }



    /**
     * Retrieves a competitor by ID and allows the user to view either short or full details.
     * 
     * @param scanner the {@link Scanner} object for user input
     * @param manager the {@link Manager} object managing competitors
     */
    private static void getCompetitorById(Scanner scanner, Manager manager) {
        System.out.println("Enter Competitor ID:");
         int competitorID = scanner.nextInt();

        MRCompetitor competitor = manager.getCompetitorsById(competitorID);


        if (competitor != null) {
            System.out.println("Do you want short or full details? (S for Short Details/F for Full Details):");
            char detailChoice = scanner.next().toUpperCase().charAt(0);

            if (detailChoice == 'S') {
                System.out.println(competitor.getShortDetails());
            } else if (detailChoice == 'F') {
                System.out.println(competitor.getFullDetails());
            } else {
                System.out.println("Invalid choice. Showing full details by default.");
                System.out.println(competitor.getFullDetails());
            }
        } else {
            System.out.println("Competitor not found.");
        }
    }

    /**
     * Displays all competitors in the system.
     * 
     * @param manager the {@link Manager} object managing competitors
     */
    private static void displayAllCompetitors(Manager manager) {
        System.out.println("Fetching all competitors...");

        List<MRCompetitor> allCompetitors = manager.getAllCompetitors();

        if (allCompetitors.isEmpty()) {
            System.out.println("No competitors found.");
        } else {
            for (MRCompetitor competitor : allCompetitors) {
                System.out.println(competitor.getFullDetails());
            }
        }
    }
    /**
     * Displays the top performer among the competitors.
     * 
     * @param manager the {@link Manager} object managing competitors
     */
    private static void displayTopPerformer(Manager manager) {
        
        MRCompetitor topPerformer = manager.getTopPerformer();
        
        
        if (topPerformer != null) {
            System.out.println("\n--- Top Performer ---");
            System.out.println(topPerformer.getFullDetails());
        } else {
            System.out.println("\nNo competitors found in the database.");
        }
    }
    /**
     * Deletes a competitor by ID after user confirmation.
     * 
     * @param scanner the {@link Scanner} object for user input
     * @param manager the {@link Manager} object managing competitors
     */
    private static void deleteCompetitor(Scanner scanner, Manager manager) {
        int competitorID;
        MRCompetitor competitor;

        
        while (true) {
           
            while (true) {
                System.out.println("Enter the Competitor ID to delete:");
                if (scanner.hasNextInt()) {
                    competitorID = scanner.nextInt();
                    scanner.nextLine(); 
                    break;
                } else {
                    System.out.println("Invalid input! Please enter a valid Competitor ID.");
                    scanner.nextLine(); 
                }
            }

            // Fetch competitor and validate if it exists
            competitor = manager.getCompetitorsById(competitorID);
            if (competitor != null) {
                break; 
            } else {
                System.out.println("Competitor with ID " + competitorID + " not found. Please try again.");
            }
        }

        
        System.out.println("\nCompetitor Details:");
        System.out.println(competitor.getFullDetails());

        
        while (true) {
            System.out.println("Are you sure you want to delete Competitor ID " + competitorID + "? (Y/N):");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Y")) {
                manager.deleteCompetitor(competitorID); 
                System.out.println("Competitor ID " + competitorID + " has been successfully deleted.");
                break;
            } else if (input.equals("N")) {
                System.out.println("Deletion cancelled.");
                break;
            } else {
                System.out.println("Invalid input! Please enter 'Y' to confirm or 'N' to cancel.");
            }
        }
    }

    /**
     * Generates Competitor table
     * 
     * @param manager the {@link Manager} object managing competitors
     */
    private static void generateCompetitorTable(Manager manager) {
        System.out.println("\n--- Comprehensive Competitor Table ---");
        
        List<MRCompetitor> allCompetitors = manager.getAllCompetitors();

        if (allCompetitors.isEmpty()) {
            System.out.println("No competitors found in the database.");
        } else {
            System.out.printf("%-10s %-20s %-15s %-25s %-10s\n", "ID", "Name", "Level", "Scores", "Overall");
            System.out.println("----------------------------------------------------------------------------");
            for (MRCompetitor competitor : allCompetitors) {
                String scores = Arrays.toString(competitor.getScores()).replaceAll("[\\[\\]]", "");
                System.out.printf("%-10d %-20s %-15s %-25s %-10.2f\n",
                        competitor.getCompetitorID(),
                        competitor.getName().getFullName(),
                        competitor.getCompetitionLevel(),
                        scores,
                        competitor.getOverallScore());
            }
        }
    } 
    
}
    

