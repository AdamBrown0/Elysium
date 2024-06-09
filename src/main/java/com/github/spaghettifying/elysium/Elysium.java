package com.github.spaghettifying.elysium;

import com.github.spaghettifying.elysium.config.KeyBindings;
import com.github.spaghettifying.elysium.hacks.BoatFly;
import com.github.spaghettifying.elysium.hud.EnabledMods;
import com.github.spaghettifying.elysium.initializers.InitKeyBindings;
import com.github.spaghettifying.elysium.screen.MenuScreen;
import dev.lambdaurora.spruceui.hud.HudManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class Elysium implements ClientModInitializer {

    private static final String identifier = "Elysium";

    @Override
    public void onInitializeClient() {
        InitKeyBindings.init();
        HudManager.register(new EnabledMods(new Identifier("enabled-mods-hud")));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KeyBindings.KEY_BINDINGS.getFirst().wasPressed()) {
                client.setScreen(new MenuScreen(null));
            }
            while (KeyBindings.KEY_BINDINGS.getLast().wasPressed()) {
                BoatFly.enabled = !BoatFly.enabled;
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.networkHandler.sendChatMessage("Boat Fly: " + BoatFly.enabled);
            }
        });
    }

    public static String getIdentifier() { return identifier; }

}
