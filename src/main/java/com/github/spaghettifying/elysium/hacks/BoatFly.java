package com.github.spaghettifying.elysium.hacks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class BoatFly {

    public static boolean enabled = false;

    private static int toggle = 1;
    private static final int MAX_SPEED = 3;
    private static final double FALL_SPEED = -0.04;
    private static double acceleration = 0.1;

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
                if (acceleration < MAX_SPEED)
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
    }
}
