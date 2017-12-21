package pl.DaAmazingShit.MultiWorld.util;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.CraftWorld;

import net.minecraft.server.WorldServer;
import pl.DaAmazingShit.MultiWorld.MultiWorldMain;

public class Util {
	
	public static void addWorld(String worldname, String environment) {
		MultiWorldMain.config.setProperty("worlds." + worldname + ".environment", environment);
		MultiWorldMain.config.setProperty("worlds." + worldname + ".load-on-startup", true);
		List<String> worlds = new LinkedList<String>();
		worlds.add(worldname);
		MultiWorldMain.config.setProperty("worldlist", worlds);
	}
	
	public static void eraseWorld(String worldname) {
		MultiWorldMain.config.removeProperty("worlds." + worldname);
		MultiWorldMain.config.removeProperty("worldlist." + worldname);
	}
	
	public static void loadWorlds() {
		List<String> worlds = new LinkedList<String>();
		worlds = MultiWorldMain.config.getStringList("worldlist", worlds);
		
		for (String w : worlds) {
			if ((boolean)MultiWorldMain.config.getProperty("worlds." + w + ".load-on-startup")) {
				// do try{}
				Environment env = Environment.valueOf((String) MultiWorldMain.config.getProperty("worlds." + w + ".environment"));
				MultiWorldMain.staticServer.createWorld(w, env);
			}
		}
	}
	
	public static void firstStartup() {
		List<String> worlds = new LinkedList<String>();
		worlds.add("world");
		MultiWorldMain.config.setProperty("worldlist", worlds);
		
		MultiWorldMain.config.setProperty("worlds.world.environment", Environment.NORMAL);
		MultiWorldMain.config.setProperty("worlds.world.load-on-startup", true);
	}
	
	public static void saveWorlds() {
		List<String> worlds = new LinkedList<String>();
		worlds = MultiWorldMain.config.getStringList("worldlist", worlds);
		
		for (String w : worlds) {
			CraftWorld wor = (CraftWorld) MultiWorldMain.staticServer.getWorld(w);
			WorldServer wo = wor.getHandle();
			wo.A.a(true, null);
		}
	}
}
