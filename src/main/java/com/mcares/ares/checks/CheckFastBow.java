package com.mcares.ares.checks;

import com.mcares.ares.check.Check;
import com.mcares.ares.check.CheckData;
import com.mcares.ares.check.CheckType;
import com.mcares.ares.player.AresCache;
import com.mcares.ares.player.AresPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class CheckFastBow extends Check {

    public CheckFastBow(){
        super(CheckType.FAST_BOW);
    }

    @EventHandler
    public void onPull(PlayerInteractEvent e){
        Player pl = e.getPlayer();
        AresPlayer p = AresCache.get().getAresPlayer(pl);
        if(pl.getItemInHand() != null){
            if(pl.getItemInHand().getType() == Material.BOW){
                p.getData().setBowPull(System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent e){
        if(e.getEntity() instanceof Arrow){
            Arrow arrow = (Arrow) e.getEntity();
            if(arrow.getShooter() != null){
                if(arrow.getShooter() instanceof Player){
                    Player pl = (Player) arrow.getShooter();
                    AresPlayer p = AresCache.get().getAresPlayer(pl);
                    p.getData().setBowShoot(System.currentTimeMillis());
                    p.getData().setBowPower(e.getEntity().getVelocity().length());
                    if(check(p)){
                        e.setCancelled(true);
                    }
                    reset(p);
                }
            }
        }
    }

    private boolean check(AresPlayer p){
        CheckData data = p.getData();
        double pull = data.getBowShoot() - data.getBowPull();
        double power = data.getBowPower();

        if(power >= 2.5){
            if(pull <= 200){
                return fail(p).isCancelled();
            }
        }
        return false;
    }

    @EventHandler
    public void onSwap(PlayerItemHeldEvent e){
        Player pl = e.getPlayer();
        AresPlayer p = AresCache.get().getAresPlayer(pl);
        reset(p);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player pl = e.getPlayer();
        AresPlayer p = AresCache.get().getAresPlayer(pl);
        reset(p);
    }

    private void reset(AresPlayer p){
        p.getData().setBowShoot(0);
        p.getData().setBowPull(0);
        p.getData().setBowPower(0);
    }

}
