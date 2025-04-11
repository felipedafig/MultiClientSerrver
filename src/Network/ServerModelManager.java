package Network;

import Model.Request;
import Model.Vinyl;

import java.util.List;
import java.util.ArrayList;

public class ServerModelManager {
  private List<Vinyl> vinyls;

  public ServerModelManager() {
    this.vinyls = new ArrayList<>();
  }

  public Response processRequest(Request request) {
    String action = request.getAction();
    Vinyl vinyl = request.getVinyl();

    switch (action.toLowerCase()) {
      case "updatevinyl":
        boolean vinylExists = false;
        for (int i = 0; i < vinyls.size(); i++) {
          if (vinyls.get(i).getTitle().equals(vinyl.getTitle())) {
            vinyls.set(i, vinyl);
            vinylExists = true;
            break;
          }
        }
        if (!vinylExists) {
          vinyls.add(vinyl);
        }
        break;

      case "removevinyl":
        vinyls.removeIf(v -> v.getTitle().equals(vinyl.getTitle()));
        break;

      default:
        System.out.println("Unknown action: " + action);
        break;
    }

    broadcast(vinyls);

    Response response = new Response();
    response.setVinyls(vinyls);
    response.setMessage("Request processed successfully");

    return response;
  }

  private void broadcast(List<Vinyl> updatedVinyls) {
    System.out.println("Broadcasting updated vinyl list to all clients: " + updatedVinyls);
  }
}