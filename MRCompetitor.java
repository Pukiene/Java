package Main;

import java.util.*;

/**
 * The {@code MRCompetitor} class represents a competitor in a martial arts competition.
 * It stores the competitor's ID, name, competition level, gender, belt color, and scores.
 * This class provides methods to compute overall scores, retrieve details, and update individual scores.
 * 
 * @author YourName
 * @version 1.0
 * @since 2024
 */
public class MRCompetitor {

    /**
     * The unique ID of the competitor.
     */
    private Integer CompetitorID;

    /**
     * The {@link Name} object representing the full name of the competitor.
     */
    private Name name;

    /**
     * The competition level of the competitor (e.g., Beginner, Intermediate, Advanced).
     */
    private String CompetitionLevel;

    /**
     * The gender of the competitor.
     */
    private String Gender;

    /**
     * The belt color of the competitor.
     */
    private String BelbeltColort;

    /**
     * An array representing the scores of the competitor in various events.
     */
    private int[] Scores;

    /**
     * Constructs an {@code MRCompetitor} with the specified details.
     * 
     * @param competitorID the unique ID of the competitor
     * @param name the {@link Name} object representing the competitor's name
     * @param CompetitionLevel the competition level of the competitor
     * @param gender the gender of the competitor
     * @param BelbeltColort the belt color of the competitor
     * @param Scores an array of scores for the competitor
     */
    public MRCompetitor(Integer competitorID, Name name, String CompetitionLevel, String gender, String BelbeltColort, int[] Scores) {
        setCompetitorID(competitorID);
        setName(name);
        setCompetitionLevel(CompetitionLevel);
        setgender(gender);
        setBelbeltColort(BelbeltColort);
        setScores(Scores);
    }

    /**
     * Gets the competitor's unique ID.
     * 
     * @return the competitor's ID
     */
    public Integer getCompetitorID() {
        return CompetitorID;
    }

    /**
     * Sets the competitor's unique ID.
     * 
     * @param competitorID the ID to set
     */
    public void setCompetitorID(Integer competitorID) {
        this.CompetitorID = competitorID;
    }

    /**
     * Gets the competition level of the competitor.
     * 
     * @return the competition level
     */
    public String getCompetitionLevel() {
        return CompetitionLevel;
    }

    /**
     * Sets the competition level of the competitor.
     * 
     * @param CompetitionLevel the competition level to set
     */
    public void setCompetitionLevel(String CompetitionLevel) {
        this.CompetitionLevel = CompetitionLevel;
    }

    /**
     * Gets the competitor's gender.
     * 
     * @return the gender of the competitor
     */
    public String getgender() {
        return Gender;
    }

    /**
     * Sets the competitor's gender.
     * 
     * @param gender the gender to set
     */
    public void setgender(String gender) {
        this.Gender = gender;
    }

    /**
     * Gets the competitor's belt color.
     * 
     * @return the belt color
     */
    public String getBelbeltColor() {
        return BelbeltColort;
    }

    /**
     * Sets the competitor's belt color.
     * 
     * @param belbeltColort the belt color to set
     */
    public void setBelbeltColort(String belbeltColort) {
        this.BelbeltColort = belbeltColort;
    }

    /**
     * Gets the scores of the competitor.
     * 
     * @return an array of scores
     */
    public int[] getScores() {
        return Scores;
    }

    /**
     * Sets the scores of the competitor.
     * 
     * @param scores the scores to set
     */
    public void setScores(int[] scores) {
        this.Scores = scores;
    }

    /**
     * Gets the first score of the competitor.
     * 
     * @return the first score, or 0 if not available
     */
    public int getScore1() {
        return Scores.length > 0 ? Scores[0] : 0;
    }

    /**
     * Gets the second score of the competitor.
     * 
     * @return the second score, or 0 if not available
     */
    public int getScore2() {
        return Scores.length > 1 ? Scores[1] : 0;
    }

    /**
     * Gets the third score of the competitor.
     * 
     * @return the third score, or 0 if not available
     */
    public int getScore3() {
        return Scores.length > 2 ? Scores[2] : 0;
    }

    /**
     * Gets the fourth score of the competitor.
     * 
     * @return the fourth score, or 0 if not available
     */
    public int getScore4() {
        return Scores.length > 3 ? Scores[3] : 0;
    }

    /**
     * Gets the fifth score of the competitor.
     * 
     * @return the fifth score, or 0 if not available
     */
    public int getScore5() {
        return Scores.length > 4 ? Scores[4] : 0;
    }

    /**
     * Sets the first score of the competitor.
     * 
     * @param score the score to set
     */
    public void setScore1(int score) {
        Scores[0] = score;
    }

    /**
     * Sets the second score of the competitor.
     * 
     * @param score the score to set
     */
    public void setScore2(int score) {
        Scores[1] = score;
    }

    /**
     * Sets the third score of the competitor.
     * 
     * @param score the score to set
     */
    public void setScore3(int score) {
        Scores[2] = score;
    }

    /**
     * Sets the fourth score of the competitor.
     * 
     * @param score the score to set
     */
    public void setScore4(int score) {
        Scores[3] = score;
    }

    /**
     * Sets the fifth score of the competitor.
     * 
     * @param score the score to set
     */
    public void setScore5(int score) {
        Scores[4] = score;
    }

    /**
     * Sets the competitor's name.
     * 
     * @param name the {@link Name} object to set
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Gets the competitor's name.
     * 
     * @return the {@link Name} object representing the competitor's name
     */
    public Name getName() {
        return name;
    }

    /**
     * Calculates the overall score of the competitor by averaging all but the highest and lowest scores.
     * 
     * @return the overall score as a double
     */
    public double getOverallScore() {
        if (Scores == null || Scores.length < 3) {
            return 0.0; 
        }   
        int[] sortedScores = Scores.clone();
        Arrays.sort(sortedScores);
        int sum = 0;
        for (int i = 1; i < sortedScores.length - 1; i++) {
            sum += sortedScores[i];
        }
        return sum / (double) (sortedScores.length - 2);
    }

    /**
     * Returns a string representation of the competitor.
     * 
     * @return a string containing the competitor's details
     */
    @Override
    public String toString() {
        return "Competitor ID: " + CompetitorID +
               "\nName: " + name.getFullName() +
               "\nCompetition Level: " + CompetitionLevel +
               "\nGender: " + Gender +
               "\nBelt Color: " + BelbeltColort +
               "\nScores: " + Arrays.toString(Scores) +
               "\nOverall Score: " + getOverallScore();
    }

    /**
     * Returns a detailed description of the competitor's attributes.
     * 
     * @return a string containing the full details of the competitor
     */
    public String getFullDetails() {
        return "Competitor ID " + CompetitorID + ", name " + name.getFullName() + " gender: " + Gender + ".\n" +
               name.getFullName() + " is a " + CompetitionLevel + " level with belt color " + this.getBelbeltColor() + ".\n" +
               "Received these scores: " + Arrays.toString(Scores) + " and has an overall score of " + getOverallScore() + ".\n";
    }

    /**
     * Returns a brief summary of the competitor's details.
     * 
     * @return a string containing the short details of the competitor
     */
    public String getShortDetails() {
        return "CN " + CompetitorID + " (" + name.getInitials() + ") has overall score " + getOverallScore();
    }
}
