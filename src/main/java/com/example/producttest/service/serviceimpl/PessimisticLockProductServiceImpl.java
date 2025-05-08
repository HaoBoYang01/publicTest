package com.example.producttest.service.serviceimpl;

import com.example.producttest.bean.ProductBean;
import com.example.producttest.repository.ProductRepository;
import com.example.producttest.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("pessimisticLockProductService") // 悲观锁的实现类
public class PessimisticLockProductServiceImpl implements InventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean purchaseWithOptimisticLock(Long productId, int quantity) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 开启事务，异常回滚
    public boolean purchaseWithPessimisticLock(Long productId, int quantity) {
        Optional<ProductBean> byIdWithPessimisticLock = productRepository.findByIdWithPessimisticLock(productId, quantity); // 悲观锁

        // 判断是否存在该商品，存在则进行库存减少操作
        if (byIdWithPessimisticLock.isPresent() && byIdWithPessimisticLock.get().getStock() >= quantity) {
            byIdWithPessimisticLock.get().setStock(byIdWithPessimisticLock.get().getStock() - quantity); // 库存减少
            productRepository.saveAndFlush(byIdWithPessimisticLock.get()); // 保存并刷新
            return true;
        }
        return false;
    }
}
