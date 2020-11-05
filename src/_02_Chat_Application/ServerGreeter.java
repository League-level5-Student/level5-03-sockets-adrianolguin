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
import javax.swing.JTextArea;

public class ServerGreeter extends Thread {
	JFrame frame = new JFrame("Server");
	JPanel panel = new JPanel();
	JButton button = new JButton("send message");
	JTextArea textArea = new JTextArea();
	
	ServerSocket server;

	Socket socket;

	DataInputStream dis;
	DataOutputStream dos;

	public ServerGreeter() throws IOException {
		server = new ServerSocket(8080);
	}

	void CreateGUI() {
		frame.setVisible(true);
		frame.add(panel);
		panel.add(textArea);
		panel.add(button);

		button.addActionListener(e -> {
			String message = JOptionPane.showInputDialog("What is your message");
			try {
				dos.writeUTF(message);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		frame.pack();
	}

	public void run() {
		boolean done = false;

		while (!done) {
			try {
				JOptionPane.showMessageDialog(null, "Waiting for a client to connect");

				socket = server.accept();

				done = true;

				JOptionPane.showMessageDialog(null, "Client has connected ");

				dis = new DataInputStream(socket.getInputStream());
				System.out.println(dis.readUTF());

				dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF("Welcome Client");

			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "IOException");

				done = true;
			}
		}

		while (done && socket != null) {
			try {
				//JOptionPane.showMessageDialog(null, "From Client:\n" + dis.readUTF());
				textArea.setText(textArea.getText() + "\n" + dis.readUTF());
				frame.pack();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		try {
			ServerGreeter main = new ServerGreeter();
			Thread serverGreeter = new Thread(main);
			serverGreeter.start();
			main.CreateGUI();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}