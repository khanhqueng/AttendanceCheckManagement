package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.mapper.MessageMapper;
import com.security.Jwt_service.dto.request.MessageRequest;
import com.security.Jwt_service.dto.response.MessageResponse;
import com.security.Jwt_service.entity.chat.Message;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.repository.MessageRepository;
import com.security.Jwt_service.repository.RoomRepository;
import com.security.Jwt_service.repository.UserRepository;
import com.security.Jwt_service.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper mapper;
    @Override
    public MessageResponse saveMessage(MessageRequest request) {
        Message message= mapper.mapToEntity(request);
        userRepository.findById(request.getUserId()).ifPresent(message::setUser);
        roomRepository.findById(request.getRoomId()).ifPresent(message::setRoom);
        Message savedMessage= messageRepository.save(message);
        MessageResponse response=  mapper.mapToResponseDto(savedMessage);
        response.setUser(MessageResponse.UserResponse.builder()
                    .id(savedMessage.getUser().getId())
                    .email(savedMessage.getUser().getEmail())
                    .username(savedMessage.getUser().getUsername())
                .build());
        return response;
    }
}
