package com.bookstore.service;

import com.bookstore.entity.Voucher;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public Voucher createVoucher(Voucher voucher){
        if (voucher.getId() == null){
            Optional<Voucher> voucherOptional = voucherRepository.findVoucherByTitle(voucher.getTitle());
            if (voucherOptional.isPresent()){
                throw new GlobalException("Tiêu đề voucher đã tồn tại");
            }
            voucher.setCreateDate(LocalDateTime.now());
            voucher.setEndDate(voucher.getCreateDate().plusDays(7));
            return voucherRepository.save(voucher);
        } else {
            return updateVoucher(voucher);
        }
    }

    public Voucher updateVoucher(Voucher voucher){
        Optional<Voucher> voucherOptional = voucherRepository.findById(voucher.getId());
        if (voucherOptional.isEmpty()){
            throw new GlobalException("Voucher này không tồn tại");
        }
        if (voucherOptional.get().getTitle().equals(voucher.getTitle())){
            throw new GlobalException("Tiêu đề voucher đã tồn tại");
        }
        voucher.setCreateDate(LocalDateTime.now());
        voucher.setEndDate(voucher.getCreateDate().plusDays(7));
        return voucherRepository.save(voucher);
    }

    public String deleteVoucher(Long id){
        Optional<Voucher> voucherOptional = voucherRepository.findById(id);
        if (voucherOptional.isEmpty()){
            throw new GlobalException("Voucher này không tồn tại");
        }
        voucherRepository.delete(voucherOptional.get());
        return "Đã xóa voucher thành công";
    }

    public Voucher findVoucher(Long id){
        Optional<Voucher> voucherOptional = voucherRepository.findById(id);
        if (voucherOptional.isEmpty()){
            throw new GlobalException("Voucher này không tồn tại");
        }
        return voucherOptional.get();
    }

    public Page<Voucher> getAllVoucher(Pageable pageable){
        return voucherRepository.getAllVoucher(pageable);
    }
}
