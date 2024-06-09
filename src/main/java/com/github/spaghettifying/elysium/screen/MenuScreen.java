package com.github.spaghettifying.elysium.screen;

import com.github.spaghettifying.elysium.hacks.BoatFly;
import com.github.spaghettifying.elysium.hacks.CreativeFlight;
import com.github.spaghettifying.elysium.hacks.Speed;
import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import dev.lambdaurora.spruceui.widget.container.tabbed.SpruceTabbedWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class MenuScreen extends SpruceScreen {

    private final Screen parent;

    public MenuScreen(@Nullable Screen parent) {
        super(Text.literal("Menu Screen"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        SpruceTabbedWidget tabbedWidget = new SpruceTabbedWidget(Position.of(this, 0, 4), this.width, this.height - 35 - 4, this.title);

        tabbedWidget.addSeparatorEntry(Text.literal("Movement"));

        tabbedWidget.addTabEntry(Text.literal("Flight"), null, (width, height) -> {
            var container = new SpruceOptionListWidget(Position.origin(), width, height);
            BoatFly.construct(container);
            CreativeFlight.construct(container);
            return container;
        });

        tabbedWidget.addTabEntry(Text.literal("Ground"), null, (width, height) -> {
            var container = new SpruceOptionListWidget(Position.origin(), width, height);
            Speed.construct(container);
            return container;
        });

        this.addDrawableChild(tabbedWidget);

        // Add done button
        this.addDrawableChild(new SpruceButtonWidget(Position.of(this, this.width / 2 - 75, this.height - 29), 150, 20, SpruceTexts.GUI_DONE,
                btn -> {
                    assert this.client != null;
                    this.client.setScreen(this.parent);
                }).asVanilla());
    }
}