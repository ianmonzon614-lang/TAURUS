package dev.minecraftjailbreak.basefinder.core;

import dev.minecraftjailbreak.basefinder.model.BlockType;
import java.util.*;

/**
 * Registry-name based signal catalog. The adapter should pass identifiers such
 * as minecraft:chest, minecraft:redstone_wire and minecraft:tinted_glass.
 */
public final class BlockCatalog {
    private BlockCatalog() {}

    private static final Set<String> STORAGE = Set.of(
        "chest", "trapped_chest", "barrel",
        "hopper", "furnace", "smoker", "blast_furnace",
        "shulker_box",
        "white_shulker_box", "orange_shulker_box", "magenta_shulker_box",
        "light_blue_shulker_box", "yellow_shulker_box", "lime_shulker_box",
        "pink_shulker_box", "gray_shulker_box", "light_gray_shulker_box",
        "cyan_shulker_box", "purple_shulker_box", "blue_shulker_box",
        "brown_shulker_box", "green_shulker_box", "red_shulker_box",
        "black_shulker_box", "ender_chest"
    );

    private static final Set<String> REDSTONE = Set.of(
        "redstone_wire", "observer", "piston", "sticky_piston",
        "redstone_block", "redstone_torch", "redstone_wall_torch",
        "repeater", "comparator", "lever", "target",
        "dispenser", "dropper", "daylight_detector",
        "tripwire_hook", "tripwire", "sculk_sensor", "calibrated_sculk_sensor"
    );

    private static final Set<String> AUTOMATION = Set.of(
        "crafter", "dispenser", "dropper", "hopper",
        "observer", "piston", "sticky_piston",
        "repeater", "comparator"
    );

    // Stash Finder treats these as contextual clues, not proof of a hidden base.
    private static final Set<String> CONCEALMENT = Set.of(
        "tinted_glass", "budding_amethyst", "amethyst_block",
        "amethyst_cluster", "large_amethyst_bud",
        "medium_amethyst_bud", "small_amethyst_bud"
    );

    public static BlockType classify(String registryId) {
        String path = pathOf(registryId);
        if (CONCEALMENT.contains(path)) return BlockType.CONCEALMENT;
        if (STORAGE.contains(path)) return BlockType.STORAGE;
        if (AUTOMATION.contains(path)) return BlockType.AUTOMATION;
        if (REDSTONE.contains(path)) return BlockType.REDSTONE;
        return BlockType.OTHER;
    }

    public static boolean isChunkSignal(String registryId) {
        BlockType type = classify(registryId);
        return type != BlockType.OTHER;
    }

    public static boolean isStashSignal(String registryId) {
        BlockType type = classify(registryId);
        return type == BlockType.STORAGE || type == BlockType.CONCEALMENT
            || type == BlockType.AUTOMATION || type == BlockType.REDSTONE;
    }

    private static String pathOf(String id) {
        if (id == null || id.isBlank()) return "";
        int colon = id.indexOf(':');
        return (colon >= 0 ? id.substring(colon + 1) : id).toLowerCase(Locale.ROOT);
    }
}
