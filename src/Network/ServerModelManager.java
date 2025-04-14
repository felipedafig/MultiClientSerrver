package Network;

import Model.Request;
import Model.Vinyl;

import java.util.List;
import java.util.ArrayList;

public class ServerModelManager {
  private List<Vinyl> vinyls;

  public ServerModelManager() {

    this.vinyls = new ArrayList<>();
    vinyls.add(new Vinyl("The Dark Side of the Moon", "Pink Floyd", 1973));
    vinyls.add(new Vinyl("Abbey Road", "The Beatles", 1969));
    vinyls.add(new Vinyl("Led Zeppelin IV", "Led Zeppelin", 1971));
    vinyls.add(new Vinyl("The Wall", "Pink Floyd", 1979));
    vinyls.add(new Vinyl("Sgt. Pepper's Lonely Hearts Club Band", "The Beatles", 1967));
    vinyls.add(new Vinyl("Revolver", "The Beatles", 1966));
    vinyls.add(new Vinyl("The White Album", "The Beatles", 1968));
    vinyls.add(new Vinyl("Physical Graffiti", "Led Zeppelin", 1975));
  }

  public Response processRequest(Request request) {
    String action = request.getAction().toLowerCase();
    Vinyl vinyl = request.getVinyl();
    Response response = new Response();
    String message = "Request processed successfully";
    int userID = request.getUserID(); // Get userID from the request

    if (action.equals("borrow")) {
      for (int i = 0; i < vinyls.size(); i++) {
        Vinyl currentVinyl = vinyls.get(i);
        if (currentVinyl.getTitle().equals(vinyl.getTitle())) {
          boolean success = currentVinyl.onBorrow(userID);
          System.out.println(currentVinyl.getTitle()+ " borrowedd");

          if (!success) {
            message = "Vinyl cannot be borrowed";
          }
          break;
        }
      }
    } else if (action.equals("return")) {
      for (int i = 0; i < vinyls.size(); i++) {
        Vinyl currentVinyl = vinyls.get(i);
        if (currentVinyl.getTitle().equals(vinyl.getTitle())) {
          boolean success = currentVinyl.onReturn(userID);
          if (!success) {
            message = "Vinyl cannot be returned";
          }
          break;
        }
      }
    } else if (action.equals("reserve")) {
      for (int i = 0; i < vinyls.size(); i++) {
        Vinyl currentVinyl = vinyls.get(i);
        if (currentVinyl.getTitle().equals(vinyl.getTitle())) {
          boolean success = currentVinyl.onReserve(userID);
          if (!success) {
            message = "Vinyl cannot be reserved";
          }
          break;
        }
      }
    } else if (action.equals("remove")) {
      for (int i = 0; i < vinyls.size(); i++) {
        Vinyl currentVinyl = vinyls.get(i);
        if (currentVinyl.getTitle().equals(vinyl.getTitle())) {
          if (!currentVinyl.getFlagged()) {
            currentVinyl.flagForRemoval();
            message = "Vinyl flagged for removal";
          } else {
            vinyls.remove(i);
            message = "Vinyl removed successfully";
          }
          break;
        }
      }
    } else if (action.equals("add")) {
      boolean vinylExists = false;
      for (int i = 0; i < vinyls.size(); i++) {
        Vinyl currentVinyl = vinyls.get(i);
        if (currentVinyl.getTitle().equals(vinyl.getTitle())) {
          vinyls.set(i, vinyl);
          vinylExists = true;
          break;
        }
      }
      if (!vinylExists) {
        vinyls.add(vinyl);
      }
    } else {
      System.out.println("Unknown action: " + action);
      message = "Unknown action: " + action;
    }

    broadcast(vinyls);

    response.setVinyls(vinyls);
    response.setMessage(message);

    return response;
  }

  public List<Vinyl> getVinyls()
  {
    return vinyls;
  }

  private void broadcast(List<Vinyl> updatedVinyls) {
    System.out.println("Broadcasting updated vinyl list to all clients: " + updatedVinyls);
  }
}