package com.bookstore.service;

import com.bookstore.entity.Invoice;
import com.bookstore.entity.StatusInvoice;
import com.bookstore.entity.User;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.InvoiceRepository;
import com.bookstore.respository.StatusInvoiceRepository;
import com.bookstore.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StatusInvoiceService {

    @Autowired
    private StatusInvoiceRepository statusInvoiceRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    public StatusInvoice createStatusInvoice(StatusInvoice statusInvoice){
        if (statusInvoice.getId() == null){
            Optional<Invoice> invoice = invoiceRepository.findById(statusInvoice.getInvoice().getId());
            if (invoice.isEmpty()){
                throw new GlobalException("Hóa đơn không tồn tại");
            }
            Optional<User> user = userRepository.findById(statusInvoice.getUser().getId());
            if (user.isEmpty()){
                throw new GlobalException("Nguời dùng không tồn tại");
            }
            statusInvoice.setCreatedDate(LocalDateTime.now());
            statusInvoice.setInvoice(invoice.get());
            statusInvoice.setUser(user.get());
            return statusInvoiceRepository.save(statusInvoice);
        } else {
            return updateStatusInvoice(statusInvoice);
        }
    }

    public StatusInvoice updateStatusInvoice(StatusInvoice statusInvoice){
        Optional<StatusInvoice> statusInvoiceOptional = statusInvoiceRepository.findById(statusInvoice.getId());
        if (statusInvoiceOptional.isEmpty()){
            throw new GlobalException("Trạng thái hóa đơn này không tồn tại");
        }
        Optional<Invoice> invoice = invoiceRepository.findById(statusInvoice.getInvoice().getId());
        if (invoice.isEmpty()){
            throw new GlobalException("Hóa đơn không tồn tại");
        }
        Optional<User> user = userRepository.findById(statusInvoice.getUser().getId());
        if (user.isEmpty()){
            throw new GlobalException("Nguời dùng không tồn tại");
        }
        statusInvoice.setCreatedDate(LocalDateTime.now());
        statusInvoice.setInvoice(invoice.get());
        statusInvoice.setUser(user.get());
        return statusInvoiceRepository.save(statusInvoice);
    }

    public String deleteStatusInvoice(Long id){
        Optional<StatusInvoice> statusInvoiceOptional = statusInvoiceRepository.findById(id);
        if (statusInvoiceOptional.isEmpty()){
            throw new GlobalException("Trạng thái hóa đơn này không tồn tại");
        }
        statusInvoiceRepository.delete(statusInvoiceOptional.get());
        return "Đã xóa trạng thái hóa đơn thành công";
    }

    public StatusInvoice findStatusInvoice(Long id){
        Optional<StatusInvoice> statusInvoiceOptional = statusInvoiceRepository.findById(id);
        if (statusInvoiceOptional.isEmpty()){
            throw new GlobalException("Trạng thái hóa đơn này không tồn tại");
        }
        return statusInvoiceOptional.get();
    }
}
