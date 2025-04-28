package Network;

import Model.Vinyl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private ArrayList<ServerConnection> connections;
    public ConnectionPool() {
        connections = new ArrayList<ServerConnection>();
    }

    public void addConnection(ServerConnection connection) {
        connections.add(connection);
    }

    public void broadcast(List<Vinyl> updatedVinyls)
    {
        for(ServerConnection connection : connections)
        {
            Response response = new Response();
            response.setVinyls(updatedVinyls);
            response.setMessage("The list has been altered by other users");
            try {
                connection.sendResponse(response);
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
