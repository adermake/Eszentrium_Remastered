package esze.types;

import esze.enums.Gamestate;
import esze.main.GameRunnable;
import esze.main.LobbyBackgroundRunnable;
import esze.main.main;
import esze.menu.GameModifier;
import esze.menu.ModifierMenu;
import esze.scoreboards.MonumenteScorboard;
import esze.utils.*;
import monuments.Nexus;
import monuments.NexusBeam;
import monuments.Soul;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import spells.spellcore.SilenceSelection;
import spells.spellcore.Spell;
import weapons.Damage;
import weapons.WeaponMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class TypeMONUMENTE extends TypeTeamBased {

    public Nexus redNexus;
    public Nexus blueNexus;
    NexusBeam nexusBeam = null;
    State currentPhase = State.BUILDPHASE;
    // ---------------------------------------------------------
    ArrayList<PlayerInventory> battleInvs = new ArrayList<PlayerInventory>();
    ArrayList<PlayerInventory> buildInvs = new ArrayList<PlayerInventory>();
    // ---------------------------------------------------------
    //HashMap<Player,ArrayList<Soul>> soulOwners = new HashMap<Player,ArrayList<Soul>>();
    public HashMap<Player, Integer> soulCount = new HashMap<Player, Integer>();
    //public HashMap<Player,Float> soulPitch = new HashMap<Player,Float>();

    public TypeMONUMENTE() {
        name = "MONUMENTE";

        setupLists();

        for (Player p : Bukkit.getOnlinePlayers()) {
            givePlayerLobbyItems(p);

        }

    }

    boolean gameEnding = false;

    @Override
    public void runEverySecond() {
        if (gameEnding) {
            return;
        }
        // TODO Auto-generated method stub
        if (blueNexus.isDead() || redNexus.isDead()) {

            new BukkitRunnable() {

                @Override
                public void run() {
                    endGame();
                }
            }.runTaskLater(main.plugin, 20 * 3);
            gameEnding = true;

        }
    }

    public void setupLists() {
        allTeams.clear();
        soulCount.clear();

        battleInvs.clear();
        buildInvs.clear();
        allTeams.add(new EszeTeam("§cRot", ChatColor.RED, Color.RED, Material.RED_WOOL));
        allTeams.add(new EszeTeam("§9Blau", ChatColor.BLUE, Color.BLUE, Material.BLUE_WOOL));
    }

    @Override
    public void runEveryTick() {
        // TODO Auto-generated method stub
        ArrayList<Soul> removeLater = new ArrayList<Soul>();
        for (Soul s : Soul.allSouls) {
            if (s.update()) {
                removeLater.add(s);
            }
        }
        for (Soul s : removeLater) {

            soulCount.put(s.follow, soulCount.get(s.follow) + 1);
            Soul.allSouls.remove(s);
            float soulPitch = 2 * (float) soulCount.get(s.follow) / 50F;
            SoundUtils.playSound(Sound.BLOCK_AMETHYST_CLUSTER_PLACE, s.follow.getLocation(), soulPitch, 4);

        }


        killInVoidCheck();
    }

    EszeTeam teamRed;
    EszeTeam teamBlue;

    @Override
    public void gameStart() {

        autoFillPlayers();
        teamRed = allTeams.get(0);
        teamBlue = allTeams.get(1);

        for (Player p : players) {
            soulCount.put(p, 0);
            teleportToSpawn(p);
            setupPlayer(p);
            WeaponMenu.deliverItems();
            //openSpellSelection(p);
        }
        setupGame();
        redNexus = new Nexus(teamRed.players.get(0), getLoc(1).clone().add(0, 2, 0), teamRed);
        blueNexus = new Nexus(teamBlue.players.get(0), getLoc(3).clone().add(0, 2, 0), teamBlue);
        scoreboard = new MonumenteScorboard();
        scoreboard.showScoreboard();

    }

    public void setupGame() {
        Bukkit.getWorld("world").loadChunk(Bukkit.getWorld("world").getChunkAt(getLoc(1)));
        Bukkit.getWorld("world").loadChunk(Bukkit.getWorld("world").getChunkAt(getLoc(2)));
        Bukkit.getWorld("world").loadChunk(Bukkit.getWorld("world").getChunkAt(getLoc(3)));
        Bukkit.getWorld("world").loadChunk(Bukkit.getWorld("world").getChunkAt(getLoc(4)));
        setupJumpPad(currentmap);
        Music.startRandomMusic();
        spectator.clear();
    }

    @Override
    public void endGame() {
        // TODO Auto-generated method stub


        if (blueNexus.isDead()) {
            Title title = new Title("§cRot", "§ehat gewonnen");
            redNexus.killSilent();
            title.sendAll();
        } else if (redNexus.isDead()) {
            Title title = new Title("§9Blau", "§ehat gewonnen");
            blueNexus.killSilent();
            title.sendAll();
        }
        Music.sp.destroy();
        GameRunnable.stop();
        Gamestate.setGameState(Gamestate.LOBBY);
        LobbyBackgroundRunnable.start();
        LobbyUtils.recallAll();
        Spell.clearSpells();
        for (Entity ent : Bukkit.getWorld("world").getEntities()) {
            if (!(ent instanceof Player)) {
                ent.remove();
            }
        }
        gameEnding = false;
        LobbyUtils.recallAll();
        setupLists();
    }

    @Override
    public void death(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (deathCheck(p)) {
            p.setHealth(Damage.lastHealthTaken.get(p));
            return;
        }

        //ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, p.getLocation(), 0.2F, 0.5F, 0.2F, 25, 1, new Vector(0,1,0));
        ParUtils.createParticle(Particle.SOUL, p.getLocation(), 0.5F, 0.5F, 0.5F, 50, 0.01);
        ParUtils.createParticle(Particle.LARGE_SMOKE, p.getLocation(), 0.5F, 0.5F, 0.5F, 10, 0.001);
        //SoundUtils.playSound(Sound.BLOCK_SCULK_SENSOR_BREAK, p.getLocation(),1,1);
        SoundUtils.playSound(Sound.ENTITY_GOAT_RAM_IMPACT, p.getLocation(), 1F, 1F);
        if (Spell.damageCause.get(p) != null && Spell.damageCause.get(p).killer != null && !getTeammates(Spell.damageCause.get(p).killer).contains(p)) {

            generateSoul(Spell.damageCause.get(p).killer, p, 5);
            takeSouls(Spell.damageCause.get(p).killer, p);

        } else {

            if (teamRed.players.contains(p)) {
                Player rand = teamBlue.getRandomPlayer();
                generateSoul(rand, p, 5);
                takeSouls(rand, p);
            }
            if (teamBlue.players.contains(p)) {
                Player rand = teamRed.getRandomPlayer();
                generateSoul(rand, p, 5);
                takeSouls(rand, p);
            }

        }


        teleportToSpawn(p);
    }


    public void removeSoul(Player p, int amount) {
        if (soulCount.containsKey(p)) {
            soulCount.put(p, soulCount.get(p) - amount);
        }
    }

    public void generateSoul(Player owner, Player victim, int amount) {
		
	
		
		/*
		for (int i = 0;i< amount;i++) {
			if (soulOwners.containsKey(owner) && !soulOwners.get(owner).isEmpty()) {
				ArrayList<Soul> souls = soulOwners.get(owner);
				Soul s = new Soul(victim, souls.get(souls.size()-1).getEntity(), victim.getLocation());
				souls.add(s);
				
			}
			else {
				ArrayList<Soul> souls = new ArrayList<Soul>();	
				Soul s =new Soul(victim, owner, victim.getLocation());
				souls.add(s);
				soulOwners.put(owner, souls);
			}
			
		}
		*/
        SoundUtils.playSound(Sound.PARTICLE_SOUL_ESCAPE, victim.getLocation(), 1.5F, 1);
        SoundUtils.playSound(Sound.PARTICLE_SOUL_ESCAPE, owner.getLocation(), 1.5F, 1);
        if (soulCount.containsKey(owner)) {
            //soulCount.put(owner,soulCount.get(owner)+amount);
            for (int i = 0; i < amount; i++) {
                new Soul(victim, owner, victim.getEyeLocation());
            }
        }

    }

    public void takeSouls(Player taker, Player victim) {
		/*
		for (Soul s : soulOwners.get(victim)) {
			ArrayList<Soul> souls = soulOwners.get(taker);
			s.reTrack(souls.get(souls.size()-1).getEntity());
			souls.add(s);
			
			
			
		}
		soulOwners.get(victim).clear();
		*/
        if (soulCount.containsKey(taker)) {
            //soulCount.put(taker,soulCount.get(taker)+soulCount.get(victim));


            for (int i = 0; i < soulCount.get(victim); i++) {
                new Soul(victim, taker, victim.getEyeLocation());
            }
            soulCount.put(victim, 0);
        }
    }


    public void resendScorboardTeams(Player p) {

        for (EszeTeam team : allTeams) {
            for (Player pl : team.players) {
                ScoreboardTeamUtils.colorPlayer(pl, p, team.color);
            }

        }
    }

    public void swichPhase(State phase) {

        exitPhase();

        if (phase == State.BUILDPHASE) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                SilenceSelection silence = new SilenceSelection();
                Spell.silence(p, silence);
                p.setGameMode(GameMode.ADVENTURE);
            }
        }
        // --------------------------------------
        if (phase == State.BATTLEPHASE) {
            Spell.silenced.clear();
        }
        // --------------------------------------
        if (phase == State.LASERPHASE) {
            nexusBeam = new NexusBeam(redNexus, blueNexus);
        }
    }

    public void teleportToSpawn(Player p) {
        for (EszeTeam team : allTeams) {
            if (team.players.contains(p)) {
                if (team.parcolor == Color.RED) {
                    Location l = getLoc(2).clone().add(randInt(-5, 5), 0, randInt(-5, 5));
                    p.teleport(l);
                }
                if (team.parcolor == Color.BLUE) {
                    Location l = getLoc(4).clone().add(randInt(-5, 5), 0, randInt(-5, 5));
                    p.teleport(l);
                }

            }
        }
    }

    public void exitPhase() {

        if (currentPhase == State.BUILDPHASE) {
            buildInvs.clear();
            for (Player p : Bukkit.getOnlinePlayers()) {
                buildInvs.add(new PlayerInventory(p));
                p.getInventory().clear();
            }
        }
        if (currentPhase == State.BATTLEPHASE) {
            battleInvs.clear();
            for (Player p : Bukkit.getOnlinePlayers()) {
                battleInvs.add(new PlayerInventory(p));
                p.getInventory().clear();
            }
        }
        if (currentPhase == State.LASERPHASE) {
            nexusBeam = null;
        }
    }

    @Override
    public void givePlayerLobbyItems(Player p) {
        if (p.getGameMode().equals(GameMode.SURVIVAL)) {
            p.getInventory().clear();
        }
        if (!p.getName().equals("adermake") || p.getGameMode() != GameMode.CREATIVE) {
            if (p.isOp()) {
                p.getInventory().setItem(0,
                        ItemStackUtils.createItemStack(Material.COMMAND_BLOCK, 1, 0, "§3Modifikatoren", null, true));
            }

            p.getInventory().setItem(8, ItemStackUtils.createItemStack(Material.MAP, 1, 0, "§3Map wählen", null, true));
            p.getInventory().setItem(7,
                    ItemStackUtils.createItemStack(Material.ENDER_CHEST, 1, 0, "§3Spellsammlung", null, true));
            p.getInventory().setItem(6, ItemStackUtils.createItemStack(Material.DIAMOND, 1, 0, "§3Georg", null, true));
            p.getInventory().setItem(5,
                    ItemStackUtils.createItemStack(Material.NETHER_STAR, 1, 0, "§3Kosmetik", null, true));
            p.getInventory().setItem(4,
                    ItemStackUtils.createItemStack(Material.NAME_TAG, 1, 0, "§3Teamauswahl", null, true));

            resendScorboardTeams(p);
        }
    }

    public void setupPlayer(Player p) {
        Spell.damageCause.remove(p);
        Spell.damageCause.put(p, null); //Reset damage Cause

        p.setGameMode(GameMode.SURVIVAL);
        p.getInventory().clear();
        p.getInventory().addItem(ItemStackUtils.attackSpeedify(ItemStackUtils.createItemStack(Material.WOODEN_SWORD, 1, 0, "§cSchwert", null, true)));

        if (ModifierMenu.hasModifier(GameModifier.GESCHWINDIGKEIT)) {
            p.setWalkSpeed(0.6F);
            p.setFlySpeed(0.3F);

        }


    }

    public void autoFillPlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!playerHasTeam(p)) {
                for (EszeTeam team : allTeams) {
                    if (team.players.size() <= 0) {
                        team.addPlayer(p);
                        break;
                    }
                }
                if (!playerHasTeam(p)) {
                    EszeTeam et = allTeams.get(randInt(0, allTeams.size() - 1));
                    et.addPlayer(p);
                }

            }
            p.setGlowing(false);
        }
    }

    enum State {
        BUILDPHASE, BATTLEPHASE, LASERPHASE
    }

    ;

}
