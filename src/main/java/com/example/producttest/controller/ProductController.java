package com.example.producttest.controller;

import com.example.producttest.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/product")
public class ProductController {

    @Autowired
    @Qualifier("pessimisticLockProductService") // 注入悲观锁的实现
    private InventoryService pessimisticLockProduct; // 注入悲观锁的实现

    @Autowired
    @Qualifier("optimisticLockService") // 注入乐观锁的实现
    private InventoryService optimisticLockProduct; // 注入乐观锁的实现


    @PostMapping("/purchaseWithPessimisticLock/{productId}")
    public String purchaseWithPessimisticLock(@PathVariable Long productId, @RequestParam int quantity) {
        boolean purchaseWithPessimisticLock = pessimisticLockProduct.purchaseWithPessimisticLock(productId, quantity); // 调用悲观锁的实现
        return purchaseWithPessimisticLock ? "购买成功" : "购买失败，库存不足，发生冲突";
    }

    @PostMapping("/purchaseWithOptimisticLock/{productId}")
    public String purchaseWithOptimisticLock(@PathVariable Long productId, @RequestParam int quantity) {
        boolean purchaseWithOptimisticLock = optimisticLockProduct.purchaseWithOptimisticLock(productId, quantity); // 调用乐观锁的实现
        return purchaseWithOptimisticLock ? "购买成功" : "购买失败，库存不足";
    }




}
