package com.github.spaghettifying.elysium.hack.impl;

import com.github.spaghettifying.elysium.annotation.Listen;
import com.github.spaghettifying.elysium.hack.Hack;
import com.github.spaghettifying.elysium.hud.EnabledMods;
import dev.lambdaurora.spruceui.option.*;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

@Getter
@Setter
@Listen(PlayerMoveC2SPacket.PositionAndOnGround.class)
@Listen(PlayerMoveC2SPacket.Full.class)
public class CreativeFlight extends Hack {

    private int antiKickInterval;
    private double fallSpeed;
    private float flySpeed;

    private static int tickCounter = 0;
    private static long lastTime = System.nanoTime();

    public CreativeFlight() {
        this.identifier = "CreativeFlight";
        this.enabled = false;
        this.antiKickInterval = 60;
        this.fallSpeed = -0.04;
        this.flySpeed = 0.05f;
    }

    @Override
    public void tick(MinecraftClient client) {
        assert client.player != null;
        PlayerAbilities playerAbilities = client.player.getAbilities();
        playerAbilities.allowFlying = enabled;
        playerAbilities.setFlySpeed(flySpeed);

        if (playerAbilities.flying) {
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

    @Override
    public void construct(SpruceOptionListWidget container) {
        SpruceOption checkboxOption = new SpruceCheckboxBooleanOption("elysium.option.checkbox.creative-flight",
                () -> this.enabled,
                newValue -> {
                    this.enabled = newValue;
                    EnabledMods.enableMod(this, newValue);
                    System.out.println("CreativeFlight: " + this.enabled);
                },
                Text.literal("Enable/Disable CreativeFlight"),
                true);
        SpruceOption intOption = new SpruceIntegerInputOption("elysium.option.input.integer.creative-flight.anti-kick-interval",
                () -> this.antiKickInterval,
                newValue -> {
                    this.antiKickInterval = newValue;
                    System.out.println("CreativeFlight Anti Kick Interval: " + this.antiKickInterval);
                },
                Text.literal("Set CreativeFlight Anti Kick Interval"));
        SpruceOption doubleOption = new SpruceDoubleInputOption("elysium.option.input.integer.creative-flight.fall-speed",
                () -> this.fallSpeed,
                newValue -> {
                    this.fallSpeed = newValue;
                    System.out.println("CreativeFlight Fall Speed: " + this.fallSpeed);
                },
                Text.literal("Set CreativeFlight Fall Speed"));
        SpruceOption floatOption = new SpruceFloatInputOption("elysium.option.input.integer.creative-flight.fly-speed",
                () -> this.flySpeed,
                newValue -> {
                    this.flySpeed = newValue;
                    System.out.println("CreativeFlight Fly Speed: " + this.flySpeed);
                },
                Text.literal("Set CreativeFlight Fly Speed"));
        container.addSingleOptionEntry(checkboxOption);
        container.addSmallSingleOptionEntry(intOption);
        container.addSmallSingleOptionEntry(doubleOption);
        container.addSmallSingleOptionEntry(floatOption);

    }

}
