package Network;

import Model.Request;
import Network.Response;
import Model.Vinyl;
import Network.ActionStrategy;
import Network.BorrowVinylStrategy;
import Network.ReturnVinylStrategy;
import Network.ReserveVinylStrategy;
import Network.RemoveVinylStrategy;
import Network.AddVinylStrategy;

import java.util.List;
import java.util.ArrayList;

public class ServerModelManager {
  private List<Vinyl> vinyls;
  private ActionStrategy strategy;

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

  public void setStrategy(ActionStrategy strategy) {
    this.strategy = strategy;
  }

  public ActionStrategy getStrategy() {
    return this.strategy;
  }

  public Response processRequest(Request request) {
    String action = request.getAction().toLowerCase();


    if (action.equals("borrow")) {
      setStrategy(new BorrowVinylStrategy());
    } else if (action.equals("return")) {
      setStrategy(new ReturnVinylStrategy());
    } else if (action.equals("reserve")) {
      setStrategy(new ReserveVinylStrategy());
    } else if (action.equals("remove")) {
      setStrategy(new RemoveVinylStrategy());
    } else if (action.equals("add")) {
      setStrategy(new AddVinylStrategy());
    }

    Response response = getStrategy().execute(request, vinyls);
    broadcast(vinyls);

    return response;
  }

  public List<Vinyl> getVinyls() {
    return vinyls;
  }

  private void broadcast(List<Vinyl> updatedVinyls) {
    System.out.println("Broadcasting updated vinyl list to all clients: " + updatedVinyls);
  }
}