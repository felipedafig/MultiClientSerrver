package Network;

import Model.Request;
import Network.Response;
import Model.Vinyl;
import java.util.List;

public class BorrowVinylStrategy implements ActionStrategy {
  @Override
  public Response execute(Request request, List<Vinyl> vinyls) {
    Response response = new Response();
    String message = "Request processed successfully";
    int userID = request.getUserID();
    Vinyl requestedVinyl = request.getVinyl();

    for (Vinyl currentVinyl : vinyls) {
      if (currentVinyl.getTitle().equals(requestedVinyl.getTitle())) {
        boolean success = currentVinyl.onBorrow(userID);
        System.out.println(currentVinyl.getTitle() + " borrowed");
        if (!success) {
          message = "Vinyl cannot be borrowed";
        }
        break;
      }
    }
    response.setVinyls(vinyls); //update the vinyls in the response
    response.setMessage(message);
    return response;
  }
}