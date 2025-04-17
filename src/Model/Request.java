package Model;

import java.io.Serializable;

public class Request implements Serializable
{
  private Vinyl vinyl;
  private String action;
  private int userID;

  public Request(Vinyl vinyl, String action, int userID)
  {
    {
      this.vinyl = vinyl;
      this.action = action;
      this.userID = userID;

    }

  }
  public Vinyl getVinyl()
  {
    return this.vinyl;
  }

  public String getAction() {return this.action;}

  public int getUserID() {return this.userID;}

}