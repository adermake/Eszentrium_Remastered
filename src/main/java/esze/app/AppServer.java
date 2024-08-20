package esze.app;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;

public class AppServer {
	
	public boolean isServerStarted = false;
	private int serverPort = 11111;
	public Thread serverThread = null;
	private java.net.ServerSocket serverSocket;
	
	public ArrayList<AppClientSocket> clientSockets = new ArrayList<AppClientSocket>();
	
	public void startServer() {
		
		serverThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//CREATE SERVER SOCKET
				try {
					serverSocket = new java.net.ServerSocket(serverPort);
					serverSocket.setReuseAddress(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				isServerStarted = true;
				
				//LET ANY CLIENT CONNECT AT ANY TIME
				while(true) {
					try {
						if(!isServerStarted || serverSocket == null || serverSocket.isClosed()) {
							isServerStarted = false;
							break;
						}
						java.net.Socket client = serverSocket.accept();
						Bukkit.broadcastMessage(client.getInetAddress().getHostAddress() + " is now connected to server.");
					 	AppClientSocket clientSocket = new AppClientSocket(client, AppServer.this);
					 	clientSockets.add(clientSocket);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		serverThread.start();
		
	}
	
	public void shutdownServer() {
		try {
			serverSocket.setReuseAddress(true);
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(AppClientSocket acs : clientSockets) {
			try {
				acs.clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
