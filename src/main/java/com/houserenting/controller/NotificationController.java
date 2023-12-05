package com.houserenting.controller;
import com.houserenting.model.Notification;
import com.houserenting.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationServiceImpl;

    @GetMapping("/notifications")
    public ResponseEntity<Iterable<Notification>> showList(){
        return new ResponseEntity<>(notificationServiceImpl.showAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Notification>> showOne(@PathVariable Long id){
        Optional<Notification> notification = notificationServiceImpl.findOne(id);
        if(notification.isPresent()){
            return new ResponseEntity<>(notification,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("")
    public ResponseEntity<Notification> create(@RequestBody Notification notification){
        notificationServiceImpl.save(notification);
        return new ResponseEntity<>(notification,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> save(@PathVariable Long id, @RequestBody Notification notification){
        Optional<Notification> oldNotification = notificationServiceImpl.findOne(id);
        if(oldNotification.isPresent()){
            notification.setId(oldNotification.get().getId());
            notificationServiceImpl.save(notification);
            return new ResponseEntity<>(notification,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        Optional<Notification> notification = notificationServiceImpl.findOne(id);
        if(notification.isPresent()){
            notificationServiceImpl.delete(id);
            return new ResponseEntity<>(id,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
