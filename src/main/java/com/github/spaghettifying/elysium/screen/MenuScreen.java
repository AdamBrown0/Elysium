package com.github.spaghettifying.elysium.screen;

import com.github.spaghettifying.elysium.hacks.BoatFly;
import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.option.SpruceCheckboxBooleanOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
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

            SpruceOption checkboxOption = new SpruceCheckboxBooleanOption("elysium.option.checkbox.boat-fly",
                    () -> BoatFly.enabled,
                    newValue -> {
                        BoatFly.enabled = newValue;
                        System.out.println("BoatFly: " + BoatFly.enabled);
                    },
                    Text.literal("Enable/Disable BoatFly"),
                    true);
            container.addOptionEntry(checkboxOption, null);

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