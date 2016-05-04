package com.mcares.ares.cmd.commands;

import com.mcares.ares.Ares;
import com.mcares.ares.cmd.AresCommand;
import com.mcares.ares.cmd.CmdArgs;
import com.mcares.ares.cmd.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@Command(name = "help", usage = "/ares help")
public class CommandHelp implements AresCommand{

    @Override
    public void onCommand(CmdArgs cmdArgs) {
        CommandSender sender = cmdArgs.getSender();
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "----------------------------------------------------");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7*** [&c&lAres&r&7] ***"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7v"+Ares.getPlugin().getDescription().getVersion()+" by Shawckz, TheLightMC and Evoltr"));
        for(AresCommand cmd : Ares.getCommandHandler().getCommands()){
            if(cmd.getClass().isAnnotationPresent(Command.class)){
                Command c = cmd.getClass().getAnnotation(Command.class);
                sender.sendMessage(ChatColor.RED+"/ares "+ChatColor.GRAY+c.name());
            }
        }
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "----------------------------------------------------");
    }
}
