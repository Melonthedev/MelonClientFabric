package wtf.melonthedev.melonclient.gui.modhud;

import com.google.gson.annotations.Expose;
import net.minecraft.client.Minecraft;
import wtf.melonthedev.melonclient.Client;

public class ScreenPosition {

    @Expose(serialize = false)
    private static final Minecraft mc = Client.getMinecraft();
    //private int x, y;
    private int x, y;

    public ScreenPosition(double x, double y) {
        setRelative(x, y);
    }

    public ScreenPosition(int x, int y) {
        setAbsolute(x, y);
    }

    public static ScreenPosition fromRelativePosition(double x, double y) {
        return new ScreenPosition(x, y);
    }

    public static ScreenPosition fromAbsolute(int x, int y) {
        return new ScreenPosition(x, y);
    }

    public int getAbsoluteX() {
        return x;
    }

    public int getAbsoluteY() {
        return y;
    }

    public double getRelativeX() {
        return (double) x / mc.getWindow().getGuiScaledWidth();
    }

    public double getRelativeY() {
        return (double) y / mc.getWindow().getGuiScaledHeight();
    }

    public void setAbsolute(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setRelative(double x, double y) {
        this.x = (int) (mc.getWindow().getGuiScaledWidth() / x);
        this.y = (int) (mc.getWindow().getGuiScaledHeight() / y);
    }
}
