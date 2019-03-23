package neal_kumar;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * My main assumption going in is that I did not want to alter any of the
 * existing client class files. Therefore, no Client/DriverClient class files
 * are included + in this project.
 * 
 * This Server accepts multiple Sockets. The main change is the infinite
 * while-loop which allows for multiple clients to connect, and spawns a new
 * thread for each one.
 * 
 * @author Owner
 *
 */
public class Server {

	private Socket socket = null;
	private ServerSocket server = null;
	private Color color;

	// Import Constants
	private int port = ServerConstants.PORT;
	private int panelWidth = ServerConstants.PANEL_WIDTH;
	private int panelHeight = ServerConstants.PANEL_HEIGHT;
	private Color panelBackColor = ServerConstants.PANEL_BACK_COLOR;

	public void init() {
		invokeSwingThread(); // creates new JPanel
		try {
			server = new ServerSocket(port);
			System.out.println("New server created.\nWaiting for connection on Port 6112...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Infinite while-loop to accept new clients. Spawns a new thread for each new
		// client connected.
		while (true) {
			try {
				socket = server.accept(); // accepts new client
				color = getRandomColor(); // assigns random color upon connection
				System.out.println("\nClient connected.");
				System.out.println("Client color in RGB = (" + color.getRed() + ", " + color.getGreen() + ", "
						+ color.getBlue() + ")");
				new ServerThread(socket, color).start(); // spawns new client thread
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Generates random color upon new client acceptance
	 * 
	 * @return - Generated color
	 */
	private Color getRandomColor() {
		Random r = new Random();
		float red = r.nextFloat(); // randomizes out of 256
		float green = r.nextFloat();
		float blue = r.nextFloat();
		return new Color(red, green, blue);
	}

	/**
	 * Creates new JPanel
	 */
	private void invokeSwingThread() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Panel myPanel = new Panel(panelWidth, panelHeight, panelBackColor);
				JFrame frame = new JFrame("Server View");
				frame.add(myPanel);
				frame.setDefaultCloseOperation(3); // 3 for std exit behavior
				frame.pack();
				frame.setLocationRelativeTo(null); // centers on the screen... doesn't work in multi-monitor
													// configuration however
				frame.setVisible(true);
			}

		});
	}
}

/**
 * This is the custom thread class to handle I/O operations upon new client
 * acceptance. Going forward, this should be in a ThreadPool using
 * ExecutorService.
 * 
 * @author Owner
 *
 */
final class ServerThread extends Thread {
	private String readLine = "";
	private BufferedReader in = null;
	private PrintWriter outputWriter = null;
	private Socket socket = null;

	private Color color = null;

	public ServerThread(Socket socket, Color color) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.color = color;
	}

	@Override
	public synchronized void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputWriter = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (!readLine.equalsIgnoreCase("Exit")) {
				readLine = in.readLine();
				// Below commences the Strategy pattern
				if (readLine.charAt(0) == 'a') {
					new AddToPanelStrategy(readLine, color);
				} else if (readLine.charAt(0) == 'd') {
					System.out.println("D read.");
					new DeleteFromPanelStrategy(readLine, color);
				} else {
					System.out.println("Invalid coordinates for JPanel.");
				}
			}
		} catch (IOException e) {
			System.out.println("Client " + this.getName() + " terminated abruptly.");
		} catch (NullPointerException e) {
			System.out.println("Client " + this.getName() + " Closed");
			e.printStackTrace();
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			// Close all I/O components
			try {
				System.out.println("Connection Closing...");
				if (in != null) {
					in.close();
					System.out.println("Buffered Reader Input Stream Closed.");
				}
				if (outputWriter != null) {
					outputWriter.close();
					System.out.println("Print Writer Closed.");
				}
				if (socket != null) {
					socket.close();
					System.out.println("Socket Closed.");
				}
			} catch (IOException ie) {
				System.out.println("Socket Close Error.");
			}
		}
	}
}
