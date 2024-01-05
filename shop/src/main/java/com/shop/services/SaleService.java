package com.shop.services;

import com.shop.models.daos.SaleDAO;
import com.shop.models.dtos.SaleDTO;
import com.shop.models.orms.Sale;
import com.shop.repositories.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CostumerService costumerService;

    @Transactional
    public ResponseEntity<SaleDAO> createSale(SaleDTO dto) {

        var sale = new Sale(dto);

        dto.productsId().forEach(productId -> productService.removeQuantity(productId));
        sellerService.addSale(dto.sellerId());
        costumerService.addPurchase(dto.costumerId());

        repository.save(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SaleDAO(sale));
    }

    public Page<SaleDAO> search(Pageable pageable, Long costumerId, Long sellerId) {

        Page<Sale> sales = null;
        List<SaleDAO> pagedSales;

        if (Objects.isNull(costumerId) && Objects.isNull(sellerId)) sales = repository.findAll(pageable);
        if (!Objects.isNull(costumerId) && Objects.isNull(sellerId)) sales = repository.findByCostumerId(pageable, costumerId);
        if (Objects.isNull(costumerId) && !Objects.isNull(sellerId)) sales = repository.findBySellerId(pageable, sellerId);
        if (!Objects.isNull(costumerId) && !Objects.isNull(sellerId)) sales = repository.findByCostumerIdAndSellerId(pageable, costumerId, sellerId);

        pagedSales = sales.getContent().stream().map(SaleDAO::new).collect(Collectors.toList());
        return new PageImpl<>(pagedSales, pageable, sales.getTotalElements());
    }

    public ResponseEntity<Object> getSaleById(Long id) {

        if (repository.existsById(id)) {

            var sale = repository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new SaleDAO(sale));
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada!");
        }

    }

    @Transactional
    public ResponseEntity<List<SaleDAO>> createMultipleSales(List<SaleDTO> dtos) {

        var sales = dtos.stream().map(Sale::new).collect(Collectors.toList());
        repository.saveAll(sales);
        var saleList = sales.stream().map(SaleDAO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(saleList);
    }
}
