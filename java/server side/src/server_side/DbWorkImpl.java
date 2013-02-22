package server_side;

//import server.side._DbWorkImplBase;
// File "FooImpl.java"
import com.mycompany.testrest.App;
import java.net.URI;

public class DbWorkImpl extends _DbWorkImplBase /* which implements Foo */ {

    public String hello(){
        return "hello!";
    }
    
    public String addNode(String name) {
        URI firstNode = App.createNode();
        App.addProperty(firstNode, "name", name);
        return "ok";
    }

    public String getNode(String name) {
        return name;
    }

    public String addRelationship(String node1, String node2) {
        return node1;
    }

    public String getRelationship(String node1, String node2) {
        return node1;
    }
}