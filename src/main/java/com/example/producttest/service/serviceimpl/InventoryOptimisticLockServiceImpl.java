package com.example.producttest.service.serviceimpl;

import com.example.producttest.bean.ProductBean;
import com.example.producttest.repository.ProductRepository;
import com.example.producttest.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("optimisticLockService") // 乐观锁的实现类
public class InventoryOptimisticLockServiceImpl implements InventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(noRollbackFor = RuntimeException.class)
    public boolean purchaseWithOptimisticLock(Long productId, int quantity) {
        try{
            //乐观锁的实现，减少库存数量
            int reduceStockOptimisticLock = productRepository.reduceStockOptimisticLock(productId, quantity);
            return reduceStockOptimisticLock > 0;
        }catch (Exception e){
            //乐观锁失败，返回false而不是抛出异常
            return false;
        }
    }

    @Override
    public boolean purchaseWithPessimisticLock(Long productId, int quantity) {
        return false;
    }
}
