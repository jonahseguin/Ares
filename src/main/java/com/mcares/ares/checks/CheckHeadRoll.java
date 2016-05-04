package com.mcares.ares.checks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.mcares.ares.Ares;
import com.mcares.ares.check.Check;
import com.mcares.ares.check.CheckManager;
import com.mcares.ares.check.CheckType;
import com.mcares.ares.player.AresCache;
import com.mcares.ares.player.AresPlayer;

import org.bukkit.entity.Player;

public class CheckHeadRoll extends Check {

    public CheckHeadRoll() {
        super(CheckType.HEAD_ROLL);
        Ares.getProtocolManager().addPacketListener(new PacketAdapter(Ares.getPlugin(), ListenerPriority.HIGHEST,
                PacketType.Play.Client.LOOK) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if(!isEnabled()) return;
                if(event.isCancelled()) return;
                Player p = event.getPlayer();
                AresPlayer ap = AresCache.get().getAresPlayer(p);
                if (event.getPacketType() == PacketType.Play.Client.LOOK) {
                    float pitch = event.getPacket().getFloat().readSafely(1);
                    if (pitch > 90.1F || pitch < -90.1F) {
                        if(fail(ap).isCancelled()){
                            event.setCancelled(true);
                        }
                    }
                }
            }
        });
    }


}
