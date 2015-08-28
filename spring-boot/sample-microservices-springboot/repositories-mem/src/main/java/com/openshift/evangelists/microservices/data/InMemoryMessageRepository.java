package com.openshift.evangelists.microservices.data;

/**
 * Created by jmorales on 8/28/15.
 */
import com.openshift.evangelists.microservices.api.Message;
import com.openshift.evangelists.microservices.api.MessageRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Dave Syer
 */
public class InMemoryMessageRepository implements MessageRepository {

    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, Message> messages = new ConcurrentHashMap<Long, Message>();

    @Override
    public List<Message> findAll() {
        return new ArrayList<Message>(this.messages.values());
    }

    @Override
    public Iterable<Message> findAllIt() {
        return this.messages.values();
    }

    @Override
    public Message save(Message message) {
        Long id = message.getId();
        if (id == null) {
            id = counter.incrementAndGet();
            message.setId(id);
        }
        this.messages.put(id, message);
        return message;
    }

    @Override
    public Message findMessage(Long id) {
        return this.messages.get(id);
    }

    @Override
    public void delete(Long id) {
        messages.remove(id);
    }

}
