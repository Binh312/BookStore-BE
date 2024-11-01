package com.bookstore.api;

import com.bookstore.entity.Voucher;
import com.bookstore.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voucher")
@CrossOrigin("*")
public class VoucherApi {

    @Autowired
    private VoucherService voucherService;

    @PostMapping("/create-update")
    public ResponseEntity<?> createOrUpdateVoucher(@RequestBody Voucher voucher){
        Voucher result = voucherService.createVoucher(voucher);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVoucher(@RequestParam Long id){
        String str = voucherService.deleteVoucher(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-voucher")
    public ResponseEntity<?> findVoucher(@RequestParam Long id){
        Voucher result = voucherService.findVoucher(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-all-voucher")
    public ResponseEntity<?> getAllVoucher(Pageable pageable){
        Page<Voucher> page = voucherService.getAllVoucher(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/get-list-voucher")
    public ResponseEntity<?> getListVoucher(){
        List<Voucher> list = voucherService.getListVoucher();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
