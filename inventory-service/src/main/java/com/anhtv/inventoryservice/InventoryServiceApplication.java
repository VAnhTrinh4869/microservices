package com.anhtv.inventoryservice;

import com.anhtv.inventoryservice.model.Inventory;
import com.anhtv.inventoryservice.repository.InventoryRepository;
import com.anhtv.inventoryservice.service.InventoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }


    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository){
        return args -> {
            Inventory i1 = new Inventory();
            i1.setSkuCode("iphone11");
            i1.setQuantity(10);

            Inventory i2 = new Inventory();
            i2.setSkuCode("iphone12");
            i2.setQuantity(10);

            inventoryRepository.save(i1);
            inventoryRepository.save(i2);
        };
    }
}
