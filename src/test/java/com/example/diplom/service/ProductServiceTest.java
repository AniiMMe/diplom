package com.example.diplom.service;

import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Product;
import com.example.diplom.reposiroty.AssortmentRepository;
import com.example.diplom.reposiroty.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock(lenient = true)
    private AssortmentRepository assortmentRepository;

    @InjectMocks
    private ProductService productService;


    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckProductFalse() {

        Assortment assortment = new Assortment();
        int col = 5;
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(1);
        product1.setProductQuantity(10);
        productList.add(product1);
        when(productRepository.findAllByAssortmentAndProductEnddataIsBetween(any(), any(), any()))
                .thenReturn(productList);
        boolean result = productService.checkProduct(assortment, col);
        assertFalse(result);
        verify(productRepository, times(1)).delete(product1);
    }
    @Test
    public void testGetProductForOrder() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductQuantity(5);
        productList.add(product1);

        List<String> assortmentList = Arrays.asList("Assortment1", "Assortment2");
        Assortment assortment1 = new Assortment();
        assortment1.setCount(10);
        Assortment assortment2 = new Assortment();
        assortment2.setCount(3);
        when(assortmentRepository.findAllByProductName("Assortment1")).thenReturn(assortment1);
        when(assortmentRepository.findAllByProductName("Assortment2")).thenReturn(assortment2);
        Map<Assortment, String> result = productService.getProductForOrder(productList, assortmentList);

        assertEquals(1, result.size());
        assertEquals("Недостаточно товара", result.get(assortment1));
        verify(assortmentRepository, times(1)).save(assortment1);
        verify(assortmentRepository, never()).save(assortment2);
    }



}