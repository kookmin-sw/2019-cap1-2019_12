//package com.capstone.server.handler;
//
//import com.capstone.server.config.SessionConstants;
//import com.capstone.server.user.GoogleUser;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Component
//public class GoogleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    private HttpSession httpSession;
//    private ObjectMapper objectMapper;
//
//    public GoogleAuthenticationSuccessHandler(HttpSession httpSession, ObjectMapper objectMapper) {
//        this.httpSession = httpSession;
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        httpSession.setAttribute(SessionConstants.LOGIN_USER, getGoogleUser(authentication)); // 간단한 구글계정 정보를 세션에 저장
//        response.sendRedirect("/me");
//    }
//
//    private GoogleUser getGoogleUser(Authentication authentication) { // OAuth 인증정보를 통해 GoogleUser 인스턴스 생성
//        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
//        return objectMapper.convertValue(oAuth2Authentication.getUserAuthentication().getDetails(), GoogleUser.class);
//    }
//}
