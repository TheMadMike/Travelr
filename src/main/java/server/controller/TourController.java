package server.controller;

import server.model.Tour;
import server.service.TourService;
import server.util.Controller;
import server.util.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TourController extends Controller {

    TourService tourService = new TourService();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    protected void initialize() {
        endpoints.put("GET all", this::getAll);
        endpoints.put("GET by_id", this::getById);
        endpoints.put("UPDATE new", this::createNew);
    }

    // GET /tours/all
    private void getAll() {
        List<Tour> tours = tourService.getAll();
        String csvList = tours.stream()
                .map(Tour::toCsv)
                .reduce((first, second) -> first + '\n' + second)
                .orElse("");

        if(!csvList.isBlank()) {
            csvList += '\n';
        }

        respond(csvList);
    }

    // GET /tours/by_id <id>
    private void getById() {
        int id = Integer.parseInt(rawRequestBodyBuffer);

        Tour tour = null;
        try {
            tour = tourService.getById(id);
        } catch(RuntimeException exception) {
            respond(Response.Type.ERROR, exception.getMessage()+"\n");
        }

        respond(tour.toCsv() + "\n");
    }


    // UPDATE /tours/new <name>, <description>, <deprature date (dd/MM/yyyy)>, <max tourists>
    private void createNew() {
        String[] tokens = rawRequestBodyBuffer.split(",");

        if(tokens.length != 4) {
            respond(Response.Type.ERROR, "Invalid number of arguments\n");
        }

        String name = tokens[0].trim();
        String description = tokens[1].trim();
        int id = (name + description).hashCode();
        LocalDate departureDate = LocalDate.parse(tokens[2].trim(), dateFormatter);
        int maxTourists = Integer.parseInt(tokens[3].trim());

        tourService.create(new Tour(id, name, description, departureDate, maxTourists));

        ok();
    }

}
