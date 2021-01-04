package esze.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/*import net.minecraft.server.v1_13_R1.MinecraftKey;
import net.minecraft.server.v1_13_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_13_R1.Particle;
import net.minecraft.server.v1_13_R1.ParticleParam;
import net.minecraft.server.v1_13_R1.ParticleParamItem;*/

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import com.google.gson.JsonObject;

import esze.analytics.SaveUtils;
import esze.app.AppServer;
import esze.app.AppUserPasswordUtils;
import esze.enums.GameType;
import esze.enums.GameType.TypeEnum;
import esze.enums.Gamestate;
import esze.listeners.Block;
import esze.listeners.CancelClick;
import esze.listeners.Chat;
import esze.listeners.Death;
import esze.listeners.DropPickup;
import esze.listeners.Emerald;
import esze.listeners.FBoost;
import esze.listeners.Hunger;
import esze.listeners.Interact;
import esze.listeners.Join;
import esze.listeners.Launch;
import esze.listeners.Move;
import esze.listeners.Projectiles;
import esze.listeners.Reconnect;
import esze.listeners.Spawn;
import esze.map.JumpPad;
import esze.map.JumpPadHandler;
import esze.map.MapSelect;
import esze.menu.Menu;
import esze.utils.ChatUtils;
import esze.utils.CorpseUtils;
import esze.utils.ItemStackUtils;
import esze.utils.LibUtils;
import esze.utils.Music;
import esze.utils.NoCollision;
import esze.utils.PlayerConfig;
import esze.utils.PlayerUtils;
import esze.utils.TTTFusion;
import esze.utils.TTTTrade;
import esze.voice.Discord;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import spells.spellcore.Cooldowns;
import spells.spellcore.EventCollector;
import spells.spellcore.SpellList;
import spells.spellcore.Spelldrop;
import weapons.BuffHandler;
import weapons.Damage;
import weapons.WeaponAbilitys;
import weapons.WeaponList;

public class main extends JavaPlugin {

	public static main plugin;
	public static String discord_TOKEN = "";
	public static String mapname;

	public static HashMap<Player, String> damageCause = new HashMap<Player, String>();
	public AppServer appServer;

	public static ArrayList<String> colorTags = new ArrayList<String>();

