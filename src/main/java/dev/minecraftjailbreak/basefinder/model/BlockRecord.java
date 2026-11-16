package dev.minecraftjailbreak.basefinder.model;

public record BlockRecord(String dimension, int x, int y, int z,
                          String blockId, BlockType type) {
    public String coordinates() { return x + " " + y + " " + z; }
    public String chunkKey() {
        return Math.floorDiv(x, 16) + "," + Math.floorDiv(z, 16);
    }
}
