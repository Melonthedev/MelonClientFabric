package wtf.melonthedev.melonclient.gui.screens.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.LoadingDotsText;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.GuiUtils;
import wtf.melonthedev.melonclient.gui.modhud.HudEditScreen;
import wtf.melonthedev.melonclient.mods.ModInstanceManager;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;

import java.awt.*;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;


public class ModSelectionList extends ObjectSelectionList<ModSelectionList.Entry> {
    static final Logger LOGGER = LogUtils.getLogger();
    private static final Duration MAX_LOAD_BLOCK_TIME = Duration.ofMillis(100L);
    private final HudEditScreen screen;
    private CompletableFuture<List<ModuleDraggable>> modsFuture;
    private final LoadingHeader loadingHeader;

    public ModSelectionList(HudEditScreen screen, Minecraft minecraft, int width, int height, int y0, int y1, Supplier<String> searchQuery, ModSelectionList modSelectionList) {
        super(minecraft, width, height, y0, y1);
        this.screen = screen;
        this.loadingHeader = new LoadingHeader(minecraft);
        if (modSelectionList != null) {
            this.modsFuture = modSelectionList.modsFuture;
            this.refreshList(searchQuery.get());
        } else this.reloadMods(searchQuery);
    }

    public void reloadMods(Supplier<String> searchQuery) {
        this.modsFuture = this.loadMods();
        List<ModuleDraggable> list = this.pollReadyMods(this.modsFuture, MAX_LOAD_BLOCK_TIME);
        if (list != null) {
            this.fillMods(searchQuery.get(), list);
            return;
        }
        this.fillLoadingMods();
        this.modsFuture.thenAcceptAsync((mods) -> this.fillMods(searchQuery.get(), mods), this.minecraft);
    }

    String oldSearchQuery = "";
    public void refreshList(String searchQuery) {
        if (this.modsFuture == null) {
            this.clearEntries();
            return;
        }
        List<ModuleDraggable> list = this.pollReadyMods(this.modsFuture, Duration.ZERO);
        if (list != null) this.fillMods(searchQuery, list);
        else this.fillLoadingMods();
        if (!searchQuery.equalsIgnoreCase(oldSearchQuery)) {
            setScrollAmount(0);
            oldSearchQuery = searchQuery;
        }
    }

    private CompletableFuture<List<ModuleDraggable>> loadMods() {
        return CompletableFuture.completedFuture(List.of(ModInstanceManager.getMods()));
    }

