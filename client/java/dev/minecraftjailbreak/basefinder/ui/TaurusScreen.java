package dev.minecraftjailbreak.basefinder.ui;

import dev.minecraftjailbreak.basefinder.TaurusClient;
import dev.minecraftjailbreak.basefinder.core.ClientModule;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * TAURUS centered crimson module menu.
 * Minecraft 26.2 uses Screen.extractRenderState / GuiGraphicsExtractor.
 */
public final class TaurusScreen extends Screen {
    private static final int RED = 0xFFFF3038;
    private static final int PANEL = 0xF0101015;
    private static final int PANEL_ALT = 0xFF19151B;
    private static final int MUTED = 0xFFAAA4AD;

    private final List<Button> moduleButtons = new ArrayList<>();

    public TaurusScreen() {
        super(Component.literal("TAURUS"));
    }

    @Override
    protected void init() {
        moduleButtons.clear();
        int panelWidth = Math.min(430, this.width - 24);
        int left = (this.width - panelWidth) / 2;
        int top = Math.max(24, (this.height - 270) / 2);
        int buttonWidth = (panelWidth - 54) / 2;
        int buttonHeight = 28;
        int gap = 10;
        int firstY = top + 112;

        addModuleButton("chunk_finder", "CHUNK FINDER", left + 18, firstY,
                buttonWidth, buttonHeight);
        addModuleButton("stash_finder", "STASH FINDER", left + 18 + buttonWidth + gap,
                firstY, buttonWidth, buttonHeight);
        addModuleButton("freecam", "FREECAM", left + 18, firstY + 42,
                buttonWidth, buttonHeight);
        addModuleButton("freelook", "FREELOOK", left + 18 + buttonWidth + gap,
                firstY + 42, buttonWidth, buttonHeight);

        addRenderableWidget(Button.builder(Component.literal("CLOSE"), button ->
                this.onClose()).bounds(left + 18, firstY + 104, panelWidth - 36, 24).build());
    }

    private void addModuleButton(String id, String label, int x, int y, int w, int h) {
        ClientModule module = TaurusClient.MODULES.get(id).orElse(null);
        if (module == null) return;
        Button button = Button.builder(Component.literal(label + "  [" +
                (module.isEnabled() ? "ON" : "OFF") + "]"), b -> {
            module.setEnabled(!module.isEnabled());
            b.setMessage(Component.literal(label + "  [" +
                    (module.isEnabled() ? "ON" : "OFF") + "]"));
        }).bounds(x, y, w, h).build();
        moduleButtons.add(button);
        addRenderableWidget(button);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX,
                                   int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        int panelWidth = Math.min(430, this.width - 24);
        int panelHeight = 270;
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;

        graphics.fill(0, 0, this.width, this.height, 0x99000000);
        graphics.fill(left, top, left + panelWidth, top + panelHeight, PANEL);
        graphics.fill(left, top, left + panelWidth, top + 3, RED);
        graphics.fill(left + 12, top + 12, left + panelWidth - 12, top + 13, 0xFF4A222A);

        graphics.text(this.font, "TAURUS", left + 18, top + 22, RED, true);
        graphics.text(this.font, "MINECRAFT CLIENT  /  BASE FINDER EDITION",
                left + 18, top + 42, 0xFFE8E2E8, true);
        graphics.text(this.font, "MODULE CONTROL", left + 18, top + 77, MUTED, false);

        graphics.fill(left + 18, top + 94, left + panelWidth - 18, top + 95, 0xFF54252D);
        graphics.text(this.font, "RIGHT SHIFT  •  TOGGLE MENU",
                left + 18, top + panelHeight - 29, MUTED, false);
        graphics.text(this.font, "TAURUS 1.0", left + panelWidth - 92,
                top + panelHeight - 29, RED, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
