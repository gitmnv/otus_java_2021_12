package ru.otus.processor;

import ru.otus.model.Message;


public class TimeProcessor implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public TimeProcessor(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        System.out.println(dateTimeProvider.getDate().getSecond());
        if (dateTimeProvider.getDate().getSecond() % 2 == 0) {
            throw new RuntimeException("Time error: " + " getSecond() % 2 == " + dateTimeProvider.getDate().getSecond() % 2);
        } else {
            return message;
        }
    }
}



