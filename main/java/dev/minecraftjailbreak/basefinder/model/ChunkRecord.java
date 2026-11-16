package dev.minecraftjailbreak.basefinder.model;

public record ChunkRecord(int x, int z, long lastSeenEpochMillis, int storageBlockCount) {
    public String key() { return x + "," + z; }
}
