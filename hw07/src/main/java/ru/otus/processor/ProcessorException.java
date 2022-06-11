package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ProcessorException implements Processor{
    @Override
    public Message process(Message message) {
        System.out.println("ProcessorException");
        var time = LocalTime.now();
        if(time.getSecond() % 2 == 0) {
            throw new RuntimeException("Even second");
        }

        return message;
    }
}
