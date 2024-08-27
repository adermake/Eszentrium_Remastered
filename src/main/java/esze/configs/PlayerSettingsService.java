package esze.configs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import esze.configs.entities.Cosmetic;
import esze.configs.entities.CosmeticType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PlayerSettingsService {

    private static final String FILE_PATH = "plugins/Eszentrium/playerSettings.json";
    private static ArrayList<PlayerSettings> playerSettings;
    private static Gson gson;

    public static PlayerSettings getPlayerSettings(Player player) {
        return getPlayerSettings(player.getUniqueId().toString());
    }

    public static PlayerSettings getPlayerSettings(String playerUuid) {
        loadSettings();
        for (PlayerSettings playerSetting : playerSettings) {
            if (playerSetting.getPlayerUuid().equals(playerUuid)) {
                return playerSetting;
            }
        }
        PlayerSettings playerSetting = new PlayerSettings(playerUuid);
        playerSettings.add(playerSetting);
        save();
        return playerSetting;
    }

    private static void loadSettings() {
        if(playerSettings == null) {
            gson = new Gson();

            try {
                JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
                Type typeOfT = TypeToken.getParameterized(List.class, PlayerSettings.class).getType();
                playerSettings = gson.fromJson(reader, typeOfT);
            } catch (FileNotFoundException e) {
                playerSettings = new ArrayList<>();
                save();
            }
        }
    }

    private static void save() {
        loadSettings();
        Path p = Paths.get(FILE_PATH).getParent();
        try {
            Files.createDirectories(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(playerSettings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiredArgsConstructor
    public static class PlayerSettings {
        @Getter
        private final String playerUuid;
        @Getter
        private boolean musicEnabled = true;
        private HashMap<CosmeticType, Cosmetic> cosmetics = new HashMap<>();

        public Cosmetic getCosmetic(CosmeticType cosmeticType) {
            return cosmetics.getOrDefault(cosmeticType, null);
        }

        public void setCosmetic(CosmeticType cosmeticType, Cosmetic cosmetic) {
            cosmetics.put(cosmeticType, cosmetic);
            save();
        }

        public void setMusicEnabled(boolean musicEnabled) {
            this.musicEnabled = musicEnabled;
            save();
        }

    }




}
