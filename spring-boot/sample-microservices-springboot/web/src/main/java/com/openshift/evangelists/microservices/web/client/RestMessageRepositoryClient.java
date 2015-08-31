package com.openshift.evangelists.microservices.web.client;

import com.openshift.evangelists.microservices.web.api.Message;
import com.openshift.evangelists.microservices.web.api.MessageRepository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jmorales on 8/31/15.
 */
public class RestMessageRepositoryClient implements MessageRepository {
    public static final String REMOTE_SVC = "http://localhost:9090/";

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Message> findAll() {
        System.out.println("RestMessageRepositoryClient.findAll");
        List<Message> msgList = new ArrayList<>();
        try{
            msgList = restTemplate.getForObject(REMOTE_SVC, List.class);
        }catch(Exception e){
            System.out.println("Remote server is not available");
        }
        return msgList;
    }

    @Override
    public Iterable<Message> findAllIt() {
        System.out.println("RestMessageRepositoryClient.findAllIt");
        Iterable<Message> itMsg = new Iterable<Message>() {
            @Override
            public Iterator<Message> iterator() {
                return null;
            }
        };
        try {
            itMsg = restTemplate.getForObject(REMOTE_SVC, Iterable.class);
        }catch(Exception e){
            System.out.println("Remote server is not available");
        }
        return itMsg;
    }

    @Override
    public Message save(Message message) {
        System.out.println("RestMessageRepositoryClient.save: " + message);
        Message msg = null;
        try{
            if (message.getId()==null){
                System.out.println("RestMessageRepositoryClient.save[new]");
                msg = restTemplate.postForObject(REMOTE_SVC,message, Message.class);
            }else{
                System.out.println("RestMessageRepositoryClient.save[update]");
                restTemplate.put(REMOTE_SVC+message.getId(), message);
                msg = restTemplate.getForObject(REMOTE_SVC+message.getId(), Message.class);
            }
        }catch(Exception e){
            System.out.println("Remote server is not available");
        }
        return msg;
    }

    @Override
    public Message findMessage(Long id) {
        System.out.println("RestMessageRepositoryClient.findMessage: "+ id);
        Message msg = null;
        try {
            msg = restTemplate.getForObject(REMOTE_SVC + id, Message.class);
            System.out.println("Found: " + msg);
        }catch(Exception e){
            System.out.println("Remote server is not available");
        }
        return msg;
    }

    @Override
    public void delete(Long id) {
        try {
            System.out.println("RestMessageRepositoryClient.delete: " + id);
            restTemplate.delete(REMOTE_SVC +id);
        }catch(Exception e){
            System.out.println("Remote server is not available");
        }
    }


}
