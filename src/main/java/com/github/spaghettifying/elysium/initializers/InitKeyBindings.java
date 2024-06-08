package com.github.spaghettifying.elysium.initializers;

import com.github.spaghettifying.elysium.config.KeyBindings;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;

public class InitKeyBindings {

    public static void init() {
        for (KeyBinding keyBinding : KeyBindings.KEY_BINDINGS) {
            KeyBindingHelper.registerKeyBinding(keyBinding);
        }
    }

}
