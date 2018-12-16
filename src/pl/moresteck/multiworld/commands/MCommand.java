package pl.moresteck.multiworld.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.moresteck.bukkitversion.BukkitVersion;
import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.Perm;

public abstract class MCommand {
	private CType cmd;
	private CommandSender cs;
	protected String[] args;
	protected String perm;
	protected BukkitVersion BukkitVersion;

	public MCommand(Command cmd, CommandSender cs, String[] args) {
		this.BukkitVersion = MultiWorld.bukkitversion;
		this.cmd = CType.getType(cmd);
		this.cs = cs;
		this.args = args;
		this.perm = "multiworld.*";
	}

	public CommandSender getSender() {
		return this.cs;
	}

	public CType getCommand() {
		return this.cmd;
	}

	public String[] getArgs() {
		return this.args;
	}

	public void send(String message) {
		if (this.isPlayer()) {
			this.getSender().sendMessage(message);
		} else {
			this.getSender().sendMessage("[MultiWorld] " + message);
		}
	}

	public boolean exists(int index, String str) {
		boolean help;
		try {
			help = this.args[index].equalsIgnoreCase(str);
		} catch (Exception ex) {
			help = false;
		}
		return help;
	}

	public boolean isPlayer() {
		return (this.getSender() instanceof Player);
	}

	public boolean hasPermission() {
		return Perm.has(cs, perm);
	}

	public String getPermission() {
		return this.perm;
	}

	public void setPermission(String perm) {
		this.perm = perm;
	}

	protected enum CType {
		MW(0),
		MWP(1);

		private int cmd;

		private CType(int s) {
			this.cmd = s;
		}

		public String getCmd() {
			return this.isMW() ? "mw" : (this.isMWP() ? "mwp" : null);
		}

		public boolean isMW() {
			return cmd == 0;
		}

		public boolean isMWP() {
			return cmd == 1;
		}

		public static CType getType(Command cmd) {
			CType ctype = CType.getType(cmd.getName().equalsIgnoreCase("mw") ? 0 : (cmd.getName().equalsIgnoreCase("mwp") ? 1 : -1));
			return ctype;
		}

		public static CType getType(int i) {
			if (i == 0) return CType.MW;
			if (i == 1) return CType.MWP;
			return null;
		}
	}

	public abstract void execute();

	public abstract void displayCommandHelp();
}
