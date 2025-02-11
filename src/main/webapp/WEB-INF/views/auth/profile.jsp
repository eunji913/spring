<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<!-- 페이지 내용 -->
<div class="row">
    <div class="col-12">
        <!-- 프로필 -->
        <div class="card mb-3">
            <div class="card-header">
                <h5 class="card-title">프로필</h5>
            </div>
            <div class="card-body">
                <p class="card-text">
                    <div class="d-flex justify-content-between">
                        <span class="text-muted">아이디</span>
                        ${user.userId}
                    </div>
                </p>
                <hr>
                <p class="card-text">
                    <div class="d-flex justify-content-between">
                        <span class="text-muted">이름</span>
                        ${user.username}
                    </div>
                </p>
                <hr>
                <p class="card-text">
                    <div class="d-flex justify-content-between">
                        <span class="text-muted">연락처</span>
                        ${user.tel}
                    </div>
                </p>
                <hr>
                <p class="card-text">
                    <div class="d-flex justify-content-between">
                        <span class="text-muted">이메일</span>
                        ${user.email}
                    </div>
                </p>
            </div>
        </div>
        <div class="text-center">
            <a href="/auth/update-profile" class="btn btn-success mx-2">프로필 수정</a>
            <a href="/auth/update-password" class="btn btn-warning mx-2">비밀번호 변경</a>
            <a href="/auth/delete-account" class="btn btn-danger mx-2">회원탈퇴</a>
        </div>
        <!--// 프로필 -->
    </div>
</div>
<!--// 페이지 내용 -->

<%@ include file="../base/script.jsp" %>
<%@ include file="../base/bottom.jsp" %>
