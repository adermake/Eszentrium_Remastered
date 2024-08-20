package esze.app;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.UUID;

public class AppCommunicator {

    AppClientSocket appClientSocket;

    public AppCommunicator(AppClientSocket appClientSocket) {
        this.appClientSocket = appClientSocket;
    }

    public void receivedMessage(String message) {
        JSONObject obj = null;
        try {
            obj = (JSONObject) new JSONParser().parse(message);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (obj != null) {
            String purpose = (String) obj.get("p");


            if (purpose.equals("CHECK_CRED")) {
                String username = (String) obj.get("user");
                String password = (String) obj.get("pass");

                boolean authenticated = false;

                OfflinePlayer op = Bukkit.getOfflinePlayer(username);
                if (op.hasPlayedBefore()) {
                    UUID uuid = op.getUniqueId();

                    authenticated = AppUserPasswordUtils.checkPassword(uuid.toString(), password);
                    Bukkit.broadcastMessage(uuid.toString() + " " + authenticated);
                }

                if (authenticated) {
                    appClientSocket.identity = new AppClientIdentity(username);
                }

                JSONObject response = new JSONObject();
                response.put("p", "R_CHECK_CRED");
                response.put("login", authenticated);
                try {
                    appClientSocket.sendMessage(appClientSocket.clientSocket, response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        }

    }


}
