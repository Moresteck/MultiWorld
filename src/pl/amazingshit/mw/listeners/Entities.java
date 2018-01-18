package pl.amazingshit.mw.listeners;

import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

import com.garbagemule.MobArena.ArenaManager;

import pl.amazingshit.mw.mw;
import pl.amazingshit.mw.managers.ConfigWorld;
import pl.amazingshit.mw.util.*;

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
		if (ent instanceof WaterMob) {
			if (!ConfigWorld.getAllow(PropertyType.ANIMALS, w.getName())) {
				e.setCancelled(true);
			}
		}
		if (ent instanceof Monster) {
			if (!ConfigWorld.getAllow(PropertyType.MONSTERS, w.getName())) {
				e.setCancelled(true);
			}
		}
		if (ent instanceof Slime) {
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
				if (mw.mobarenaEnabled) {
					if (ArenaManager.playerSet.contains(damager) || ArenaManager.playerSet.contains(damaged)) {
						return;
					}
				}
				
				e.setCancelled(true);
				e.setDamage(0);
			}
		}
		else {
			return;
		}
	}

	@Override public void onEntityExplode(EntityExplodeEvent e) {
		if (e.isCancelled()) {
			return;
		}
		Entity entity = e.getEntity();
		if (entity instanceof TNTPrimed) {
			boolean allowed = ConfigWorld.allowExplode(Explode.TNT, entity.getWorld().getName());
			if (allowed) {
				allowed = false;
			}
			if (!allowed) {
				allowed = true;
			}
			e.setCancelled(allowed);
			return;
		}
		if (entity instanceof Creeper) {
			boolean allowed = ConfigWorld.allowExplode(Explode.CREEPER, entity.getWorld().getName());
			if (allowed) {
				allowed = false;
			}
			if (!allowed) {
				allowed = true;
			}
			e.setCancelled(allowed);
			return;
		}
		else {
			boolean allowed = ConfigWorld.allowExplode(Explode.OTHER, entity.getWorld().getName());
			if (allowed) {
				allowed = false;
			}
			if (!allowed) {
				allowed = true;
			}
			e.setCancelled(allowed);
		}
	}
}