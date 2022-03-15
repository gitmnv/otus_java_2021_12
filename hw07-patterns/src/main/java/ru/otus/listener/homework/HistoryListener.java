package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.*;

public class HistoryListener implements Listener, HistoryReader {
    Map<Long, MessageHistoryObject> history = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        MessageHistoryObject messageHistoryObject = MessageHistoryObject.builder()
                .id(history.size() + 1L)
                .message(msg)
                .field13(List.copyOf(msg.getField13().getData()))
                .messageTime(LocalDateTime.now())
                .build();
        history.put(messageHistoryObject.getId(), messageHistoryObject);
        System.out.println(messageHistoryObject + " hi");
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        Optional<Message> optionalMessage = Optional.ofNullable(history.get(id).getMessage());
        var obj = optionalMessage.get().getField13();
        List<String> data = history.get(id).getField13();
        obj.setData(data);
        return optionalMessage;
    }
}
