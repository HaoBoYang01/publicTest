package com.example.producttest.service;

public interface InventoryService {

    //乐观锁
    boolean purchaseWithOptimisticLock(Long productId, int quantity);

    //悲观锁
    boolean purchaseWithPessimisticLock(Long productId, int quantity);
}
