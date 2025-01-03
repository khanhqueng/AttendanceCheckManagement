package com.security.Jwt_service.dto.request.survey;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyRequestDto {
    private Long sessionId;
    @Max(value = 5, message = "Understanding Rate must not exceed 5")
    private BigDecimal underStandingRate;
    @Max(value = 100, message = "Efficient Rate must not exceed 100")
    private BigDecimal efficientRate;
}
