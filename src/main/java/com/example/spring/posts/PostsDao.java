package com.example.spring.posts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostsDao {

    @Autowired
    private SqlSession session;

    private static final String namespace = "postsMapper";

    // 게시글 등록
    public int create(PostsVo postsVo) {
        return session.insert(namespace + ".create", postsVo);
    }

    // 게시글 목록
    public List<PostsVo> list(int offset, int pageSize, String searchType, String searchKeyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        params.put("searchType", searchType);
        params.put("searchKeyword", searchKeyword);
        return session.selectList(namespace + ".list", params);
    }

    // 게시글 보기
    public PostsVo read(int id) {
        return session.selectOne(namespace + ".read", id);
    }

    // 게시글 수정
    public int update(PostsVo postsVo) {
        return session.update(namespace + ".update", postsVo);
    }

    // 게시글 삭제
    public int delete(int id) {
        return session.delete(namespace + ".delete", id);
    }

    // 전체 게시글 수
    public int getTotalCount(String searchType, String searchKeyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("searchType", searchType);
        params.put("searchKeyword", searchKeyword);
        return session.selectOne(namespace + ".getTotalCount", params);
    }
}
