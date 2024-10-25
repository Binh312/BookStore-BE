package com.bookstore.respository;

import com.bookstore.entity.StatusInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusInvoiceRepository extends JpaRepository<StatusInvoice, Long> {

}
