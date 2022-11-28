package com.unistar.orders.repository;

import com.unistar.orders.entity.OrderEntity;
import com.unistar.orders.model.OrderType;
import com.unistar.orders.shared.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderRepositoryIntegrationTest {
    // Integration Test to load Json file into DB when table items is empty

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    Utils utils;

    @Test
    void loadJsonToDB(){
        long count = orderRepository.count();
        if(count > 0) {
            System.out.println("### loadJsonToDB() : database is already not empty! count = " + count);
            return;
        }

        Path dataPath = Paths.get("src","test", "data", "data.json");
        try {
            String jsonString = new String(Files.readAllBytes(dataPath));
            JSONObject root = new JSONObject(jsonString);
            JSONArray items = root.getJSONArray("items");
            for(int i=0; i<items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderNumber(Integer.parseInt(item.getString("order-number")));
                orderEntity.setOrderType(OrderType.from(item.getString("order-type")));

                orderEntity.setDueDate(utils.parseDate(item.getString("due-date")));
                orderEntity.setCustomerName(item.getString("customer-name"));

                OrderEntity saved = orderRepository.save(orderEntity);
                System.out.println("OrderNumber : " + orderEntity.getOrderNumber() + " - saved: " + saved.getOrderNumber());
            }

            count = orderRepository.count();
            System.out.println("### Load file success ! count = " + count);
            Assertions.assertTrue(count > 0);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}