package com.github.spaghettifying.elysium.hud;

import com.github.spaghettifying.elysium.Elysium;
import dev.lambdaurora.spruceui.hud.Hud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EnabledMods extends Hud {

    public static int x = 1;
    public static int y = 1;

    private static final ArrayList<Class<?>> enabledMods = new ArrayList<>();

    public EnabledMods(@NotNull Identifier id) {
        super(id);
    }

    @Override
    public void init(@NotNull MinecraftClient client, int screenWidth, int screenHeight) {
        super.init(client, screenWidth, screenHeight);
    }

    @Override
    public void render(DrawContext graphics, float tickDelta) {
        super.render(graphics, tickDelta);
        renderLogo(graphics);
        update(graphics);
    }

    private void renderLogo(DrawContext graphics) {
        graphics.drawText(MinecraftClient.getInstance().textRenderer, Text.literal(Elysium.getIdentifier()), x, y, -65281, true);
    }

    public void update(DrawContext graphics) {
        int count = 1;
        for (Class<?> mod : enabledMods) {
            graphics.drawText(MinecraftClient.getInstance().textRenderer, Text.literal(mod.getSimpleName()), 1, y + MinecraftClient.getInstance().textRenderer.fontHeight * count++, -123343, true);
        }
    }

    public static void enableMod(Class<?> mod, boolean enabled) {
        if (enabled)
            enabledMods.add(mod);
        else
            enabledMods.remove(mod);
    }

}
