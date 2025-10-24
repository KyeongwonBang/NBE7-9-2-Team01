package org.example.povi.auth.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 이메일 인증 요청을 위한 DTO
 * 클라이언트가 입력한 이메일 주소를 포함
 */
@Schema(description = "이메일 인증 요청 DTO")
public record EmailVerificationRequestDto(

        @Schema(description = "이메일 주소", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        String email

) {}