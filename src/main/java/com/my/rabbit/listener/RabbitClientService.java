package com.my.rabbit.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author jim lin
 *         2018/8/12.
 */
@Service
@Slf4j
@SuppressWarnings("unused")
public class RabbitClientService {

    @RabbitListener(queues = {"queue_fanout_B"})
    public void fanoutReceive1(String data){
        log.info("================fanoutReceive1================:{}",data);
    }

    @RabbitListener(queues = {"queue_fanout_A"})
    public void fanoutReceive2(Message message, Channel channel){
        log.info("================fanoutReceive2================:{}",new String(message.getBody()));
    }


    @RabbitListener(queues = {"queue_direct_A"},priority = "1")
    public void directReceive(String data){
        log.info("================directReceive================:{}",data);
    }

    /**
     * 优先级越高越先处理
     * @param data 数据
     */
    @RabbitListener(queues = {"queue_direct_A"},priority = "1")
    public void directReceive2(String data){
        log.info("================directReceive2================:{}",data);
    }


    @RabbitListener(queues = {"queue_topic"})
    public void topicReceive(String data){
        log.info("================topicReceive================:{}",data);
        if ("directSendMessage hello rabbit====2".equals(data)){
            throw new NullPointerException("");
        }
    }

    @RabbitListener(queues = {"queue_headers"})
    public void queueReceive(Message message){
        log.info("================queueReceive================:{}",new String(message.getBody()));
    }
}
