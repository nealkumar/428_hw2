package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    //Other options....
//  private DataInputStream input; 
//  private BufferedReader out;
//   private DataOutputStream out; 
    
    public Client(String dstAddress, int port) {
        try { 
            socket = new Socket(dstAddress, port); 
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream());

        } catch(Exception e) {
        	System.out.println("Failed to create socket: " + e);
        }
  
        getInput(); //runs until Exit is typed
        closeSocket();
    }
    
    /**
     * Reads input from the user until "Exit" is typed
     */
    public void getInput() {
        //Read lines from the keyboard
        String line = ""; 
        while (!line.equals("Exit")) {
            System.out.println("Type text to send to server."); 
            try { 
                line = in.readLine();
                System.out.println("Transmitting: " + line);
                out.write(line+"\n");
                out.flush(); //for the receiver to have access to the buffer, the sender must flush it
            } catch(Exception e) { 
                System.out.println("Failed to write to socket: " + e); 
            } 
        }
        
        //Close readers/writers, keyboard entry is done
        try { 
            in.close(); 
            out.close(); 
        } catch(Exception e) { 
            System.out.println("Failed to reader or writer: " + e); 
        }
    }
    
    /**
     * Politely closes the socket to free up system resources on both ends.
     */
    public void closeSocket() {
        try { socket.close(); } 
        catch(Exception e) { System.out.println("Failed to close socket: " + e); }
    }
}
