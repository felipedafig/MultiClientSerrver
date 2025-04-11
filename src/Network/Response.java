package Network;

import Model.Vinyl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable {
  private List<Vinyl> vinyls;
  private String message;

  public Response() {
    this.vinyls = new ArrayList<>();
    this.message = "";
  }

  public Response(List<Vinyl> vinyls, String message) {
    this.vinyls = new ArrayList<>(vinyls);
    this.message = message;
  }

  public List<Vinyl> getVinyls() {
    return new ArrayList<>(vinyls);
  }

  public void setVinyls(List<Vinyl> vinyls) {
    this.vinyls = new ArrayList<>(vinyls);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}