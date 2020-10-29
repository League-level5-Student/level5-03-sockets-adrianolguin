package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ServerGreeter extends Thread {

	ServerSocket server;
	Socket socket;
	DataOutputStream dos;
	DataInputStream dis;

	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JButton message = new JButton("Send Message");

	public ServerGreeter() throws IOException {

		server = new ServerSocket(8080);

	}

	public void run() {
		boolean done = true;
		while (done) {
			try {
				JOptionPane.showMessageDialog(null, "Waiting for a client to connect");

				socket = server.accept();

				JOptionPane.showMessageDialog(null, "Client has connected ");

				dis = new DataInputStream(socket.getInputStream());

				JOptionPane.showMessageDialog(null, "From Client: \n" + dis.readUTF());

				dos = new DataOutputStream(socket.getOutputStream());

				dos.writeUTF("Welcome Client!");

			} catch (IOException e) {
				e.printStackTrace();

				JOptionPane.showMessageDialog(null, "IOException");

				done = false;

			}
		}

	}

	public void createGUI() {
		frame.setVisible(true);
		frame.add(panel);
		panel.add(message);

		message.addActionListener(e -> {
			String message = JOptionPane.showInputDialog("Whats your message");
			try {
				dos.writeUTF(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

	}

	public static void main(String[] args) {
		try {
			ServerGreeter main = new ServerGreeter();
			Thread serverGreeter = new Thread(main);
			serverGreeter.start();
			main.createGUI();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

}
