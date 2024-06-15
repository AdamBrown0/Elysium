package com.github.spaghettifying.elysium.hack.impl;

import com.github.spaghettifying.elysium.annotation.Listen;
import com.github.spaghettifying.elysium.hack.Hack;
import com.github.spaghettifying.elysium.hud.EnabledMods;
import dev.lambdaurora.spruceui.option.SpruceCheckboxBooleanOption;
import dev.lambdaurora.spruceui.option.SpruceDoubleInputOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

@Getter
@Setter
@Listen(PlayerMoveC2SPacket.PositionAndOnGround.class)
@Listen(PlayerMoveC2SPacket.Full.class)
public class Speed extends Hack {

    private double maxSpeed;
    private double acceleration;
    private double miniJump;

    private static double currentSpeed = 0;

    public Speed() {
        this.identifier = "Speed";
        this.enabled = false;
        this.maxSpeed = 0.66F;
        this.acceleration = 1.8;
        this.miniJump = 0.1;
    }

    @Override
    public void tick(MinecraftClient client) {
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

    @Override
    public void construct(SpruceOptionListWidget container) {
        SpruceOption checkboxOption = new SpruceCheckboxBooleanOption("elysium.option.checkbox.speed",
                () -> this.enabled,
                newValue -> {
                    this.enabled = newValue;
                    EnabledMods.enableMod(this, newValue);
                    System.out.println("Speed: " + this.enabled);
                },
                Text.literal("Enable/Disable Speed"),
                true);
        SpruceOption doubleOption = new SpruceDoubleInputOption("elysium.option.input.integer.speed.max-speed",
                () -> this.maxSpeed,
                newValue -> {
                    this.maxSpeed = newValue;
                    System.out.println("Speed Max Speed: " + this.maxSpeed);
                },
                Text.literal("Set Speed Max Speed"));
        container.addSingleOptionEntry(checkboxOption);
        container.addSmallSingleOptionEntry(doubleOption);
        doubleOption = new SpruceDoubleInputOption("elysium.option.input.integer.speed.acceleration",
                () -> this.acceleration,
                newValue -> {
                    this.acceleration = newValue;
                    System.out.println("Speed Acceleration: " + this.acceleration);
                },
                Text.literal("Set Speed Acceleration"));
        container.addSmallSingleOptionEntry(doubleOption);
        doubleOption = new SpruceDoubleInputOption("elysium.option.input.integer.speed.mini-jump",
                () -> this.miniJump,
                newValue -> {
                    this.miniJump = newValue;
                    System.out.println("Speed Mini Jump: " + this.miniJump);
                },
                Text.literal("Set Speed Mini Jump"));
        container.addSmallSingleOptionEntry(doubleOption);
    }

}
