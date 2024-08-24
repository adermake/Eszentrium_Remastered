package esze.app;

import lombok.Getter;
import lombok.Setter;

import java.io.*;

public class AppClientSocket {

    @Getter
    @Setter
    volatile java.net.Socket clientSocket;
    @Getter
    @Setter
    volatile AppServer server;
    volatile Thread listeningThread;
    volatile AppCommunicator communicator;
    @Getter
    volatile AppClientIdentity identity;

    public AppClientSocket(java.net.Socket clientSocket, AppServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
        communicator = new AppCommunicator(this);
        startListeningForMessages(); //Bug when Connection gets reset
    }

    public void startListeningForMessages() {
        listeningThread = new Thread(() -> {
            while (true) {
                String nachricht;
                try {
                    if (!server.isServerStarted || clientSocket == null || clientSocket.isClosed() || !clientSocket.isConnected()) {
                        identity = null;
                        break;
                    }
                    nachricht = readMessage(clientSocket);
                } catch (Exception e) {
                    //e.printStackTrace();
                    if (identity != null && identity.getUsername() != null)
                        System.out.println("Esze | User " + identity.getUsername() + " hat die App-Verbindung abgebrochen.");
                    identity = null;
                    break;
                }
                if (nachricht != null) {
                    communicator.receivedMessage(nachricht);
                    System.out.println(nachricht); //DEBUG MSG
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

}
