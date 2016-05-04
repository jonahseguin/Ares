package com.mcares.ares.check;

import com.mcares.ares.player.AresPlayer;

public abstract class TimerCheck extends Check {

    public TimerCheck(final CheckType checkType) {
        super(checkType);
    }

    public abstract void check(AresPlayer player);//This method will be called ONCE every 20 ticks; or once a second.
                                                  //Used for checks such as speed that need to check something on a
                                                  //Per-second basis

}
