package config;

public class Config {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final String TITLE = "Hotel Booking";
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String RESOURCES_DIR = System.getProperty("user.dir") + FILE_SEPARATOR + "resources"
            + FILE_SEPARATOR;
    public static final String FXML_DIR = RESOURCES_DIR + "fxml"
            + FILE_SEPARATOR;
    public static final String LOGIN_FXML_PATH = "file:/" + FXML_DIR + "LoginView.fxml";
    public static final String SIGN_UP_FXML_PATH = "file:/" + FXML_DIR + "SignUpView.fxml";
    public static final String Main_APP_CONTAINER_FXML_PATH = "file:/" + FXML_DIR + "MainAppContainerView.fxml";
    public static final String FIND_HOTEL_FXML_PATH = "file:/" + FXML_DIR + "FindHotelView.fxml";
    public static final String MY_HOTELS_FXML_PATH = "file:/" + FXML_DIR + "MyHotelsView.fxml";
}