	@Override
	public void onEnable() {
		// S
		NoCollision.setUpCollsionStopper();
		colorTags.add("§1");
		colorTags.add("§2");
		colorTags.add("§3");
		colorTags.add("§4");
		colorTags.add("§5");
		colorTags.add("§6");
		colorTags.add("§7");
		colorTags.add("§8");
		colorTags.add("§9");
		colorTags.add("§a");
		colorTags.add("§b");
		colorTags.add("§c");
		colorTags.add("§d");
		colorTags.add("§e");
		colorTags.add("§f");
		plugin = this;
		// R

		PlayerConfig.load();
		this.getServer().getPluginManager().registerEvents(new EventCollector(), this);
		Cooldowns.startCooldownHandler();

		ConfigurationSerialization.registerClass(JumpPad.class);
		ConfigurationSerialization.registerClass(PlayerConfig.class);
		// R
		/*
		 * ParticleParam p = new ParticleParamItem((Particle<ParticleParamItem>)
		 * Particle.REGISTRY.get(new MinecraftKey("hugeexplosion")), null);
		 * 
		 * PacketPlayOutWorldParticles w = new PacketPlayOutWorldParticles(arg0, arg1,
		 * arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9) Particle.REGISTRY.get(new
		 * MinecraftKey("<particlename>"))
		 */

		this.getCommand("playrandomsound").setExecutor(new CommandReciever());
		this.getCommand("showpads").setExecutor(new CommandReciever());
		this.getCommand("loadpads").setExecutor(new CommandReciever());
		this.getCommand("unload").setExecutor(new CommandReciever());
		this.getCommand("spell").setExecutor(new CommandReciever());
		this.getCommand("game").setExecutor(new CommandReciever());
		this.getCommand("maps").setExecutor(new CommandReciever());
		this.getCommand("setspawn").setExecutor(new CommandReciever());
		this.getCommand("setpassword").setExecutor(new CommandReciever());
		this.getCommand("setitem").setExecutor(new CommandReciever());
		this.getCommand("setdiscordtoken").setExecutor(new CommandReciever());
		this.getCommand("setlobby").setExecutor(new CommandReciever());
		this.getCommand("downloadfile").setExecutor(new CommandReciever());
		this.getCommand("ping").setExecutor(new CommandReciever());
		this.getCommand("setmode").setExecutor(new CommandReciever());
		this.getCommand("removemap").setExecutor(new CommandReciever());
		this.getCommand("gamemode").setExecutor(new CommandReciever());
		this.getCommand("gm").setExecutor(new CommandReciever());
		this.getCommand("itemname").setExecutor(new CommandReciever());
		this.getCommand("setjumppad").setExecutor(new CommandReciever());
		this.getCommand("removepads").setExecutor(new CommandReciever());

		this.getCommand("removepads").setExecutor(new CommandReciever());
		// LOLAAa
		this.getCommand("analytics").setExecutor(new CommandReciever());
		getServer().getPluginManager().registerEvents(new Join(), this);
		getServer().getPluginManager().registerEvents(new Move(), this);
		getServer().getPluginManager().registerEvents(new Death(), this);
		getServer().getPluginManager().registerEvents(new Damage(), this);
		getServer().getPluginManager().registerEvents(new Spawn(), this);
		getServer().getPluginManager().registerEvents(new Hunger(), this);
		getServer().getPluginManager().registerEvents(new Interact(), this);
		getServer().getPluginManager().registerEvents(new WeaponAbilitys(), this);
		getServer().getPluginManager().registerEvents(new FBoost(), this);
		getServer().getPluginManager().registerEvents(new CancelClick(), this);
		getServer().getPluginManager().registerEvents(new Block(), this);
		getServer().getPluginManager().registerEvents(new DropPickup(), this);
		getServer().getPluginManager().registerEvents(new JumpPadHandler(), this);
		getServer().getPluginManager().registerEvents(new Emerald(), this);
		getServer().getPluginManager().registerEvents(new MapSelect(), this);
		getServer().getPluginManager().registerEvents(new Menu(), this);
		getServer().getPluginManager().registerEvents(new Spelldrop(), this);
		getServer().getPluginManager().registerEvents(new TTTFusion(), this);
		getServer().getPluginManager().registerEvents(new TTTTrade(), this);
		getServer().getPluginManager().registerEvents(new Music(), this);
		getServer().getPluginManager().registerEvents(new Chat(), this);
		getServer().getPluginManager().registerEvents(new Reconnect(), this);
		getServer().getPluginManager().registerEvents(new Launch(), this);
		getServer().getPluginManager().registerEvents(new Projectiles(), this);
		TTTFusion.start();

		//PacketListner.registerPackets();
		PlayerUtils.showAllPlayers();
		PlayerUtils.stopVelocity();
		JumpPadHandler.start();
		Gamestate.setGameState(Gamestate.LOBBY);
		SpellList.registerSpells();
		// SpellList.sortSpells();
		WeaponList.setUpWeapons();
		BuffHandler.tickMethod();
		if (getConfig().contains("settings.mode")) {
			GameType.setTypeByName(getConfig().getString("settings.mode"));
		} else {
			GameType.setTypeByEnum(TypeEnum.SOLO);
		}

		LobbyBackgroundRunnable.start();

		MinecraftServer.getServer().setMotd(ChatUtils.centerMotD("§cEsze§3Remastered").substring(2) + "\n§8"
				+ ChatUtils.centerMotD("Der Klassiker neu aufgelegt!").substring(3));

		LibUtils.initlibs();

		for (Player p : Bukkit.getOnlinePlayers()) {
			main.damageCause.put(p, "unknown"); // Damage Cause
			p.setExp(0F);
			p.setLevel(0);
			p.setFoodLevel(20);
			p.setHealth(20);
			p.setMaxHealth(20);
			p.setWalkSpeed(0.2F);
			p.setGlowing(false);
			// Clears Inventory of Players
			if (p.getGameMode().equals(GameMode.SURVIVAL)) {
				p.getInventory().clear();
				p.teleport(new Location(Bukkit.getWorld("world"), 0, 105, 0));// teleport into Lobby
			}

			GameType.getType().givePlayerLobbyItems(p);
		}

		if (getConfig().contains("settings.dcToken")) {
			discord_TOKEN = getConfig().getString("settings.dcToken");
			new BukkitRunnable() {

				@Override
				public void run() {
					Discord.run();

				}
			}.runTaskAsynchronously(main.plugin);
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.isOp()) {
					p.sendMessage("Der Discord Token wurde nicht in der Config gefunden! (/setdiscordtoken <TOKEN>)");
				}
			}
		}
		
		//Analytics
		if (getConfig().contains("settings.sqlPass")) {
			SaveUtils.setPassword(getConfig().getString("settings.sqlPass"));
			SaveUtils.update();
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.isOp()) {
					p.sendMessage("Kein SQL Password eingetragen! (/analytics setPassword <Password>");
				}
			}
		}
		SaveUtils.checkConnection();
		

		AppUserPasswordUtils.createPasswordConfig();

		System.out.println("Esze | Fahre App-Server hoch.");
		appServer = new AppServer();
		appServer.startServer();
		System.out.println("Esze | App-Server hochgefahren.");

		// PACKETS
		/*
		PacketListenerAPI.addPacketHandler(new PacketHandler() {
			@Override
			public void onSend(SentPacket packet) {
				if (packet.getPacketName().contains("Chat")) 
					return;
				
				Bukkit.broadcastMessage(""+packet.getPacketName());
			}

			@Override
			public void onReceive(ReceivedPacket packet) {
			}
		});
*/
	}

	@Override
	public void onDisable() {
		// SaveUtils.backup();
		//PlayerConfig.save();
		
		
		try {
			CorpseUtils.removeAllCorpses();
		} catch (Error e) {
			System.out.println("Esze | Fehler beim Löschen der Leichen");
		}
		for (Entity e : Bukkit.getWorld("world").getEntities()) {
			if (e.getType() != EntityType.PLAYER) {
				e.remove();
			}
		}

		System.out.println("Esze | Fahre App-Server herunter.");
		try {
			appServer.shutdownServer();
			System.out.println("Esze | App-Server heruntergefahren.");
		} catch (Exception e) {
			System.out.println("Esze | App-Server herunterfahren fehlgeschlagen.");
		}

		try {
			Discord.unMuteAll();
			Discord.logout();
			System.out.println("Esze | Discord heruntergefahren.");
		} catch (Error e) {
			System.out.println("Esze | Discord herunterfahren fehlgeschlagen.");
		}

		/*
		 * try { wait(2000); } catch (InterruptedException e1) { // TODO Auto-generated
		 * catch block e1.printStackTrace(); }
		 */

	}

	public String objToJson(Object obj) {
		Class<?> objClass = obj.getClass();

		Field[] fields = objClass.getFields();
		JsonObject Jobj = new JsonObject();
		for (Field field : fields) {
			String name = field.getName();
			Object value = "ERROR-objToJsonMethod";
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Jobj.addProperty(name, value.toString());
		}
		return Jobj.toString();
	}

	
}
