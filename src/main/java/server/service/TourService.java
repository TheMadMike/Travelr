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

    public Tour getById(int id) throws RuntimeException {
        int index = findIndex(id);

        return (Tour) toursRepository.read(index);
    }

    public void create(Tour tour) {
        toursRepository.create(tour);
    }

    public void update(Tour tour) throws RuntimeException {
        int index = findIndex(tour.getId());

        toursRepository.update(index, tour);
    }

    public void remove(int id) throws RuntimeException {
        int index = findIndex(id);

        toursRepository.delete(index);
    }

    private int findIndex(int id) throws RuntimeException {
        int index = -1;
        for(int i = 0; i < getAll().size(); ++i) {
            if(getAll().get(i).getId() == id) {
                index = i;
                break;
            }
        }

        if(index == -1) {
            throw new RuntimeException("Tour not found");
        }

        return index;
    }
}
