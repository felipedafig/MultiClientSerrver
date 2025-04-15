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
    // It's often better practice to create the streams in the reverse order
    // Output stream first, then input stream, to avoid potential deadlocks
    // although less common with Object streams compared to raw byte streams.
    this.outToClient = new ObjectOutputStream(socket.getOutputStream());
    // Flush the stream header immediately.
    this.outToClient.flush();
    this.inFromClient = new ObjectInputStream(socket.getInputStream());
  }

  @Override
  public void run() {
    try {
      // Send initial response with the current vinyl list
      Response initialResponse = new Response();
      initialResponse.setVinyls(model.getVinyls());
      initialResponse.setMessage("Initial list loaded."); // Or null as before
      // Use the sendResponse method consistently if desired, or send directly
      // Using sendResponse here for consistency demonstration:
      sendResponse(initialResponse);
      System.out.println("Initial state sent. First vinyl state: " + (initialResponse.getVinyls().isEmpty() ? "N/A" : initialResponse.getVinyls().getFirst().getState()));


      while (true) { // Consider adding a condition to exit the loop gracefully
        Request request = (Request) inFromClient.readObject();
        System.out.println("Received request: " + request.getAction() + " for " + request.getVinyl().getTitle()); // Log received request

        Response response = model.processRequest(request);

        // *** THE FIX: Reset the stream's object cache ***
        outToClient.reset();

        // Now write the object with its current state
        outToClient.writeObject(response);
        outToClient.flush(); // Ensure data is sent immediately

        System.out.println("Processed request. Sent response. Message: " + response.getMessage());
        System.out.println("Current state sent. First vinyl state: " + (response.getVinyls().isEmpty() ? "N/A" : response.getVinyls().getFirst().getState()));


      }
    } catch (java.io.EOFException e) {
      System.out.println("Client disconnected."); // Handle client disconnection gracefully
    } catch (java.net.SocketException e) {
      System.out.println("Socket exception (client likely disconnected forcibly): " + e.getMessage());
    } catch (IOException | ClassNotFoundException | ClassCastException e) {
      // Log the full stack trace for better debugging
      System.err.println("Error in ServerConnection run loop: " + e.getMessage());
      e.printStackTrace(); // Print stack trace
      // Consider how to handle the error - maybe close the connection?
    } finally {
      // Ensure resources are closed when the loop terminates or an error occurs
      try {
        if (socket != null && !socket.isClosed()) {
          socket.close();
          System.out.println("Closed connection socket.");
        }
      } catch (IOException ex) {
        System.err.println("Error closing socket: " + ex.getMessage());
      }
    }
  }

  // Keep this method if you want to use it for sending responses outside the main loop,
  // but ensure it also uses reset() if necessary for its context.
  // For consistency, you might call this from the loop as well.
  public void sendResponse(Response response) throws IOException {
    System.out.println("Sending response. First vinyl state: " + (response.getVinyls().isEmpty() ? "N/A" : response.getVinyls().getFirst().getState()));
    // Reset here too if this method could be called after state changes
    // and before the main loop's reset.
    outToClient.reset();
    outToClient.writeObject(response);
    outToClient.flush(); // Ensure data is sent
  }
}