import java.io.*;

public interface Sender{

    /**
     * Sends a file to a bound host.
     * Reads the contents of the specified file, and sends it to the host 
     * and port specified elswhere.
     *
     * @param theFile  the file to send
     */
    public void sendFile(File theFile) throws IOException;
    

}