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
        if (categoryMap == null) {
            categoryMap = new HashMap<>();
            for (Category category: trivia_categories) {
                //System.out.println(category);
                categoryMap.put(category.getId(), category.getName());
            }
        }
        return categoryMap;
    }
}
