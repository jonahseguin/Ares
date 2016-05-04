package com.mcares.ares.checks;

import com.mcares.ares.check.Check;
import com.mcares.ares.check.CheckType;
import com.mcares.ares.player.AresCache;
import com.mcares.ares.player.AresPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;

public class CheckNoSwing extends Check {
    public CheckNoSwing() {
        super(CheckType.NO_SWING);
    }

    @EventHandler
    public void onPlayerClick(PlayerAnimationEvent event) {
        AresCache.get().getAresPlayer(event.getPlayer()).getData().setHasSwung(true);
    }
    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        AresPlayer aresPlayer = AresCache.get().getAresPlayer((Player) event.getDamager());
        if (!aresPlayer.getData().isHasSwung()) {
            if (fail(aresPlayer).isCancelled()) {
                event.setCancelled(true);
            }
        }
    }
}
