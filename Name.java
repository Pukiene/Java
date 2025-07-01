package Main;

/**
 * The {@code Name} class represents a person's name with attributes for
 * first name, middle name, last name, and their initials. It provides methods
 * to compute and retrieve the full name, initials, and short details.
 * 
 * @author YourName
 * @version 1.0
 * @since 2024
 */
public class Name {

    /**
     * The first name of the person.
     */
    private String firstName;

    /**
     * The middle name of the person.
     */
    private String middleName;

    /**
     * The last name of the person.
     */
    private String lastName;

    /**
     * The initials of the person, computed from the name attributes.
     */
    private String initials;

    /**
     * Constructs a {@code Name} object with the specified first, middle, and last names.
     * The initials are computed automatically.
     * 
     * @param firstName the first name of the person
     * @param middleName the middle name of the person
     * @param lastName the last name of the person
     */
    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        computeInitials();
    }

    /**
     * Gets the first name of the person.
     * 
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the person and updates the initials.
     * 
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        computeInitials(); // Recompute initials when first name changes
    }

    /**
     * Gets the middle name of the person.
     * 
     * @return the middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middle name of the person and updates the initials.
     * 
     * @param middleName the middle name to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
        computeInitials(); // Recompute initials when middle name changes
    }

    /**
     * Gets the last name of the person.
     * 
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person and updates the initials.
     * 
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        computeInitials(); // Recompute initials when last name changes
    }

    /**
     * Gets the initials of the person.
     * 
     * @return the initials, computed from the first, middle, and last names
     */
    public String getInitials() {
        return initials;
    }

    /**
     * Computes the initials of the person based on the first, middle, and last names.
     * Handles null or empty names gracefully.
     */
    private void computeInitials() {
        if (firstName == null || lastName == null) {
            initials = ""; // Handle null names gracefully
            return;
        }

        initials = (firstName.charAt(0) +
                   (middleName != null && !middleName.isEmpty() ? middleName.charAt(0) + "" : "") +
                    lastName.charAt(0)).toUpperCase();
    }

    /**
     * Gets the full name of the person by concatenating the first, middle, and last names.
     * 
     * @return the full name of the person
     */
    public String getFullName() {
        return firstName + " " + (middleName != null && !middleName.isEmpty() ? middleName + " " : "") + lastName;
    }

    /**
     * Gets a short representation of the person's name, typically used for initials.
     * 
     * @return a string containing the initials of the person's name
     */
    public String getShortDetails() {
        StringBuilder shortDetails = new StringBuilder();

        // Add first name initial
        if (firstName != null && !firstName.isEmpty()) {
            shortDetails.append(firstName.charAt(0));
        }

        // Add middle name initial
        if (middleName != null && !middleName.isEmpty()) {
            shortDetails.append(middleName.charAt(0));
        }

        // Add last name initial
        if (lastName != null && !lastName.isEmpty()) {
            shortDetails.append(lastName.charAt(0));
        }

        return shortDetails.toString().toUpperCase(); // Ensure initials are uppercase
    }

    /**
     * Returns a string representation of the {@code Name} object.
     * 
     * @return the full name of the person
     */
    @Override
    public String toString() {
        return getFullName(); // Return full name when Name object is printed
    }
}
