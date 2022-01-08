package server.controller;

import server.model.Guide;
import server.service.GuideService;
import server.util.Controller;
import server.util.Response;

import java.util.List;

public class GuideController extends Controller {
    private GuideService guideService = new GuideService();

    @Override
    protected void initialize() {
        endpoints.put("UPDATE new", this::createGuide);
        endpoints.put("GET all", this::getAll);
    }

    // UPDATE /guides/new <name>
    private void createGuide() {
        String name = rawRequestBodyBuffer.trim();

        if(name.isBlank() || name.isEmpty()) {
            respond(Response.Type.ERROR, "You must provide a guide name!");
            return;
        }

        int id = guideService.createGuide(name);

        respond(id + "\n");
    }

    // GET /guides/all
    private void getAll() {
        List<Guide> guides = guideService.getAll();
        String csvList = guides.stream()
                .map(Guide::toCsv)
                .reduce((first, second) -> first + '\n' + second)
                .orElse("");

        if(!csvList.isBlank()) {
            csvList += '\n';
        }

        respond(csvList);
    }
}
