package application;

import config.Config;
import db.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            Database.getConnection();

            Parent root = FXMLLoader.load(new URL(Config.LOGIN_FXML_PATH));
            Scene scene = new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
            Main.mainScene = scene;
            primaryStage.setScene(scene);
            primaryStage.setTitle(Config.TITLE);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
        Database.disconnect();
    }

    public static void changeToLoginScene() {
        try {
            Parent root = FXMLLoader.load(new URL(Config.LOGIN_FXML_PATH));
            Main.mainScene.setRoot(root);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void changeToSignUpScene() {
        try {
            Parent root = FXMLLoader.load(new URL(Config.SIGN_UP_FXML_PATH));
            Main.mainScene.setRoot(root);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
