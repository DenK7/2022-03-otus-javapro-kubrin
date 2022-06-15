package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorException implements Processor{

    private final DateTimeProvider time;

    public ProcessorException(DateTimeProvider dateTimeProvider) {
        this.time = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        System.out.println("ProcessorException --> "+ time.getDate().getSecond());
        if (time.getDate().getSecond() % 2 == 0) {
            System.out.println("Exception");
            throw new RuntimeException("Even second");
        }

        return message;
    }

}
