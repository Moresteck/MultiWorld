package pl.daamazingshit.mw.listeners;

import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import com.garbagemule.MobArena.ArenaManager;

import pl.daamazingshit.mw.managers.ConfigWorld;
import pl.daamazingshit.mw.util.PropertyType;

public class Entities extends EntityListener {

	@Override public void onCreatureSpawn(CreatureSpawnEvent e) {
		if (e.isCancelled()) {
			return;
		}
		World w = e.getEntity().getLocation().getWorld();
		Entity ent = e.getEntity();
		if (ent instanceof Animals) {
			if (!ConfigWorld.getAllow(PropertyType.ANIMALS, w.getName())) {
				e.setCancelled(true);
			}
		}
		if (ent instanceof Monster) {
			if (!ConfigWorld.getAllow(PropertyType.MONSTERS, w.getName())) {
				e.setCancelled(true);
			}
		}
		// May use it in the future, idk.
		/*switch (ent) {
		// Animals
		case PIG:
			
		case SHEEP:
			
		case COW:
			
		case SQUID:
			
		case CHICKEN:
			
		// Monsters
		case SLIME:
			
		case ZOMBIE:
			
		case SKELETON:
			
		case SPIDER:
			
		case PIG_ZOMBIE:
			
		case CREEPER:
			
		case MONSTER:
			
		case GIANT:
			
		case GHAST:
			
		}*/
	}

	@Override public void onEntityDamage(EntityDamageEvent e) {
		if (!(e instanceof EntityDamageByEntityEvent)) {
			return;
		}
		EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
		Entity entityDamager = event.getDamager();
		Entity entityDamaged = event.getEntity();
		if ((entityDamager instanceof Player) && (entityDamaged instanceof Player)) {
			Player damager = (Player)entityDamager;
			Player damaged = (Player)entityDamaged;
			if (!ConfigWorld.getAllow(PropertyType.PVP, damager.getWorld().getName())) {
				if (ArenaManager.playerSet.contains(damager) && ArenaManager.playerSet.contains(damaged)) {
					return;
				}
				e.setCancelled(true);
				e.setDamage(0);
			}
		}
		else {
			return;
		}
	}
}
