package wtf.melonthedev.melonclient.gui.modengine;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.modengine.ModInstanceManager;
import wtf.melonthedev.melonclient.modengine.hud.ModDraggable;
import wtf.melonthedev.melonclient.utils.ClientUtils;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class ModSelectionList extends ObjectSelectionList<ModSelectionList.Entry> {

    private static final Duration MAX_LOAD_BLOCK_TIME = Duration.ofMillis(100L);
    private final HudEditScreen screen;
    private CompletableFuture<List<ModDraggable>> modsFuture;

    public ModSelectionList(HudEditScreen screen, int width, int height, int x, int y0, int y1, Supplier<String> searchQuery, ModSelectionList modSelectionList) {
        super(Minecraft.getInstance(), width, height, y0, y1);
        this.setX(x);
        this.screen = screen;
        if (modSelectionList != null) {
            this.modsFuture = modSelectionList.modsFuture;
            this.refreshList(searchQuery.get());
        } else this.reloadMods(searchQuery);
    }

    public void reloadMods(Supplier<String> searchQuery) {
        this.modsFuture = this.loadMods();
        List<ModDraggable> list = this.pollReadyMods(this.modsFuture, MAX_LOAD_BLOCK_TIME);
        if (list != null) {
            this.fillMods(searchQuery.get(), list);
            return;
        }
        this.modsFuture.thenAcceptAsync((mods) -> this.fillMods(searchQuery.get(), mods), this.minecraft);
    }

    private CompletableFuture<List<ModDraggable>> loadMods() {
        return CompletableFuture.completedFuture(List.of(ModInstanceManager.getMods()));
    }

    String oldSearchQuery = "";
    public void refreshList(String searchQuery) {
        if (this.modsFuture == null) {
            this.clearEntries();
            return;
        }
        List<ModDraggable> list = this.pollReadyMods(this.modsFuture, Duration.ZERO);
        if (list != null) this.fillMods(searchQuery, list);
        else this.fillLoadingMods();
        if (!searchQuery.equalsIgnoreCase(oldSearchQuery)) {
            setScrollAmount(0);
            oldSearchQuery = searchQuery;
        }
    }

    private List<ModDraggable> pollReadyMods(CompletableFuture<List<ModDraggable>> mods, Duration duration) {
        List<ModDraggable> list = null;
        try {
            list = mods.get(duration.toMillis(), TimeUnit.MILLISECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException ignored) {}
        return list;
    }

    private void fillMods(String searchQuery, List<ModDraggable> mods) {
        this.clearEntries();
        for(ModDraggable mod : mods) {
            if (this.filterAccepts(searchQuery.toLowerCase(Locale.ROOT), mod))
                this.addEntry(new ModListEntry(this, mod));
        }
        this.notifyListUpdated();
    }

    private boolean filterAccepts(String query, ModDraggable moduleDraggable) {
        return moduleDraggable.getName().toLowerCase(Locale.ROOT).contains(query);
    }

    private void fillLoadingMods() {
        this.clearEntries();
        this.notifyListUpdated();
    }

    private void notifyListUpdated() {
        this.screen.triggerImmediateNarration(true);
    }

    protected int getScrollbarPosition() {
        //return getScreen().width / 2 + 100;
        return getScreen().width / 2 + width/2 - 6;
    }

    public int getRowWidth() {
        //return super.getRowWidth() - 12;
        return super.getRowWidth();
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

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        for (Entry entry : this.children()) {
            if (!(entry instanceof ModListEntry modListEntry)) continue;
            if (modListEntry.mouseClicked(d, e, i)) {
                return true;
            }
        }
        return super.mouseClicked(d, e, i);
    }

    public abstract class Entry extends ObjectSelectionList.Entry<Entry> implements AutoCloseable {
        public void close() {}
    }

    public final class ModListEntry extends Entry implements AutoCloseable {

        private final Minecraft minecraft;
        private final HudEditScreen screen;
        private final ModDraggable mod;
        private final ResourceLocation iconLocation;
        public Button toggleButton;
        public Button editButton;

        public ModListEntry(ModSelectionList list, ModDraggable mod) {
            this.minecraft = list.minecraft;
            this.screen = list.getScreen();
            this.mod = mod;
            this.iconLocation = mod.getIcon();
            this.toggleButton = Button.builder(getAddRemoveButtonComponent(mod), (button) -> {
                ClientUtils.logDev("Toggel Mod " + mod.getIdentifier() + " enabled to " + !mod.isEnabled());
                mod.setEnabled(!mod.isEnabled());
                if (mod.isEnabled()) {
                    screen.selectedRenderers.add(mod);
                } else {
                    screen.selectedRenderers.remove(mod);
                }
                toggleButton.setMessage(getAddRemoveButtonComponent(mod));
            }).bounds(screen.width/2 + list.width/2 - 40, list.getY() + 10, 20, 20).build();
            this.editButton = Button.builder(Component.literal("Details"), (button) -> {
                Client.setScreen(new MelonClientModCustomizerScreen(screen, mod));
            }).bounds(screen.width/2 + list.width/2 - 60, list.getY() + 10, 50, 20).build();
        }

        public Component getAddRemoveButtonComponent(ModDraggable mod) {
            if (!mod.isEnabled()) return Component.literal("+").withStyle(style -> style.withColor(ChatFormatting.GREEN));
            return Component.literal("-").withStyle(style -> style.withColor(ChatFormatting.RED));
        }

        public Component getNarration() {
            return Component.literal("Selected ").append(this.mod.getName());
        }

        @Override
        public void render(GuiGraphics guiGraphics, int x, int y, int k, int l, int m, int mouseX, int mouseY, boolean hovered, float f) {
            //String description = this.mod.getOptions().description;
            guiGraphics.drawString(minecraft.font, mod.getName(), (k + 32 + 3), (y + 4 + 1), 16777215);
            //guiGraphics.drawString(minecraft.font, Component.literal(mod.getIdentifier()).withStyle(ChatFormatting.GRAY), (k + 32 + 3), (y + 9 + 1), 16777215);
            guiGraphics.drawString(minecraft.font, Component.literal(mod.isEnabled() ? "Enabled" : "Disabled").withStyle(style -> style.withItalic(true).withColor(ChatFormatting.GRAY)), (k + 32 + 3), (y + 9 + 9 + 1), 16777215);
            guiGraphics.blit(this.iconLocation, k, y, 0.0F, 0.0F, 32, 32, 32, 32);
            //if (this.minecraft.options.touchscreen().get() || hovered) {
            toggleButton.setX(k + width - 40);
            toggleButton.setY(y + m/2 - toggleButton.getHeight()/2);
            toggleButton.render(guiGraphics, mouseX, mouseY, f);
            editButton.setX(k + width - 90);
            editButton.setY(y + m/2 - editButton.getHeight()/2);
            editButton.render(guiGraphics, mouseX, mouseY, f);
            //}
        }
        @Override
        public boolean mouseClicked(double d, double e, int i) {
            return toggleButton.mouseClicked(d, e, i) || editButton.mouseClicked(d, e, i);
        }
    }
}