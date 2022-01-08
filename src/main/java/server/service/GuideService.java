package server.service;

import server.data.DataContext;
import server.data.DataRepository;
import server.model.Guide;

import java.util.List;
import java.util.stream.Collectors;

public class GuideService {
    private DataRepository guidesRepository = DataContext.getInstance().getGuidesRepository();

    public List<Guide> getAll() {
        return guidesRepository.readAll()
                .stream()
                .map(serializable -> (Guide) serializable)
                .collect(Collectors.toList());
    }

    public int createGuide(String name) {
        int id = name.hashCode();
        guidesRepository.create(new Guide(id, name));
        return id;
    }

    public void assignTour(int guideId, int tourId) throws RuntimeException {
        int index = findIndex(guideId);
        Guide guide = (Guide) guidesRepository.read(index);
        guide.getAssignedTours().add(tourId);
        guidesRepository.update(index, guide);
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
            throw new RuntimeException("Guide not found");
        }

        return index;
    }

    public List<Guide> getByIdList(List<Integer> ids) throws RuntimeException {
        return getAll().stream()
                .filter(tourist -> ids.contains(tourist.getId()))
                .collect(Collectors.toList());
    }
}
