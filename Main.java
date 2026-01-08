import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));
            if (root == null) {
                throw new RuntimeException("Impossible de charger le fichier FXML: interface.fxml non trouvé");
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gestion des Employés");
            stage.show();
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du fichier FXML: " + e.getMessage());
            System.err.println("Trace complète:");
            for (StackTraceElement element : e.getStackTrace()) {
                System.err.println("  at " + element);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}