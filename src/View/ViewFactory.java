package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ViewModel.*;

import java.io.IOException;

public class ViewFactory

    {
    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;

    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
    }

    public Scene load(String viewName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = null;

            switch (viewName) {
                case "VinylList" -> {
                    loader.setLocation(getClass().getResource("/View/VinylList.fxml"));
                    root = loader.load();
                    ListViewController controller = loader.getController();
                    controller.init(viewHandler, viewModelFactory.getListViewModel());
                }
                case "AddVinyl" -> {
                    loader.setLocation(getClass().getResource("/View/AddVinylView.fxml"));
                    root = loader.load();
                    AddVinylViewController controller = loader.getController();
                    controller.init(viewHandler, viewModelFactory.getAddVinylViewModel());
                }
            }

            return new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
