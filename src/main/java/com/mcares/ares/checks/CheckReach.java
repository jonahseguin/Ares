package com.mcares.ares.checks;

import com.mcares.ares.check.Check;
import com.mcares.ares.check.CheckType;
import com.mcares.ares.player.AresCache;
import com.mcares.ares.player.AresPlayer;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CheckReach extends Check {

    public CheckReach() {
        super(CheckType.REACH);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getCause() == EntityDamageEvent.DamageCause.THORNS) return;
        if(e.isCancelled()) return;
        if(e.getDamager() instanceof Player){
            final Player p = (Player) e.getDamager();
            AresPlayer ap = AresCache.get().getAresPlayer(p);

            Location dmger = p.getLocation().add(0, 1.5, 0);
            Location dmged = e.getEntity().getLocation();

            double distance = dmger.distance(dmged);

            if(distance > 10.5){
                if(fail(ap).isCancelled()){
                    e.setCancelled(true);
                }
            }

        }
    }

}
