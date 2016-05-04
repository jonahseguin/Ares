package com.mcares.ares;

import com.google.common.collect.ImmutableList;
import com.mcares.ares.check.Check;
import com.mcares.ares.check.CheckManager;
import com.mcares.ares.check.TimerCheck;
import com.mcares.ares.player.AresCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AresTimer implements Runnable {
    private final List<TimerCheck> checks;

    public AresTimer(List<TimerCheck> checks) {
        this.checks = ImmutableList.copyOf(checks);
    }
    public AresTimer() {
        List<TimerCheck> list = new ArrayList<>();
        for (Check check : CheckManager.get().getChecks().values()) {
            if (check instanceof TimerCheck) {
                list.add((TimerCheck) check);
            }
        }
        this.checks = ImmutableList.copyOf(list);
    }

    public void run(){
        for (TimerCheck timerCheck : checks) {
            if(!timerCheck.isEnabled()) continue;
            for(Player pl : Bukkit.getOnlinePlayers()){
                if(AresCache.get().contains(pl.getName())){
                    timerCheck.check(AresCache.get().getAresPlayer(pl.getName()));
                }
            }
        }
    }
}
