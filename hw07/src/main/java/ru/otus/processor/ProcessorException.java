package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalTime;

public class ProcessorException implements Processor{
    @Override
    public Message process(Message message) {
        var time = LocalTime.now();
        System.out.println("ProcessorException --> "+ time.getSecond());
        if (time.getSecond() % 2 == 0) {
            System.out.println("Exception");
            throw new RuntimeException("Even second");
        }

        return message;
    }
}
