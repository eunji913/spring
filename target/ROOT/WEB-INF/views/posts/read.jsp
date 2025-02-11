<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<!-- 페이지 내용 -->
<div class="row">
    <div class="col-12">
        <!-- 게시글 보기 -->
        <div class="card mb-3">
            <h5 class="card-header text-primary">
                <strong>${postsVo.title}</strong>
            </h5>
            <div class="card-body">  
                <div class="mb-3 text-muted">
                    글쓴이: ${postsVo.username} | 등록일시: ${postsVo.createdAt.substring(0, 16)} | 수정일시: ${postsVo.createdAt.substring(0, 16)}
                </div>
                <div class="mb-3">
                    첨부파일: <a href="/posts/${postsVo.id}/download" class="btn btn-outline-primary">${postsVo.originalFileName}</a>
                </div>
                <div class="mb-3">
                    ${postsVo.content}
                </div>
            </div>
        </div>
        <div>
            <a href="/posts/" class="btn btn-primary">목록</a>
            <a href="/posts/${postsVo.id}/update" class="btn btn-warning">수정</a>
            <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">삭제</button>
        </div>
        <!--// 게시글 보기 -->
    </div>
</div>
<!--// 페이지 내용 -->

<!-- 삭제 모달 -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="deleteForm" action="/posts/${postsVo.id}/delete" method="POST">
                <div class="modal-header">
                    <h1 class="modal-title fs-5 text-danger" id="deleteModalModalLabel">
                        <strong>게시글 삭제</strong>
                    </h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <p class="text-danger">삭제된 데이터는 복구할 수 없습니다.</p>
                        <p>비밀번호를 입력해주세요.</p>
                    </div>
                    <div>
                        <input type="password" id="password" name="password" placeholder="비밀번호" class="form-control">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-outline-danger">삭제</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 삭제 모달 -->

<%@ include file="../base/script.jsp" %>

<!-- script -->
<script>
    /* 자바스크립트 */
</script>
<!--// script -->

<%@ include file="../base/bottom.jsp" %>