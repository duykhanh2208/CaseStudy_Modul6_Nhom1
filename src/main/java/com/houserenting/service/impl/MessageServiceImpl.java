package com.houserenting.service.impl;

import com.houserenting.model.Booking;
import com.houserenting.model.Message;
import com.houserenting.repository.BookingRepository;
import com.houserenting.repository.MessageRepository;
import com.houserenting.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Iterable<Message> showAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> findOne(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Long delete(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()){
            message.get().setStatus("deleted");
            messageRepository.save(message.get());
            return id;
        }
        return null;
    }
}
