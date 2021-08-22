package com.yim.asocream.security.auth;



import com.yim.asocream.user.model.entity.UserEntity;
import com.yim.asocream.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {


    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException{

        UserEntity user = service.findUserEmailAndUserEntity(userEmail);
        return new PrincipalDetails(user);
    }
}
