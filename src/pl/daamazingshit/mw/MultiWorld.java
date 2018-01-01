package pl.daamazingshit.mw;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import pl.daamazingshit.mw.managers.Help;

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
			if (args[0].equalsIgnoreCase("")) {
				
			}
		}
		return true;
	}
}
