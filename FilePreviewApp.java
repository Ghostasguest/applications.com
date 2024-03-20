import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilePreviewApp extends Application {

    private List<File> fileList;
    private int currentIndex = 0;
    private ImageView previewImageView;

    @Override
    public void start(Stage primaryStage) {
        fileList = new ArrayList<>();
        scanFiles(new File("/")); // Start scanning from the root directory

        BorderPane root = new BorderPane();
        previewImageView = new ImageView();
        navigateTo(currentIndex);

        Button previousButton = new Button("Previous");
        previousButton.setOnAction(event -> {
            currentIndex = (currentIndex - 1 + fileList.size()) % fileList.size();
            navigateTo(currentIndex);
        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            currentIndex = (currentIndex + 1) % fileList.size();
            navigateTo(currentIndex);
        });

        root.setCenter(previewImageView);
        root.setLeft(previousButton);
        root.setRight(nextButton);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("File Preview App");
        primaryStage.show();
    }

    private void scanFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    scanFiles(file); // Recursively scan subdirectories
                } else {
                    // Add files to the list for preview
                    fileList.add(file);
                }
            }
        }
    }

    private void navigateTo(int index) {
        File file = fileList.get(index);
        if (file.isFile()) {
            // Display preview for image files
            if (file.getName().toLowerCase().endsWith(".jpg") ||
                    file.getName().toLowerCase().endsWith(".jpeg") ||
                    file.getName().toLowerCase().endsWith(".png")) {
                Image image = new Image(file.toURI().toString());
                previewImageView.setImage(image);
            } else {
                // For other file types, you can add handling here
                previewImageView.setImage(null);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
