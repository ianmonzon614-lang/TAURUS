package dev.minecraftjailbreak.basefinder.modules;

import dev.minecraftjailbreak.basefinder.core.AbstractModule;
import dev.minecraftjailbreak.basefinder.core.BlockCatalog;
import dev.minecraftjailbreak.basefinder.model.BlockRecord;
import dev.minecraftjailbreak.basefinder.model.ChunkRecord;
import dev.minecraftjailbreak.basefinder.model.WorldSnapshot;
import java.util.*;

public final class ChunkFinder extends AbstractModule {
    private final Map<String, ChunkRecord> discovered = new LinkedHashMap<>();
    private final Map<String, Integer> signalCounts = new LinkedHashMap<>();

    @Override public String id() { return "chunk_finder"; }
    @Override public String displayName() { return "Chunk Finder"; }

    /** Indexes signals only in chunks already supplied by the authorized adapter. */
    public synchronized List<ChunkRecord> scan(WorldSnapshot snapshot, long now) {
        if (!isEnabled()) return List.copyOf(discovered.values());

        Set<String> loaded = new HashSet<>();
        for (var chunk : snapshot.loadedChunks()) {
            String key = chunk.x() + "," + chunk.z();
            loaded.add(key);
            discovered.putIfAbsent(key, new ChunkRecord(chunk.x(), chunk.z(), now, 0));
        }

        Map<String, Integer> counts = new HashMap<>();
        for (var block : snapshot.loadedBlocks()) {
            if (!BlockCatalog.isChunkSignal(block.blockId())) continue;
            String key = Math.floorDiv(block.x(), 16) + "," + Math.floorDiv(block.z(), 16);
            if (loaded.contains(key)) counts.merge(key, 1, Integer::sum);
        }

        for (String key : loaded) {
            String[] parts = key.split(",");
            int x = Integer.parseInt(parts[0]), z = Integer.parseInt(parts[1]);
            int count = counts.getOrDefault(key, 0);
            discovered.put(key, new ChunkRecord(x, z, now, count));
            signalCounts.put(key, count);
        }
        return List.copyOf(discovered.values());
    }

    public synchronized Map<String, Integer> signalCounts() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(signalCounts));
    }

    public synchronized List<ChunkRecord> results() {
        return List.copyOf(discovered.values());
    }

    public synchronized void clear() {
        discovered.clear();
        signalCounts.clear();
    }
}
