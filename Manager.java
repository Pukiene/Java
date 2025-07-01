package Main;

import java.sql.*;
import java.util.*;

/**
 * The {@code Manager} class handles the operations for managing competitors in the system.
 * It interacts with the {@link CompetitorDB} to add, update, delete, and retrieve competitors.
 * Additionally, it provides functionalities to fetch top performers and display statistical summaries.
 * 
 * @author YourName
 * @version 1.0
 * @since 2024
 */
public class Manager {

    /**
     * List of all competitors managed by this manager.
     */
    private List<MRCompetitor> allCompetitors;

    /**
     * The top-performing competitor.
     */
    private MRCompetitor topPerformer;

    /**
     * Database connection for competitor operations.
     */
    private CompetitorDB db;

    /**
     * Constructs a new {@code Manager} object and initializes the database connection.
     */
    public Manager() {
        db = new CompetitorDB();
    }

    /**
     * Sets the list of all competitors.
     * 
     * @param allCompetitors a {@link List} of {@link MRCompetitor} objects
     */
    public void setAllCompetitors(List<MRCompetitor> allCompetitors) {
        this.allCompetitors = allCompetitors;
    }

    /**
     * Retrieves all competitors from the database.
     * 
     * @return a {@link List} of {@link MRCompetitor} objects
     */
    public List<MRCompetitor> getAllCompetitors() {
        return db.getAllCompetitors();
    }

    /**
     * Adds a competitor to the database.
     * 
     * @param competitor the {@link MRCompetitor} object to add
     */
    public void addCompetitor(MRCompetitor competitor) {
        db.addCompetitorDB(competitor, competitor.getName());
    }

    /**
     * Updates the details of an existing competitor in the database.
     * 
     * @param competitor the {@link MRCompetitor} object with updated details
     */
    public void updateCompetitor(MRCompetitor competitor) {
        db.updateCompetitor(competitor);
    }

    /**
     * Retrieves a competitor by their ID from the database.
     * 
     * @param competitorID the unique ID of the competitor
     * @return the {@link MRCompetitor} object if found, or {@code null} if not found
     */
    public MRCompetitor getCompetitorsById(int competitorID) {
        if (!db.isConnectionValid()) {
            System.err.println("Cannot fetch competitor: Database connection is null.");
            return null;
        }

        String sql_statement = "SELECT * FROM mrcompetitors WHERE competitorID=?";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql_statement)) {
            statement.setInt(1, competitorID);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
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
                    return new MRCompetitor(competitorID, name, competitionLevel, gender, beltColor, scores);
                } else {
                    System.out.println("No competitor found with ID " + competitorID);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching competitor: " + e.getMessage());
            return null;
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
     * Retrieves the top-performing competitor.
     * 
     * @return the {@link MRCompetitor} object representing the top performer
     */
    public MRCompetitor getTopPerformer() {
        if (topPerformer == null) {
            topPerformer = fetchTopPerformer();
        }
        return topPerformer;
    }

    /**
     * Sets the top-performing competitor.
     * 
     * @param topPerformer the {@link MRCompetitor} object to set as the top performer
     */
    public void setTopPerformer(MRCompetitor topPerformer) {
        this.topPerformer = topPerformer;
    }

    /**
     * Fetches the top-performing competitor from the database.
     * 
     * @return the {@link MRCompetitor} object representing the top performer
     */
    private MRCompetitor fetchTopPerformer() {
        String sql = "SELECT * FROM mrcompetitors ORDER BY overallScore DESC LIMIT 1";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
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

                Name name = new Name(firstName, middleName, lastName);
                return new MRCompetitor(competitorID, name, competitionLevel, gender, beltColor, scores);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching top performer: " + e.getMessage());
        }
        return null;
    }

    /**
     * Deletes a competitor from the database by ID.
     * 
     * @param competitorID the unique ID of the competitor to delete
     */
    public void deleteCompetitor(int competitorID) {
        db.deleteCompetitor(competitorID);
    }

    /**
     * Displays a statistical summary of all competitors in the system.
     */
    public void displayStatisticalSummary() {
        System.out.println("\n--- Statistical Summary ---");

        List<MRCompetitor> allCompetitors = getAllCompetitors();

        if (allCompetitors.isEmpty()) {
            System.out.println("No competitors available to display statistics.");
            return;
        }

        int totalCompetitors = allCompetitors.size();
        System.out.println("Total number of competitors: " + totalCompetitors);

        MRCompetitor topPerformer = getTopPerformer();
        if (topPerformer != null) {
            System.out.println("Top Performer: " + topPerformer.getName().getFullName() +
                    " with an overall score of " + topPerformer.getOverallScore());
        } else {
            System.out.println("No top performer found.");
        }

        Map<Integer, Integer> scoreFrequency = getScoreFrequency();
        System.out.println("\nScore Frequency:");
        System.out.printf("%-10s %-10s\n", "Score", "Frequency");
        System.out.println("--------------------");
        for (Map.Entry<Integer, Integer> entry : scoreFrequency.entrySet()) {
            System.out.printf("%-10d %-10d\n", entry.getKey(), entry.getValue());
        }
    }

    /**
     * Calculates the frequency of individual scores among all competitors.
     * 
     * @return a {@link Map} where the key is the score and the value is its frequency
     */
    public Map<Integer, Integer> getScoreFrequency() {
        List<MRCompetitor> allCompetitors = getAllCompetitors();
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (MRCompetitor competitor : allCompetitors) {
            for (int score : competitor.getScores()) {
                frequencyMap.put(score, frequencyMap.getOrDefault(score, 0) + 1);
            }
        }
        return frequencyMap;
    }
}
