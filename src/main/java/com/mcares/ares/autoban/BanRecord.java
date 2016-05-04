package com.mcares.ares.autoban;

import com.mcares.ares.check.CheckType;
import lombok.Data;

@Data
public class BanRecord {

    private final String id;//ban record id
    private final CheckType check;
    private final long time;
    private final String uniqueId;//banned player's UUID


}
