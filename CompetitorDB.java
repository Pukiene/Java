package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.*;

public class CompetitorDB {

    private Connection connection;

    /**
     * The {@code CompetitorDB} class manages the database operations for competitors.
     * It handles tasks such as adding, updating, deleting competitors, fetching competitor data,
     * and retrieving statistical information from the database.
     * 
     * @author YourName
     * @version 1.0
     * @since 2024
     */
    public CompetitorDB() {
    	
        try {
            connection = DriverManager.getConnection(
            		//"jdbc:mysql://mi-linux.wlv.ac.uk/db2334768","2334768","agurkas"
                "jdbc:mysql://localhost:3306/competitors", // Correct database name
                "root", // Your MySQL username
                ""      // Your MySQL password
            );
           // System.out.println("Database connection successful!");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database. Please check your credentials and database server.");
            e.printStackTrace();
           
        }
    }
    /**
     * Retrieves the database connection.
     * 
     * @return the {@link Connection} object
     */
    public Connection getConnection() {
        return connection;
    }
    /**
     * Checks if the database connection is valid.
     * 
     * @return {@code true} if the connection is valid, {@code false} otherwise
     */
    public boolean isConnectionValid() {
        return connection != null;
    }

    /**
     * Adds a competitor to the database.
     * 
     * @param competitors the {@link MRCompetitor} object containing competitor details
     * @param name the {@link Name} object representing the competitor's name
     */
    public void addCompetitorDB(MRCompetitor competitors, Name name) {
        if (!isConnectionValid()) {
            System.err.println("Cannot add competitor: Database connection is null.");
            return;
        }

        String sql_statement = "INSERT INTO mrcompetitors (competitorID, firstName, middleName, lastName, CompetitionLevel, gender, BelbeltColort, score1, score2, score3, score4, score5, overallScore) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql_statement)) {
            statement.setInt(1, competitors.getCompetitorID());
            statement.setString(2, name.getFirstName());
            statement.setString(3, name.getMiddleName());
            statement.setString(4, name.getLastName());
            statement.setString(5, competitors.getCompetitionLevel());
            statement.setString(6, competitors.getgender());
            statement.setString(7, competitors.getBelbeltColor());
            statement.setInt(8, competitors.getScore1());
            statement.setInt(9, competitors.getScore2());
            statement.setInt(10, competitors.getScore3());
            statement.setInt(11, competitors.getScore4());
            statement.setInt(12, competitors.getScore5());
 

            double overallScore = competitors.getOverallScore();
            System.out.println("Computed Overall Score for Add: " + overallScore); // Debug
            statement.setDouble(13, overallScore);
            statement.executeUpdate();
            
            
            System.out.println("Competitor added to the database.");
        } catch (SQLException e) {
            System.err.println("Error adding competitor: " + e.getMessage());
        }
    }


    /**
     * Updates an existing competitor in the database.
     * 
     * @param competitors the {@link MRCompetitor} object containing updated competitor details
     */
    public void updateCompetitor(MRCompetitor competitors) {
        if (!isConnectionValid()) {
            System.err.println("Cannot update competitor: Database connection is null.");
            return;
        }

        String sql_statement = "UPDATE competitors.mrcompetitors " +
                               "SET firstName=?, middleName=?, lastName=?, CompetitionLevel=?, gender=?, BelbeltColort=?, score1=?, score2=?, score3=?, score4=?, score5=?, overallScore=? " +
                               "WHERE competitorID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql_statement)) {
            statement.setString(1, competitors.getName().getFirstName());
            statement.setString(2, competitors.getName().getMiddleName());
            statement.setString(3, competitors.getName().getLastName());
            statement.setString(4, competitors.getCompetitionLevel());
            statement.setString(5, competitors.getgender());
            statement.setString(6, competitors.getBelbeltColor());
            statement.setInt(7, competitors.getScore1());
            statement.setInt(8, competitors.getScore2());
            statement.setInt(9, competitors.getScore3());
            statement.setInt(10, competitors.getScore4());
            statement.setInt(11, competitors.getScore5());
           
            double overallScore = competitors.getOverallScore();
            System.out.println("Computed Overall Score for Update: " + overallScore); // Debug

            statement.setDouble(12, competitors.getOverallScore());
            statement.setInt(13, competitors.getCompetitorID());


          
            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Competitor with ID " + competitors.getCompetitorID() + " updated successfully.");
            } else {
                System.out.println("No competitor found with ID " + competitors.getCompetitorID());
            }
        } catch (SQLException e) {
            System.err.println("Error while updating competitor: " + e.getMessage());
        }
    }

    /**
     * Deletes a competitor from the database by ID.
     * 
     * @param competitorID the unique ID of the competitor to delete
     */
    public void deleteCompetitor(int competitorID) {
        if (!isConnectionValid()) {
            System.err.println("Cannot delete competitor: Database connection is null.");
            return;
        }

        String sql_statement = "DELETE FROM competitors.mrcompetitors WHERE competitorID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql_statement)) {
            statement.setInt(1, competitorID);
            statement.executeUpdate();

            System.out.println("The competitor with ID " + competitorID + " was deleted from the database.");
        } catch (SQLException e) {
            System.err.println("Error while deleting competitor: " + e.getMessage());
        }
    }
    /**
     * Retrieves all competitors from the database.
     * 
     * @return a {@link List} of {@link MRCompetitor} objects representing all competitors
     */
    public List<MRCompetitor> getAllCompetitors() {
        String sql = "SELECT * FROM mrcompetitors";
        List<MRCompetitor> competitors = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int competitorID = rs.getInt("competitorID");
                String firstName = rs.getString("firstName");
                String middleName = rs.getString("middleName");
                String lastName = rs.getString("lastName");
                String competitionLevel = rs.getString("CompetitionLevel");
                String gender = rs.getString("gender");
                String beltColor = rs.getString("BelbeltColort");

                int[] scores = new int[5];
                scores[0] = rs.getInt("score1");
                scores[1] = rs.getInt("score2");
                scores[2] = rs.getInt("score3");
                scores[3] = rs.getInt("score4");
                scores[4] = rs.getInt("score5");

                Name name = new Name(firstName, middleName, lastName);
                MRCompetitor competitor = new MRCompetitor(competitorID, name, competitionLevel, gender, beltColor, scores);

                competitors.add(competitor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return competitors;
    }

    /**
     * Retrieves a competitor by their ID from the database.
     * 
     * @param competitorID the unique ID of the competitor
     * @return the {@link MRCompetitor} object if found, or {@code null} if not found
     */
    public MRCompetitor getCompetitorsById(int competitorID) {
        String sql_statement = "SELECT * FROM mrcompetitors WHERE competitorID=?";
        try (PreparedStatement statement = connection.prepareStatement(sql_statement)) {
            statement.setInt(1, competitorID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                // Retrieve fields from the result set
                String firstName = rs.getString("firstName");
                String middleName = rs.getString("middleName");
                String lastName = rs.getString("lastName");
                String competitionLevel = rs.getString("CompetitionLevel");
                String gender = rs.getString("gender");
                String beltColor = rs.getString("BelbeltColort");
                int score1 = rs.getInt("score1");
                int score2 = rs.getInt("score2");
                int score3 = rs.getInt("score3");
                int score4 = rs.getInt("score4");
                int score5 = rs.getInt("score5");

                // Create Name object
                Name name = new Name(firstName, middleName, lastName);

                // Create Scores array
                int[] scores = {score1, score2, score3, score4, score5};

                // Construct and return the MRCompetitor object
                return new MRCompetitor(competitorID, name, competitionLevel, gender, beltColor, scores);
            } else {
                System.out.println("No competitor found with ID: " + competitorID);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching competitor by ID: " + e.getMessage());
        }

        return null; // Return null if no competitor found or in case of an error
    }
    
    /**
     * Retrieves the top performer from the database.
     * 
     * @return the {@link MRCompetitor} object representing the top performer
     */
    public MRCompetitor getTopPerformer() {
        String sql_statement = "SELECT * FROM competitors.mrcompetitors ORDER BY overallScore DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql_statement)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int competitorID = rs.getInt("competitorID");
                String firstName = rs.getString("firstName");
                String middleName = rs.getString("middleName");
                String lastName = rs.getString("lastName");
                String competitionLevel = rs.getString("CompetitionLevel");
                String gender = rs.getString("gender");
                String beltColor = rs.getString("BelbeltColort");

                int[] scores = new int[5];
                scores[0] = rs.getInt("score1");
                scores[1] = rs.getInt("score2");
                scores[2] = rs.getInt("score3");
                scores[3] = rs.getInt("score4");
                scores[4] = rs.getInt("score5");

                double overallScore = rs.getDouble("overallScore");

                Name name = new Name(firstName, middleName, lastName);
                
                
                MRCompetitor competitor = new MRCompetitor(competitorID, name, competitionLevel, gender, beltColor, scores);
                return competitor;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving top performer: " + e.getMessage());
        }
        return null;
    }

        /**
     * Calculates the frequency of scores among all competitors.
     * 
     * @return a {@link Map} where the key is the score and the value is its frequency
     */
    public Map<Integer, Integer> getScoreFrequency() {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        String sql_statement = "SELECT score1, score2, score3, score4, score5 FROM competitors.mrcompetitors";
        try (PreparedStatement statement = connection.prepareStatement(sql_statement)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int[] scores = new int[5];
                scores[0] = rs.getInt("score1");
                scores[1] = rs.getInt("score2");
                scores[2] = rs.getInt("score3");
                scores[3] = rs.getInt("score4");
                scores[4] = rs.getInt("score5");

                for (int score : scores) {
                    frequencyMap.put(score, frequencyMap.getOrDefault(score, 0) + 1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error calculating score frequency: " + e.getMessage());
        }
        return frequencyMap;
    }
    /**
     * Retrieves the total number of competitors in the database.
     * 
     * @return the total number of competitors, or 0 if an error occurs
     */
    public int getTotalNumberOfCompetitors() {
        String sql = "SELECT COUNT(*) AS total FROM mrcompetitors";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving total number of competitors: " + e.getMessage());
        }
        return 0; // Return 0 if an error occurs
    }
    /**
     * Retrieves a table of competitor details from the database.
     * 
     * @return a {@link List} of strings representing rows of competitor data
     */
    public List<String> getCompetitorTable() {
        List<String> tableRows = new ArrayList<>();
        String sql = "SELECT competitorID, firstName, lastName, CompetitionLevel, score1, score2, score3, score4, score5, overallScore FROM mrcompetitors";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int competitorID = rs.getInt("competitorID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String level = rs.getString("CompetitionLevel");
                int[] scores = {
                    rs.getInt("score1"),
                    rs.getInt("score2"),
                    rs.getInt("score3"),
                    rs.getInt("score4"),
                    rs.getInt("score5")
                };
                double overallScore = rs.getDouble("overallScore");

                String row = String.format(
                    "%-10d %-15s %-10s %-10s %-15s %-5.2f",
                    competitorID,
                    firstName + " " + lastName,
                    level,
                    Arrays.toString(scores),
                    overallScore
                );
                tableRows.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching competitor table: " + e.getMessage());
        }
        return tableRows;
    }
    /**
     * Retrieves details of a specific competitor by ID.
     * 
     * @param competitorID the unique ID of the competitor
     * @return a string containing competitor details, or an error message if not found
     */
    public String getCompetitorDetails(int competitorID) {
        String sql = "SELECT * FROM mrcompetitors WHERE competitorID=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, competitorID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return "Competitor ID: " + rs.getInt("competitorID") +
                       ", Name: " + rs.getString("firstName") + " " + rs.getString("lastName") +
                       ", Level: " + rs.getString("CompetitionLevel") +
                       ", Overall Score: " + rs.getDouble("overallScore");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching competitor details: " + e.getMessage());
        }
        return "No competitor found with ID: " + competitorID;
    }
  
    /**
     * Retrieves details of a max number of the competitorID from database.
     * 
     * @return a string containing last number from database, or an error message if not found
     */
    public int getMaxCompetitorID() {
        if (!isConnectionValid()) {
            System.err.println("Cannot fetch maximum competitor ID: Database connection is null.");
            return -1; // Return a sentinel value indicating an error
        }

        int maxCompetitorID = 0; // Default value if no ID exists in the database
        String sqlStatement = "SELECT MAX(competitorID) AS max_id FROM mrcompetitors";

        try (PreparedStatement statement = connection.prepareStatement(sqlStatement);
             ResultSet resultSet = statement.executeQuery()) {
            
            if (resultSet.next()) {
                maxCompetitorID = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching maximum competitor ID: " + e.getMessage());
        }

        return maxCompetitorID;
    }
    
    
    /**
     * stores details of a max number of the competitorID from database.
     * 
     * @return a string containing last number from database.
     */
    public static int fetchMaxCompetitorID() {
        CompetitorDB competitorDB = new CompetitorDB(); // Create an instance of the class
        return competitorDB.getMaxCompetitorID(); // Call the non-static method
    }
} 





    
    
