<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<!-- 페이지 내용 -->
<div class="row">
    <div class="col-12">
        <!-- 프로필 수정 -->
        <form id="updateProfileForm" action="/auth/update-profile" method="POST">
            <div class="card mb-3">
                <div class="card-header">
                    <h5 class="card-title">프로필 수정</h5>
                </div>
                <div class="card-body">                
                    <div class="mb-3">
                        <label for="userId" class="form-label">아이디</label>
                        <input type="text" class="form-control" id="userId" name="userId" placeholder="아이디" required>
                    </div>     
                    <div class="mb-3">
                        <label for="username" class="form-label">이름</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="이름" required>
                    </div>
                    <div class="mb-3">
                        <label for="tel" class="form-label">전화번호</label>
                        <input type="text" class="form-control" id="tel" name="tel" placeholder="전화번호" required>
                    </div>                
                    <div class="mb-3">
                        <label for="email" class="form-label">이메일</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="이메일" required>
                    </div>
                </div>
            </div>
            <div class="mt-3">
                <button type="submit" class="btn btn-primary">수정하기</button>
                <a href="/auth/profile" class="btn btn-secondary">취소</a>
            </div>
        </form>
        <!--// 프로필 수정 -->
    </div>
</div>
<!--// 페이지 내용 -->

<%@ include file="../base/script.jsp" %>

<!-- script -->
<script>
    $(document).ready(function() {        
        // 프로필 수정 폼 검증
        $('#updateProfileForm').validate({
            rules: {
                userId: {
                    required: true,
                    minlength: 4,
                    maxlength: 20
                },
                username: {
                    required: true,
                    maxlength: 50
                },
                tel: {
                    required: true,
                    maxlength: 20
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
                username: {
                    required: '이름을 입력하세요.',
                    maxlength: '이름은 최대 50자까지 가능합니다.'
                },
                tel: {
                    required: '전화번호를 입력하세요.',
                    maxlength: '전화번호는 최대 20자까지 가능합니다.'
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