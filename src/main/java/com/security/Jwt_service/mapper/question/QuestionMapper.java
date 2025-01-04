package com.security.Jwt_service.mapper.question;

import com.security.Jwt_service.dto.request.question.AddQuestionDto;
import com.security.Jwt_service.dto.response.question.QuestionResponseDto;
import com.security.Jwt_service.entity.session.Question;
import com.security.Jwt_service.mapper.GenericMapper;
import com.security.Jwt_service.util.MapperUtils;
import org.mapstruct.Mapper;

@Mapper(uses = {MapperUtils.class})
public interface QuestionMapper extends GenericMapper<Question, AddQuestionDto, QuestionResponseDto> {

}
