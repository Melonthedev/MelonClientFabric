package wtf.melonthedev.melonclient.event.events;

import wtf.melonthedev.melonclient.event.Event;

public class KeyEvent extends Event {

    private final int key;

    public KeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

}
