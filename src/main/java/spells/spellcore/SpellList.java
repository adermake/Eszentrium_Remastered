package spells.spellcore;

import esze.analytics.SaveUtils;
import esze.enums.GameType;
import esze.types.TypeSOLO;
import esze.types.TypeTEAMS;
import esze.types.TypeTTT;
import esze.utils.MathUtils;
import spells.spells.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SpellList {

    public static LinkedHashMap<Spell, List<Class>> spells = new LinkedHashMap<Spell, List<Class>>();
    public static ArrayList<Spell> traitorSpells = new ArrayList<Spell>();


    public static ArrayList<Spell> getDiffrentRandom(int count) {

        ArrayList<Spell> spellsForThisType = new ArrayList<Spell>();

        for (Spell spell : spells.keySet()) {
            List<Class> classes = spells.get(spell);

            if (classes.contains(GameType.getType().getClass())) {
                spellsForThisType.add(spell);
            }
        }


        ArrayList<Spell> randomList = (ArrayList<Spell>) spellsForThisType.clone();
        while (randomList.size() > count) {
            randomList.remove(MathUtils.randInt(0, randomList.size() - 1));
        }
        return randomList;

    }

    public static Spell getRandomSpell() {
        HashMap<Spell, List<Class>> spellsForThisType = new HashMap<Spell, List<Class>>();

        for (Spell spell : spells.keySet()) {
            List<Class> classes = spells.get(spell);

            if (classes.contains(GameType.getType().getClass())) {
                spellsForThisType.put(spell, classes);
            }
        }


        return new ArrayList<>(spellsForThisType.keySet()).get(MathUtils.randInt(0, spellsForThisType.size() - 1));
    }

    public static ArrayList<Spell> getDiffrentRandomGreen(int count) {

        ArrayList<Spell> spellsForThisType = new ArrayList<Spell>();

        for (Spell spell : spells.keySet()) {
            if (spell.name.contains("§c") || spell.name.contains("§8")) {
                continue;
            }
            List<Class> classes = spells.get(spell);

            if (classes.contains(GameType.getType().getClass())) {
                spellsForThisType.add(spell);
            }
        }


        ArrayList<Spell> randomList = (ArrayList<Spell>) spellsForThisType.clone();
        while (randomList.size() > count) {
            randomList.remove(MathUtils.randInt(0, randomList.size() - 1));
        }
        return randomList;

    }

    public static void registerSpells() {
        spells.clear();
        //registerSpell(new Ansturm());
        //registerSpell(new Astralsprung());
        //registerSpell(new HimmlischesUrteil());
        //registerSpell(new Binden());
        registerSpell(new AntlitzderGoettin());
        registerSpell(new Aufwind());
        registerSpell(new AugedesDrachen());
        registerSpell(new Schallwelle());
        registerSpell(new Scharfschuss());
        registerSpell(new SchreidesPhoenix(), TypeTTT.class);
        registerSpell(new Avatar());
        registerSpell(new Beben());
        registerSpell(new Chaoswelle());
        registerSpell(new Enterhaken());
        registerSpell(new Explosion());
        registerSpell(new Blasensturm());
        registerSpell(new Feuerball());
        registerSpell(new Flucht());
        //registerSpell(new Archon(), TypeTTT.class);
        registerSpell(new Erlösung(), TypeTTT.class);
        registerSpell(new Heilen(), TypeTTT.class);
        registerSpell(new Heilen(), TypeTEAMS.class);
        registerSpell(new Hühnchenluftschlag());
        registerSpell(new Kätzchenkanone());
        registerSpell(new Kaminchen());
        registerSpell(new Lamaturm());
        registerSpell(new Lichtstrudel());
        registerSpell(new Luftsprung());
        registerSpell(new Magmafalle());
        registerSpell(new Magnetball());
        registerSpell(new Meteoritenhagel());
        registerSpell(new Notenzauber());
        registerSpell(new Opfersuche());
        registerSpell(new Orbitar());
        registerSpell(new Raumwechsel());
        registerSpell(new RufderOzeane());
        registerSpell(new Erdsurfer());
        registerSpell(new Schallbrecher());
        registerSpell(new SchwerterausLicht());
        registerSpell(new Schock());
        registerSpell(new Requiemspfeil());
        registerSpell(new Ranke());
        registerSpell(new SchnittdersiebenWinde());
        registerSpell(new SpeerderZwietracht());
        registerSpell(new SiegelderFurcht());
        registerSpell(new Teleport());
        registerSpell(new Thermolanze());
        registerSpell(new Verstummen());
        registerSpell(new Vampirpilz());
        registerSpell(new Wunsch());
        registerSpell(new Wurmloch());
        registerSpell(new Zaubersprung());
        registerSpell(new Mondkugel());
        registerSpell(new Feuerwerkshelix());
        registerSpell(new Delfintorpedo());
        registerSpell(new Knochenparty());
        registerSpell(new Stich());
        registerSpell(new Schleimschleuder());
        registerSpell(new Sternentor());
        registerSpell(new Quantentunnel());
        registerSpell(new Ghul());
        registerSpell(new KosmischeBindung());
        registerSpell(new Bienenschwarm());
        registerSpell(new Eisstachel());
        registerSpell(new Impulsion());
        registerSpell(new Wasserdüse());
        registerSpell(new Höllenhast());
        registerSpell(new Plasmablase());
        registerSpell(new Heldenstoß());
        //registerSpell(new Blutsiegel());
        registerSpell(new DunklerWind());
        registerSpell(new Flammenwand());
        registerSpell(new Machtwort());
        registerSpell(new Vogelattacke());
        registerSpell(new Stase());
        registerSpell(new Schweberknecht());
        registerSpell(new Dimensionsschnitt());
        registerSpell(new Himmelsläufer());
        registerSpell(new Wildwuchs());
        registerSpell(new Fokusspirale());
        registerSpell(new Wassergeysir());
        registerSpell(new Todessaege());
        registerSpell(new Substitution());
        registerSpell(new Seelenmarionette());
        registerSpell(new UntotePhalanx());
        //registerSpell(new Schicksalsschnitt());
        registerSpell(new Kettenbrecher());
        registerSpell(new TordesRuins());
        //registerSpell(new ArkanesGeschütz());
        //registerSpell(new Springkraut());
    }

    public static void registerSpell(Spell spell, Class... gameTypes) {
        ArrayList<Class> classes = new ArrayList<Class>();
        for (Class glass : gameTypes) {
            classes.add(glass);
        }


        if (classes.isEmpty()) {
            classes.add(TypeTTT.class);
            classes.add(TypeSOLO.class);
            classes.add(TypeTEAMS.class);
        }

        spells.put(spell, classes);
    }


    public static void sortSpells() {


        List<Spell> sorted = getSortedSpells();
        spells.clear();

        for (Spell s : sorted) {
            registerSpell(s);
        }
    }


    public static List<Spell> getSortedSpells() {
        List<Spell> sorted = spells.keySet().stream().sorted(
                (Spell s1, Spell s2) -> ((SaveUtils.getAnalytics().getWorth(s1.getName()) - SaveUtils.getAnalytics().getWorth(s2.getName())) == 0) ? 0 :
                        ((SaveUtils.getAnalytics().getWorth(s1.getName()) - SaveUtils.getAnalytics().getWorth(s2.getName())) > 0) ? -1 : 1
        ).collect(Collectors.toList());
        return sorted;
    }


    public static List<Spell> getSortedSpellsAlphabetically() {

        List<Spell> sorted = spells.keySet().stream().sorted((Spell s1, Spell s2) -> (s1.getName().compareToIgnoreCase(s2.getName()))).collect(Collectors.toList());
        return sorted;
    }


}
