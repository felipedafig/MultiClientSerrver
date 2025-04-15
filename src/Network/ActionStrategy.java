package Network;

import Model.Request;
import Network.Response;
import Model.Vinyl;
import java.util.List;

public interface ActionStrategy {
  Response execute(Request request, List<Vinyl> vinyls);
}