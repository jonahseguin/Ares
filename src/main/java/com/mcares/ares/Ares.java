package com.mcares.ares;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.mcares.ares.cache.CachePlayer;
import com.mcares.ares.check.CheckManager;
import com.mcares.ares.cmd.CommandHandler;
import com.mcares.ares.configuration.AresConfig;
import com.mcares.ares.database.DBManager;
import com.mcares.ares.player.AresCache;
import com.mcares.ares.util.Lag;
import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Ares extends JavaPlugin {

    @Getter private static Plugin plugin;
    @Getter private static AresConfig aresConfig;

    @Getter private static ProtocolManager protocolManager;

    @Getter private static CommandHandler commandHandler;

    public static String getPrefix(){
        return ChatColor.translateAlternateColorCodes('&',getAresConfig().getPrefix());
    }

    @Override
    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable(){
        plugin = this;

        aresConfig = new AresConfig(this);

        new DBManager(this);

        commandHandler = new CommandHandler(this);

        //Make reload-friendly, load all online players
        for(Player pl : Bukkit.getOnlinePlayers()){
            CachePlayer cp = AresCache.get().loadCachePlayer(pl.getName());
            if(cp != null){
                AresCache.get().put(cp);
            }
            else{
                cp = AresCache.get().create(pl.getName(), pl.getUniqueId().toString());
                AresCache.get().put(cp);
                cp.update();
            }
        }

        CheckManager.get().setupChecks();//Setup
        AresCache.get();//Setup
        Bukkit.getScheduler().runTaskTimer(this, new AresTimer(),20L,20L);

        Bukkit.getScheduler().runTaskTimer(this, new Lag(), 1L, 1L);

    }

    @Override
    public void onDisable(){

        //Make reload-friendly, save all online players
        for(Player pl : Bukkit.getOnlinePlayers()){
            AresCache.get().save(pl);
        }

        aresConfig.save();
        aresConfig = null;
        plugin = null;
    }

}
