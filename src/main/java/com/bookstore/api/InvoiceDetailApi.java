package com.bookstore.api;

import com.bookstore.entity.InvoiceDetail;
import com.bookstore.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice-detail")
@CrossOrigin("*")
public class InvoiceDetailApi {

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @PostMapping("/create")
    public ResponseEntity<?> createInvoiceDetail(@RequestBody InvoiceDetail invoiceDetail){
        InvoiceDetail result = invoiceDetailService.createInvoiceDetail(invoiceDetail);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInvoiceDetail(@RequestParam Long id){
        String str = invoiceDetailService.deleteInvoiceDetail(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-invoice-detail")
    public ResponseEntity<?> findInvoiceDetail(@RequestParam Long id){
        InvoiceDetail result = invoiceDetailService.findInvoiceDetail(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
