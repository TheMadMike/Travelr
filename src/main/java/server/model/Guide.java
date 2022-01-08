package server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Guide implements Serializable {
    private int id;
    private List<Integer> assignedTours = new ArrayList<>();
    private String name;

    public Guide(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getAssignedTours() {
        return assignedTours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toCsv() {
        return id + ", " + name;
    }
}
