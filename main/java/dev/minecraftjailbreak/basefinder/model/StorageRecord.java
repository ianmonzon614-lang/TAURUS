package dev.minecraftjailbreak.basefinder.model;

public record StorageRecord(String dimension, int x, int y, int z, String blockId) {
    public String coordinates() { return x + " " + y + " " + z; }
}
