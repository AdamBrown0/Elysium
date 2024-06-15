package com.github.spaghettifying.elysium.hack;

import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;

@Getter
public abstract class Hack {
    protected String identifier = "GenericHack";

    @Setter
    protected boolean enabled;

    public abstract void tick(MinecraftClient client);
    public abstract void construct(SpruceOptionListWidget container);
}
