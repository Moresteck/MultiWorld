package bc.pl.daamazingshit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class MultiWorld extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public void onDisable() {
		log.info("[MultiWorld] MultiWorld v0.0 wylaczony");
		
	}

	@Override
	public void onEnable() {
		log.info("[MultiWorld] MultiWorld v0.0 wlaczony");
		getConfig().load();
		
	}
	
	public Configuration getConfig() {
		return getConfiguration();
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		//
		Player p         = (Player)sender;
		//
		ChatColor red    = ChatColor.RED;
		ChatColor green  = ChatColor.GREEN;
		ChatColor gold   = ChatColor.GOLD;
		ChatColor white  = ChatColor.WHITE;
		ChatColor blue   = ChatColor.BLUE;
		ChatColor black  = ChatColor.BLACK;
		ChatColor daqua  = ChatColor.DARK_AQUA;
		ChatColor yellow = ChatColor.YELLOW;
		//
		if (cmd.getName().equalsIgnoreCase("mw")) {
			//
			if (args.length == 0) {
				//
				sender.sendMessage(red   + "Za malo argumentów! Pomoc:");
				sender.sendMessage(gold  + "  /mw stworz " + blue  + "<swiat> (typ) "   + white  + "-"   + green + " Stwarza nowy swiat");
				sender.sendMessage(gold  + "  /mw usun "   + blue  + "<swiat> "         + white  + "-"   + green + " Usuwa swiat");
				sender.sendMessage(gold  + "  /mw idz "    + blue  + "<swiat> "         + white  + "-"   + green + " Wysyla cie na wybrany swiat");
				sender.sendMessage(gold  + "  /mw wyslij " + blue  + "<gracz> <swiat> " + white  + "-"   + green + " Wysyla kogos na wybrany swiat");
				sender.sendMessage(gold  + "  /mw kto "    + blue  + "(swiat) "         + white  + "-"   + green + " Sprawdza kto jest na danym swiecie");
				sender.sendMessage(gold  + "  /mw lista "                               + white  + "-"   + green + " Ukazuje liste swiatów");
				sender.sendMessage(black + " Copyright "   + daqua + "BetaCraftNet "    + yellow + "2017");
				return true;
			}
			if (args[0].equalsIgnoreCase("stworz") && sender.isOp()) {
				if (args.length == 1 || args.length == 2) {
					sender.sendMessage(red  + "Za malo argumentów! Pomoc:");
					sender.sendMessage(gold + "  /mw stworz " + blue + "<swiat> (typ)");
					return true;
				}
				if (args[2].equalsIgnoreCase("nether")) {
					getServer().broadcastMessage(gold + "Tworzenie nowego swiata - nazwa: " + yellow + args[1] + gold + ", typ: " + yellow + args[2] + gold + "!");
					getServer().createWorld(args[1], Environment.NETHER);
					getConfig().setProperty(args[1], "nether");
					getConfig().save();
					getServer().broadcastMessage(green + "Stworzono nowy swiat!");
					return true;
				}
				if (args[2].equalsIgnoreCase("normalny")) {
					getServer().broadcastMessage(gold  + "Tworzenie nowego swiata - nazwa: " + yellow + args[1] + gold + ", typ: " + yellow + args[2] + gold + "!");
					getServer().createWorld(args[1], Environment.NORMAL);
					getConfig().setProperty(args[1], "normalny");
					getConfig().save();
					getServer().broadcastMessage(green + "Stworzono nowy swiat!");
					return true;
				} else {
					sender.sendMessage(red    + "Dopuszczalne typy swiata:");
					sender.sendMessage(green  + " normalny");
					sender.sendMessage(yellow + " nether");
					return true;
				}
				//
			}
			if (args[0].equalsIgnoreCase("usun") && sender.isOp()) {
				if (args.length == 1) {
					sender.sendMessage(red  + "Za malo argumentów! Pomoc:");
					sender.sendMessage(gold + "  /mw usun " + blue + "<swiat>");
					return true;
				}
				if (getConfig().getProperty(args[1]) != null) {
					getConfig().removeProperty(args[1]);
					getConfig().save();
					sender.sendMessage(green + "Swiat usuniety pomyslnie! (" + gold + args[1] + green + ")!");
					return true;
				} else {
					sender.sendMessage(red + "Taki swiat nie istnieje (" + gold + args[1] + red + ")!");
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("idz") && sender.isOp()) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("Takie rzeczy tylko w grze!");
				}
				if (args.length == 1) {
					sender.sendMessage(red  + "Za malo argumentów! Pomoc:");
					sender.sendMessage(gold + "  /mw idz " + blue + "<swiat>");
					return true;
				}
				if (getConfig().getProperty(args[1]) != null) {
					Location spawn = getServer().getWorld(args[1]).getSpawnLocation();
					p.teleportTo(spawn);
					p.sendMessage(ChatColor.GRAY + "Teleportacja do swiata " + gold + args[1] + ChatColor.GRAY + ".");
					return true;
				} else {
					sender.sendMessage(red + "Ten swiat nie istnieje (" + gold + args[2] + red + ")!");
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("wyslij") && sender.isOp()) {
				if (args.length == 1 || args.length == 2) {
					sender.sendMessage(red  + "Za malo argumentów! Pomoc:");
					sender.sendMessage(gold + "  /mw wyslij " + blue + "<gracz> <swiat>");
					return true;
				}
				Player target = getServer().getPlayer(args[1]);
				
				if (target != null) {
					
					Location spawn = getServer().getWorld(args[2]).getSpawnLocation();
					
					if (getConfig().getProperty(args[2]) != null) {
						target.teleportTo(spawn);
						sender.sendMessage(green + "Teleportacja gracza " + yellow + target.getName() + green + " do swiata " + yellow + target.getWorld() + green + ".");
						return true;
					} else {
						sender.sendMessage(red + "Ten swiat nie istnieje (" + gold + args[2] + red + ")!");
						return true;
					}
				} else {
					sender.sendMessage(gold + "Gracz " + yellow + args[1] + red + " nie" + gold + " istnieje!");
					return true;
				}
				
			}
			if (args[0].equalsIgnoreCase("lista")) {
				sender.sendMessage(green + "Lista swiatow: ");
				sender.sendMessage(getServer().getWorlds().toString());
				return true;
			}
			
			
			else {
				sender.sendMessage(red + "Nie masz uprawnien aby to robic!");
				return true;
			}
			//
		}
		//
		return true;
	}
	//

}
