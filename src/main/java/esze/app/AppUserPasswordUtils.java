package esze.app;

import esze.main.main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AppUserPasswordUtils {

    public static File passwordConfigFile;
    public static FileConfiguration passwordConfig;

    private static FileConfiguration getPasswordConfig() {
        return passwordConfig;
    }

    public static void createPasswordConfig() {
        passwordConfigFile = new File(main.plugin.getDataFolder(), "appPasswords.yml");
        if (!passwordConfigFile.exists()) {
            passwordConfigFile.getParentFile().mkdirs();
            //main.plugin.saveResource("appPasswords.yml", false);
            try {
                passwordConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        passwordConfig = new YamlConfiguration();
        try {
            passwordConfig.load(passwordConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void changeUserPassword(String uuid, String password) {
        getPasswordConfig().set("password." + uuid, AppPasswordCrypt.generateSecurePassword(password, AppPasswordCrypt.standardSalt));
        try {
            getPasswordConfig().save(passwordConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkPassword(String uuid, String password) {
        if (!getPasswordConfig().contains("password." + uuid))
            return false;


        String hashed = getPasswordConfig().getString("password." + uuid);

        boolean passwordMatch = AppPasswordCrypt.verifyUserPassword(password, hashed, AppPasswordCrypt.standardSalt);
        return passwordMatch;
    }

}
