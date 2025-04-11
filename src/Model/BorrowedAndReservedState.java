package Model;

public class BorrowedAndReservedState implements VinylState
{
    private final static String STATE = "Borrowed and reserved";

    // The array should always be of size 2 - on index 0 is the borrower and on index 1 is the reserver
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
