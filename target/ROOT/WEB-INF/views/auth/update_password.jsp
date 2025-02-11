<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<!-- 페이지 내용 -->
<div class="row">
    <div class="col-12">
        <!-- 비밀번호 수정 -->
        <form id="updatePasswordForm" action="/auth/update-password" method="POST">
            <div class="card mb-3">
                <div class="card-header">
                    <h5 class="card-title">비밀번호 수정</h5>
                </div>
                <div class="card-body">   
                    <div class="mb-3">
                        <label for="password" class="form-label">기존 비밀번호</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="기존 비밀번호" required>
                    </div>         
                    <div class="mb-3">
                        <label for="password1" class="form-label">새 비밀번호</label>
                        <input type="password" class="form-control" id="password1" name="password1" placeholder="새 비밀번호" required>
                    </div>    
                    <div class="mb-3">
                        <label for="password2" class="form-label">비밀번호 확인</label>
                        <input type="password" class="form-control" id="password2" name="password2" placeholder="비밀번호 확인" required>
                    </div>
                </div>
            </div>
            <div class="mt-3">
                <button type="submit" class="btn btn-primary">수정하기</button>
                <a href="/auth/profile" class="btn btn-secondary">취소</a>
            </div>
        </form>
        <!--// 비밀번호 수정 -->
    </div>
</div>
<!--// 페이지 내용 -->

<%@ include file="../base/script.jsp" %>

<!-- script -->
<script>
    $(document).ready(function() {        
        // 비밀번호 수정 폼 검증
        $('#updatePasswordForm').validate({
            rules: {
                password: {
                    required: true,
                },
                password1: {
                    required: true,
                    minlength: 8,
                    maxlength: 20
                },
                password2: {
                    required: true,
                    equalTo: "#password1"
                }
            },
            messages: {
                password: {
                    required: '기존 비밀번호를 입력하세요.',
                },
                password1: {
                    required: '새로운 비밀번호를 입력하세요.',
                    minlength: '새로운 비밀번호는 최소 8자 이상 입력하세요.',
                    maxlength: '새로운 비밀번호는 최대 50자 이하로 입력하세요.'
                },
                password2: {
                    required: '새로운 비밀번호를 다시 입력하세요.',
                    equalTo: '새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.'
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
