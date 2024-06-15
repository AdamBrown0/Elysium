package com.github.spaghettifying.elysium;

import com.github.spaghettifying.elysium.config.KeyBindings;
import com.github.spaghettifying.elysium.hack.HackRegistry;
import com.github.spaghettifying.elysium.hud.EnabledMods;
import com.github.spaghettifying.elysium.initializers.InitHacks;
import com.github.spaghettifying.elysium.initializers.InitKeyBindings;
import com.github.spaghettifying.elysium.screen.MenuScreen;
import dev.lambdaurora.spruceui.hud.HudManager;
import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Elysium implements ClientModInitializer {

    @Getter
    private static final String identifier = "Elysium";

    @Getter
    private static final HackRegistry hackRegistry = new HackRegistry();

    @Getter
    private static final Logger logger = LoggerFactory.getLogger(Elysium.class);

    @Override
    public void onInitializeClient() {
        InitKeyBindings.init();
        InitHacks.init();
        HudManager.register(new EnabledMods(new Identifier("enabled-mods-hud")));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KeyBindings.KEY_BINDINGS.getFirst().wasPressed()) {
                client.setScreen(new MenuScreen(null));
            }
        });
    }

}
