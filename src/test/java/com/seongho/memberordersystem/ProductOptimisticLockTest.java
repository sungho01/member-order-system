package com.seongho.memberordersystem;

import com.seongho.memberordersystem.entity.Product;
import com.seongho.memberordersystem.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProductOptimisticLockTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    void optimisticLockTest() {
        Product product = new Product();
        product.setName("콜라");
        product.setPrice(2000);
        product.setStock(10);

        Product savedProduct = productRepository.saveAndFlush(product);

        TransactionTemplate tx = new TransactionTemplate(transactionManager);

        // 같은 시점에 각각 조회했다고 가정하는 두 개의 복사본
        Product product1 = tx.execute(status ->
                productRepository.findById(savedProduct.getProductId()).orElseThrow()
        );

        Product product2 = tx.execute(status ->
                productRepository.findById(savedProduct.getProductId()).orElseThrow()
        );

        // 첫 번째 수정 성공
        product1.setStock(9);
        tx.executeWithoutResult(status -> productRepository.saveAndFlush(product1));

        // 두 번째 수정은 이전 버전으로 저장하려 하므로 낙관적 락 예외 발생
        product2.setStock(8);
        assertThrows(OptimisticLockingFailureException.class, () ->
                tx.executeWithoutResult(status -> productRepository.saveAndFlush(product2))
        );

        // 최종 재고는 첫 번째 수정만 반영되어야 함
        Product result = productRepository.findById(savedProduct.getProductId()).orElseThrow();
        assertEquals(9, result.getStock());
    }
}