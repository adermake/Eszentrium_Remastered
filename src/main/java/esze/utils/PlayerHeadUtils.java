package esze.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

public class PlayerHeadUtils {

    private static final HashMap<String, BaseComponent[]> skinCache = new HashMap<>();

    public static BaseComponent[] getHead(String uuid, boolean overlay) {
        if (skinCache.containsKey(uuid)) {
            return skinCache.get(uuid);
        }
        String playerSkinURL = getPlayerSkinFromMojang(uuid);
        if(playerSkinURL == null) {
            // If user has no skin or offline mode -> use default skin
            playerSkinURL = "https://static.planetminecraft.com/files/resource_media/skin/original-steve-15053860.png";
        }
        BaseComponent[] head = toBaseComponent(getPixelColorsFromSkin(playerSkinURL, overlay));
        skinCache.put(uuid, head);
        return head;
    }

    public static String getHeadAsString(String uuid, boolean overlay) {
        return TextComponent.toLegacyText(
                getHead(uuid, overlay)
        );
    }

    private static String getPlayerSkinFromMojang(String uuid) {
        try {
            // Construct the URL for fetching player's profile information from Mojang's session server
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                // Read the response from the connection and append it to the StringBuilder
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close(); // Close the reader
                // Parse the JSON response
                String jsonResponse = response.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray propertiesArray = jsonObject.getJSONArray("properties");

                // Iterate through the properties array to find the textures property
                for (int i = 0; i < propertiesArray.length(); i++) {
                    JSONObject property = propertiesArray.getJSONObject(i);
                    if (property.getString("name").equals("textures")) {
                        String value = property.getString("value");
                        // Decode the Base64 encoded value
                        byte[] decodedBytes = Base64.getDecoder().decode(value);
                        String decodedValue = new String(decodedBytes);
                        JSONObject textureJson = new JSONObject(decodedValue);
                        // Extract and return the URL of the player's skin
                        return textureJson.getJSONObject("textures").getJSONObject("SKIN").getString("url");
                    }
                }
            }
        } catch (IOException | JSONException e) {
            System.out.println("Esze | No skin info for player with UUID: " + uuid);
        }
        return null;
    }

    private static BaseComponent[] toBaseComponent(String[] hexColors) {
        // Check if the retrieved colors array is valid (has at least 64 elements)
        if (hexColors == null || hexColors.length < 64) {
            throw new IllegalArgumentException("Hex colors must have at least 64 elements.");
        } //TODO add error handling

        // Initialize a 2D array to store TextComponents representing each pixel of the players head
        TextComponent[][] components = new TextComponent[8][8];

        for (int i = 0; i < 64; i++) {
            int row = i / 8;
            int col = i % 8;
            char unicodeChar = (char) ('\uF000' + (i % 8) + 1);
            TextComponent component = new TextComponent();

            // Determine the character and styling based on the position of the pixel within the 8x8 grid
            if (i == 7 || i == 15 || i == 23 || i == 31 || i == 39 || i == 47 || i == 55) {
                component.setText(unicodeChar + Character.toString('\uF101'));
            } else if (i == 63) {
                component.setText(Character.toString(unicodeChar));
            } else {
                component.setText(unicodeChar + Character.toString('\uF102'));
            }

            // Set the color of the TextComponent based on the corresponding hexadecimal color
            component.setColor(ChatColor.of(hexColors[i]));
            components[row][col] = component;
        }

        // Create a default TextComponent with no text and the default font.
        TextComponent defaultFont = new TextComponent();
        defaultFont.setText("");
        defaultFont.setFont("minecraft:default");

        // Construct the array of BaseComponents representing the player's head by appending the TextComponents
        BaseComponent[] baseComponents = new ComponentBuilder()
                .append(Arrays.stream(components)
                        .flatMap(Arrays::stream)
                        .toArray(TextComponent[]::new))
                .append(defaultFont)
                .create();

        return baseComponents; // Return the array of BaseComponents representing the players head

    }

    /**
     * Retrieves the pixel colors from the skin image of a Minecraft player.
     * The function fetches the player's skin image from the provided URL and extracts
     * the color information of each pixel from the face region of the skin.
     * Optionally, it can apply an overlay effect by extracting color information
     * from a specified overlay region of the skin image.
     *
     * @param playerSkinUrl The URL of the Minecraft player's skin image.
     * @param overlay       A boolean value indicating whether to apply an overlay effect.
     *                      If set to true, an overlay effect will be applied; otherwise, only the face region colors will be extracted.
     * @return An array of strings representing the hexadecimal color codes of pixels extracted from the player's skin image.
     * Each string in the array represents the color of a single pixel.
     * The array has a fixed length of 64 elements, corresponding to an 8x8 grid of pixels.
     * If any error occurs during the retrieval or processing of the skin image, an empty array is returned.
     */
    private static String[] getPixelColorsFromSkin(String playerSkinUrl, boolean overlay) {
        String[] colors = new String[64];
        try {
            BufferedImage skinImage = ImageIO.read(new URL(playerSkinUrl));

            int faceStartX = 8, faceStartY = 8;
            int faceWidth = 8, faceHeight = 8;

            int overlayStartX = 40;
            int overlayStartY = 8;

            BufferedImage faceImage = skinImage.getSubimage(faceStartX, faceStartY, faceWidth, faceHeight);
            BufferedImage overlayImage = skinImage.getSubimage(overlayStartX, overlayStartY, faceWidth, faceHeight);

            int index = 0;
            for (int x = 0; x < faceHeight; x++) {
                for (int y = 0; y < faceWidth; y++) {
                    int rgbFace = faceImage.getRGB(x, y);
                    int rgbOverlay = overlayImage.getRGB(x, y);

                    // Check if the overlay pixel is not transparent
                    if ((rgbOverlay >> 24) != 0x00 && overlay) {
                        colors[index++] = String.format("#%06X", (rgbOverlay & 0xFFFFFF)); // Use overlay color
                    } else {
                        colors[index++] = String.format("#%06X", (rgbFace & 0xFFFFFF)); // Use face color
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return colors; // Return the array containing the pixel colors
    }

}
