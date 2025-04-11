package Model;

public interface VinylState
{
    boolean onBorrow (Vinyl vinyl, int userID);
    boolean onReturn (Vinyl vinyl, int userID);
    boolean onReserve(Vinyl vinyl, int userID);
    int[] getUsers();
    String getState();
    String getStateDescription();
}