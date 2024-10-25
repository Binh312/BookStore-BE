package com.bookstore.api;

import com.bookstore.entity.HistoryPay;
import com.bookstore.service.HistoryPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history-pay")
@CrossOrigin("*")
public class HistoryPayApi {

    @Autowired
    private HistoryPayService historyPayService;

    @PostMapping("/create")
    public ResponseEntity<?> createHistoryPay(@RequestBody HistoryPay historyPay){
        HistoryPay result = historyPayService.createHistoryPay(historyPay);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHistoryPay(@RequestParam Long id){
        String str = historyPayService.deleteHistoryPay(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-history-pay")
    public ResponseEntity<?> findHistoryPay(@RequestParam Long id){
        HistoryPay result = historyPayService.findHistoryPay(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
