package com.mcares.ares.checks;

import com.google.common.collect.ImmutableList;
import com.mcares.ares.Ares;
import com.mcares.ares.check.Check;
import com.mcares.ares.check.CheckType;
import com.mcares.ares.player.AresCache;
import com.mcares.ares.player.AresPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class CheckNoSlowdown extends Check {
    private final static double SPEED = 4.317;
    private final List<Material> slowdownBlocks;
    public CheckNoSlowdown() {
        super(CheckType.NO_SLOW_DOWN);
        slowdownBlocks = ImmutableList.of(Material.SOUL_SAND, Material.WEB, Material.WATER);
    }
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Block block = event.getFrom().getBlock();
        if (slowdownBlocks.contains(block.getRelative(BlockFace.DOWN).getType()) || slowdownBlocks.contains(block.getType())) {
            final AresPlayer player = AresCache.get().getAresPlayer(event.getPlayer());
            if (player.getData().getBlocksPerSecond() < SPEED) {
                return;
            }
            Bukkit.getScheduler().runTaskLater(Ares.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    if (player.getData().getBlocksPerSecond() < SPEED) {
                        fail(player);
                    }
                }
            },20);
        }
    }
}
