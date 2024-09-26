package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.MessageRequest;
import com.security.Jwt_service.dto.response.MessageResponse;

public interface MessageService {
    MessageResponse saveMessage(MessageRequest request);
}
