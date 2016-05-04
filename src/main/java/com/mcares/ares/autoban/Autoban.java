package com.mcares.ares.autoban;

import com.mcares.ares.check.CheckType;
import lombok.Data;

@Data
public class Autoban {
    private final String name;
    private final CheckType checkType;
    private boolean cancelled;
}
