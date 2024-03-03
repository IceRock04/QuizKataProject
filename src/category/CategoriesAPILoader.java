package src.category;

import java.util.HashMap;
import java.util.List;

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
