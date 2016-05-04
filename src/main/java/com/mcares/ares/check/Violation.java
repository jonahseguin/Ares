package com.mcares.ares.check;

import com.mcares.ares.player.AresPlayer;
import lombok.Data;

import java.util.UUID;

@Data
public class Violation {

    private final AresPlayer player;
    private final int totalVL;
    private final CheckType check;
    private final int vl;
    private final String id = UUID.randomUUID().toString().substring(0,6);
    private final boolean cancelled;

}
