package pl.daamazingshit.mw.listeners;

import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

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
}
