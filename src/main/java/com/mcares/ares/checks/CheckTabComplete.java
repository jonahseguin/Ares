package com.mcares.ares.checks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.mcares.ares.Ares;
import com.mcares.ares.check.Check;
import com.mcares.ares.check.CheckType;
import com.mcares.ares.player.AresCache;
import com.mcares.ares.player.AresPlayer;

import org.bukkit.entity.Player;

public class CheckTabComplete extends Check {

    public CheckTabComplete() {
        super(CheckType.TAB_COMPLETE);

        Ares.getProtocolManager().addPacketListener(new PacketAdapter(Ares.getPlugin(), ListenerPriority.NORMAL,
                PacketType.Play.Client.TAB_COMPLETE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if(!isEnabled()) return;
                Player p = event.getPlayer();
                AresPlayer ap = AresCache.get().getAresPlayer(p);
                if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
                    String s = event.getPacket().getStrings().read(0);
                    if (s.length() <= 2) return;
                    if(s.startsWith(".") && !s.startsWith("./")){
                        fail(ap, s);
                    }
                }
            }
        });
    }

}
