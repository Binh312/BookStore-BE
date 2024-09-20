package com.bookstore.service;

import com.bookstore.entity.Publisher;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher createPublisher(Publisher publisher){
        Optional<Publisher> publisherOptional = publisherRepository.findById(publisher.getId());
        if (publisherOptional.get().getName().equals(publisher.getName())){
            throw new GlobalException("Tên nhà phát hành đã tồn tại");
        }
        return publisherRepository.save(publisher);
    }
}
