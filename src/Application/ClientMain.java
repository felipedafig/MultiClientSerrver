package Application;

import Model.ClientConnection;
import Model.ClientModelManager;
import View.ViewHandler;
import ViewModel.ListViewModel;
import ViewModel.ViewModelFactory;
import javafx.application.Application;
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
    ClientConnection client = new ClientConnection("localhost", 1235, model);
    model.setClientConnection(client);

    ViewModelFactory viewModelFactory = new ViewModelFactory(model);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);


      viewHandler.start();

    Thread thread = new Thread(client);
    thread.start();
  }
}