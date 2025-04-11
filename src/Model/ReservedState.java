package Model;

public class ReservedState implements VinylState
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
        if(userID == users[0]) {
            vinyl.setState(new BorrowedState(new int[]{users[0]}));
            return true;
        }
        return false;
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
