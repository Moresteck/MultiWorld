package pl.daamazingshit.mw;

import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import pl.daamazingshit.mw.managers.Help;
import pl.daamazingshit.mw.managers.WorldManager;

public class MultiWorld extends JavaPlugin {

	@Override
	public void onDisable() {
		
	}

	@Override
	public void onEnable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdalias, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mw")) {
			if (args.length == 0) {
				Help.showHelp(sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("create")) {
				if (args.length == 1) {
					Help.showHelp(sender);
					return true;
				}
				WorldManager wm = null;
				// If create command contains world's name
				if (args.length == 2) {
					wm = new WorldManager(args[1]);
				}
				// If create command contains world's name and environment or world's seed
				if (args.length == 3) {
					try {
						// Done if argument 3 is environment
						Environment env = Environment.valueOf(args[2].toUpperCase());
						wm = new WorldManager(args[1], env);
					}
					catch (Exception ex) {
						// Done if argument 3 is seed
						wm = new WorldManager(args[1], Long.parseLong(args[2]));
					}
				}
				// If create command contains world's name, environment and seed
				if (args.length == 4) {
					try {
						wm = new WorldManager(args[1], Environment.valueOf(args[2].toUpperCase()), Long.parseLong(args[3]));
					}
					catch (Exception ex) {
						sender.sendMessage("Invalid format.");
						Help.showHelp(sender);
						return true;
					}
				}
				if (wm == null) {
					sender.sendMessage("Something went wrong! Please try again.");
					return true;
				}
				boolean done = wm.create();
				sender.sendMessage(done == true ? "Complete." : "Something went wrong!");
				return true;
			}
		}
		return true;
	}
}
