package Model;

import java.io.Serializable;

public interface VinylState extends Serializable
{
    boolean onBorrow (Vinyl vinyl, int userID);
    boolean onReturn (Vinyl vinyl, int userID);
    boolean onReserve(Vinyl vinyl, int userID);
    int[] getUsers();
    String getState();
    String getStateDescription();
}