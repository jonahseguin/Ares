package com.mcares.ares.player;

/*
 * Copyright (c) 2015 Jonah Seguin (Shawckz).  All rights reserved.  You may not modify, decompile, distribute or use any code/text contained in this document(plugin) without explicit signed permission from Jonah Seguin.
 */


import com.mcares.ares.Ares;
import com.mcares.ares.cache.AbstractCache;
import com.mcares.ares.cache.CachePlayer;
import com.mcares.ares.util.Alert;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by Jonah on 6/15/2015.
 */
public class AresCache extends AbstractCache {

    private static AresCache instance;

    private AresCache(Plugin plugin) {
        super(plugin, AresPlayer.class);
    }

    public static AresCache get() {
        if (instance == null) {
            synchronized (AresCache.class) {
                if (instance == null) {
                    instance = new AresCache(Ares.getPlugin());
                }
            }
        }
        return instance;
    }

    //See superclass for documentation
    public AresPlayer getAresPlayer(String name){
        CachePlayer cachePlayer = getBasePlayer(name);
        if(cachePlayer != null) {
            return (AresPlayer) getBasePlayer(name);
        }
        return null;
    }

    //See superclass for documentation
    public AresPlayer getAresPlayer(Player p){
        return getAresPlayer(p.getName());
    }

    @Override
    public CachePlayer create(String name, String uuid) {
        return new AresPlayer(name,uuid);
    }

    @Override
    public void init(Player p,CachePlayer cachePlayer) {
        if(cachePlayer instanceof AresPlayer){
            AresPlayer aresPlayer = (AresPlayer) cachePlayer;
            aresPlayer.setBukkitPlayer(p);
            if(p.hasPermission("ares.alerts")){
                aresPlayer.setAlertsEnabled(true);
                p.sendMessage(Ares.getPrefix() + " §7Alerts have been §aenabled§7.");
            }
            //TODO: Setup their shit.
        }
    }
}
