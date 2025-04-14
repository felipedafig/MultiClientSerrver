package Network;

import Model.Request;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;

public class ServerConnection implements Runnable {
  private Socket socket;
  private ObjectInputStream inFromClient;
  private ObjectOutputStream outToClient;
  private ServerModelManager model;

  public ServerConnection(Socket socket, ServerModelManager model) throws IOException {
    this.socket = socket;
    this.model = model;
    this.outToClient = new ObjectOutputStream(socket.getOutputStream());
    this.inFromClient = new ObjectInputStream(socket.getInputStream());
  }

  @Override
  public void run() {
    try {
      // Send initial response with the current vinyl list
      Response initialResponse = new Response();
      initialResponse.setVinyls(model.getVinyls()); // Assuming ServerModelManager has a getVinyls method
      initialResponse.setMessage(null);
      sendResponse(initialResponse);

      System.out.println(initialResponse.getVinyls().getFirst().getState());

      while (true) {


        Request request = (Request) inFromClient.readObject();
        Response response = model.processRequest(request);

        outToClient.writeObject(response);
        outToClient.flush();

        System.out.println(response.getVinyls().getFirst().getState());

      }
    } catch (IOException | ClassNotFoundException | ClassCastException e) {
      System.err.println("Error in ServerConnection: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public void sendResponse(Response response) throws IOException {
    System.out.println("Before sending response: " + response.getVinyls().getFirst().getState());
    outToClient.flush();
    outToClient.writeObject(response);

  }
}