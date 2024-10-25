package com.bookstore.api;

import com.bookstore.entity.Invoice;
import com.bookstore.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
@CrossOrigin("*")
public class InvoiceApi {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice){
        Invoice result = invoiceService.createInvoice(invoice);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInvoice(@RequestParam Long id){
        String str = invoiceService.deleteInvoice(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-invoice")
    public ResponseEntity<?> findInvoice(@RequestParam Long id){
        Invoice result = invoiceService.findInvoice(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-all-invoice")
    public ResponseEntity<?> getAllInvoice(Pageable pageable){
        Page<Invoice> page = invoiceService.getAllInvoice(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
