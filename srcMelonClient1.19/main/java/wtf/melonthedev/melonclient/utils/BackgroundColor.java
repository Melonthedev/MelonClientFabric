package wtf.melonthedev.melonclient.utils;

public enum BackgroundColor {

    WHITE_LIGHT(0xFFFFFFFF, "White Light"),
    WHITE_NORMAL(0x80FFFFFF, "White Normal"),
    WHITE_TRANSLUCENT(0x26FFFFFF, "White Transluc."),
    GRAY_LIGHT(0xFF808080, "Gray Light"),
    GRAY_NORMAL(0x80808080, "Gray Normal"),
    GRAY_TRANSLUCENT(0x26808080, "Gray Transluc."),
    BLACK_LIGHT(0xFF000000, "Black Light"),
    BLACK_NORMAL(0x80000000, "Black Normal"),
    BLACK_TRANSLUCENT(0x26000000, "Black Transluc."),

    DARK_BLUE(0x0000AA, "Dark Blue"),
    DARK_GREEN(0x00AA00, "Dark Green"),
    DARK_AQUA(0x00AAAA, "Dark Aqua"),
    DARK_RED(0xAA0000, "Dark Red"),
    DARK_PURPLE(0xAA00AA, "Dark Purple"),
    DARK_YELLOW(0xAAAA00, "Dark Yellow"),
    GRAY(0xAAAAAA, "Gray"),
    BLUE(0x5555FF, "Blue"),
    GREEN(0x55FF55, "Green"),
    AQUA(0x55FFFF, "Aqua"),
    RED(0xFF5555, "Red"),
    PURPLE(0xFF55FF, "Purple"),
    YELLOW(0xFFFF55, "Yellow");

    private final int color;
    private final String name;

    BackgroundColor(int color, String name) {
        this.color = color;
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

}
