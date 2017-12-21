package pl.DaAmazingShit.MultiWorld.listeners;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

import pl.DaAmazingShit.MultiWorld.MultiWorldMain;
import pl.DaAmazingShit.MultiWorld.util.Lang;
import pl.DaAmazingShit.MultiWorld.util.Respond;
import pl.DaAmazingShit.MultiWorld.util.Util;

public class MWListener extends PlayerListener {
	
	public static HashMap<String, World> whereIsPlayer     = new HashMap<String, World>();
	public static HashMap<String, String> worldsAndPlayers = new HashMap<String, String>();
	
	@Override
	public void onPlayerTeleport(PlayerMoveEvent e) {
		World from = e.getFrom().getWorld();
		World to   = e.getTo().getWorld();
		
		if (from.getName() != to.getName()) {
			whereIsPlayer.remove(e.getPlayer().getName());
			whereIsPlayer.put(e.getPlayer().getName(), to);
			worldsAndPlayers.remove(from.getName(), e.getPlayer().getName());
			worldsAndPlayers.put(to.getName(), e.getPlayer().getName());
		}
	}
	
	@Override
	public void onPlayerCommand(PlayerChatEvent e) {
		String[] args = e.getMessage().split(" ");
        Player player = e.getPlayer();
        
        String[] helpCmd   = Lang.helpCommand.split(" ");
        String[] createCmd = Lang.helpCommand.split(" ");
        String[] removeCmd = Lang.helpCommand.split(" ");
        String[] infoCmd   = Lang.helpCommand.split(" ");
        String[] whoCmd    = Lang.helpCommand.split(" ");
        String[] importCmd = Lang.helpCommand.split(" ");
        String[] whereCmd  = Lang.helpCommand.split(" ");
        String[] clearCmd  = Lang.helpCommand.split(" ");
        
        
        /**
         * XXX REMOVE COMMAND
         * 
         */
        
        if (args[0].equalsIgnoreCase("/" + removeCmd[0])) {
        	World world = null;
        	if (args[1] == null) {
        		player.sendMessage(Lang.prefixWrong + Respond.notEnoughArguments.toString());
        		return;
        	}
        	try {
            	// Great checker: if world exists V
        		world = MultiWorldMain.staticServer.getWorld(args[1]);
        		
        		File toRemove = new File(args[1]);
        		toRemove.delete();
        		
        		String rem = Respond.removedSuccess.toString();
        		rem.replaceAll("<WORLD>", args[1]);
        		player.sendMessage(Lang.prefix + rem);
        		
        		Util.eraseWorld(args[1]);
        	}
        	catch (Exception ex) {
        		player.sendMessage(Lang.prefixWrong + Respond.errorRemove.toString());
        		ex.printStackTrace();
        		return;
        	}
        }
        
        /**
         * XXX CREATE COMMAND
         * 
         */
        
        if (args[0].equalsIgnoreCase("/" + createCmd[0])) {
        	if (args[1] == null) {
        		player.sendMessage(Lang.prefixWrong + Respond.notEnoughArguments.toString());
        		return;
        	}
        	if (args[2] == null) {
        		player.sendMessage(Lang.prefixWrong + Respond.notEnoughArguments.toString());
        		return;
        	}
        	try {
        		MultiWorldMain.staticServer.createWorld(args[1], Environment.valueOf(args[2]));
        		
        		String cr = Respond.createdSuccess.toString();
        		cr.replaceAll("<WORLD>", args[1]);
        		player.sendMessage(Lang.prefix + cr);
        		
        		Util.addWorld(args[1], args[2]);
        	}
        	catch (Exception ex) {
        		player.sendMessage(Lang.prefixWrong + Respond.errorCreate.toString());
        		ex.printStackTrace();
        		return;
        	}
        }
        
        /**
         * XXX IMPORT COMMAND
         * 
         */
        
        if (args[0].equalsIgnoreCase("/" + importCmd[0])) {
        	if (args[1] == null) {
        		player.sendMessage(Lang.prefixWrong + Respond.notEnoughArguments.toString());
        		return;
        	}
        	if (args[2] == null) {
        		player.sendMessage(Lang.prefixWrong + Respond.notEnoughArguments.toString());
        		return;
        	}
        	try {
        		MultiWorldMain.staticServer.createWorld(args[1], Environment.valueOf(args[2]));
        		
        		String im = Respond.importedSuccess.toString();
        		im.replaceAll("<WORLD>", args[1]);
        		player.sendMessage(Lang.prefix + im);
        		
        		Util.addWorld(args[1], args[2]);
        	}
        	catch (Exception ex) {
        		player.sendMessage(Lang.prefixWrong + Respond.errorImport.toString());
        		ex.printStackTrace();
        		return;
        	}
        }
        
        /**
         * XXX WHO COMMAND
         * 
         */
        
        if (args[0].equalsIgnoreCase("/" + whoCmd[0])) {
        	if (args[1] == null) {
        		player.sendMessage(Lang.prefixWrong + Respond.notEnoughArguments.toString());
        		return;
        	}
        	try {
        		if (worldsAndPlayers.get(args[1]) == null) {
        			player.sendMessage(Lang.prefix + "§e" + args[1] + "§f has no players in.");
        			return;
        		}
        		String pl  = worldsAndPlayers.get(args[1]);
            	String who = Respond.who.toString();
            	who.replaceAll("<NUM>", Integer.toString(worldsAndPlayers.size()));
            	who.replaceAll("<WORLD>", args[1]);
            	who.replaceAll("<PLAYERS>", pl);
            	player.sendMessage(Lang.prefix + who);
        	}
        	catch (Exception ex) {
        		player.sendMessage(Lang.prefixWrong + Respond.unknownError.toString());
        		ex.printStackTrace();
        		return;
        	}
        }
        
        /**
         * XXX WHERE COMMAND
         * 
         */
        if (args[0].equalsIgnoreCase("/" + whereCmd[0])) {
        	if (args[1] == null) {
        		player.sendMessage(Lang.prefixWrong + Respond.notEnoughArguments.toString());
        		return;
        	}
        	try {
        		Player p = MultiWorldMain.staticServer.getPlayer(args[1]);
        		if (worldsAndPlayers.get(args[1]) == null) {
        			player.sendMessage(Lang.prefix + "§e" + args[1] + "§f has no players in.");
        			return;
        		}
            	String whe = Respond.where.toString();
            	whe.replaceAll("<WORLD>", p.getWorld().getName());
            	whe.replaceAll("<PLAYER>", p.getName());
            	player.sendMessage(Lang.prefix + whe);
        	}
        	catch (Exception ex) {
        		player.sendMessage(Lang.prefixWrong + Respond.errorWhere.toString());
        		ex.printStackTrace();
        		return;
        	}
        }
        
        /**
         * XXX HELP COMMAND
         * 
         */
        
        if (args[0].equalsIgnoreCase("/" + helpCmd[0])) {
        	if (args[1].equalsIgnoreCase("2")) {
            	try {
            		String[] help = Respond.help2.toString().split("+");
            		player.sendMessage(help[0]);
            		player.sendMessage(help[1]);
            		player.sendMessage(help[2]);
            		player.sendMessage(help[3]);
            		player.sendMessage(help[4]);
            	}
            	catch (Exception ex) {
            		player.sendMessage(Lang.prefixWrong + Respond.unknownError.toString());
            		ex.printStackTrace();
            		return;
            	}
        	}
        	try {
        		String[] help = Respond.help.toString().split("+");
        		player.sendMessage(help[0]);
        		player.sendMessage(help[1]);
        		player.sendMessage(help[2]);
        		player.sendMessage(help[3]);
        		player.sendMessage(help[4]);
        		player.sendMessage(help[5]);
        		player.sendMessage(help[6]);
        		player.sendMessage(help[7]);
        		player.sendMessage(help[8]);
        	}
        	catch (Exception ex) {
        		player.sendMessage(Lang.prefixWrong + Respond.unknownError.toString());
        		ex.printStackTrace();
        		return;
        	}
        }
        
        /**
         * XXX CLEAR COMMAND
         * 
         */
        
        if (args[0].equalsIgnoreCase("/" + clearCmd[0])) {

        	if (args[1] == null) {
            	World world = player.getWorld();
        		List<Entity> entities = new LinkedList<Entity>();
        		entities = MultiWorldMain.staticServer.getWorld(world.getName()).getEntities();
        		
        		for (Entity ent : entities) {
        			if ((!(ent instanceof HumanEntity)) || (!(ent instanceof Player))) {
        				ent.remove();
        			}
        		}
        		
            	String clear = Respond.clearSuccess.toString();
            	clear.replaceAll("<NUM>", Integer.toString(entities.size()));
            	clear.replaceAll("<WORLD>", args[1]);
            	player.sendMessage(Lang.prefix + clear);
        		return;
        	}
        	try {
        		List<Entity> entities = new LinkedList<Entity>();
        		entities = MultiWorldMain.staticServer.getWorld(args[1]).getEntities();
        		
        		for (Entity ent : entities) {
        			if ((!(ent instanceof HumanEntity)) || (!(ent instanceof Player))) {
        				ent.remove();
        			}
        		}
        		
            	String clear = Respond.clearSuccess.toString();
            	clear.replaceAll("<NUM>", Integer.toString(entities.size()));
            	clear.replaceAll("<WORLD>", args[1]);
            	player.sendMessage(Lang.prefix + clear);
        	}
        	catch (Exception ex) {
        		player.sendMessage(Lang.prefixWrong + Respond.unknownError.toString());
        		ex.printStackTrace();
        		return;
        	}
        }
        
        /**
         * XXX INFO COMMAND
         * 
         */
        
        if (args[0].equalsIgnoreCase("/" + infoCmd[0])) {
        	try {
        		String[] info = Respond.info.toString().split("+");
        		player.sendMessage(info[0]);
        		player.sendMessage(info[1]);
        		player.sendMessage(info[2]);
        		player.sendMessage(info[3]);
        	}
        	catch (Exception ex) {
        		player.sendMessage(Lang.prefixWrong + Respond.unknownError.toString());
        		ex.printStackTrace();
        		return;
        	}
        }
	}
}
