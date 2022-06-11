package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private HashMap<Long, Message> historyMessagesHashMap;

    @Override
    public void onUpdated(Message msg) {
        if (historyMessagesHashMap == null) {
            historyMessagesHashMap = new HashMap<>();
        }
        historyMessagesHashMap.put(msg.getId(),
                new Message.Builder(msg.getId())
                        .field1(msg.getField1())
                        .field2(msg.getField2())
                        .field3(msg.getField3())
                        .field4(msg.getField4())
                        .field5(msg.getField5())
                        .field6(msg.getField6())
                        .field7(msg.getField7())
                        .field8(msg.getField8())
                        .field9(msg.getField9())
                        .field10(msg.getField10())
                        .field11(msg.getField11())
                        .field12(msg.getField12())
                        .field13(msg.getField13())
                        .build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        if (historyMessagesHashMap == null) {
            historyMessagesHashMap = new HashMap<>();
        }
        return Optional.ofNullable(historyMessagesHashMap.get(id));
    }
}
