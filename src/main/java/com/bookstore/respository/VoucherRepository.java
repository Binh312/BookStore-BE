package com.bookstore.respository;

import com.bookstore.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @Query("select v from Voucher v where v.title = ?1")
    Optional<Voucher> findVoucherByTitle(String title);

    @Query("select  v from Voucher v")
    Page<Voucher> getAllVoucher(Pageable pageable);

    @Query("select v from Voucher v")
    List<Voucher> getListVoucher();
}
