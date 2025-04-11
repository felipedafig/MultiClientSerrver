package Application;

import Model.ClientConnection;
import Model.ClientModelManager;
import Model.DataModel;
import Model.User;
import View.ViewController;
import ViewModel.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
  public static void main(String[] args)
  {
    launch();
  }

  @Override public void start(Stage primaryStage) throws Exception
  {
    ClientConnection client = new ClientConnection("localhost", 2137);
    ClientModelManager model = new ClientModelManager(client);
    ViewModel viewModel = new ViewModel(model);

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/VinylList.fxml"));
    fxmlLoader.setControllerFactory(controllerClass -> new ViewController(viewModel));

    Scene scene = new Scene(fxmlLoader.load(), 700, 380);
    primaryStage.setTitle("Vinyl Library");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}