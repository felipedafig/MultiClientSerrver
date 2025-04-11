package Model;

import java.util.List;

public interface Model
{
  List<Vinyl> getAllVinyls();
  void borrowVinyl(Vinyl vinyl);
  void returnVinyl(Vinyl vinyl);
  void reserveVinyl(Vinyl vinyl);
  void removeVinyl(Vinyl vinyl);
  void addVinyl(Vinyl vinyl);
}
