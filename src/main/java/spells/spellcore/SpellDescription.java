package spells.spellcore;

import esze.utils.CharRepo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SpellDescription {

    private String normalDescription;
    private String refinedDescription;

    private String normalFDescription;
    private String refinedFDescription;

    private String normalShiftDescription;
    private String refinedShiftDescription;

    private int cooldown;

    public List<String> getNormalLore() {
        ArrayList<String> lore = new ArrayList<>();
        final int maxLineLength = 27;

        List<String> normalDescriptionLines = normalDescription != null ? splitTextIntoLines(normalDescription, maxLineLength, "") : List.of();
        List<String> normalFDescriptionLines = normalFDescription != null ? splitTextIntoLines(normalFDescription, maxLineLength, "§f" + CharRepo.TAG_KEYBIND_F.literal + "§7") : List.of();
        List<String> normalShiftDescriptionLines = normalShiftDescription != null ? splitTextIntoLines(normalShiftDescription, maxLineLength, "§f" + CharRepo.TAG_KEYBIND_SHIFT.literal + "§7") : List.of();

        lore.addAll(normalDescriptionLines);
        if(!normalDescriptionLines.isEmpty()) lore.add(" ");
        lore.addAll(normalFDescriptionLines);
        if(!normalFDescriptionLines.isEmpty()) lore.add(" ");
        lore.addAll(normalShiftDescriptionLines);
        if(!normalShiftDescriptionLines.isEmpty()) lore.add(" ");
        lore.add("§eCooldown: " + cooldown / 20 + "s");

        return lore.stream().map(line -> "§7" + line).toList();
    }

    public List<String> getRefinedLore() {
        ArrayList<String> lore = new ArrayList<>();
        final int maxLineLength = 27;

        List<String> refinedDescriptionLines = refinedDescription != null ? splitTextIntoLines(refinedDescription, maxLineLength, "") : List.of();
        List<String> refinedFDescriptionLines = refinedFDescription != null ? splitTextIntoLines(refinedFDescription, maxLineLength, "§f" + CharRepo.TAG_KEYBIND_F.literal + "§7") : List.of();
        List<String> refinedShiftDescriptionLines = refinedShiftDescription != null ? splitTextIntoLines(refinedShiftDescription, maxLineLength, "§f" + CharRepo.TAG_KEYBIND_SHIFT.literal + "§7") : List.of();

        lore.addAll(refinedDescriptionLines);
        if(!refinedDescriptionLines.isEmpty()) lore.add(" ");
        lore.addAll(refinedFDescriptionLines);
        if(!refinedFDescriptionLines.isEmpty()) lore.add(" ");
        lore.addAll(refinedShiftDescriptionLines);
        if(!refinedShiftDescriptionLines.isEmpty()) lore.add(" ");
        lore.add("§eCooldown: " + cooldown / 20 + "s");

        return lore.stream().map(line -> "§7" + line).toList();
    }

    private ArrayList<String> splitTextIntoLines(String text, int maxLineLength, String prefix) {
        ArrayList<String> lines = new ArrayList<>();
        String line = prefix;
        for(String word : text.split(" ")) {
            if(line.replace(prefix, "").length() >= maxLineLength) {
                lines.add(line.trim());
                line = "";
            }
            line += " " + word;
        }
        if(!line.isEmpty()) {
            lines.add(line.trim());
        }
        return lines;
    }
}
