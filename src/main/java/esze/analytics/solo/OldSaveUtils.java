package esze.analytics.solo;

import com.google.common.io.Files;
import esze.main.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class OldSaveUtils {

    private static SaveGame gme = null;
    private static SaveEsze svgms = null;
    private static final String FOLDER = "analytics/";
    private static final String EXTENTION = ".sav";
    private static final String NAME = "analytics_solo";
    private static final String FILE = FOLDER + NAME + EXTENTION;

    public static String[] readString(String s) {
        ArrayList<String> list = new ArrayList<String>();
        s = s.substring(1, s.length() - 1);
        int depth = 0;
        int lastloc = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '[':
                    depth++;
                    break;
                case ']':
                    depth--;
                    break;
                case ',':
                    if (depth == 0) {
                        if (lastloc < i) {
                            list.add(s.substring(lastloc, i));
                        } else {
                            list.add("");
                        }
                        lastloc = (i + 1);
                    }
                    break;
                default:
                    break;
            }
        }

        if (lastloc < s.length()) {
            list.add(s.substring(lastloc, s.length()));
        }

        String[] l = new String[list.size()];
        int i = 0;

        //System.out.println("----------------");
        for (String bla : list) {
            //System.out.println(bla);
            l[i] = bla;
            i++;
        }
        //System.out.println("----------------");
        return l;
    }

    public static void startGame() {
        if (gme != null) {
            return;
        }
        gme = new SaveGame();
    }

    public static void reload() {
        svgms = new SaveEsze(svgms.toString());
    }

    public static void endGame() {
        if (svgms == null) {
            load();
        }
        gme.endGame();
        svgms.add(gme);
        gme = null;
        save();
    }

    public static void setWinner(String g) {
        gme.setWinner(g);
    }

    public static void reset() {
        svgms = new SaveEsze();
        save();
    }

    public static void addPlayer(String s) {
        if (gme == null) {
            return;
        }
        gme.addPlayer(s);
    }

    public static void addPlayerDeath(String name, String cause) {
        if (gme == null) {
            return;
        }
        gme.addDeath(name, cause);
    }

    public static void addPlayerSelection(String name, SaveSelection sele) {
        if (gme == null) {
            return;
        }
        gme.addSelect(name, sele);
    }

    public static SaveEsze getSaveEsze() {
        if (svgms == null) {
            load();
        }
        return svgms;
    }

    public static void load() {
        File in = new File(FILE);
        if (!in.exists()) {
            svgms = new SaveEsze();
        } else {
            try {
                Scanner inScan = new Scanner(in);
                String input = "";
                while (inScan.hasNext()) {
                    input += inScan.next();

                }
                inScan.close();
                svgms = new SaveEsze(input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public static void save() {
        if (svgms == null) {
            load();
        }
        File out = new File(FILE);
        if (out.exists()) {
            out.delete();
        }
        try {
            FileWriter outWriter = new FileWriter(out);
            outWriter.write(svgms.toString());
            outWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void backup() {
        save();
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd-mm-ss");
            Date d = new Date();
            File out = new File(FOLDER + NAME + "-backup-" + f.format(d) + EXTENTION);
            File in = new File(FILE);
            Files.copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String rmColor(String s) {
        for (String tag : main.colorTags) {
            s = s.replace(tag, "");
        }

        s = s.replace(" ", "");

        s = s.replace("ä", "ae");
        s = s.replace("Ä", "Ae");
        s = s.replace("ö", "oe");
        s = s.replace("Ö", "Oe");
        s = s.replace("ü", "ue");
        s = s.replace("Ü", "Ue");
        return s;
    }
}
