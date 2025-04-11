package Model;
public class Request
{
  private Vinyl vinyl;
  private String action;

  public Request(Vinyl vinyl, String action)
  {
    {
      this.vinyl = vinyl;
      this.action = action;
    }

  }
  public Vinyl getVinyl()
  {
    return this.vinyl;
  }

  public String getAction()
  {
    return action;
  }
}