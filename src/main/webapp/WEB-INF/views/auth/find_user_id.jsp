<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<!-- 페이지 내용 -->
<div class="row">
    <div class="col-12">
        <!-- 아이디 찾기 -->
        <form id="findUserIdForm" action="/auth/find-user-id" method="POST">
            <div class="card mb-3">
                <div class="card-header">
                    <h5 class="card-title">아이디 찾기</h5>
                </div>
                <div class="card-body">                
                    <div class="mb-3">
                        <label for="username" class="form-label">이름</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="이름" required>
                    </div>                
                    <div class="mb-3">
                        <label for="tel" class="form-label">연락처</label>
                        <input type="text" class="form-control" id="tel" name="tel" placeholder="연락처" required>
                    </div>                
                    <div class="mb-3">
                        <label for="email" class="form-label">이메일</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="이메일" required>
                    </div>
                </div>
            </div>
            <div class="mt-3">
                <button type="submit" class="btn btn-primary">아이디 찾기</button>
                <a href="/auth/login" class="btn btn-outline-secondary">취소</a>
            </div>
        </form>
        <!--// 아이디 찾기 -->
    </div>
</div>
<!--// 페이지 내용 -->

<%@ include file="../base/script.jsp" %>

<!-- script -->
<script>
    $(document).ready(function() {        
        // 아이디 찾기 폼 검증
        $('#findUserIdForm').validate({
            rules: {
                username: {
                    required: true,
                },
                tel: {
                    required: true,
                },
                email: {
                    required: true,
                },
            },
            messages: {
                username: {
                    required: '이름을 입력하세요.',
                },
                tel: {
                    required: '연락처를 입력하세요.',
                },
                email: {
                    required: '이메일을 입력하세요.',
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
