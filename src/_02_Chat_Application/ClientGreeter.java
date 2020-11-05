package _02_Chat_Application;

import java.awt.HeadlessException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClientGreeter {

	JFrame frame = new JFrame("Client");
	JPanel panel = new JPanel();
	JButton button = new JButton("Send Message");
	
	static Socket socket;
	
	static DataOutputStream dos;
	
	 static DataInputStream dis;
	
	void createGUI() {
		frame.setVisible(true);
		frame.add(panel);
		panel.add(button);
		
		button.addActionListener(e -> {
			String message = JOptionPane.showInputDialog("whats your message");
			try {
				dos.writeUTF(message);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		frame.pack();
	}
	
	public static void main(String[] args) {
		String address = "192.168.0.148";
		
		int port = 8080;
		
		try {
			 socket = new Socket(address, port);
			
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF("Hello Server");
			
			dis = new DataInputStream(socket.getInputStream());
			System.out.println(dis.readUTF());
						
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		ClientGreeter main = new ClientGreeter();
		main.createGUI();
		
		while(socket != null) {
			try {
				JOptionPane.showMessageDialog(null, "From Server:\n" + dis.readUTF());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		
	}
}