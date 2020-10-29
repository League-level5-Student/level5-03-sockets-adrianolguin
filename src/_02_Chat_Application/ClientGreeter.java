package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ClientGreeter {
	public static void main(String[] args) {

		String address = "192.168.0.148";
		int port = 8080;

		try {
			Socket socket = new Socket(address, port);

			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			dos.writeUTF("Greetings Server, from Client!");

			DataInputStream dis = new DataInputStream(socket.getInputStream());

			JOptionPane.showMessageDialog(null, "From Server: \n" + dis.readUTF());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
