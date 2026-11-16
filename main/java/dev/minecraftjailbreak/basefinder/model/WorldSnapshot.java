package dev.minecraftjailbreak.basefinder.model;

import java.util.List;

/**
 * Immutable snapshot supplied by a Minecraft 26.2 client adapter.
 * Only include blocks from chunks currently loaded and visible to the client.
 */
public record WorldSnapshot(String dimension, List<LoadedChunk> loadedChunks,
                            List<LoadedBlock> loadedBlocks) {
    public WorldSnapshot {
        loadedChunks = List.copyOf(loadedChunks);
        loadedBlocks = List.copyOf(loadedBlocks);
    }

    public record LoadedChunk(int x, int z) {}
    public record LoadedBlock(int x, int y, int z, String blockId) {}
}
