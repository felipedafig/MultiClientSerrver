package Model;

import Network.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable
{
  private ClientModelManager clientModelManager;
  private Socket socket;
  private ObjectOutputStream out;
  private ObjectInputStream in;

  public ClientConnection(String host, int port){
    try {
      socket = new Socket(host, port);
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream(socket.getInputStream());
//    System.out.println("Connected to server at " + host + ":" + port);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendRequest(Request request){
    try{
      out.writeObject(request);
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override public void run()
  {
    try {
      // Continuously listen for responses from the server
      while (true) {
        // Read the response from the server
        Object obj = in.readObject();
        if (!(obj instanceof Response)) {
          System.out.println("Received invalid object, expected Response");
          continue;
        }
        Response response = (Response) obj;
        clientModelManager.handleServerResponse(response);
      }
    } catch (IOException e) {

      System.out.println("Server disconnected: " + socket.getInetAddress());
    } catch (ClassNotFoundException e)
    {
      throw new RuntimeException();
  } finally {
  try {
    in.close();
    out.close();
    socket.close();
  } catch (IOException e) {
    System.err.println("Error closing resources: " + e.getMessage());
  }
    }
  }
}
