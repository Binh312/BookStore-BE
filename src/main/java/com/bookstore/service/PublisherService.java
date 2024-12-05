package com.bookstore.service;

import com.bookstore.entity.Publisher;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher createPublisher(Publisher publisher){
        if (publisher.getId() == null){
            Optional<Publisher> publisherOptional = publisherRepository.findPublisherByName(publisher.getName());
            if (publisherOptional.isPresent()){
                throw new GlobalException("Tên nhà phát hành đã tồn tại");
            }
            return publisherRepository.save(publisher);
        } else {
            return updatePublisher(publisher);
        }
    }

    public Publisher updatePublisher(Publisher publisher){
        Optional<Publisher> publisherOptional = publisherRepository.findById(publisher.getId());
        if (publisherOptional.isEmpty()){
            throw new GlobalException("Nhà phát hành không tồn tại");
        }
        if (publisherOptional.get().getName().equals(publisher.getName())){
            throw new GlobalException("Tên nhà phát hành đã tồn tại");
        }
        return publisherRepository.save(publisher);
    }

    public String deletePublisher(Long id){
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isEmpty()){
            throw new GlobalException("Nhà phát hành không tồn tại");
        }
        publisherRepository.delete(publisher.get());
        return "Đã xóa nhà phát hành thành công";
    }

    public Publisher findPublisher(Long id){
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isEmpty()){
            throw new GlobalException("Nhà phát hành không tồn tại");
        }
        return publisher.get();
    }

    public Page<Publisher> searchPublisher(String name, Pageable pageable){
        return publisherRepository.searchPublisher(name,pageable);
    }

    public Page<Publisher> getAllPublisher(Pageable pageable){
        return publisherRepository.getAllPublisher(pageable);
    }

    public List<Publisher> getListPublisher(){
        return publisherRepository.getListPublisher();
    }
}
