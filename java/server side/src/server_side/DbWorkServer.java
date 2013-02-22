package server_side;

// File "FooServer.java"
public class DbWorkServer {
  // The following is based on the program in lib/ic/examples/java-client-server/server.java
  static java.lang.String snode = "javaserver";
  static java.lang.String cookie = "thisiscookie";

  public static void main(String[] args) throws java.io.IOException, com.ericsson.otp.erlang.OtpAuthException {

    com.ericsson.otp.erlang.OtpServer self = new com.ericsson.otp.erlang.OtpServer(snode, cookie);

    System.err.print("Registering with EPMD...");
    boolean res = self.publishPort();
    if (!res) throw new RuntimeException("Node name was already taken.");
    System.err.println("done");

    do {
      try {
        com.ericsson.otp.erlang.OtpConnection connection = self.accept();
        System.err.println("Incoming connection.");
        try {
          handleConnection(connection);
        } catch (Exception e) {
          System.err.println("Server terminated: "+e);
        } finally {
          connection.close();
          System.err.println("Connection terminated.");
        }
      } catch (Exception e) {
        System.err.println("Error accepting connection: "+e);
      }
    } while (true);
  }

  static void handleConnection(com.ericsson.otp.erlang.OtpConnection connection) throws Exception {
    while (connection.isConnected() == true) {
      DbWorkImpl srv = new DbWorkImpl();
      com.ericsson.otp.erlang.OtpInputStream request = connection.receiveBuf();
      try {
        com.ericsson.otp.erlang.OtpOutputStream reply = srv.invoke(request);
        if (reply != null) {
          connection.sendBuf(srv.__getCallerPid(), reply);
        }
      } catch (Exception e) {
        System.err.println("Server exception: "+e); 
        e.printStackTrace(System.err);
        handleException(e, connection, null);
      }
    }
  }

  static void handleException(Exception e, com.ericsson.otp.erlang.OtpConnection connection, com.ericsson.otp.ic.Environment env) throws Exception {
    // We'll improve on this later...
    throw e;
  }
}