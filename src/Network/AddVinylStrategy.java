package Network;

import Model.Request;
import Network.Response;
import Model.Vinyl;
import java.util.List;

public class AddVinylStrategy implements ActionStrategy {
  @Override
  public Response execute(Request request, List<Vinyl> vinyls) {
    Response response = new Response();
    String message = "Request processed successfully";
    Vinyl newVinyl = request.getVinyl();
    boolean vinylExists = false;

    for (int i = 0; i < vinyls.size(); i++) {
      Vinyl currentVinyl = vinyls.get(i);
      if (currentVinyl.getTitle().equals(newVinyl.getTitle())) {
        vinyls.set(i, newVinyl);
        vinylExists = true;
        break;
      }
    }
    if (!vinylExists) {
      vinyls.add(newVinyl);
    }
    response.setVinyls(vinyls);
    response.setMessage(message);
    return response;
  }
}