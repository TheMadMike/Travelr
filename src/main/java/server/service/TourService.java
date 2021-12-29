package server.service;

import server.data.DataContext;
import server.data.DataRepository;
import server.model.Tour;

import java.util.List;
import java.util.stream.Collectors;

public class TourService {
    private DataRepository toursRepository = DataContext.getInstance().getToursRepository();

    public List<Tour> getAll() {
        return toursRepository.readAll()
                .stream()
                .map(serializable -> (Tour) serializable)
                .collect(Collectors.toList());
    }

    public Tour getById(int id) {
        return getAll().stream()
                .filter(tour -> tour.getId() == id)
                .findFirst().orElse(null);
    }
}
