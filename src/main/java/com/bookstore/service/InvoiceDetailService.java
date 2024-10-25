package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Invoice;
import com.bookstore.entity.InvoiceDetail;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.BookRepository;
import com.bookstore.respository.InvoiceDetailRepository;
import com.bookstore.respository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceDetailService {

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceDetail createInvoiceDetail(InvoiceDetail invoiceDetail){
        Optional<Book> book = bookRepository.findById(invoiceDetail.getBook().getId());
        if (book.isEmpty()){
            throw new GlobalException("Sách này không tồn tại");
        }
        Optional<Invoice> invoice = invoiceRepository.findById(invoiceDetail.getInvoice().getId());
        if (invoice.isEmpty()){
            throw new GlobalException("Hóa đơn này không tồn tại");
        }
        invoiceDetail.setBook(book.get());
        invoiceDetail.setInvoice(invoice.get());
        return invoiceDetailRepository.save(invoiceDetail);
    }

    public String deleteInvoiceDetail(Long id){
        Optional<InvoiceDetail> invoiceDetail = invoiceDetailRepository.findById(id);
        if (invoiceDetail.isEmpty()){
            throw new GlobalException("Chi tiết hóa đơn này không tồn tại");
        }
        invoiceDetailRepository.delete(invoiceDetail.get());
        return "Đã xóa chi tiết hóa đơn thành công";
    }

    public InvoiceDetail findInvoiceDetail(Long id){
        Optional<InvoiceDetail> invoiceDetail = invoiceDetailRepository.findById(id);
        if (invoiceDetail.isEmpty()){
            throw new GlobalException("Chi tiết hóa đơn này không tồn tại");
        }
        return invoiceDetail.get();
    }
}
