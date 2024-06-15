package com.github.spaghettifying.elysium.mixin;

import com.github.spaghettifying.elysium.util.TickByMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VehicleMoveC2SPacket.class)
public class VehicleMoveC2SPacketMixin {
    @Inject(at = @At("TAIL"), method = "write")
    public void write(PacketByteBuf buf, CallbackInfo ci) {
        TickByMixin.tick(VehicleMoveC2SPacket.class, MinecraftClient.getInstance());
    }
}
