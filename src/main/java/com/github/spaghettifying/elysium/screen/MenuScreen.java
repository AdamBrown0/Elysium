package com.github.spaghettifying.elysium.screen;

import com.github.spaghettifying.elysium.Elysium;
import com.github.spaghettifying.elysium.hack.Hack;
import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import dev.lambdaurora.spruceui.widget.container.tabbed.SpruceTabbedWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

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

        constructTabEntry(tabbedWidget, "Flight", "They said pigs would never fly smh");
        constructTabEntry(tabbedWidget, "Ground", "Yum :)");

        this.addDrawableChild(tabbedWidget);

        // Add done button
        this.addDrawableChild(new SpruceButtonWidget(Position.of(this, this.width / 2 - 75, this.height - 29), 150, 20, SpruceTexts.GUI_DONE,
                btn -> {
                    assert this.client != null;
                    this.client.setScreen(this.parent);
                }).asVanilla());
    }

    private void constructTabEntry(SpruceTabbedWidget tabbedWidget, String category, String description) {
        tabbedWidget.addTabEntry(Text.literal(category), Text.literal(description), (width, height) -> {
            var container = new SpruceOptionListWidget(Position.origin(), width, height);
            for (Map.Entry<Hack, String> hackEntry : Elysium.getHackRegistry().getHacks().entrySet())
                if (hackEntry.getValue().equals(category))
                    hackEntry.getKey().construct(container);
            return container;
        });
    }

}