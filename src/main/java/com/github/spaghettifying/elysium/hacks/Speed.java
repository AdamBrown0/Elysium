package com.github.spaghettifying.elysium.hacks;

import com.github.spaghettifying.elysium.hud.EnabledMods;
import dev.lambdaurora.spruceui.option.SpruceCheckboxBooleanOption;
import dev.lambdaurora.spruceui.option.SpruceDoubleInputOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class Speed {

    public static boolean enabled = false;
    public static double maxSpeed = 0.66F;
    public static double acceleration = 1.8;
    public static double miniJump = 0.1;
    private static double currentSpeed = 0;

    public static void tick(MinecraftClient client) {
        if (!enabled)
            return;

        assert client.player != null;

        if (client.player.isSneaking() || (client.player.forwardSpeed == 0 && client.player.sidewaysSpeed == 0))
            return;

        if (client.player.forwardSpeed > 0 && !client.player.isSprinting())
            client.player.setSprinting(true);

        if (!client.player.isOnGround())
            return;

        Vec3d currentVelocity = client.player.getVelocity();
        client.player.setVelocity(currentVelocity.x * acceleration, currentVelocity.y + miniJump, currentVelocity.z * acceleration);

        currentVelocity = client.player.getVelocity();
        currentSpeed = Math.sqrt(Math.pow(currentVelocity.x, 2) + Math.pow(currentVelocity.z, 2));
        if (currentSpeed > maxSpeed)
            client.player.setVelocity(currentVelocity.x / currentSpeed * maxSpeed, currentVelocity.y, currentVelocity.z / currentSpeed * maxSpeed);
    }

    public static void construct(SpruceOptionListWidget container) {
        SpruceOption checkboxOption = new SpruceCheckboxBooleanOption("elysium.option.checkbox.speed",
                () -> Speed.enabled,
                newValue -> {
                    Speed.enabled = newValue;
                    EnabledMods.enableMod(Speed.class, newValue);
                    System.out.println("Speed: " + Speed.enabled);
                },
                Text.literal("Enable/Disable Speed"),
                true);
        SpruceOption doubleOption = new SpruceDoubleInputOption("elysium.option.input.integer.speed.max-speed",
                () -> Speed.maxSpeed,
                newValue -> {
                    Speed.maxSpeed = newValue;
                    System.out.println("Speed Max Speed: " + Speed.maxSpeed);
                },
                Text.literal("Set Speed Max Speed"));
        container.addSingleOptionEntry(checkboxOption);
        container.addSmallSingleOptionEntry(doubleOption);
        doubleOption = new SpruceDoubleInputOption("elysium.option.input.integer.speed.acceleration",
                () -> Speed.acceleration,
                newValue -> {
                    Speed.acceleration = newValue;
                    System.out.println("Speed Acceleration: " + Speed.acceleration);
                },
                Text.literal("Set Speed Acceleration"));
        container.addSmallSingleOptionEntry(doubleOption);
        doubleOption = new SpruceDoubleInputOption("elysium.option.input.integer.speed.mini-jump",
                () -> Speed.miniJump,
                newValue -> {
                    Speed.miniJump = newValue;
                    System.out.println("Speed Mini Jump: " + Speed.miniJump);
                },
                Text.literal("Set Speed Mini Jump"));
        container.addSmallSingleOptionEntry(doubleOption);
    }

}
