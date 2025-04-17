package wtf.melonthedev.melonclient.gui.screens;

import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;

import java.util.Timer;
import java.util.concurrent.CompletableFuture;

public class MelonClientMultiplayerScreen extends JoinMultiplayerScreen {

    public MelonClientMultiplayerScreen() {
        super(null);
    }

    @Override
    public void joinSelectedServer() {
        disconnect();
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(100);
                super.joinSelectedServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /*@Override
    public void directJoinCallback(boolean flag) {
        if (flag) disconnect();
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(100);
                super.directJoinCallback(flag);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }*/

    private void disconnect() {
        this.minecraft.level.disconnect();
        if (this.minecraft.isLocalServer()) {
            this.minecraft.clearLevel(new GenericDirtMessageScreen(Component.translatable("menu.savingLevel")));
        } else {
            this.minecraft.clearLevel();
        }
    }
}
