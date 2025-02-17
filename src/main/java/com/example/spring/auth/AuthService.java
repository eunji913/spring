package com.example.spring.auth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring.users.UsersDao;
import com.example.spring.users.UsersVo;

@Service
public class AuthService {

    @Autowired
    UsersDao usersDao;

    private final PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // 로그인
    public UsersVo login(UsersVo usersVo) {
        UsersVo existUsersVo = usersDao.read(usersVo);

        if (existUsersVo != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(usersVo.getPassword(), existUsersVo.getPassword())) { 
                return existUsersVo;
            }
        }

        
        return null;
    }

    // 비밀번호 초기화
    public String resetPassword(UsersVo usersVo) {
        // 랜덤 비밀번호 생성
        String rndPassword = UUID.randomUUID().toString().substring(0, 8);
        String encodedPassword = passwordEncoder.encode(rndPassword);


            // 기존 유저 정보 가져오기 (userId 포함)
        UsersVo existingUser = usersDao.read(usersVo);
        if (existingUser == null) {
            return null; // 존재하지 않는 사용자
        }

        // 비밀번호 업데이트
        existingUser.setPassword(encodedPassword);
        int updated = usersDao.update(existingUser);

        if (updated > 0) {
            return rndPassword;
        } else {
            return null;
        }
    }
}
