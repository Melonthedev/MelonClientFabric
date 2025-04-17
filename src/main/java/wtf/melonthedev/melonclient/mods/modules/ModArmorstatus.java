package wtf.melonthedev.melonclient.mods.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import wtf.melonthedev.melonclient.gui.modhud.ScreenPosition;
import wtf.melonthedev.melonclient.mods.ModFlag;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;

import java.util.List;

public class ModArmorstatus extends ModuleDraggable {

    public ModArmorstatus() {
        title = "Armorstatus: ";
        name = "Armorstatus";
        updateDummy();
    }

    @Override
    public void updateDummy() {
        dummy = getOptions().flags.contains(ModFlag.SHOW_DAMAGE_IN_PERCENT) ? "100%" : "100/120";
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy) + 20;
    }

    @Override
    public int getHeight() {
        return 64;
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        if (mc.player == null) return;
        List<ItemStack> armor = mc.player.getInventory().armor;
        renderBackground(guiGraphics.pose(), mc.font.width(dummy) + 22, getHeight() + 2, pos.getAbsoluteX(), pos.getAbsoluteY());
        renderItemStack(guiGraphics, pos, armor.get(3), 3);
        renderItemStack(guiGraphics, pos, armor.get(2), 2);
        renderItemStack(guiGraphics, pos, armor.get(1), 1);
        renderItemStack(guiGraphics, pos, armor.get(0), 0);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        renderBackground(guiGraphics.pose(), mc.font.width(dummy) + 22, getHeight() + 2, pos.getAbsoluteX(), pos.getAbsoluteY());
        renderItemStack(guiGraphics, pos, new ItemStack(Items.TURTLE_HELMET), 3); //TODO ADD FAKE DAMAGE
        renderItemStack(guiGraphics, pos, new ItemStack(Items.ELYTRA), 2);
        renderItemStack(guiGraphics, pos, new ItemStack(Items.GOLDEN_LEGGINGS), 1);
        renderItemStack(guiGraphics, pos, new ItemStack(Items.DIAMOND_BOOTS), 0);
    }

    public void renderItemStack(GuiGraphics guiGraphics, ScreenPosition pos, ItemStack itemStack, int i) {
        if (itemStack == null) return;
        int yAdd = (-16 * i) + 48;
        if (itemStack.isDamageableItem()) {
            if (getOptions().flags.contains(ModFlag.SHOW_DAMAGE_IN_PERCENT)) {
                double damagePercent = ((itemStack.getMaxDamage() - itemStack.getDamageValue()) / (double) itemStack.getMaxDamage()) * 100;
                drawStandardText(guiGraphics, String.format("%.0f%%", damagePercent), pos.getAbsoluteX() + 20, pos.getAbsoluteY() + yAdd + 5);
            } else {
                drawStandardText(guiGraphics, itemStack.getMaxDamage() - itemStack.getDamageValue() + "/" + itemStack.getMaxDamage(), pos.getAbsoluteX() + 20, pos.getAbsoluteY() + yAdd + 5);
            }
        }
        guiGraphics.renderItem(itemStack, pos.getAbsoluteX() + 2, pos.getAbsoluteY() + yAdd);
    }
}
