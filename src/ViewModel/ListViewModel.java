package ViewModel;

import Model.ClientModelManager;
import Model.Vinyl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class ListViewModel
{
  private ClientModelManager model;
  private ObservableList<Vinyl> vinyls;

  public ListViewModel(ClientModelManager model) {
    this.model = model;
    this.vinyls = FXCollections.observableArrayList();
    refresh();
    model.addPropertyChangeListener("VinylUpdated", this::update);
  }

  private void update(PropertyChangeEvent propertyChangeEvent) {
    List<Vinyl> newVinylsList = (List<Vinyl>) propertyChangeEvent.getNewValue();
    System.out.println("ViewModel update received:");
    for (Vinyl v : newVinylsList) {
      System.out.println("Vinyl: " + v.getTitle() + ", state: " + v.getState());
    }
    ObservableList<Vinyl> newVinyls = FXCollections.observableArrayList(newVinylsList);
    vinyls.setAll(newVinyls);
  }

  public void refresh() {
    vinyls.clear();
    vinyls.addAll(model.getAllVinyls());
  }

  public ObservableList<Vinyl> getVinyls() {
    return vinyls;
  }

  public void borrowVinyl(Vinyl vinyl, int userID) {
    if (vinyl != null) {
      model.borrowVinyl(vinyl, userID);

    }
  }

  public void returnVinyl(Vinyl vinyl, int userID) {
    model.returnVinyl(vinyl, userID);
  }

  public void reserveVinyl(Vinyl vinyl, int userID) {
    if (vinyl != null) {
      model.reserveVinyl(vinyl, userID);
    }
  }

  public void remove(Vinyl vinyl) {
    if (vinyl != null && vinyl.getState().equals("Available")) {
      model.removeVinyl(vinyl, 0); // Default userID for remove
    }
    else
    {
      vinyl.flagForRemoval();
    }
  }

  public ClientModelManager getModel() {
    return model;
  }
}