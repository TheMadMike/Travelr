package server.controller;

import server.model.Guide;
import server.model.Tour;
import server.service.TourService;
import server.util.Controller;
import server.util.CsvParser;
import server.util.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TourController extends Controller {

    TourService tourService = new TourService();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    protected void initialize() {
        endpoints.put("GET all", this::getAll);
        endpoints.put("GET by_id", this::getById);
        endpoints.put("UPDATE new", this::createNew);
        endpoints.put("SET guide", this::assignGuide);
        endpoints.put("GET details", this::getDetails);
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
        List<String> tokens = CsvParser.parse(rawRequestBodyBuffer);

        if(tokens.size() != 4) {
            respond(Response.Type.ERROR, "Invalid number of arguments\n");
        }

        String name = tokens.get(0);
        String description = tokens.get(1);
        int id = (name + description).hashCode();
        LocalDate departureDate = LocalDate.parse(tokens.get(2), dateFormatter);
        int maxTourists = Integer.parseInt(tokens.get(3));

        tourService.create(new Tour(id, name, description, departureDate, maxTourists));

        ok();
    }

    // SET /tours/guide <tour id>, <guide id>
    private void assignGuide() {
        List<String> tokens = CsvParser.parse(rawRequestBodyBuffer);

        if(tokens.size() != 2) {
            respond(Response.Type.ERROR, "Invalid number of arguments\n");
        }

        int tourId = Integer.parseInt(tokens.get(0));
        int guideId = Integer.parseInt(tokens.get(1));

        tourService.assignGuide(guideId, tourId);

        ok();
    }

    // GET /tours/details <id>
    private void getDetails() {
        int id = Integer.parseInt(rawRequestBodyBuffer.trim());
        Tour tour = tourService.getById(id);

        StringBuffer buffer = new StringBuffer();

        buffer.append("Tour name: ");
        buffer.append(tour.getName());
        buffer.append("\nDescription: ");
        buffer.append(tour.getDescription());
        buffer.append("\nDeparture date: ");
        buffer.append(tour.getDepartureDate());
        buffer.append("\nNumber of tourists: ");
        buffer.append(tour.getTourists().size() + " / "+ tour.getMaxTourists());
        buffer.append("\nTourists: ");
        buffer.append(tour.getTourists());
        buffer.append("\nGuide(s): ");
        buffer.append(tourService.getGuidesForTour(tour.getId()).stream()
                .map(Guide::getName).collect(Collectors.toList()));
        buffer.append("\n");

        respond(buffer.toString());
    }

}
