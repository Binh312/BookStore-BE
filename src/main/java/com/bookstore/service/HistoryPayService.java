package com.bookstore.service;

import com.bookstore.entity.HistoryPay;
import com.bookstore.entity.Invoice;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.HistoryPayRepository;
import com.bookstore.respository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class HistoryPayService {

    @Autowired
    private HistoryPayRepository historyPayRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public HistoryPay createHistoryPay(HistoryPay historyPay){
        Optional<Invoice> invoice = invoiceRepository.findById(historyPay.getInvoice().getId());
        if (invoice.isEmpty()){
           throw new GlobalException("Hóa đơn này không tồn tại");
        }
        historyPay.setCreatedDate(LocalDateTime.now());
        historyPay.setCreatedTime(LocalDateTime.now());
        historyPay.setInvoice(invoice.get());
        return historyPayRepository.save(historyPay);
    }

    public String deleteHistoryPay(Long id){
        Optional<HistoryPay> historyPay = historyPayRepository.findById(id);
        if (historyPay.isEmpty()){
            throw new GlobalException("Lịch sử thanh toán này không tồn tại");
        }
        historyPayRepository.delete(historyPay.get());
        return "Đã xóa lịch sử thanh toán thành công";
    }

    public HistoryPay findHistoryPay(Long id){
        Optional<HistoryPay> historyPay = historyPayRepository.findById(id);
        if (historyPay.isEmpty()){
            throw new GlobalException("Lịch sử thanh toán này không tồn tại");
        }
        return historyPay.get();
    }
}
