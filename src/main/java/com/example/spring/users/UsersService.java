package com.example.spring.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring.libs.Pagination;

@Service
public class UsersService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 사용자 등록
    public boolean create(UsersVo usersVo) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(usersVo.getPassword());
        usersVo.setPassword(encodedPassword);

        int result = usersDao.create(usersVo);
        return result > 0;
    }

    // 사용자 보기
    public UsersVo read(UsersVo usersVo) {
        return usersDao.read(usersVo);
    }

    // 사용자 수정
    public boolean update(UsersVo usersVo) {
        // 사용자 정보를 업데이트
        int result = usersDao.update(usersVo);
        return result > 0;
    }

    // 비밀번호 수정
    public boolean updatePassword(UsersVo usersVo) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(usersVo.getPassword());
        usersVo.setPassword(encodedPassword);

        int result = usersDao.update(usersVo);
        return result > 0;
    }

    // 사용자 목록
    public Map<String, Object> list(int page, String searchType, String searchKeyword) {
        int pageSize = 10; // 페이지당 사용자 수

        // 전체 사용자 수 조회
        int totalCount = usersDao.getTotalCount(searchType, searchKeyword);

        // 페이지네이션 정보 생성
        Pagination pagination = new Pagination(page, pageSize, totalCount);

        // 페이징된 사용자 목록 조회
        List<UsersVo> userVoList = usersDao.list(pagination.getOffset(), pageSize, searchType, searchKeyword);

        // 결과 맵 생성
        Map<String, Object> result = new HashMap<>();
        result.put("userVoList", userVoList);
        result.put("pagination", pagination);
        result.put("searchType", searchType);
        result.put("searchKeyword", searchKeyword);

        return result;
    }

    // 사용자 삭제
    public boolean delete(UsersVo usersVo, String password) {
        // DB에서 사용자 정보 조회 (usersVo에 userId, username, email 등이 설정되어 있음)
        UsersVo dbUser = usersDao.read(usersVo);
        if (dbUser == null) {
            return false;  // 해당 사용자가 존재하지 않음
        }
        
        // 입력받은 이름과 이메일이 DB 정보와 일치하는지 확인
        if (!dbUser.getUsername().equals(usersVo.getUsername()) ||
            !dbUser.getEmail().equals(usersVo.getEmail())) {
            return false;
        }
        
        // 입력받은 비밀번호가 DB에 저장된 암호화된 비밀번호와 일치하는지 확인
        if (!passwordEncoder.matches(password, dbUser.getPassword())) {
            return false;
        }
        
        // 모든 조건이 만족되면 삭제 실행
        int result = usersDao.delete(dbUser.getUserId());
        return result > 0;
    }
}