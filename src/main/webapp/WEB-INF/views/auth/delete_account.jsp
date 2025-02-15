<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<!-- 페이지 내용 -->
<div class="row">
    <div class="col-12">
        <!-- 회원탈퇴 -->
        <form id="deleteAccount" action="/auth/delete-account" method="POST">
            <div class="card mb-3">
                <div class="card-header">
                    <h5 class="card-title">회원탈퇴</h5>
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
                    <div class="mb-3">
                        <label for="username" class="form-label">이름</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="이름" required>
                    </div>                
                    <div class="mb-3">
                        <label for="email" class="form-label">이메일</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="이메일" required>
                    </div>
                </div>
            </div>
            <div>
                <button type="submit" class="btn btn-primary">회원탈퇴</button>
                <a href="/auth/profile" class="btn btn-outline-secondary">취소</a>
            </div>
        </form>
        <!--// 회원탈퇴 -->
    </div>
</div>


<script>
    $(document).ready(function() {
        // 회원 탈퇴 검증
        $('#deleteAccount').validate({
            rules: {
                userId: {
                    required: true,
                    minlength: 4,
                    maxlength: 20
                },
                password: {
                    required: true,
                    minlength: 8,
                    maxlength: 20
                },
                username: {
                    required: true,
                    maxlength: 50
                },
                email: {
                    required: true,
                    email: true,
                    maxlength: 50
                }
            },
            messages: {
                userId: {
                    required: '아이디를 입력하세요.',
                    minlength: '아이디는 최소 4자 이상이어야 합니다.',
                    maxlength: '아이디는 최대 20자까지 가능합니다.'
                },
                password: {
                    required: '비밀번호를 입력하세요.',
                    minlength: '비밀번호는 최소 8자 이상이어야 합니다.',
                    maxlength: '비밀번호는 최대 20자까지 가능합니다.'
                },
                username: {
                    required: '이름을 입력하세요.',
                    maxlength: '이름은 최대 10자까지 가능합니다.'
                },
                email: {
                    required: '이메일을 입력하세요.',
                    email: '올바른 이메일 형식이 아닙니다.',
                    maxlength: '이메일은 최대 50자까지 가능합니다.'
                }
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