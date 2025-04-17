package Model;

import java.io.Serializable;

public class Vinyl implements Serializable
{

  private String title;
  private String artist;
  private int releaseYear;
  private boolean isForRemoval;
  private VinylState currentState;


  public Vinyl(String title, String artist, int releaseYear)
  {
    this.title = title;
    this.artist = artist;
    this.releaseYear = releaseYear;

    this.currentState = new AvailableState();
    this.isForRemoval = false;

  }

  public boolean onReturn(int userID)
  {
    return currentState.onReturn(this, userID);
  }

  public boolean onReserve(int userID)
  {
    return currentState.onReserve(this, userID);
  }

  public boolean onBorrow(int userID)
  {
    return currentState.onBorrow(this, userID);
  }

  public void flagForRemoval () {
    isForRemoval = true;
  }

  public boolean getFlagged()
  {
    return isForRemoval;
  }

  public String getState () {
    return currentState.getState();
  }

  public String getStateDescription () {
    return currentState.getStateDescription();
  }

  void setState (VinylState newState){
    this.currentState = newState;
  }

  public String getTitle () {
    return title;
  }

  public String getArtist () {
    return artist;
  }

  public int getReleaseYear () {
    return releaseYear;
  }

}



