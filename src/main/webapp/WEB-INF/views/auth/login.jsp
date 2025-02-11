<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<!-- 페이지 내용 -->
<div class="row">
    <div class="col-12">
        <!-- 로그인 -->
        <form id="loginForm" action="/auth/login" method="POST">
            <div class="card mb-3">
                <div class="card-header">
                    <h5 class="card-title">로그인</h5>
                </div>
                <div class="card-body">                
                    <div class="mb-3">
                        <label for="userId" class="form-label">아이디</label>
                        <input type="text" class="form-control" id="userId" name="userId" placeholder="아이디" required>
                    </div>    
                    <div class="mb-3">
                        <label for="password" class="form-label">비밀번호</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호" required>
                    </div>    
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">로그인</button>
                    </div>
                </div>
            </div>
            <div class="mt-3">
                <a href="/auth/register" class="btn btn-outline-secondary">회원가입</a>
                <a href="/auth/find-user-id" class="btn btn-outline-secondary">아이디 찾기</a>
                <a href="/auth/reset-password" class="btn btn-outline-secondary">비밀번호 찾기</a>
            </div>
        </form>
        <!--// 로그인 -->
    </div>
</div>
<!--// 페이지 내용 -->

<%@ include file="../base/script.jsp" %>

<!-- script -->
<script>
    $(document).ready(function() {        
        // 로그인 폼 검증
        $('#loginForm').validate({
            rules: {
                userId: {
                    required: true,
                },
                password: {
                    required: true,
                },
            },
            messages: {
                userId: {
                    required: '아이디를 입력하세요.',
                },
                password: {
                    required: '비밀번호를 입력하세요.',
                },
            },
            errorClass: 'is-invalid',
            validClass: 'is-valid',
            errorPlacement: function(error, element) {
                error.addClass('invalid-feedback');
                element.closest('.mb-3').append(error);
            },
            submitHandler: function(form) {
                form.submit();
            }
        });
    });
</script>
<!--// script -->

<%@ include file="../base/bottom.jsp" %>
