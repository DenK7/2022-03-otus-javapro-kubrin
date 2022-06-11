package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorSwap implements Processor {
    @Override
    public Message process(Message message) {
        System.out.println("ProcessorSwap");
        return message.toBuilder()
                .field11(message.getField12())
                .field12(message.getField11())
                .build();
    }
}
