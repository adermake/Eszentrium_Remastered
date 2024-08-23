package esze.utils;

import esze.main.main;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.craftbukkit.v1_21_R1.boss.CraftBossBar;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class BossbarSpellHUD {

    private static final int MAX_ROW_LENGTH = 34;
    private static final String DIVIDER = " | ";

    @NonNull
    private Player p;
    @NonNull
    private Spell spell;
    @NonNull
    private boolean spellIsRefined;

    private Integer bukkitScheduler;
    private int row1Scroll;
    private int row2Scroll;
    private BossBar bossbar1;
    private BossBar bossbar2;

    private static ArrayList<BossbarSpellHUD> bossbarSpellHUDs = new ArrayList<>();


    public void show() {
        removeAllBossbars(p);

        TextComponent component = new TextComponent();
        component.setText(CharRepo.HUD_SPELL.literal);

        String _fDesc = spellIsRefined ? spell.getSpellDescription().getRefinedFDescription() : spell.getSpellDescription().getNormalFDescription();
        String _shiftDesc = spellIsRefined ? spell.getSpellDescription().getRefinedShiftDescription() : spell.getSpellDescription().getNormalShiftDescription();

        if (_fDesc != null && _fDesc.length() > MAX_ROW_LENGTH) {
            _fDesc += DIVIDER;
        }
        if (_shiftDesc != null && _shiftDesc.length() > MAX_ROW_LENGTH) {
            _shiftDesc += DIVIDER;
        }
        String fDesc = _fDesc;
        String shiftDesc = _shiftDesc;

        NamespacedKey namespacedKeyA = new NamespacedKey(main.plugin, UUID.randomUUID().toString());
        NamespacedKey namespacedKeyB = new NamespacedKey(main.plugin, UUID.randomUUID().toString());
        NamespacedKey namespacedKeyC = new NamespacedKey(main.plugin, UUID.randomUUID().toString());
        NamespacedKey namespacedKeyD = new NamespacedKey(main.plugin, UUID.randomUUID().toString());
        BossBar bossBarHUD = Bukkit.createBossBar(namespacedKeyA, TextComponent.toLegacyText(component), BarColor.WHITE, BarStyle.SOLID);
        bossBarHUD.addPlayer(p);
        BossBar bossBarSpellName = Bukkit.createBossBar(namespacedKeyB, "", BarColor.WHITE, BarStyle.SOLID);
        bossBarSpellName.addPlayer(p);

        Style style = Style.EMPTY.withFont(ResourceLocation.withDefaultNamespace("small"));
        ((CraftBossBar)bossBarSpellName).getHandle().setName(Component.literal(spell.getName().toUpperCase()).withStyle(style));

        if (fDesc != null) {
            bossbar1 = Bukkit.createBossBar(namespacedKeyC, "", BarColor.WHITE, BarStyle.SOLID);
            bossbar1.addPlayer(p);
        }
        if (shiftDesc != null) {
            bossbar2 = Bukkit.createBossBar(namespacedKeyD, "", BarColor.WHITE, BarStyle.SOLID);
            bossbar2.addPlayer(p);
        }

        bukkitScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, new Runnable() {
            @Override
            public void run() {

                if (fDesc != null) {
                    String fOutput = fDesc.substring(row1Scroll, Math.min(row1Scroll + MAX_ROW_LENGTH, fDesc.length()));
                    if (fOutput.length() < MAX_ROW_LENGTH && fDesc.length() > MAX_ROW_LENGTH) {
                        fOutput += fDesc.substring(0, Math.min(MAX_ROW_LENGTH - fOutput.length(), fDesc.length()));
                    }
                    TextComponent componentF = new TextComponent();
                    componentF.setText(CharRepo.TAG_KEYBIND_F.literal + " §0" + fOutput);
                    bossbar1.setTitle(TextComponent.toLegacyText(componentF));

                    if (fDesc.length() > MAX_ROW_LENGTH) {
                        row1Scroll++;
                        if (row1Scroll >= fDesc.length()) {
                            row1Scroll = 0;
                        }
                    }
                }

                if (shiftDesc != null) {
                    String shiftOutput = shiftDesc.substring(row2Scroll, Math.min(row2Scroll + MAX_ROW_LENGTH, shiftDesc.length()));
                    if (shiftOutput.length() < MAX_ROW_LENGTH && shiftDesc.length() > MAX_ROW_LENGTH) {
                        shiftOutput += shiftDesc.substring(0, Math.min(MAX_ROW_LENGTH - shiftOutput.length(), shiftDesc.length()));
                    }
                    TextComponent componentShift = new TextComponent();
                    componentShift.setText(CharRepo.TAG_KEYBIND_SHIFT.literal + " §0" + shiftOutput);
                    bossbar2.setTitle(TextComponent.toLegacyText(componentShift));

                    if (shiftDesc.length() > MAX_ROW_LENGTH) {
                        row2Scroll++;
                        if (row2Scroll >= shiftDesc.length()) {
                            row2Scroll = 0;
                        }
                    }
                }


            }
        }, 0, 3);
    }

    public void stopScrolling() {
        if (bukkitScheduler != null) {
            Bukkit.getScheduler().cancelTask(bukkitScheduler);
            bukkitScheduler = null;
        }
    }

    public static void removeAllBossbars() {
        for (BossbarSpellHUD bossbarSpellHUD : bossbarSpellHUDs) {
            bossbarSpellHUD.stopScrolling();
        }
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
        for (BossbarSpellHUD bossbarSpellHUD : bossbarSpellHUDs) {
            if (bossbarSpellHUD.getP().equals(p)) {
                bossbarSpellHUD.stopScrolling();
            }
        }

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
