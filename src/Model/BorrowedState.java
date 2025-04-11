package Model;

public class BorrowedState implements VinylState
{
    private final static String STATE = "Borrowed";
    private int[] users;

    public BorrowedState(int[] users)
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
            vinyl.setState(new AvailableState());
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
        if(userID != users[0])
        {
            vinyl.setState(new BorrowedAndReservedState(new int[] {users[0], userID}));
            return true;
        }
        return false;
    }

    @Override
    public int[] getUsers() {
        return users;
    }

    public String getStateDescription()
    {
        return "Borrowed by: " + users[0];
    }
}

