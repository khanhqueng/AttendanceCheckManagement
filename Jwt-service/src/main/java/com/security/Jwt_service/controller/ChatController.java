package com.security.Jwt_service.controller;

import com.security.Jwt_service.dto.request.MessageRequest;
import com.security.Jwt_service.dto.response.MessageResponse;
import com.security.Jwt_service.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, @Payload MessageRequest message){
        MessageResponse response= messageService.saveMessage(message);
        simpMessagingTemplate.convertAndSend("/topic/"+ roomId, response);
    }
}
