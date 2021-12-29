package server.controller;

import server.model.Tour;
import server.service.TourService;
import server.util.Controller;
import server.util.Response;

import java.util.List;

public class TourController extends Controller {

    TourService tourService = new TourService();

    @Override
    protected void initialize() {
        endpoints.put("GET all", this::getAll);
        endpoints.put("GET by_id", this::getById);
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
        Tour tour = tourService.getById(id);

        if(tour == null) {
            respond(Response.Type.ERROR, "Tour not found\n");
            return;
        }

        respond(tour.toCsv() + "\n");
    }


}
