package com.security.Jwt_service.mapper.session;

import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseDto;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.mapper.GenericMapper;
import com.security.Jwt_service.util.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {MapperUtils.class})
public interface SessionMapper extends GenericMapper<Session, SessionCreateDto, SessionResponseDto> {
    @Override
    @Mapping(target = "classroom", source = "request.classRoomId")
    Session requestToEntity(SessionCreateDto request);
}
