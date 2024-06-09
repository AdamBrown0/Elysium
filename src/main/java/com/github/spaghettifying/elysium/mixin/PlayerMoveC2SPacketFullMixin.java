package com.github.spaghettifying.elysium.mixin;

import com.github.spaghettifying.elysium.hacks.CreativeFlight;
import com.github.spaghettifying.elysium.hacks.Speed;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoveC2SPacket.Full.class)
public class PlayerMoveC2SPacketFullMixin {

    @Inject(at = @At("TAIL"), method = "write")
    public void write(PacketByteBuf buf, CallbackInfo ci) {
        Speed.tick(MinecraftClient.getInstance());
        CreativeFlight.tick(MinecraftClient.getInstance());
    }

}
