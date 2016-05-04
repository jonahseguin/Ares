package com.mcares.ares.checks;


import com.mcares.ares.check.CheckType;
import com.mcares.ares.check.TimerCheck;
import com.mcares.ares.player.AresCache;
import com.mcares.ares.player.AresPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class CheckRegen extends TimerCheck {

    public CheckRegen() {
        super(CheckType.REGEN);
    }

    @EventHandler
    public void onHealth(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof Player){
            Player pl = (Player) e.getEntity();
            if(e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
                AresPlayer p = AresCache.get().getAresPlayer(pl);
                p.getData().setHealthPerSecond(p.getData().getHealthPerSecond() + e.getAmount());
            }
        }
    }

    @Override
    public void check(AresPlayer player) {
        if(player.getData().getHealthPerSecond() > 2){
            fail(player);//Err how shall we cancel this
        }

        //Reset
        player.getData().setHealthPerSecond(0);
    }

}
