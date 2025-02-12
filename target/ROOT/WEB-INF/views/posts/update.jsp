<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<!-- 페이지 내용 -->
<div class="row">
    <div class="col-12">
        <!-- 게시글 수정 -->
        <form id="updateForm" action="/posts/${postsVo.id}/update" method="POST" enctype="multipart/form-data">
            <div class="card mb-3">
                <div class="card-header">
                    <span class="text-danger">*</span> 표시는 필수항목입니다.
                </div>
                <div class="card-body">                
                    <div class="mb-3">
                        <label for="title" class="form-label">제목</label>
                        <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력하세요" required value="${postsVo.title}">
                    </div>
                    <div class="mb-3">
                        <label for="content" class="form-label">내용</label>
                        <textarea class="form-control" id="content" name="content" rows="5" placeholder="내용을 입력하세요" required>${postsVo.content}</textarea>
                    </div>
                    <div class="mb-3">
                        <label for="username" class="form-label">작성자</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="작성자를 입력하세요" required value="${postsVo.username}">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">비밀번호</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
                    </div>
                </div>
            </div>
            <div>
                <button type="submit" class="btn btn-primary">수정하기</button>
                <a href="/posts/${postsVo.id}" class="btn btn-secondary">취소</a>
            </div>
        </form>
        <!--// 게시글 수정 -->
    </div>
</div>
<!--// 페이지 내용 -->

<%@ include file="../base/script.jsp" %>

<!-- script -->
<script>
    $(document).ready(function() {
        // TinyMCE 초기화
        tinymce.init({
            selector: '#content',
            language: 'ko_KR',
            // TinyMCE 필수 입력 설정
            setup: function(editor) {
                editor.on('change', function() {
                    editor.save(); // 에디터 내용을 textarea에 반영
                    validateContent(); // 컨텐츠 유효성 검사
                });
            }
        });

        // 컨텐츠 유효성 검사 함수
        function validateContent() {
            var content = tinymce.get('content').getContent();
            var textContent = $('<div>').html(content).text(); // HTML 태그 제거

            if (textContent.length < 2) {
                $('#content').addClass('is-invalid');
                $('#content-error').remove();
                $('#content').closest('.mb-3').append('<div id="content-error" class="invalid-feedback">내용은 최소 2자 이상 입력하세요.</div>');
                return false;
            } else if (textContent.length > 1000) {
                $('#content').addClass('is-invalid');
                $('#content-error').remove();
                $('#content').closest('.mb-3').append('<div id="content-error" class="invalid-feedback">내용은 최대 1000자 이하로 입력하세요.</div>');
                return false;
            } else {
                $('#content').removeClass('is-invalid').addClass('is-valid');
                $('#content-error').remove();
                return true;
            }
        }

        // 게시글 폼 검증
        $('#updateForm').validate({
            rules: {
                title: {
                    required: true,
                    minlength: 2,
                    maxlength: 100
                },
                username: {
                    required: true,
                    minlength: 2,
                    maxlength: 10
                },
                password: {
                    required: true,
                    minlength: 4,
                    maxlength: 20
                }
            },
            messages: {
                title: {
                    required: '제목을 입력하세요.',
                    minlength: '제목은 최소 2자 이상 입력하세요.',
                    maxlength: '제목은 최대 100자 이하로 입력하세요.'
                },
                username: {
                    required: '작성자를 입력하세요.',
                    minlength: '작성자는 최소 2자 이상 입력하세요.',
                    maxlength: '작성자는 최대 10자 이하로 입력하세요.'
                },
                password: {
                    required: '비밀번호를 입력하세요.',
                    minlength: '비밀번호는 최소 4자 이상 입력하세요.',
                    maxlength: '비밀번호는 최대 20자 이하로 입력하세요.'
                }
            },
            errorClass: 'is-invalid',
            validClass: 'is-valid',
            errorPlacement: function(error, element) {
                error.addClass('invalid-feedback');
                element.closest('.mb-3').append(error);
            },
            submitHandler: function(form) {
                // 폼 제출 전 내용 검증
                if (validateContent()) {
                    form.submit();
                }
                return false;
            }
        });
    });
</script>
<!--// script -->

<%@ include file="../base/bottom.jsp" %>