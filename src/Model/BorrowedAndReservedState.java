package Model;

import java.io.Serializable;

public class BorrowedAndReservedState implements VinylState, Serializable
{
    private final static String STATE = "Borrowed and Reserved";

    private int[] users;

    public BorrowedAndReservedState(int[] users)
    {
        this.users = users;
    }

    @Override
    public String getState() {
        return STATE;
    }
    @Override
    public boolean onReturn(Vinyl vinyl, int userID) {
        if(userID == users[0]) {
            vinyl.setState(new ReservedState(new int[]{users[1]}));
            return true;
        }
        return false;
    }

    @Override
    public boolean onBorrow(Vinyl vinyl, int userID) {
        return false;
    }

    @Override
    public boolean onReserve(Vinyl vinyl, int userID) {
        return false;
    }

    @Override
    public int[] getUsers() {
        return users;
    }

    public String getStateDescription()
    {
        return "Borrowed by: " + users[0] + " and reserved by: " + users[1];
    }
}
