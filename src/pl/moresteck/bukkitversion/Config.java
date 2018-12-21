package pl.moresteck.bukkitversion;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.moresteck.multiworld.MultiWorld;

public class Config {
	private org.bukkit.util.config.Configuration oldConfig = null;
	private org.bukkit.configuration.file.YamlConfiguration newConfig = null;
	private boolean useNew = false;
	private BukkitVersion bukkitversion = MultiWorld.bukkitversion;
	private File file;

	public Config(File file) {
		this.file = file;
		if (this.bukkitversion.isBukkitNewSystem()) {
			this.newConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
			this.useNew = true;
		} else {
			this.oldConfig = new org.bukkit.util.config.Configuration(file);
			this.oldConfig.load();
		}
	}

	public Config(BukkitVersion ver) {
		this.bukkitversion = ver;
	}

	public boolean isNewConfig() {
		return this.useNew;
	}

	public boolean getBoolean(String path, boolean def) {
		if (this.useNew) {
			return this.newConfig.getBoolean(path, def);
		} else {
			return this.oldConfig.getBoolean(path, def);
		}
	}

	public String getString(String path, String def) {
		if (this.useNew) {
			return this.newConfig.getString(path, def);
		} else {
			return this.oldConfig.getString(path, def);
		}
	}

	public int getInt(String path, int def) {
		if (this.useNew) {
			return this.newConfig.getInt(path, def);
		} else {
			return this.oldConfig.getInt(path, def);
		}
	}

	public double getDouble(String path, double def) {
		if (this.useNew) {
			return this.newConfig.getDouble(path, def);
		} else {
			return this.oldConfig.getDouble(path, def);
		}
	}

	public long getLong(String path, long def) {
		if (this.useNew) {
			return this.newConfig.getLong(path, def);
		} else {
			return Long.parseLong((String) this.oldConfig.getProperty(path));
		}
	}

	public Set<String> getKeys(String path) {
		if (this.useNew) {
			return this.newConfig.getKeys(true);
		} else {
			Set<String> set = new HashSet<String>();
			if (this.oldConfig.getKeys(path) == null) return set;
			for (String s : this.oldConfig.getKeys(path)) {
				set.add(s);
			}
			return set;
		}
	}

	public Set<String> getKeys() {
		if (this.useNew) {
			return this.newConfig.getKeys(false);
		} else {
			Set<String> set = new HashSet<String>();
			if (this.oldConfig.getKeys(null) == null) return set;
			for (String s : this.oldConfig.getKeys(null)) {
				set.add(s);
			}
			return set;
		}
	}

	public List<String> getStringList(String path, List<String> def) {
		if (this.useNew) {
			return this.newConfig.getStringList(path).isEmpty() ? def : newConfig.getStringList(path);
		} else {
			return this.oldConfig.getStringList(path, def);
		}
	}

	public void set(String path, Object to) {
		if (this.useNew) {
			this.newConfig.set(path, to);
		} else {
			this.oldConfig.setProperty(path, to);
		}
	}

	public void remove(String path) {
		if (this.useNew) {
			this.newConfig.set(path, null);
		} else {
			this.oldConfig.removeProperty(path);
		}
	}

	public boolean save() {
		if (this.useNew) {
			try {
				this.newConfig.save(this.file);
				return true;
			} catch (IOException e) {
				return false;
			}
		} else {
			return this.oldConfig.save();
		}
	}

	public File getFile() {
		return this.file;
	}
}
