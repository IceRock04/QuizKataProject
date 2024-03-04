/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src.category;

/**
 * This class is used as a JSON object container
 * It stores the name and id for a given category
 */
public class Category {
    private int id;
    private String name;

    @Override
    public String toString() {
        return id + ":" + name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
