package com.openshift.evangelists.microservices.api;

import java.util.List;

/**
 * Created by jmorales on 8/28/15.
 */
public interface MessageRepository {

    List<Message> findAll();

    Iterable<Message> findAllIt();

    Message save(Message message);

    Message findMessage(Long id);

    void delete(Long id);

}