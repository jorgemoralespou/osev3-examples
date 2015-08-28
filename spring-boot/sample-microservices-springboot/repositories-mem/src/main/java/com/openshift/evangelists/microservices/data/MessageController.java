package com.openshift.evangelists.microservices.data;

import com.openshift.evangelists.microservices.api.Message;
import com.openshift.evangelists.microservices.api.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jmorales on 8/28/15.
 */
@RestController
@RequestMapping("/data")
/*
 * POST: curl  -H "Content-type: application/json" -X POST -d '{"id":10,"text":"AAAA","summary":"BBBB"}'  http://localhost:9090/data
 * PUT: curl  -H "Content-type: application/json" -X PUT -d '{"id":10,"text":"aaaaa","summary":"bbbbb"}'  http://localhost:9090/data/10
 * GET: curl http://localhost:9090/data
 * GET: curl http://localhost:9090/data/1
 * DELETE: curl -X DELETE http://localhost:9090/data/10
 */
public class MessageController {
        private final MessageRepository messageRepository;

        @Autowired
        public MessageController(MessageRepository messageRepository) {
                this.messageRepository = messageRepository;
                messageRepository.save(new Message(1L,"1A","1B"));
                messageRepository.save(new Message(2L,"2A","2B"));
                messageRepository.save(new Message(3L,"3A","3B"));
        }

        private final AtomicLong counter = new AtomicLong();

        @RequestMapping(method= RequestMethod.POST)
        public @ResponseBody
        Message create(@RequestBody Message msg) {
                messageRepository.save(msg);
                return msg;
        }

        @RequestMapping(method=RequestMethod.GET)
        public @ResponseBody Iterable<Message> getAll() {
                return messageRepository.findAllIt();
        }

        @RequestMapping(method=RequestMethod.DELETE, value="{id}")
        public void delete(@PathVariable Long id) {
                messageRepository.delete(id);
        }

        @RequestMapping(method=RequestMethod.PUT, value="{id}")
        public @ResponseBody Message update(@PathVariable String id, @RequestBody Message contact) {
                Message msg = messageRepository.findMessage(contact.getId());
                msg.setSummary(contact.getSummary());
                msg.setText(contact.getText());
                messageRepository.save(msg);
                return msg;
        }


        @RequestMapping(method=RequestMethod.GET, value="{id}")
        public @ResponseBody Message getById(@PathVariable("id") Long id) {
                 return messageRepository.findMessage(id);
        }


        @RequestMapping(method=RequestMethod.GET, value="/init/")
        public void init(){
                messageRepository.save(new Message(1L,"1A","1B"));
                messageRepository.save(new Message(2L,"2A","2B"));
                messageRepository.save(new Message(3L,"3A","3B"));
        }

}
