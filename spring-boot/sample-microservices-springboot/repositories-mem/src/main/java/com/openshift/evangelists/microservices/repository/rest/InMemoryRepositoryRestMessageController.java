package com.openshift.evangelists.microservices.repository.rest;

import com.openshift.evangelists.microservices.api.Message;
import com.openshift.evangelists.microservices.api.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jmorales on 8/28/15.
 */
@RestController
@RequestMapping("/")
/*
 * POST: curl  -H "Content-type: application/json" -X POST -d '{"id":10,"text":"AAAA","summary":"BBBB"}'  http://localhost:9090/
 * PUT: curl  -H "Content-type: application/json" -X PUT -d '{"id":10,"text":"aaaaa","summary":"bbbbb"}'  http://localhost:9090/10
 * GET: curl http://localhost:9090/
 * GET: curl http://localhost:9090/1
 * DELETE: curl -X DELETE http://localhost:9090/10
 */
public class InMemoryRepositoryRestMessageController {
        private final MessageRepository messageRepository;

        @Autowired
        public InMemoryRepositoryRestMessageController(MessageRepository messageRepository) {
                this.messageRepository = messageRepository;
                messageRepository.save(new Message(null,"Hello","World"));
                messageRepository.save(new Message(null,"Hi","Universe"));
                messageRepository.save(new Message(null,"Hola","OpenShift"));
        }

        private final AtomicLong counter = new AtomicLong();

        @RequestMapping(method= RequestMethod.POST)
        public @ResponseBody
        Message create(@RequestBody Message msg) {
                System.out.println("InMemoryRepositoryRestMessageController.create: " + msg);
                messageRepository.save(msg);
                return msg;
        }

        @RequestMapping(method=RequestMethod.GET)
        public @ResponseBody Iterable<Message> getAll() {
                System.out.println("InMemoryRepositoryRestMessageController.getAll" );
                return messageRepository.findAllIt();
        }

        @RequestMapping(method=RequestMethod.DELETE, value="{id}")
        public void delete(@PathVariable Long id) {
                System.out.println("InMemoryRepositoryRestMessageController.delete: " + id);
                messageRepository.delete(id);
        }

        @RequestMapping(method=RequestMethod.PUT, value="{id}")
        public @ResponseBody Message update(@PathVariable String id, @RequestBody Message contact) {
                System.out.println("InMemoryRepositoryRestMessageController.update: " + contact);
                if (contact.getId() != null) {
                        Message msg = messageRepository.findMessage(contact.getId());
                        msg.setSummary(contact.getSummary());
                        msg.setText(contact.getText());
                        messageRepository.save(msg);
                        return msg;
                }else
                {
                        Message msg = messageRepository.save(contact);
                        return msg;
                }
        }


        @RequestMapping(method=RequestMethod.GET, value="{id}")
        public @ResponseBody Message getById(@PathVariable("id") Long id) {
                System.out.println("InMemoryRepositoryRestMessageController.getById: " + id);
                return messageRepository.findMessage(id);
        }

}
