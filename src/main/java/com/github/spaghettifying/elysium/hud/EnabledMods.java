package com.github.spaghettifying.elysium.hud;

import com.github.spaghettifying.elysium.Elysium;
import com.github.spaghettifying.elysium.hack.Hack;
import dev.lambdaurora.spruceui.hud.Hud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class EnabledMods extends Hud {


    public static int x = 1;
    public static int y = 1;

    private static final HashSet<Hack> enabledMods = new HashSet<>();

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
        for (Hack mod : enabledMods) {
            graphics.drawText(MinecraftClient.getInstance().textRenderer, Text.literal(mod.getIdentifier()), 1, y + MinecraftClient.getInstance().textRenderer.fontHeight * count++, -123343, true);
        }
    }

    public static <T extends Hack> void enableMod(T mod, boolean enabled) {
        if (enabled)
            enabledMods.add(mod);
        else
            enabledMods.remove(mod);
    }

}
