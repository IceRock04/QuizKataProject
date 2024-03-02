package src.category;

import java.util.HashMap;

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
