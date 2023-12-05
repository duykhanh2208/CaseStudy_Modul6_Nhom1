package com.houserenting.controller;
import com.houserenting.model.Message;
import com.houserenting.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageServiceImpl;

    @GetMapping("/messages")
    public ResponseEntity<Iterable<Message>> showList(){
        return new ResponseEntity<>(messageServiceImpl.showAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Message>> showOne(@PathVariable Long id){
        Optional<Message> message = messageServiceImpl.findOne(id);
        if(message.isPresent()){
            return new ResponseEntity<>(message,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("")
    public ResponseEntity<Message> create(@RequestBody Message message){
        messageServiceImpl.save(message);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> save(@PathVariable Long id, @RequestBody Message message){
        Optional<Message> oleMessage = messageServiceImpl.findOne(id);
        if(oleMessage.isPresent()){
            message.setId(oleMessage.get().getId());
            messageServiceImpl.save(message);
            return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        Optional<Message> message = messageServiceImpl.findOne(id);
        if(message.isPresent()){
            messageServiceImpl.delete(id);
            return new ResponseEntity<>(id,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
