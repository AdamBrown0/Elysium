package com.github.spaghettifying.elysium.hacks;

import com.github.spaghettifying.elysium.hud.EnabledMods;
import dev.lambdaurora.spruceui.option.SpruceCheckboxBooleanOption;
import dev.lambdaurora.spruceui.option.SpruceDoubleInputOption;
import dev.lambdaurora.spruceui.option.SpruceIntegerInputOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class BoatFly {

    public static boolean enabled = false;
    public static int maxSpeed = 3;
    public static double acceleration = 0.1;

    private static int toggle = 0;
    private static final double FALL_SPEED = -0.04;

    public static void tick(MinecraftClient client) {
        if (!enabled || client.player == null)
            return;
        boolean jumpPressed = client.options.jumpKey.isPressed();
        boolean forwardPressed = client.options.forwardKey.isPressed();
        boolean leftPressed = client.options.leftKey.isPressed();
        boolean rightPressed = client.options.rightKey.isPressed();
        boolean backPressed = client.options.backKey.isPressed();
        Entity object = client.player;
        if (client.player.hasVehicle()) {
            object = client.player.getVehicle();
        }
        assert object != null;
        Vec3d velocity = object.getVelocity();
        Vec3d newVelocity = new Vec3d(velocity.x, -FALL_SPEED, velocity.z);
        if (jumpPressed) {
            if (forwardPressed) {
                newVelocity = client.player.getRotationVector().multiply(acceleration);
            }
            if (leftPressed && !client.player.hasVehicle()) {
                newVelocity = client.player.getRotationVector().multiply(acceleration).rotateY((float) (Math.PI/2));
                newVelocity = new Vec3d(newVelocity.x, 0, newVelocity.z);
            }
            if (rightPressed && !client.player.hasVehicle()) {
                newVelocity = client.player.getRotationVector().multiply(acceleration).rotateY((float) ((-Math.PI)/2));
                newVelocity = new Vec3d(newVelocity.x, 0, newVelocity.z);
            }
            if (backPressed) {
                newVelocity = client.player.getRotationVector().negate().multiply(acceleration);
            }
            newVelocity = new Vec3d(newVelocity.x, (toggle == 0 && newVelocity.y > FALL_SPEED) ? FALL_SPEED : newVelocity.y, newVelocity.z);
            object.setVelocity(newVelocity);
            if (forwardPressed || leftPressed || rightPressed || backPressed) {
                if (acceleration < maxSpeed)
                    acceleration += 0.1;
            } else if (acceleration > 0.2) {
                acceleration -= 0.2;
            }
        }
        if (toggle == 0 || newVelocity.y <= -0.04) {
            toggle = 40;
//            System.out.println("Falling");
        }
        toggle--;
        if (object.getVelocity().y > 0 && !jumpPressed && toggle == 0) {
            newVelocity = new Vec3d(newVelocity.x, newVelocity.y - FALL_SPEED, newVelocity.z);
            object.setVelocity(newVelocity);
        }
    }

    public static void construct(SpruceOptionListWidget container) {
        SpruceOption checkboxOption = new SpruceCheckboxBooleanOption("elysium.option.checkbox.boat-fly",
                () -> BoatFly.enabled,
                newValue -> {
                    BoatFly.enabled = newValue;
                    EnabledMods.enableMod(BoatFly.class, newValue);
                    System.out.println("BoatFly: " + BoatFly.enabled);
                },
                Text.literal("Enable/Disable BoatFly"),
                true);
        SpruceOption intOption = new SpruceIntegerInputOption("elysium.option.input.integer.boat-fly.speed",
                () -> BoatFly.maxSpeed,
                newValue -> {
                    BoatFly.maxSpeed = newValue;
                    System.out.println("BoatFly Max Speed: " + BoatFly.maxSpeed);
                },
                Text.literal("Set BoatFly max speed"));
        SpruceOption doubleOption = new SpruceDoubleInputOption("elysium.option.input.integer.boat-fly.acceleration",
                () -> BoatFly.acceleration,
                newValue -> {
                    BoatFly.acceleration = newValue;
                    System.out.println("BoatFly Acceleration: " + BoatFly.acceleration);
                },
                Text.literal("Set BoatFly acceleration"));
        container.addSingleOptionEntry(checkboxOption);
        container.addSmallSingleOptionEntry(intOption);
        container.addSmallSingleOptionEntry(doubleOption);

    }

}
