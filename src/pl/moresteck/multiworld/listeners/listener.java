package pl.moresteck.multiworld.listeners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.world.MWorld;

public class listener extends EntityListener {

	@Override
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		World world = e.getEntity().getWorld();
		MWorld mworld = MultiWorld.getWorld(world.getName());

		if (!mworld.getAllowSpawn(e.getEntity())) {
			e.setCancelled(true);
		}
	}

	@Override
	public void onEntityDamage(EntityDamageEvent e) {
		if (!(e instanceof EntityDamageByEntityEvent)) {
			return;
		}
		EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent)e;
		if (ev.getCause() != DamageCause.ENTITY_ATTACK) {
			return;
		}
		if ((ev.getEntity() instanceof Player) && (ev.getDamager() instanceof Player)) {
			MWorld world = MultiWorld.getWorld(e.getEntity().getWorld().getName());
			if (!world.getPvP()) {
				e.setDamage(0);
			}
		}
	}
}
