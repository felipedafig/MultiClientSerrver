package Network;

import Network.ServerConnection;
import Network.ServerModelManager;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
  private List<ServerConnection> clients;
  private ServerSocket serverSocket;
  private ServerModelManager modelManager;

  public Server(int port) throws Exception {
    this.clients = new ArrayList<>();
    this.serverSocket = new ServerSocket(port);
    this.modelManager = new ServerModelManager();
  }

  public void main(String[] args) throws Exception {
    Server server = new Server(12345);
    while (true) {
      Socket clientSocket = serverSocket.accept();
      ServerConnection connection = new ServerConnection(clientSocket, modelManager);
      clients.add(connection);
      new Thread(connection).start();
    }
  }
}