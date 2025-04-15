package Model;

import Network.Response;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ClientModelManager implements PropertyChangeSubject
{
  private PropertyChangeSupport support;
  private List<Vinyl> vinyls;
  private ClientConnection client;

  public ClientModelManager(){
    this.support = new PropertyChangeSupport(this); // Initialize support
    this.vinyls = new ArrayList<>(); // Initialize vinyls
  }

  public void setClientConnection(ClientConnection client) {
    this.client = client;
  }

 public List<Vinyl> getAllVinyls()
  {
    return vinyls;
  }

  public void addVinyl(Vinyl vinyl, int userID)
  {
    client.sendRequest(new Request(vinyl, "add", userID));
    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

  public void removeVinyl(Vinyl vinyl, int userID)
  {
    client.sendRequest(new Request(vinyl, "remove", userID));
    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

  public void reserveVinyl(Vinyl vinyl, int userID)
  {
    client.sendRequest(new Request(vinyl, "reserve", userID));
    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

  public void returnVinyl(Vinyl vinyl, int userID)
  {
    client.sendRequest(new Request(vinyl, "return", userID));
    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

  public void borrowVinyl(Vinyl vinyl, int userID)
  {
    client.sendRequest(new Request(vinyl, "borrow", userID));
    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

  public void handleServerResponse(Response response) {
    if (response == null || response.getVinyls() == null) {
      System.out.println("Received null response or vinyls list");
      return;
    }

    List<Vinyl> oldVinyls = new ArrayList<>(vinyls); // Keep a copy of the old list
    // Update the local vinyls list by matching Vinyl objects based on title
    List<Vinyl> newVinyls = response.getVinyls();
    for (Vinyl newVinyl : newVinyls) {
      boolean found = false;
      for (int i = 0; i < vinyls.size(); i++) {
        Vinyl existingVinyl = vinyls.get(i);
        if (existingVinyl.getTitle().equals(newVinyl.getTitle())) {
          vinyls.set(i, newVinyl); // Update the existing Vinyl with the new one

          found = true;
          break;
        }
      }
      if (!found) {
        vinyls.add(newVinyl); // Add new Vinyl if it doesn't exist
      }
    }

    // Remove Vinyls that are no longer in the server's list
    vinyls.removeIf(v -> newVinyls.stream().noneMatch(nv -> nv.getTitle().equals(v.getTitle())));

    System.out.println("Updated vinyls list:");
    for (Vinyl v : vinyls) {
      System.out.println("Vinyl: " + v.getTitle() + ", state: " + v.getState());
    }

    support.firePropertyChange("VinylUpdated", oldVinyls, vinyls);
    System.out.println("handleresponse");
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
}