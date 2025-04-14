package Model;

import Network.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable {
  private ClientModelManager clientModelManager;
  private Socket socket;
  private ObjectOutputStream outToServer;
  private ObjectInputStream inFromServer;

  public ClientConnection(String host, int port, ClientModelManager clientModelManager) {
    try {
      this.socket = new Socket(host, port);

      this.inFromServer = new ObjectInputStream(socket.getInputStream());
      this.outToServer = new ObjectOutputStream(socket.getOutputStream());
      this.outToServer.flush();
      this.clientModelManager = clientModelManager;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {

      while (true) {
        Response response = (Response) inFromServer.readObject();
        for (Vinyl v : response.getVinyls()) {
          System.out.println("Vinyl: " + v.getTitle() + ", state: " + v.getState());
        }

        clientModelManager.handleServerResponse(response);
        System.out.println("clientconnection");

        System.out.println(response.getVinyls().getFirst().getState());
      }
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void sendRequest(Request request) {
    try {
      outToServer.writeObject(request);
      outToServer.flush();
      System.out.println("works");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}