package Network;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(1235);
    ConnectionPool connectionPool = new ConnectionPool();
    ServerModelManager modelManager = new ServerModelManager(connectionPool);
    System.out.println("Server started on port 1234");
    while (true) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
      ServerConnection newConnection = new ServerConnection(clientSocket, modelManager);
      connectionPool.addConnection(newConnection);
      new Thread(newConnection).start();
    }
  }
}