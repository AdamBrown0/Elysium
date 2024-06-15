package com.github.spaghettifying.elysium.hack;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class HackRegistry {

    private final HashMap<Hack, String> hacks = new HashMap<>();

    public <T extends Hack> void registerHack(T hack, String category) {
        hacks.put(hack, category);
    }

}
