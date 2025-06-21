import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {launch();}

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Views/Sudoku_Interface.fxml"));

        Parent root =fxmlLoader.load();
        Scene scene = new Scene(root, 1600, 900);
        primaryStage.setTitle("SUDOKU");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}