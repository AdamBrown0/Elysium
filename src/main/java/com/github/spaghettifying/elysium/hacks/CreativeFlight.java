package com.github.spaghettifying.elysium.hacks;

import com.github.spaghettifying.elysium.hud.EnabledMods;
import dev.lambdaurora.spruceui.option.*;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class CreativeFlight {

    public static boolean enabled = false;
    public static int antiKickInterval = 60;
    public static double fallSpeed = -0.04;
    public static float flySpeed = 0.05f;

    private static int tickCounter = 0;
    private static long lastTime = System.nanoTime();

    public static void tick(MinecraftClient client) {
        assert client.player != null;
        PlayerAbilities playerAbilities = client.player.getAbilities();
        playerAbilities.allowFlying = enabled;
        playerAbilities.setFlySpeed(flySpeed);

        if (playerAbilities.flying) {
            System.out.println("Tick counter: " + tickCounter);
            Vec3d currentVelocity = client.player.getVelocity();
            long now = System.nanoTime();
            if (tickCounter > antiKickInterval + 2 || (now - lastTime) / 1_000_000_000.0 >= 0.9) {
                Vec3d newVelocity = new Vec3d(currentVelocity.x, fallSpeed, currentVelocity.z);
                client.player.setVelocity(newVelocity);
                tickCounter = 0;
                lastTime = now;
            }
            tickCounter++;
        }
    }

    public static void construct(SpruceOptionListWidget container) {
        SpruceOption checkboxOption = new SpruceCheckboxBooleanOption("elysium.option.checkbox.creative-flight",
                () -> CreativeFlight.enabled,
                newValue -> {
                    CreativeFlight.enabled = newValue;
                    EnabledMods.enableMod(CreativeFlight.class, newValue);
                    System.out.println("CreativeFlight: " + CreativeFlight.enabled);
                },
                Text.literal("Enable/Disable CreativeFlight"),
                true);
        SpruceOption intOption = new SpruceIntegerInputOption("elysium.option.input.integer.creative-flight.anti-kick-interval",
                () -> CreativeFlight.antiKickInterval,
                newValue -> {
                    CreativeFlight.antiKickInterval = newValue;
                    System.out.println("CreativeFlight Anti Kick Interval: " + CreativeFlight.antiKickInterval);
                },
                Text.literal("Set CreativeFlight Anti Kick Interval"));
        SpruceOption doubleOption = new SpruceDoubleInputOption("elysium.option.input.integer.creative-flight.fall-speed",
                () -> CreativeFlight.fallSpeed,
                newValue -> {
                    CreativeFlight.fallSpeed = newValue;
                    System.out.println("CreativeFlight Fall Speed: " + CreativeFlight.fallSpeed);
                },
                Text.literal("Set CreativeFlight Fall Speed"));
        SpruceOption floatOption = new SpruceFloatInputOption("elysium.option.input.integer.creative-flight.fly-speed",
                () -> CreativeFlight.flySpeed,
                newValue -> {
                    CreativeFlight.flySpeed = newValue;
                    System.out.println("CreativeFlight Fly Speed: " + CreativeFlight.flySpeed);
                },
                Text.literal("Set CreativeFlight Fly Speed"));
        container.addSingleOptionEntry(checkboxOption);
        container.addSmallSingleOptionEntry(intOption);
        container.addSmallSingleOptionEntry(doubleOption);
        container.addSmallSingleOptionEntry(floatOption);

    }

}
