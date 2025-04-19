package wtf.melonthedev.melonclient.modengine;

import wtf.melonthedev.melonclient.modengine.mods.*;

public enum ModFlag {
    SHOW_SECONDS("Show seconds", ModClock.class),
    USE_TWENTY_FOUR_HOUR_FORMAT("Use 24h format", ModClock.class),
    SHOW_DAY_OF_WEEK("Show day of week", ModDate.class),
    COORDS_SLASH_DESIGN("Separate coords with '/'", ModCoordPosition.class),
    SHOW_RIGHT_CPS("Show right CPS", ModCps.class),
    SHOW_LEFT_CPS("Show left CPS", ModCps.class),
    //SHOW_ARMORSTATUS_IF_NO_ARMOR("Show if not wearing armor", ModArmorstatus.class),
    SHOW_DAMAGE_IN_PERCENT("Show damage in %", ModArmorstatus.class),
    SHOW_LONGER_MOD_NAME("Show longer name", ModFacingDirection.class),
    TOGGLE_FREE_PERSPECTIVE("Toggle perspective", ModPerspective.class);

    private final String title;
    private final Class<? extends ModuleDraggable> modOfFlag;

     ModFlag(String title, Class<? extends ModuleDraggable> modOfFlag) {
        this.title = title;
        this.modOfFlag = modOfFlag;
    }

    public Class<? extends ModuleDraggable> getModOfFlag() {
        return modOfFlag;
    }

    public String getTitle() {
        return title;
    }
}
