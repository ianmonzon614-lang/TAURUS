package dev.minecraftjailbreak.basefinder.core;

public abstract class AbstractModule implements ClientModule {
    private boolean enabled;

    @Override public final boolean isEnabled() { return enabled; }
    @Override public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
