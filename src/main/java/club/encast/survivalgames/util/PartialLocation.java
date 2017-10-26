package club.encast.survivalgames.util;

public class PartialLocation {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    // Since SG worlds will be randomly named, the Bukkit provided Location class doesn't suffice.
    public PartialLocation(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
