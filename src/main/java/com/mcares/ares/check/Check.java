package com.mcares.ares.check;

import com.mcares.ares.Ares;
import com.mcares.ares.util.Alert;
import com.mcares.ares.autoban.AutobanManager;
import com.mcares.ares.configuration.annotations.ConfigData;
import com.mcares.ares.player.AresPlayer;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * The Check class
 * Superclass to all Checks, provides base and other important stuff.
 */
@Getter
@Setter
public abstract class Check extends CheckConfig implements Listener {

    private final CheckType checkType;

    @ConfigData(value = "enabled") private boolean enabled = true;

    @ConfigData(value = "autoban") private boolean autoban = true;

    @ConfigData(value = "cancel") private boolean cancel = true;

    @ConfigData(value = "punish-level") private int punishLevel = 10;

    @ConfigData(value = "raise-level") private int raiseLevel = 2;

    @ConfigData(value = "punish-command") private String punishCommand = "";

    public Check(final CheckType checkType) {
        super(checkType);
        this.checkType = checkType;
    }

    public final void setEnabled(final boolean enabled) {
        if(this.enabled != enabled){
            if(enabled){
                Bukkit.getServer().getPluginManager().registerEvents(this, Ares.getPlugin());
                Bukkit.getLogger().info("[Ares] Registered events for "+getName());
            }
            else{
                HandlerList.unregisterAll(this);
            }
        }
        this.enabled = enabled;
    }

    protected final Violation fail(AresPlayer player, String... detail){
        if(this.isCancel()){
            cancel(player);
        }

        final Violation violation = player.addVL(this.getCheckType(),this.isCancel());

        if(violation.getVl() > this.getPunishLevel() && this.isAutoban() && !AutobanManager.hasAutoban(player.getName())){
            //TODO: Autoban the player.
        }
        else{
            if(getDetail(violation) != null){
                // Send detailed alert
                Alert.send(violation, getDetail(violation));
            }
            else if (detail != null && detail.length > 0){
                Alert.send(violation, detail[0]);
            }
            else{
                // Send normal alert
                Alert.send(violation);
            }
        }

        return violation;
    }

    public void cancel(AresPlayer player) {
        // Can be overridden
    }

    public final String getName(){
        return checkType.getName();
    }

    public String getDetail(Violation violation){
        // Can be overriden
        return null;
    }

}
