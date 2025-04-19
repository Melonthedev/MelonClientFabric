package wtf.melonthedev.melonclient.modengine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import wtf.melonthedev.melonclient.Client;

public class Module {

    private boolean isEnabled = true;

    protected final Minecraft mc;
    //protected Font mc.font = Minecraft.getInstance().font;
    protected final Client client;

    public Module() {
        this.mc = Client.getMinecraft();
        //this.font = mc.font;
        this.client = Client.getInstance();
    }

    protected void setEnabled(boolean isEnabled) {
        //this.isEnabled = isEnabled;
        //if (isEnabled) EventManager.register(this);
        //else EventManager.unregister(this);
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
