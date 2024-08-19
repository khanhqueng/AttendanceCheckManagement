package com.security.Jwt_service.dto.mapper;

import com.security.Jwt_service.dto.request.MessageRequest;
import com.security.Jwt_service.dto.request.UserCreateDto;
import com.security.Jwt_service.dto.response.MessageResponse;
import com.security.Jwt_service.dto.response.UserResponseDto;
import com.security.Jwt_service.entity.chat.Message;
import com.security.Jwt_service.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class MessageMapper {
    @Autowired
    private ModelMapper mapper;
    public Message mapToEntity(MessageRequest request){
        Message message= mapper.map(request, Message.class);
        return message;
    }
    public MessageResponse mapToResponseDto(Message message){
        MessageResponse response= mapper.map(message, MessageResponse.class);
        return response;
    }
}
