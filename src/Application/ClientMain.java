package Application;

import Model.ClientConnection;
import Model.ClientModelManager;
import View.ViewController;
import ViewModel.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application
{
  public static void main(String[] args)
  {
    launch();
  }

  @Override public void start(Stage primaryStage) throws Exception
  {


    ClientModelManager model = new ClientModelManager();
    ClientConnection client = new ClientConnection("localhost", 1234, model);
    model.setClientConnection(client);

    ViewModel viewModel = new ViewModel(model);

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/VinylList.fxml"));
    fxmlLoader.setControllerFactory(controllerClass -> new ViewController(viewModel));

    Scene scene = new Scene(fxmlLoader.load(), 700, 380);
    primaryStage.setTitle("Vinyl Library");
    primaryStage.setScene(scene);
    primaryStage.show();

    Thread thread = new Thread(client);
    thread.start();
  }
}