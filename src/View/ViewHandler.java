package View;

import View.ViewFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ViewModel.ViewModelFactory;

public class ViewHandler {
    private final Stage primaryStage;
    private final ViewFactory viewFactory;
    Image image = new Image(getClass().getResourceAsStream("LP_Vinyl_Symbol_Icon.png"));

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.primaryStage = new Stage();
        this.viewFactory = new ViewFactory(this, viewModelFactory);
    }
    public void start() {
        openView("VinylList");
    }

    public void openView(String viewName)
    {
        Scene scene = viewFactory.load(viewName);
        if (viewName.equals("AddVinyl"))
        {
            primaryStage.setTitle("Add Vinyl");
        }
        else {
            primaryStage.setTitle("Vinyl Library");
        }
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().add(image);
    }



}
