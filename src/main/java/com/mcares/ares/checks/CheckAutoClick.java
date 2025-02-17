package com.mcares.ares.checks;

import com.mcares.ares.check.CheckType;
import com.mcares.ares.check.TimerCheck;
import com.mcares.ares.check.checker.checkers.CheckerAutoClick;
import com.mcares.ares.configuration.annotations.ConfigData;
import com.mcares.ares.player.AresCache;
import com.mcares.ares.player.AresPlayer;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CheckAutoClick extends TimerCheck {

    @ConfigData(value = "max-clicks-per-second")
    @Getter @Setter private int maxClicksPerSecond = 20;

    public CheckAutoClick() {
        super(CheckType.AUTO_CLICK);
    }

    @Override
    public void check(AresPlayer player) {
        CheckerAutoClick data = (CheckerAutoClick) player.getChecker(getCheckType());
        double[] cps = data.getClicksPerSecond();

        if(cps[3] >= maxClicksPerSecond
                && cps[2] >= maxClicksPerSecond
                && cps[1] >= maxClicksPerSecond
                && cps[0] >= maxClicksPerSecond){
            fail(player);
        }

        cps[0] = cps[1];
        cps[1] = cps[2];
        cps[2] = cps[3];
        cps[3] = 0.0;
        data.setClicksPerSecond(cps);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(PlayerInteractEvent e){
        Player pl = e.getPlayer();
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            AresPlayer p = AresCache.get().getAresPlayer(pl);
            updateCps(p,1);//Add 1 to their most recent CPS
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent e){
        if(e.isCancelled()) return;
        Player pl = e.getPlayer();
        AresPlayer p = AresCache.get().getAresPlayer(pl);
        updateCps(p, -0.5);
    }

    private void updateCps(AresPlayer p, double i){
        CheckerAutoClick data = (CheckerAutoClick) p.getChecker(getCheckType());
        double[] cps = data.getClicksPerSecond();
        cps[3] = cps[3] + i;
        data.setClicksPerSecond(cps);
    }

}
