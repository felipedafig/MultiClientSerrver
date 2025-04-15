package Model;

import java.io.Serializable;

public class AvailableState implements VinylState, Serializable
{
    private final static String STATE = "Available";
    private int[] users;

    @Override
    public String getState() {
        return STATE;
    }

    @Override
    public boolean onReturn(Vinyl vinyl, int userID) {
        return false;
    }

    @Override
    public boolean onBorrow(Vinyl vinyl, int userID) {
        vinyl.setState(new BorrowedState(new int[]{userID}));
        return true;
    }

    @Override
    public boolean onReserve(Vinyl vinyl, int userID) {
        vinyl.setState(new ReservedState(new int[]{userID}));
        return true;
    }

    @Override
    public int[] getUsers() {
        return new int[0];
    }

    public String getStateDescription()
    {
        return STATE;
    }
}
