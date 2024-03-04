/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src.category;

import java.util.HashMap;
import java.util.List;

/**
 * This class was created to receive API data for the list of available categories when taking a quiz
 * It also contains a method for creating a Hash Map for the list of categories
 */

public class CategoriesAPILoader {
    private List<Category> trivia_categories;

    private HashMap<Integer, String> categoryMap;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Category category: trivia_categories) {
            result.append(category).append("\n");
        }
        return String.valueOf(result);
    }

    /**
     * This method creates a Hash Map representation of the trivia_categories list
     * @return a Hash Map that contains category id's and their respective category name
     */
    public HashMap<Integer, String> getCategoryMap() {
        //Checks to see if the hashmap for the categories is already created
        if (categoryMap == null) {
            categoryMap = new HashMap<>();
            //Loads the categories from the list of categories into the hashmap
            for (Category category: trivia_categories) {
                categoryMap.put(category.getId(), category.getName());
            }
        }
        return categoryMap;
    }
}
