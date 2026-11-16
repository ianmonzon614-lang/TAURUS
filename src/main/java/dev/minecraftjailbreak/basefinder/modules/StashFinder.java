package dev.minecraftjailbreak.basefinder.modules;

import dev.minecraftjailbreak.basefinder.core.AbstractModule;
import dev.minecraftjailbreak.basefinder.core.BlockCatalog;
import dev.minecraftjailbreak.basefinder.model.BlockRecord;
import dev.minecraftjailbreak.basefinder.model.BlockType;
import dev.minecraftjailbreak.basefinder.model.WorldSnapshot;
import java.util.*;

public final class StashFinder extends AbstractModule {
    private final Map<String, BlockRecord> found = new LinkedHashMap<>();

    @Override public String id() { return "stash_finder"; }
    @Override public String displayName() { return "Stash Finder"; }

    /**
     * Tracks storage and base-related signals in accessible loaded chunks.
     * Tinted glass and amethyst are marked as contextual concealment clues.
     */
    public synchronized List<BlockRecord> scan(WorldSnapshot snapshot) {
        if (!isEnabled()) return List.copyOf(found.values());

        Set<String> loadedChunkKeys = new HashSet<>();
        for (var chunk : snapshot.loadedChunks())
            loadedChunkKeys.add(chunk.x() + "," + chunk.z());

        for (var block : snapshot.loadedBlocks()) {
            if (!BlockCatalog.isStashSignal(block.blockId())) continue;
            String chunkKey = Math.floorDiv(block.x(), 16) + "," + Math.floorDiv(block.z(), 16);
            if (!loadedChunkKeys.contains(chunkKey)) continue;

            BlockType type = BlockCatalog.classify(block.blockId());
            String key = snapshot.dimension() + ":" + block.x() + "," + block.y() + "," + block.z();
            found.put(key, new BlockRecord(snapshot.dimension(), block.x(), block.y(),
                    block.z(), block.blockId(), type));
        }
        return List.copyOf(found.values());
    }

    public synchronized List<BlockRecord> results() {
        return List.copyOf(found.values());
    }

    public synchronized List<BlockRecord> concealmentClues() {
        return found.values().stream()
                .filter(record -> record.type() == BlockType.CONCEALMENT)
                .toList();
    }

    public synchronized void clear() { found.clear(); }
}
