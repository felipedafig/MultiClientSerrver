package Model;

import java.io.Serializable;

public class ReservedState implements VinylState, Serializable
{
    private final static String STATE = "Reserved";
    private int[] users;

    public ReservedState(int[] users)
    {
        this.users = users;
    }

    @Override
    public String getState() {
        return STATE;
    }
    @Override
    public boolean onBorrow(Vinyl vinyl, int userID) {
            vinyl.setState(new BorrowedState(new int[]{users[0]}));
            return true;
    }

    @Override
    public boolean onReserve(Vinyl vinyl, int userID) {
        return false;
    }
    @Override
    public boolean onReturn(Vinyl vinyl, int userID) {
        return false;
    }

    @Override
    public int[] getUsers() {
        return users;
    }

    public String getStateDescription()
    {
        return "Reserved by: " + users[0];
    }
}
