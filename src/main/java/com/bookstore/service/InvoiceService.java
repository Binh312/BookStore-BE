package com.bookstore.service;

import com.bookstore.entity.Invoice;
import com.bookstore.entity.User;
import com.bookstore.entity.Voucher;
import com.bookstore.enums.InvoiceStatus;
import com.bookstore.enums.PayType;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.InvoiceRepository;
import com.bookstore.respository.UserRepository;
import com.bookstore.respository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherRepository voucherRepository;
    public Invoice createInvoice(Invoice invoice){
        Optional<User> user = userRepository.findById(invoice.getUser().getId());
        if (user.isEmpty()){
            throw new GlobalException("Người dùng không tồn tại");
        }
        if (invoice.getVoucher() != null){
            Optional<Voucher> voucher = voucherRepository.findById(invoice.getVoucher().getId());
            if (voucher.isEmpty()){
                throw new GlobalException("Voucher không tồn tại");
            }
            invoice.setVoucher(voucher.get());
        }
        invoice.setInvoiceStatus(InvoiceStatus.DANG_CHO);
        invoice.setCreateDate(LocalDateTime.now());
        invoice.setUser(user.get());

        if (invoice.getPayType() == null){
            invoice.setPayType(PayType.CODE);
        }

        return invoiceRepository.save(invoice);
    }

    public String deleteInvoice(Long id){
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()){
            throw new GlobalException("Hóa đơn không tồn tại");
        }
        invoiceRepository.delete(invoice.get());
        return "Đã xóa hóa đơn thành công";
    }

    public Invoice findInvoice(Long id){
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()){
            throw new GlobalException("Hóa đơn không tồn tại");
        }
        return invoice.get();
    }

    public Page<Invoice> getAllInvoice(Pageable pageable){
        return invoiceRepository.getAllInvoice(pageable);
    }
}
