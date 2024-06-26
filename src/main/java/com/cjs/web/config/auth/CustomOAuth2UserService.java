package com.cjs.web.config.auth;

import com.cjs.web.dto.OAuthAttributes;
import com.cjs.web.dto.SessionUser;
import com.cjs.web.entity.User;
import com.cjs.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

import org.springframework.security.core.authority.
        SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.
        DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.
        OAuth2UserRequest;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService
        <OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate
                = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        // 어떤 서비스에서 로그인하는지 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 시 키가 되는 필드값
        String userNameAttributeName = userRequest.getClientRegistration().
                getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 Attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,
                oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user",new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new
                SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),
                        attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}