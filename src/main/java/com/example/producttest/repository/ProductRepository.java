package com.example.producttest.repository;

import com.example.producttest.bean.ProductBean;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductBean, Long> {

    //乐观锁 @Version自动实现  quantity是减少的库存数量
    //flushAutomatically = true 表示在执行查询之前刷新持久化上下文中的更改到数据库
    //clearAutomatically = true 表示在执行查询之后清除持久化上下文中的更改
    @Modifying (flushAutomatically = true ,clearAutomatically = true)   //表示这是一个修改操作，会触发事务的提交
    @Query("update ProductBean p set p.stock = p.stock - :quantity ,p.version=p.version+1 where p.productId=:productId")
    int reduceStockOptimisticLock(Long productId, int quantity);


    //悲观锁
    //用于指定对数据库记录的锁定模式。
    //LockModeType.PESSIMISTIC_WRITE 指定了一种悲观锁定模式（Pessimistic Locking）
    //悲观锁定假设在执行操作时会发生并发冲突，因此它首先锁定记录，以防止其他事务访问这些记录，直到当前事务完成。
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProductBean p where p.productId= :productId")
    Optional<ProductBean> findByIdWithPessimisticLock(@Param("productId") Long productId, int quantity);

}
