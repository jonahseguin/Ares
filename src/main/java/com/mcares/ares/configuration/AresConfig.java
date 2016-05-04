package com.mcares.ares.configuration;

import com.mcares.ares.configuration.annotations.ConfigData;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;

@Getter
@Setter
public class AresConfig extends Configuration {

    public AresConfig(Plugin plugin) {
        super(plugin);
        load();
        save();
    }

    @ConfigData("enabled") private boolean enabled = true;
    @ConfigData("alert-format-detail") private String alertFormatDetail = "&7[&c&lAres&r&7] %player&7(&c%totalvlxVL&7)&r &7failed &9%check" +
            "&7(%detail&7)[&c%vlVL&7]";
    @ConfigData("alert-format") private String alertFormat = "&7[&c&lAres&r&7] %player&7(&c%totalvlxVL&7)&r &7failed &9%check" +
            "&7[&c%vlVL&7]";

    @ConfigData("prefix") private String prefix = "&7[&c&lAres&r&7]";

}
