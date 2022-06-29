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
        historyMessagesHashMap.put(msg.getId(), msg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        if (historyMessagesHashMap == null) {
            return Optional.empty();
        }
        return Optional.of(historyMessagesHashMap.get(id));
    }
}
