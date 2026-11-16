package dev.minecraftjailbreak.basefinder;

import com.mojang.blaze3d.platform.InputConstants;
import dev.minecraftjailbreak.basefinder.core.ModuleRegistry;
import dev.minecraftjailbreak.basefinder.modules.ChunkFinder;
import dev.minecraftjailbreak.basefinder.modules.StashFinder;
import dev.minecraftjailbreak.basefinder.modules.FreecamController;
import dev.minecraftjailbreak.basefinder.modules.FreelookController;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
public final class TaurusClient implements ClientModInitializer {
    public static final ModuleRegistry MODULES = new ModuleRegistry();

    private static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath("taurus", "main"));
    private static KeyMapping openMenuKey;

    @Override
    public void onInitializeClient() {
        MODULES.register(new ChunkFinder());
        MODULES.register(new StashFinder());
        MODULES.register(new FreecamController());
        MODULES.register(new FreelookController());

        openMenuKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.taurus.open_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.consumeClick()) {
                if (client.player != null && client.gui.screen() == null) {
                    client.gui.setScreen(new dev.minecraftjailbreak.basefinder.ui.TaurusScreen());
                } else if (client.gui.screen() instanceof dev.minecraftjailbreak.basefinder.ui.TaurusScreen) {
                    client.gui.setScreen(null);
                }
            }
        });

        System.out.println("[TAURUS] Client initialized: " + MODULES.names());
    }
}
