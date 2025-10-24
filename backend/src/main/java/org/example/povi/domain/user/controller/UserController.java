package org.example.povi.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.povi.auth.token.jwt.JwtTokenProvider;
import org.example.povi.domain.user.controller.docs.UserControllerDocs;
import org.example.povi.domain.user.dto.MyPageRes;
import org.example.povi.domain.user.dto.ProfileRes;
import org.example.povi.domain.user.dto.ProfileUpdateReq;
import org.example.povi.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    private String resolveToken(String bearerToken) {
        return bearerToken.replace("Bearer ", "");
    }


    @GetMapping("/myPage")
    public ResponseEntity<?> getMyPage(
            @RequestHeader("Authorization") String bearerToken
    ) {
        String token = resolveToken(bearerToken);
        Long userId = jwtTokenProvider.getUserId(token);
        MyPageRes responseDto = userService.getMyPage(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("Authorization") String bearerToken,
            @RequestPart("dto") @Valid ProfileUpdateReq reqDto,
            @RequestPart(value = "image", required = false)MultipartFile imageFile
            ) {
        String token = resolveToken(bearerToken);
        Long userId = jwtTokenProvider.getUserId(token);
        ProfileRes responseDto = userService.updateProfile(userId, reqDto, imageFile);
        return ResponseEntity.ok(responseDto);
    }

}
