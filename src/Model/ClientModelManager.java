package Model;

import Network.Response;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ClientModelManager implements Model, PropertyChangeSubject
{
  private PropertyChangeSupport support;
  private List<Vinyl> vinyls;
  private ClientConnection client;

  public ClientModelManager(ClientConnection client){
    this.client = client;
  }

  @Override public List<Vinyl> getAllVinyls()
  {
    return List.of();
  }

  @Override public void addVinyl(Vinyl vinyl)
  {
//    client.sendRequest(new Request(vinyl, "add"));
//    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

  @Override public void removeVinyl(Vinyl vinyl)
  {
    client.sendRequest(new Request(vinyl , "remove"));
    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

  @Override public void reserveVinyl(Vinyl vinyl)
  {
    client.sendRequest(new Request(vinyl, "reserve"));
    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

  @Override public void returnVinyl(Vinyl vinyl)
  {
    client.sendRequest(new Request(vinyl, "return"));
    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

  @Override public void borrowVinyl(Vinyl vinyl)
  {
    client.sendRequest(new Request(vinyl, "borrow"));
    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
    listener.propertyChange(new PropertyChangeEvent(this, null, null, vinyls));
  }

  @Override
  public void addPropertyChangeListener(String name, PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name, listener);
    listener.propertyChange(new PropertyChangeEvent(this, name, null, vinyls));
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    support.removePropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
    support.removePropertyChangeListener(name, listener);
  }

  public void updateServerWithVinyl(Vinyl vinyl, String action) {
    try {
      if (!action.equals("borrow") && !action.equals("return") && !action.equals("reserve")) {
        throw new IllegalArgumentException("Invalid action: " + action);
      }

      Request request = new Request(vinyl, action);
      client.sendRequest(request);
      System.out.println("Request sent to server: " + request.getAction());
    } catch (Exception e) {
      System.err.println("Failed to send request: " + e.getMessage());
      support.firePropertyChange("error", null, "Failed to send request: " + e.getMessage());
    }
  }

  public void handleServerResponse(Response response) {
    List<Vinyl> oldVinyls = new ArrayList<>(vinyls);
    vinyls = new ArrayList<>(response.getVinyls());
    support.firePropertyChange("vinyls", oldVinyls, vinyls);
    support.firePropertyChange("message", null, response.getMessage());
  }
}
