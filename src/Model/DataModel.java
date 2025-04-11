package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class DataModel implements PropertyChangeSubject
{
  private ObservableList<Vinyl> vinyls;
  private PropertyChangeSupport support;

  public DataModel(){

    this.vinyls = FXCollections.observableArrayList();
    vinyls.add(new Vinyl("The Dark Side of the Moon", "Pink Floyd", 1973));
    vinyls.add(new Vinyl("Abbey Road", "The Beatles", 1969));
    vinyls.add(new Vinyl("Led Zeppelin IV", "Led Zeppelin", 1971));
    vinyls.add(new Vinyl("The Wall", "Pink Floyd", 1979));
    vinyls.add(new Vinyl("Sgt. Pepper's Lonely Hearts Club Band", "The Beatles", 1967));
    vinyls.add(new Vinyl("Revolver", "The Beatles", 1966));
    vinyls.add(new Vinyl("The White Album", "The Beatles", 1968));
    vinyls.add(new Vinyl("Physical Graffiti", "Led Zeppelin", 1975));
    vinyls.add(new Vinyl("Led Zeppelin II", "Led Zeppelin", 1969));
    this.support = new PropertyChangeSupport(this);

  }

  public synchronized void borrowVinyl(Vinyl vinyl, int userID) {

    vinyl.onBorrow(userID);
    support.firePropertyChange("VinylUpdated", null, vinyls);
  }

public synchronized void returnVinyl(Vinyl vinyl, int userID) {

  boolean isSuccessful = vinyl.onReturn(userID);
  if(isSuccessful && vinyl.getFlagged())
  {
    removeVinyl(vinyl);
  }
  support.firePropertyChange("VinylUpdated", null, vinyls);
}


public synchronized void reserveVinyl(Vinyl vinyl, int userID) {

  vinyl.onReserve(userID);
  support.firePropertyChange("VinylUpdated", null, vinyls);
}


public void removeVinyl(Vinyl vinyl){
    Object removed = vinyls.remove(vinyl);
    support.firePropertyChange("VinylUpdated", null, vinyls);
}

public ObservableList<Vinyl> getVinyls()
{
  return vinyls;
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
