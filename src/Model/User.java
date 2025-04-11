package Model;

import java.util.List;
import java.util.Random;


public class User implements Runnable
{

  private DataModel model;
  private Random random = new Random();
  private int userID;

  public User(DataModel model, int userID){
    this.model = model;
    this.userID = userID;
  }

  @Override
  public void run() {
    while (true){
      try {
        Thread.sleep(random.nextInt(5000) + 2000);
        List<Vinyl> vinyls = model.getVinyls();

        if(vinyls.size() == 0)
        {
          Thread.interrupted();
        }
        Vinyl vinyl = vinyls.get(random.nextInt(vinyls.size()));

        int action = random.nextInt(3);

        switch (action){
          case 0:
            if (!vinyls.isEmpty())
            {
              model.borrowVinyl(vinyl, userID);
              System.out.println("User " + userID + "  tried to borrow vinyl: " + vinyl.getTitle());
            }
            break;
          case 1:
            if (!vinyls.isEmpty())
            {
              model.reserveVinyl(vinyl, userID);
              System.out.println("User " + userID + "  tried to reserve vinyl: " + vinyl.getTitle());
            }
            break;
          case 2:
            if (!vinyls.isEmpty())
            {
              model.returnVinyl(vinyl, userID);
              System.out.println("User " + userID + "  tried to return vinyl: " + vinyl.getTitle());
            }
            break;
        }
      } catch (InterruptedException e) {
        System.out.println("No more vinyls to borrow");
      }
    }
  }
}
