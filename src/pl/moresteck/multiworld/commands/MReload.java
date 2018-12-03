package pl.moresteck.multiworld.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.Perm;
import pl.moresteck.multiworld.world.MWorld;
import pl.moresteck.multiworld.world.MWorldConfig;

public class MReload extends MCommand {

	public MReload(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.perm = "multiworld.world.reloadconfig";
	}

	public void execute() {
		boolean reload = this.exists(0, "reload");
		if (!reload) {
			return;
		}
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("help")) {
				this.displayCommandHelp();
				return;
			}
		}
		if (!Perm.has(this.getSender(), this.perm)) {
			this.send("No permission!");
			return;
		}
		List<MWorld> before = MultiWorld.worlds;
		List<MWorld> after = new LinkedList<MWorld>();

		String[] rightnow = MWorldConfig.getWorlds();

		for (String world : rightnow) {
			after.add(new MWorld(world));
		}
		List<MWorld> remove = new LinkedList<MWorld>();
		String removed = "none";
		List<MWorld> add = new LinkedList<MWorld>();
		String added = "none";
		for (MWorld world : after) {
			boolean contains = false;
			for (MWorld world2 : before) {
				if (world2.getName().equals(world.getName())) {
					contains = true;
				}
			}
			if (!contains) {
				add.add(world);
				if (added.equals("none")) {
					added = world.getName();
				} else {
					added = added + ", " + world.getName();
				}
			}
		}
		for (MWorld world : before) {
			boolean contains = false;
			for (MWorld world2 : after) {
				if (world2.getName().equals(world.getName())) {
					contains = true;
				}
			}
			if (!contains) {
				remove.add(world);
				if (removed.equals("none")) {
					removed = world.getName();
				} else {
					removed = removed + ", " + world.getName();
				}
			}
		}
		for (MWorld world : remove) {
			if (world.getWorld() == MultiWorld.server.getWorlds().get(0)) {
				this.send("Can't unload the main world!");
				MultiWorld.log.info("[MultiWorld] Tried to unload the main world! Don't do that!");
				continue;
			}
			MWorld w = MultiWorld.getWorld(world.getName());
			MultiWorld.unloadWorld(w);
		}
		for (MWorld world : add) {
			MultiWorld.loadWorld(world.getName());
		}

		this.send(ChatColor.GREEN + "Success!");
		this.send("Worlds imported: " + added);
		this.send("Worlds unloaded: " + removed);
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw reload");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (Perm.has(this.getSender(), this.perm) ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Reloads worlds from configuration");
	}
}
