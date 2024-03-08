package com.academy.consumer.repository;

import com.academy.consumer.entity.MessageEvent;
import org.springframework.data.repository.CrudRepository;

public interface MessageEventRepository extends CrudRepository<MessageEvent, Integer> {
    MessageEvent findFirstByOrderByIdDesc();

}
