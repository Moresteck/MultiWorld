package pl.amazingshit.mw.managers;

import org.bukkit.command.CommandSender;

import pl.amazingshit.mw.commands.*;
import pl.amazingshit.mw.util.PInfo;

public class CommandManager {

	private CommandSender cmdsender;
	private PInfo sender;
	private String alias;
	private String[] arguments;

	public String[] args;

	public CommandManager(CommandSender sender, String cmdalias, String[] args) {
		this.sender    = new PInfo(sender);
		this.cmdsender = sender;
		this.alias     = cmdalias;
		this.arguments = args;
	}

	public void handleCommand() {
		if (alias.equalsIgnoreCase("mw") || alias.equalsIgnoreCase("multiworld")) {
			if (arguments.length == 0) {
				Help.showHelp(cmdsender);
				return;
			}
			if (arguments[0].equalsIgnoreCase("create")) {
				MWCreateCommand.handle(cmdsender, arguments, sender);
				return;
			}
			if (arguments[0].equalsIgnoreCase("remove")) {
				MWRemoveCommand.handle(cmdsender, arguments, sender);
				return;
			}
			if (arguments[0].equalsIgnoreCase("tp")) {
				MWTeleportCommand.handle(cmdsender, arguments, sender);
				return;
			}
			if (arguments[0].equalsIgnoreCase("seed")) {
				MWSeedCommand.handle(cmdsender, arguments, sender);
				return;
			}
			else {
				Help.showHelp(cmdsender);
			}
		}
		if (alias.equalsIgnoreCase("worldsettings") || alias.equalsIgnoreCase("ws")) {
			if (arguments.length == 0) {
				Help.showSettingsHelp(cmdsender);
				return;
			}
			if (arguments[0].equalsIgnoreCase("animals")) {
				WSAnimalsCommand.handle(cmdsender, arguments, sender);
				return;
			}
			if (arguments[0].equalsIgnoreCase("monsters")) {
				WSMonstersCommand.handle(cmdsender, arguments, sender);
				return;
			}
			if (arguments[0].equalsIgnoreCase("pvp")) {
				WSPvPCommand.handle(cmdsender, arguments, sender);
				return;
			}
			else {
				Help.showSettingsHelp(cmdsender);
			}
		}
		if (alias.equalsIgnoreCase("worldlist") || alias.equalsIgnoreCase("wl")) {
			MWListCommand.handle(cmdsender, alias, sender);
		}
	}
}