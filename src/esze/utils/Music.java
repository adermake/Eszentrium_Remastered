package esze.utils;



import java.io.File;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.xxmicloxx.NoteBlockAPI.event.SongEndEvent;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

import esze.main.main;



public class Music implements Listener {

	public static SongPlayer sp = null;

	@EventHandler
	public void musicStopped(SongEndEvent event) {
		SongPlayer player = event.getSongPlayer();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				startRandomMusic();
			}
		}.runTaskLater(main.plugin, 20);
		
	}

	public static void startRandomMusic() {
		try{
			
		File[] files = new File(main.plugin.getDataFolder().getAbsolutePath() + "/music/").listFiles();
		File file = (File) getRandom(files);
		Song s = NBSDecoder.parse(file);
		sp = new RadioSongPlayer(s);
		sp.setAutoDestroy(true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			
				sp.addPlayer(p);
			
		}
		sp.setPlaying(true);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public static void playMusicAndGoOn(String path) {
		try{
		Song s = NBSDecoder.parse(new File(path));
		sp = new RadioSongPlayer(s);
		sp.setAutoDestroy(true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if(main.plugin.getConfig().getBoolean("player."+p.getUniqueId().toString()+".music")){
				sp.addPlayer(p);
			}
		}
		sp.setPlaying(true);
		}catch(Exception e){e.printStackTrace();}
	}

	public static Object getRandom(Object[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

}
