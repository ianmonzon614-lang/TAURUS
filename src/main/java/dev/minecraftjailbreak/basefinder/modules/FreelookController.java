package dev.minecraftjailbreak.basefinder.modules;

import dev.minecraftjailbreak.basefinder.core.AbstractModule;

/** Camera look independent of the player's saved view angles. */
public final class FreelookController extends AbstractModule {
    private float cameraYaw;
    private float cameraPitch;

    @Override public String id() { return "freelook"; }
    @Override public String displayName() { return "Freelook"; }

    public void begin(float playerYaw, float playerPitch) {
        cameraYaw = playerYaw;
        cameraPitch = playerPitch;
        setEnabled(true);
    }

    public void stop() { setEnabled(false); }

    public void update(float yawDelta, float pitchDelta) {
        if (!isEnabled()) return;
        cameraYaw += yawDelta;
        cameraPitch = Math.max(-90, Math.min(90, cameraPitch + pitchDelta));
    }

    public float cameraYaw() { return cameraYaw; }
    public float cameraPitch() { return cameraPitch; }
}
