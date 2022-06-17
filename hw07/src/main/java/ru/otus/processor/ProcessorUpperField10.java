package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorUpperField10 implements Processor {

    @Override
    public Message process(Message message) {
        System.out.println("ProcessorUpperField10");
        return message.toBuilder().field4(message.getField10().toUpperCase()).build();
    }
}
