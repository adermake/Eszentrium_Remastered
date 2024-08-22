package esze.utils;

import esze.main.main;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

@AllArgsConstructor
@Data
public class BossbarSpellHUD {

    private Player p;
    private String spellName;
    private String line1;
    private String line2;

    public void show() {
        removeAllBossbars(p);
        Bukkit.broadcastMessage("show");
        TextComponent component = new TextComponent();
        component.setText(CharRepo.HUD_SPELL.literal);
        component.setFont("minecraft:default");
        NamespacedKey namespacedKeyA = new NamespacedKey(main.plugin, UUID.randomUUID().toString());
        NamespacedKey namespacedKeyB = new NamespacedKey(main.plugin, UUID.randomUUID().toString());
        NamespacedKey namespacedKeyC = new NamespacedKey(main.plugin, UUID.randomUUID().toString());
        NamespacedKey namespacedKeyD = new NamespacedKey(main.plugin, UUID.randomUUID().toString());
        BossBar bossBarHUD = Bukkit.createBossBar(namespacedKeyA, TextComponent.toLegacyText(component), BarColor.WHITE, BarStyle.SOLID);
        BossBar bossBarSpellName = Bukkit.createBossBar(namespacedKeyB, spellName, BarColor.WHITE, BarStyle.SOLID);
        BossBar bossBarLine1 = Bukkit.createBossBar(namespacedKeyC, line1, BarColor.WHITE, BarStyle.SOLID);
        BossBar bossBarLine2 = Bukkit.createBossBar(namespacedKeyD, line2, BarColor.WHITE, BarStyle.SOLID);

        bossBarHUD.addPlayer(p);
        bossBarSpellName.addPlayer(p);
        bossBarLine1.addPlayer(p);
        bossBarLine2.addPlayer(p);
    }

    public static void removeAllBossbars() {
        ArrayList<KeyedBossBar> bossBarsToRemove = new ArrayList<>();
        for (Iterator<KeyedBossBar> it = Bukkit.getServer().getBossBars(); it.hasNext(); ) {
            KeyedBossBar bossBar = it.next();
            bossBarsToRemove.add(bossBar);
        }
        for (KeyedBossBar bossBar : bossBarsToRemove) {
            bossBar.setVisible(false);
            bossBar.removeAll();
            Bukkit.getServer().removeBossBar(bossBar.getKey());
        }
    }

    public static void removeAllBossbars(Player p) {
        ArrayList<KeyedBossBar> bossBarsToRemove = new ArrayList<>();
        for (Iterator<KeyedBossBar> it = Bukkit.getServer().getBossBars(); it.hasNext(); ) {
            KeyedBossBar bossBar = it.next();
            if (bossBar.getPlayers().contains(p)) {
                bossBarsToRemove.add(bossBar);
            }
        }
        for (KeyedBossBar bossBar : bossBarsToRemove) {
            bossBar.setVisible(false);
            bossBar.removeAll();
            Bukkit.getServer().removeBossBar(bossBar.getKey());
        }
    }
}
