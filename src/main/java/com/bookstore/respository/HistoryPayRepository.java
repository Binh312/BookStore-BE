package com.bookstore.respository;

import com.bookstore.entity.HistoryPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryPayRepository extends JpaRepository<HistoryPay, Long> {
}
