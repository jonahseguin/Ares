package com.mcares.ares.cmd.commands;

import com.mcares.ares.Ares;
import com.mcares.ares.cmd.AresCommand;
import com.mcares.ares.cmd.CmdArgs;
import com.mcares.ares.cmd.Command;
import com.mcares.ares.player.AresCache;
import com.mcares.ares.player.AresPlayer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "alerts", usage = "/alerts", playerOnly = true, permission = "ares.alerts")
public class CommandAlerts implements AresCommand {

    @Override
    public void onCommand(CmdArgs cmdArgs) {
        Player p = (Player) cmdArgs.getSender();
        AresPlayer ap = AresCache.get().getAresPlayer(p);
        if(cmdArgs.getArgs().length > 0 && p.hasPermission("ares.alerts.others")){
            Player t = cmdArgs.getPlayer(0);
            if(t != null){
                AresPlayer at = AresCache.get().getAresPlayer(t);
                at.setAlertsEnabled(!at.isAlertsEnabled());
                t.sendMessage(Ares.getPrefix() + " §7Alerts have been " + (at.isAlertsEnabled() ?
                        "§aenabled" : "§cdisabled") + "§7.");
                p.sendMessage(Ares.getPrefix() + " §7Alerts have been "+(at.isAlertsEnabled() ? "§aenabled" : "§cdisabled")+
                        "§7 for "+t.getDisplayName()+".");
            }
            else{
                p.sendMessage(ChatColor.RED+"Player '"+cmdArgs.getArg(0)+"' not found.");
            }
        }
        else{
            ap.setAlertsEnabled(!ap.isAlertsEnabled());
            p.sendMessage(Ares.getPrefix() + " §7Alerts have been "+(ap.isAlertsEnabled() ?
                    "§aenabled" : "§cdisabled")+"§7.");
        }
    }
}
