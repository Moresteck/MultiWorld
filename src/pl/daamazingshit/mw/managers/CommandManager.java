package pl.daamazingshit.mw.managers;

import org.bukkit.command.CommandSender;

import pl.daamazingshit.mw.commands.MWCreateCommand;
import pl.daamazingshit.mw.commands.MWRemoveCommand;
import pl.daamazingshit.mw.commands.MWTeleportCommand;
import pl.daamazingshit.mw.commands.WSAnimalsCommand;
import pl.daamazingshit.mw.commands.WSMonstersCommand;
import pl.daamazingshit.mw.commands.WSPvPCommand;
import pl.daamazingshit.mw.util.Sender;

public class CommandManager {

	private CommandSender cmdsender;
	private Sender sender;
	private String alias;
	private String[] arguments;

	public String[] args;

	public CommandManager(CommandSender sender, String cmdalias, String[] args) {
		this.sender    = new Sender(sender);
		this.cmdsender = sender;
		this.alias     = cmdalias;
		this.arguments = args;
		this.args[0]   = cmdalias;
		this.args[1]   = args[0];
		this.args[2]   = args[1];
		this.args[3]   = args[2];
		this.args[4]   = args[3];
		this.args[5]   = args[4];
		this.args[6]   = args[5];
	}

	public void handleCommand() {
		if (alias.equalsIgnoreCase("mw") || alias.equalsIgnoreCase("multiworld")) {
			if (arguments.length == 0) {
				Help.showHelp(cmdsender);
			}
			if (arguments[0].equalsIgnoreCase("create")) {
				MWCreateCommand.handle(cmdsender, arguments, sender);
			}
			if (arguments[0].equalsIgnoreCase("remove")) {
				MWRemoveCommand.handle(cmdsender, arguments, sender);
			}
			if (arguments[0].equalsIgnoreCase("tp")) {
				MWTeleportCommand.handle(cmdsender, arguments, sender);
			}
			else {
				Help.showHelp(cmdsender);
			}
		}
		if (alias.equalsIgnoreCase("worldsettings") || alias.equalsIgnoreCase("ws")) {
			if (arguments.length == 0) {
				Help.showSettingsHelp(cmdsender);
			}
			if (arguments[0].equalsIgnoreCase("animals")) {
				WSAnimalsCommand.handle(cmdsender, arguments, sender);
			}
			if (arguments[0].equalsIgnoreCase("monsters")) {
				WSMonstersCommand.handle(cmdsender, arguments, sender);
			}
			if (arguments[0].equalsIgnoreCase("pvp")) {
				WSPvPCommand.handle(cmdsender, arguments, sender);
			}
			else {
				Help.showSettingsHelp(cmdsender);
			}
		}
	}
}