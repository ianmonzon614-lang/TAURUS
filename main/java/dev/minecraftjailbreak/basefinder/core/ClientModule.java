package dev.minecraftjailbreak.basefinder.core;

public interface ClientModule {
    String id();
    String displayName();
    boolean isEnabled();
    void setEnabled(boolean enabled);
}
