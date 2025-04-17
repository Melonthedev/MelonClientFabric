package wtf.melonthedev.melonclient.melonclientwrapper;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;

public class TutorialScreen extends MelonScreen {

    //Constructor, here you handle the fields etc.
    protected TutorialScreen() {
        super(Component.literal("My Screen"));
    }

    //Initializes the screen, add buttons here
    @Override
    public void init() {
        //Add Button - 3rd and 4th arg is the size of the button
        addRenderableWidget(new Button(this.width / 2, this.height / 2, 98, 20, Component.literal("My Button"), (p_96323_) -> {
            //Do whatever the button should do
            System.out.println("Hello, I was clicked");
        }));

        //Add Slider - last argument is the max value
        addRenderableWidget(new AbstractSliderButton(this.width / 2, this.height / 2, 98, 20, Component.literal("My Button"), 1) {
            @Override
            protected void updateMessage() {
                //You got a value variable which is the current value of the slider, you can visualize it like that:
                setMessage(Component.literal("Value: " + value));
            }

            @Override
            protected void applyValue() {
                //This just applies the value, used for saving value or doing whatever the slider should do
            }
        });
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        return super.mouseClicked(x, y, button);
        //button == 0 -> leftclick
        //button == 1 -> rightclick
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
        //Drawing Background
        super.renderBackground(stack);
        //Drawing Title
        drawCenteredString(stack, this.font, this.title, this.width / 2, 15, 16777215);
        //Draw or Render whatever you want, for example the player:
        if (minecraft.player != null)
            MelonClientWrapper.renderEntityInInventory(width - width/8, height/3 + 100, 75, 1.0f, 1.0f, this.minecraft.player, Client.noRotateBoundsInInventory);
    }

    @Override
    public void onClose() {
        super.onClose();
        System.out.println("Goodbye");
    }

    //Weather the screen should pause the game if shown
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
