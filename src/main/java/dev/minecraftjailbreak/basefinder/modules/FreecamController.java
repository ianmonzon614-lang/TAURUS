package dev.minecraftjailbreak.basefinder.modules;

import dev.minecraftjailbreak.basefinder.core.AbstractModule;

/**
 * Freecam state/controller. The game adapter must bind this to a local,
 * non-networked camera and must not send camera coordinates as player movement.
 */
public final class FreecamController extends AbstractModule {
    private double x, y, z;
    private float yaw, pitch;
    private double speed = 0.35;

    @Override public String id() { return "freecam"; }
    @Override public String displayName() { return "Freecam"; }

    public void begin(double x, double y, double z, float yaw, float pitch) {
        this.x = x; this.y = y; this.z = z;
        this.yaw = yaw; this.pitch = pitch;
        setEnabled(true);
    }

    public void stop() { setEnabled(false); }

    public void move(double forward, double strafe, double vertical) {
        if (!isEnabled()) return;
        double r = Math.toRadians(yaw);
        x += (-Math.sin(r) * forward + Math.cos(r) * strafe) * speed;
        z += ( Math.cos(r) * forward + Math.sin(r) * strafe) * speed;
        y += vertical * speed;
    }

    public void rotate(float yawDelta, float pitchDelta) {
        if (!isEnabled()) return;
        yaw += yawDelta;
        pitch = Math.max(-90, Math.min(90, pitch + pitchDelta));
    }

    public void setSpeed(double speed) {
        if (!Double.isFinite(speed)) throw new IllegalArgumentException("speed must be finite");
        this.speed = Math.max(0.05, Math.min(2.0, speed));
    }

    public double x() { return x; }
    public double y() { return y; }
    public double z() { return z; }
    public float yaw() { return yaw; }
    public float pitch() { return pitch; }
    public double speed() { return speed; }
}
