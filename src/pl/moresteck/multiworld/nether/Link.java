package pl.moresteck.multiworld.nether;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;

import pl.moresteck.multiworld.MultiWorld;

public class Link {

	public static void createSampleLink() {
		append("#");
		append("# Welcome to MultiWorld-Nether configuration file!");
		append("# Here you can set to what world a portal will link");
		append("# For example:");
		append("#world: world_nether");
		append("# You HAVE to specify the exit world for each nether world! E.g.");
		append("#world_nether: world");
		append("#");
		append("");
		append("world: world_nether");
		append("world_nether: world");
	}

	public static Map<String, String> getLinks() {
		HashMap<String, String> map = new HashMap<String, String>();
		for (World world : MultiWorld.server.getWorlds()) {
			if (getLink(world.getName()) == null) {
				MultiWorld.log.warning("[MultiWorld-Nether] WARNING! No link specified for world '" + world.getName() + "'");
				continue;
			}
			map.put(world.getName(), getLink(world.getName()));
		}
		return map;
	}

	private static void append(String append) {
		File file = new File("plugins/MultiWorld", "nether_links.yml");
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(file, true));
			output.write(append);
			output.newLine();
			output.close();
		} catch (Exception ex) {
			return;
		}
	}

	@SuppressWarnings("resource")
	public static String getLink(String world) {
		File file = new File("plugins/MultiWorld", "nether_links.yml");
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(file));
			String line;
			while ((line = input.readLine()) != null) {
				if (!line.startsWith("#") && !line.equals("")) {
					String[] arg = line.split(": ");
					if (arg[0].equals(world)) {
						return arg[1];
					}
				}
			}
			input.close();
		} catch (Exception ex) {
			return null;
		}
		return null;
	}
}
