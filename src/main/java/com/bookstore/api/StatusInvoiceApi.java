package com.bookstore.api;

import com.bookstore.entity.StatusInvoice;
import com.bookstore.service.StatusInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status-invoice")
public class StatusInvoiceApi {

    @Autowired
    private StatusInvoiceService statusInvoiceService;

    @PostMapping("/create-update")
    public ResponseEntity<?> createOrUpdateStatusInvoice(@RequestBody StatusInvoice statusInvoice){
        StatusInvoice result = statusInvoiceService.createStatusInvoice(statusInvoice);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStatusInvoice(@RequestParam Long id){
        String str = statusInvoiceService.deleteStatusInvoice(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-status-invoice")
    public ResponseEntity<?> findStatusInvoice(@RequestParam Long id){
        StatusInvoice result = statusInvoiceService.findStatusInvoice(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
