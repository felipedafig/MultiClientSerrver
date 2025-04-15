package Network;

import Model.Request;
import Network.Response;
import Model.Vinyl;
import java.util.List;

public class RemoveVinylStrategy implements ActionStrategy {
  @Override
  public Response execute(Request request, List<Vinyl> vinyls) {
    Response response = new Response();
    String message = "Request processed successfully";
    Vinyl requestedVinyl = request.getVinyl();

    for (int i = 0; i < vinyls.size(); i++) {
      Vinyl currentVinyl = vinyls.get(i);
      if (currentVinyl.getTitle().equals(requestedVinyl.getTitle())) {
        if (currentVinyl.getState().equals("Available")) {
          vinyls.remove(i);
          message = "Vinyl removed successfully";
        } else {
          if (!currentVinyl.getFlagged()) {
            currentVinyl.flagForRemoval();
            message = "Vinyl flagged for removal";
          } else {
            vinyls.remove(i);
            message = "Vinyl removed successfully";
          }
        }
        break;
      }
    }
    response.setVinyls(vinyls);
    response.setMessage(message);
    return response;
  }
}