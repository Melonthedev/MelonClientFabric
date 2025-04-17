package wtf.melonthedev.melonclient.utils;

public class MelonZoomUtils {

    public static float zoomFactor = 0.3f;

    public static void handleZoomFactor(int i) {
        if (i == 1 && zoomFactor - 0.03 >= 0.01) {
            zoomFactor -= 0.03;
        } else if (i == -1 && zoomFactor < 0.5) {
            zoomFactor += 0.03;
        }
    }
}
