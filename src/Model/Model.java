package Model;

import java.util.List;

public interface Model
{
  List<Vinyl> getAllVinyls();
  void addVinyl(Vinyl vinyl, int userID);
  void removeVinyl(Vinyl vinyl, int userID);
  void reserveVinyl(Vinyl vinyl, int userID);
  void returnVinyl(Vinyl vinyl, int userID);
  void borrowVinyl(Vinyl vinyl, int userID);
}
