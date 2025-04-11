package Network;

import Model.Request;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;

public class ServerConnection implements Runnable {
  private Socket socket;
  private ObjectInputStream inFromUser;
  private ObjectOutputStream outToUser;
  private ServerModelManager model;

  public ServerConnection(Socket socket, ServerModelManager model) throws IOException {
    this.socket = socket;
    this.model = model;
    this.outToUser = new ObjectOutputStream(socket.getOutputStream());
    this.inFromUser = new ObjectInputStream(socket.getInputStream());
  }

  public void run() {
    try {
      while (true) {
        Object obj = inFromUser.readObject();
        if (!(obj instanceof Request)) {
          System.out.println("Received invalid object, expected Request");
          continue;
        }
        Request request = (Request) obj;
        Response response = model.processRequest(request);
        outToUser.writeObject(response);
        outToUser.flush();
      }
    } catch (IOException e) {
      System.out.println("Client disconnected: " + socket.getInetAddress());
    } catch (ClassNotFoundException e) {
      System.err.println("Class not found: " + e.getMessage());
    } finally {
      try {
        inFromUser.close();
        outToUser.close();
        socket.close();
      } catch (IOException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }
  }

  public void sendRequest(Request request) throws IOException {
    outToUser.writeObject(request);
    outToUser.flush();
  }
}