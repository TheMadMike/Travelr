package server.data;


import java.nio.file.Path;

public class DataContext {
    private static DataContext instance;
    private DataRepository tours, tourists, guides;

    private static final String TOURS_FILE_NAME = "tours.bin";
    private static final String TOURISTS_FILE_NAME = "tourists.bin";
    private static final String GUIDES_FILE_NAME = "guides.bin";

    private DataContext() {
        tours = new BinaryRepository(Path.of(TOURS_FILE_NAME));
        tourists = new BinaryRepository(Path.of(TOURISTS_FILE_NAME));
        guides = new BinaryRepository(Path.of(GUIDES_FILE_NAME));
    }

    public static DataContext getInstance() {
        if(instance == null) {
            instance = new DataContext();
        }
        return instance;
    }

    public DataRepository getToursRepository() {
        return tours;
    }

    public DataRepository getTouristsRepository() {
        return tourists;
    }

    public DataRepository getGuidesRepository() {
        return guides;
    }
}