package Network;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(1235);
    ServerModelManager modelManager = new ServerModelManager();
    System.out.println("Server started on port 1234");
    while (true) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
      new Thread(new ServerConnection(clientSocket, modelManager)).start();
    }
  }
}