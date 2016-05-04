package com.mcares.ares.check;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckData{

    //Fuck it.
    //Make sure to assign all values a default value, even null.

    /**
     * Speed
     */
    private double blocksPerSecond = 0;

    /**
     * NoSwing
     */
    private boolean hasSwung = false;

    /**
     * BedFly
     */
    private boolean enteredBed = false;

    /**
     * FastBow
     */
    private long bowPull = 0;
    private long bowShoot = 0;
    private double bowPower = 0;

    /**
     * Regen
     */
    private double healthPerSecond = 0;

    /**
     * AutoClick
     */
    private double[] clicksPerSecond = new double[]{0,0,0,0};// We will be keeping track of the past 4 seconds worth of cps

    /**
     * HighJump
     */
    private boolean jumping;
}
