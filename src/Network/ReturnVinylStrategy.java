package Network;

import Model.Request;
import Network.Response;
import Model.Vinyl;
import java.util.List;

public class ReturnVinylStrategy implements ActionStrategy {
  @Override
  public Response execute(Request request, List<Vinyl> vinyls) {
    Response response = new Response();
    String message = "Request processed successfully";
    int userID = request.getUserID();
    Vinyl requestedVinyl = request.getVinyl();

    for (Vinyl currentVinyl : vinyls) {
      if (currentVinyl.getTitle().equals(requestedVinyl.getTitle())) {
        boolean success = currentVinyl.onReturn(userID);
        if (!success) {
          message = "Vinyl cannot be returned";
        }
        break;
      }
    }
    response.setVinyls(vinyls);
    response.setMessage(message);
    return response;
  }
}