    private List<ModuleDraggable> pollReadyMods(CompletableFuture<List<ModuleDraggable>> mods, Duration duration) {
        List<ModuleDraggable> list = null;
        try {
            list = mods.get(duration.toMillis(), TimeUnit.MILLISECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException ignored) {}
        return list;
    }

    private void fillMods(String searchQuery, List<ModuleDraggable> mods) {
        this.clearEntries();
        for(ModuleDraggable mod : mods) {
            if (this.filterAccepts(searchQuery.toLowerCase(Locale.ROOT), mod))
                this.addEntry(new ModListEntry(this, mod));
        }
        this.notifyListUpdated();
    }

    private boolean filterAccepts(String query, ModuleDraggable moduleDraggable) {
        return moduleDraggable.getName().toLowerCase(Locale.ROOT).contains(query);
    }

    private void fillLoadingMods() {
        this.clearEntries();
        this.addEntry(this.loadingHeader);
        this.notifyListUpdated();
    }

    private void notifyListUpdated() {
        this.screen.triggerImmediateNarration(true);
    }

    protected int getScrollbarPosition() {
        return getScreen().width / 2 + 100;
    }

    public int getRowWidth() {
        return super.getRowWidth() - 20;
    }

    public boolean isFocused() {
        return this.screen.getFocused() == this;
    }

    public void setSelected(Entry modEntry) {
        super.setSelected(modEntry);
    }

    public HudEditScreen getScreen() {
        return this.screen;
    }

    
    public abstract static class Entry extends ObjectSelectionList.Entry<Entry> implements AutoCloseable {
        public abstract boolean isSelectable();
        public void close() {}
    }

    /*@Override
    protected void renderList(PoseStack p_93452_, int p_93453_, int p_93454_,  float p_93457_) {
        int i = this.getItemCount();
        for(int j = 0; j < i; ++j) {
            int k = this.getRowTop(j);
            int l = 0;///this.getRowBottom(j);
            if (l >= this.y0 && k <= this.y1) {
                int i1 = p_93454_ + j * this.itemHeight + this.headerHeight;
                int j1 = this.itemHeight - 4;
                Entry e = this.getEntry(j);
                int k1 = this.getRowWidth();
                if (true){//this.renderSelection && this.isSelectedItem(j)) {
                    int l1 = this.x0 + this.width / 2 - k1 / 2;
                    int i2 = this.x0 + this.width / 2 + k1 / 2;
                    GuiUtils.drawHollowRect(guiGraphics, l1, i1 - 2, k1 - 5, this.itemHeight - 1, Color.LIGHT_GRAY.getRGB());
                    if (((ModListEntry) getEntry(i - 1)).mod.isEnabled())
                        fill(p_93452_, l1 + 2, i1, i2 - 1 - 5, i1 + j1, new Color(0, 255, 119, 40).getRGB());
                    else fill(p_93452_, l1 + 2, i1, i2 - 1 - 5, i1 + j1, new Color(255, 0, 60, 40).getRGB());
                }
                int j2 = this.getRowLeft();
                //e.render(p_93452_, j, k, j2, k1, j1, p_93455_, p_93456_, Objects.equals(this.hovered, e), p_93457_);
            }
        }
    }*/

    public static class LoadingHeader extends Entry {
        private static final Component LOADING_LABEL = Component.translatable("Loading mods list");
        private final Minecraft minecraft;
        public LoadingHeader(Minecraft mc) {
            this.minecraft = mc;
        }
        public void render(PoseStack stack, int p_233226_, int p_233227_, int p_233228_, int p_233229_, int p_233230_, int p_233231_, int p_233232_, boolean p_233233_, float p_233234_) {
            int i = (this.minecraft.screen.width - this.minecraft.font.width(LOADING_LABEL)) / 2;
            int j = p_233227_ + (p_233230_ - 9) / 2;
            //this.minecraft.font.draw(stack, LOADING_LABEL, (float)i, (float)j, 16777215);
            String s = LoadingDotsText.get(Util.getMillis());
            int k = (this.minecraft.screen.width - this.minecraft.font.width(s)) / 2;
            //this.minecraft.font.draw(stack, s, (float)k, (float)j + 9, 8421504);
        }
        public Component getNarration() {
            return LOADING_LABEL;
        }
        public boolean isSelectable() {
            return false;
        }

        @Override
        public void mouseMoved(double d, double e) {
            super.mouseMoved(d, e);
        }

        @Override
        public boolean mouseReleased(double d, double e, int i) {
            return super.mouseReleased(d, e, i);
        }

        @Override
        public boolean mouseDragged(double d, double e, int i, double f, double g) {
            return super.mouseDragged(d, e, i, f, g);
        }

        @Override
        public boolean mouseScrolled(double d, double e, double f, double g) {
            return super.mouseScrolled(d, e, f, g);
        }

        @Override
        public boolean keyPressed(int i, int j, int k) {
            return super.keyPressed(i, j, k);
        }

        @Override
        public boolean keyReleased(int i, int j, int k) {
            return super.keyReleased(i, j, k);
        }

        @Override
        public boolean charTyped(char c, int i) {
            return super.charTyped(c, i);
        }

        @Nullable
        @Override
        public ComponentPath nextFocusPath(FocusNavigationEvent focusNavigationEvent) {
            return super.nextFocusPath(focusNavigationEvent);
        }

        @Nullable
        @Override
        public ComponentPath getCurrentFocusPath() {
            return super.getCurrentFocusPath();
        }

        @Override
        public ScreenRectangle getRectangle() {
            return super.getRectangle();
        }

        @Override
        public void render(GuiGraphics guiGraphics, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {

        }

        @Override
        public int getTabOrderGroup() {
            return super.getTabOrderGroup();
        }
    }

    
    public final class ModListEntry extends Entry implements AutoCloseable {
        private static final int ICON_WIDTH = 32;
        private static final int ICON_HEIGHT = 32;
        private static final int ICON_OVERLAY_X_JOIN = 0;
        private static final int ICON_OVERLAY_X_JOIN_WITH_NOTIFY = 32;
        private static final int ICON_OVERLAY_X_WARNING = 64;
        private static final int ICON_OVERLAY_X_ERROR = 96;
        private static final int ICON_OVERLAY_Y_UNSELECTED = 0;
        private static final int ICON_OVERLAY_Y_SELECTED = 32;
        private final Minecraft minecraft;
        private final HudEditScreen screen;
        private final ModuleDraggable mod;
        private final ResourceLocation iconLocation;
        private long lastClickTime;
        public Button toggleButton;
        public Button editButton;

        public ModListEntry(ModSelectionList list, ModuleDraggable mod) {
            this.minecraft = list.minecraft;
            this.screen = list.getScreen();
            this.mod = mod;
            String title = mod.getName();
            this.iconLocation = ResourceLocation.fromNamespaceAndPath("melonclient", "modicons/" + title.toLowerCase()); //?
            this.toggleButton = Button.builder(Component.literal("Add"), (button) -> {
                if (mod.isEnabled()) mod.setEnabled(false);
                else mod.setEnabled(true);
                toggleButton.setMessage(Component.literal(mod.isEnabled() ? "Remove" : "Add"));
            }).bounds(getScreen().width/2 + width/2 - 100, getY() + 10, 50, 20).build();
            this.editButton = Button.builder(Component.literal("..."), (button) -> {
                Client.setScreen(new MelonClientModCustomizerScreen(getScreen(), mod));
            }).bounds(getScreen().width/2 + width/2 - 40, getY() + 10, 20, 20).build();
        }

        public Component getNarration() {
            return Component.literal("Selected ").append(this.mod.getName());
        }

        public void render(PoseStack stack, int p_101722_, int p_101723_, int p_101724_, int p_101725_, int p_101726_, int p_101727_, int p_101728_, boolean hovered, float p_101730_) {
            String name = this.mod.getName();
            String description = this.mod.getOptions().description;
            //this.minecraft.font.draw(stack, name, (float) (p_101724_ + 32 + 3), (float) (p_101723_ + 1), 16777215);
            //this.minecraft.font.draw(stack, description, (float) (p_101724_ + 32 + 3), (float) (p_101723_ + 9 + 3), 8421504);
            //this.minecraft.font.draw(stack, name, (float) (p_101724_ + 32 + 3), (float) (p_101723_ + 9 + 9 + 3), 8421504);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, this.iconLocation);
            RenderSystem.enableBlend();
            //GuiComponent.blit(stack, p_101724_, p_101723_, 0.0F, 0.0F, 32, 32, 32, 32);
            RenderSystem.disableBlend();
            if (this.minecraft.options.touchscreen().get() || hovered) {
                //RenderSystem.setShaderTexture(0, ModSelectionList.ICON_OVERLAY_LOCATION);
                //GuiComponent.fill(stack, p_101724_, p_101723_, p_101724_ + 32, p_101723_ + 32, -1601138544);
                //RenderSystem.setShader(GameRenderer::getPositionTexShader);
                //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                //int i = p_101727_ - p_101724_;
                //boolean flag = i < 32;
                //int j = flag ? 32 : 0;
                //GuiComponent.blit(stack, p_101724_, p_101723_, 0.0F, (float) j, 32, 32, 256, 256);
                /*toggleButton.x = p_101724_ + width - 100;
                toggleButton.y = p_101723_ + 10;
                toggleButton.render(stack, p_101727_, p_101728_, p_101730_);
                editButton.x = p_101724_ + width - 40;
                editButton.y = p_101723_ + 10;
                editButton.render(stack, p_101727_, p_101728_, p_101730_);*/
            }

        }

        public boolean mouseClicked(double x, double y, int button) {
            ModSelectionList.this.setSelected((Entry)this);
            if (x - (double)ModSelectionList.this.getRowLeft() <= 32.0D) {
                this.enableMod();
                return true;
            }
            if (Util.getMillis() - this.lastClickTime < 250L) {
                this.enableMod();
                return true;
            }
            this.lastClickTime = Util.getMillis();
            return false;
        }

        public void enableMod() {
            System.out.println("ENaBLEEEEE");
        }

        public void disableMod() {
            System.out.println("DISABLEEEE");
        }

        public boolean isSelectable() {
            return true;
        }

        @Override
        public void render(GuiGraphics guiGraphics, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {

        }
    }
}