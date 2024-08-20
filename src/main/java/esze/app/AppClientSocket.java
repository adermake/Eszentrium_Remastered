package esze.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;

public class AppClientSocket {
	
	volatile java.net.Socket clientSocket;
	volatile AppServer server;
	volatile Thread listeningThread;
	volatile AppCommunicator communicator;
	volatile AppClientIdentity identity;
	
	public AppClientSocket(java.net.Socket clientSocket, AppServer server) {
		this.clientSocket = clientSocket;
		this.server = server;
		communicator = new AppCommunicator(this);
		startListeningForMessages(); //Bug when Connection gets reset
	}

	public java.net.Socket getClientSocket() {
		return clientSocket;
	}

	public AppServer getServer() {
		return server;
	}

	public void setClientSocket(java.net.Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void setServer(AppServer server) {
		this.server = server;
	}
	
	public void startListeningForMessages() {
		listeningThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
				 	String nachricht = null;
					try {
						if(!server.isServerStarted || clientSocket == null || clientSocket.isClosed() || !clientSocket.isConnected()) {
							identity = null;
							break;
						}
						nachricht = readMessage(clientSocket);
					} catch (Exception e) {
						//e.printStackTrace();
						if(identity!=null && identity.username!=null)
							System.out.println("Esze | User "+identity.username+" hat die App-Verbindung abgebrochen.");
						identity = null;
						break;
					}
					if(nachricht != null) {
						communicator.receivedMessage(nachricht);
						System.out.println(nachricht); //DEBUG MSG
					}
				}
			}
		});
		listeningThread.start();
	}
	
	private String readMessage(java.net.Socket socket) throws IOException {
		BufferedReader bufferedReader =
			new BufferedReader(
				new InputStreamReader(
					socket.getInputStream()));
		char[] buffer = new char[200];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}
	
	public void sendMessage(java.net.Socket socket, String nachricht) throws IOException {
		PrintWriter printWriter =
			new PrintWriter(
				new OutputStreamWriter(
					socket.getOutputStream()));
		printWriter.print(nachricht);
 		printWriter.flush();
	}
	
	public AppClientIdentity getIdentity() {
		return identity;
	}

}
