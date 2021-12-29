package server.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tour implements Serializable {
    private String name, description;
    private LocalDate departureDate;
    private int id, maxTourists;
    private List<Integer> touristIds = new ArrayList<>();
    private List<Integer> guideIds = new ArrayList<>();

    public Tour(int id, String name, String description, LocalDate departureDate, int maxTourists) {
        this.name = name;
        this.description = description;
        this.departureDate = departureDate;
        this.id = id;
        this.maxTourists = maxTourists;
    }

    public Tour() {
        this(0, "", "", LocalDate.now(), 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public int getMaxTourists() {
        return maxTourists;
    }

    public void setMaxTourists(int maxTourists) {
        this.maxTourists = maxTourists;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getTouristIds() {
        return touristIds;
    }

    public List<Integer> getGuideIds() {
        return guideIds;
    }

    public String toCsv() {
        return id + ", "
                + name + ", "
                + description + ", "
                + departureDate.toString() + ", "
                + maxTourists + ", "
                + touristIds.size();
    }
}
