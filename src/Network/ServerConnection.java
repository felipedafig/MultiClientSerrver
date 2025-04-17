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
  private Logger logger = Logger.getInstance();
  private String clientIpAddress;

  public ServerConnection(Socket socket, ServerModelManager model) throws IOException {
    this.socket = socket;
    this.model = model;
    this.clientIpAddress = socket.getInetAddress().getHostAddress();

    logger.log(clientIpAddress, "Client connected.");

    this.outToClient = new ObjectOutputStream(socket.getOutputStream());
    this.outToClient.flush();
    this.inFromClient = new ObjectInputStream(socket.getInputStream());
  }

  @Override
  public void run() {
    try {
      Response initialResponse = new Response();
      initialResponse.setVinyls(model.getVinyls());
      initialResponse.setMessage("Initial list loaded.");
      sendResponse(initialResponse);

      while (true) {
        Request request = (Request) inFromClient.readObject();

        String requestLog = String.format("Received request: Action=%s, Vinyl=%s", request.getAction(), request.getVinyl().getTitle());
        logger.log(clientIpAddress, requestLog);

        Response response = model.processRequest(request);
        sendResponse(response);
      }
    } catch (java.io.EOFException e) {
      logger.log(clientIpAddress, "Client disconnected.");
    } catch (java.net.SocketException e) {
      logger.log(clientIpAddress, "Socket exception (client likely disconnected forcibly): " + e.getMessage());
    } catch (IOException | ClassNotFoundException | ClassCastException e) {
      logger.log(clientIpAddress, "Error in connection run loop: " + e.getMessage());
      e.printStackTrace();
    } finally {
      try {
        if (socket != null && !socket.isClosed()) {
          socket.close();
          logger.log(clientIpAddress, "Closed connection socket.");
        }
      } catch (IOException ex) {
        logger.log(clientIpAddress, "Error closing socket: " + ex.getMessage());
      }
    }
  }

  public void sendResponse(Response response) throws IOException {
    String responseLog = String.format("Sending response: Message='%s', VinylCount=%d",
        response.getMessage(),
        response.getVinyls().size());
    logger.log(clientIpAddress, responseLog);

    outToClient.reset();
    outToClient.writeObject(response);
    outToClient.flush();
  }
}