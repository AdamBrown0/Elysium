package com.github.spaghettifying.elysium.config;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    private static final KeyBinding OPEN_MENU = new KeyBinding("key.elysium.open-menu", GLFW.GLFW_KEY_G, "key.categories.ui");
    private static final KeyBinding BOAT_FLY = new KeyBinding("key.elysium.boat-fly", GLFW.GLFW_KEY_COMMA, "key.categories.movement");


    public static final ImmutableList<KeyBinding> KEY_BINDINGS = ImmutableList.of(
            OPEN_MENU,
            BOAT_FLY
    );

}
