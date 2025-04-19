package wtf.melonthedev.melonclient.event;

import java.util.ArrayList;

public class Event {

    public Event call() {
        final ArrayList<EventData> dataList = EventManager.get(this.getClass());
        if (dataList != null) {
            for (EventData data : dataList) {
                try {
                    data.target.invoke(data.source, this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return this;
    }
}
