package com.github.spaghettifying.elysium.initializers;

import com.github.spaghettifying.elysium.Elysium;
import com.github.spaghettifying.elysium.hack.HackRegistry;
import com.github.spaghettifying.elysium.hack.impl.BoatFly;
import com.github.spaghettifying.elysium.hack.impl.CreativeFlight;
import com.github.spaghettifying.elysium.hack.impl.Speed;

public class InitHacks {

    private static final HackRegistry hacks = Elysium.getHackRegistry();

    public static void init() {
        // Flight
        hacks.registerHack(new CreativeFlight(), "Flight");
        hacks.registerHack(new BoatFly(), "Flight");

        //Ground
        hacks.registerHack(new Speed(), "Ground");
    }

}
