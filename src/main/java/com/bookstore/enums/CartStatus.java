package com.bookstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CartStatus {
    Pending, Shipped, Delivered, Canceled
}
