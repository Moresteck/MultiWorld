package pl.moresteck.multiworld.world;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Entity;

import pl.moresteck.multiworld.MultiWorld;

public class MWorld {
	private String name;

	public MWorld(String string) {
		this.name = string;
	}

	public World getWorld() {
		try {
			return MultiWorld.server.getWorld(name);
		} catch (Exception ex) {
			return null;
		}
	}

	public String getName() {
		return this.name;
	}

	public long getSeed() {
		return MWorldConfig.getSeed(this.name);
	}

	public boolean getAllowSpawn(Entity type) {
		return MWorldConfig.getAllowSpawn(this.name, type);
	}

	public boolean getAllowMonsters() {
		return MWorldConfig.getAllowMonsters(this.name);
	}

	public boolean getAllowAnimals() {
		return MWorldConfig.getAllowAnimals(this.name);
	}

	public boolean getPvP() {
		return MWorldConfig.getPvP(this.name);
	}

	public Environment getEnvironment() {
		return MWorldConfig.getEnvironment(this.name);
	}

	public String getGenerator() {
		return MWorldConfig.getGenerator(this.name);
	}

	public boolean getWeather() {
		return MWorldConfig.getWeather(this.name);
	}
}
