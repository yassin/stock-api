package com.management.stock.stockapi.controllers;

import javax.validation.Valid;

import com.management.stock.stockapi.model.Stock;
import com.management.stock.stockapi.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class StockController {

    @Autowired
    StockRepository stockRepository;

    @GetMapping("/stock")
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @PostMapping("/stock")
    public Stock createStock(@Valid @RequestBody Stock stock) {
        return stockRepository.save(stock);
    }

    @GetMapping(value="/stock/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable("id") String id) {
        return stockRepository.findById(id)
                .map(stock -> ResponseEntity.ok().body(stock))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value="/stock/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable("id") String id,
                                           @Valid @RequestBody Stock stock) {
        return stockRepository.findById(id)
                .map(todoData -> {
                    todoData.setTitle(stock.getTitle());
                    Stock updatedStock = stockRepository.save(todoData);
                    return ResponseEntity.ok().body(updatedStock);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value="/stock/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable("id") String id) {
        return stockRepository.findById(id)
                .map(todo -> {
                    stockRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}