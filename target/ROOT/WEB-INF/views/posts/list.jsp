<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<!-- 페이지 내용 -->
<div class="row">
    <div class="col-12">
        <!-- 검색, 등록 버튼 -->
        <div class="d-flex justify-content-between mb-3">
            <!-- 검색 폼 -->
            <form action="/posts" method="GET">
                <div class="input-group">
                    <select name="searchType" class="form-select">
                        <option value="all" ${searchType == 'all' ? 'selected' : ''}>전체</option>
                        <option value="title" ${searchType == 'title' ? 'selected' : ''}>제목</option>
                        <option value="content" ${searchType == 'content' ? 'selected' : ''}>내용</option>
                        <option value="username" ${searchType == 'username' ? 'selected' : ''}>작성자</option>
                    </select>
                    <input type="text" name="searchKeyword" class="form-control" value="${searchKeyword}" placeholder="검색어를 입력하세요">
                    <button type="submit" class="btn btn-primary">검색</button>
                    <c:if test="${searchType != null}">
                        <a href="/posts/" class="btn btn-danger">취소</a>
                    </c:if>
                </div>
            </form>

            <!-- 게시글 등록 버튼 -->
            <a href="/posts/create" class="btn btn-primary">게시글 등록</a>
        </div>
        <!--// 검색, 등록 버튼 -->

        <!-- 게시글 목록 -->
        <table class="table table-striped table-hover table-bordered">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>생성일시</th>
                    <th>수정일시</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${postsVoList}" var="postsVo">
                    <tr>
                        <td>${postsVo.id}</td>
                        <td><a href="/posts/${postsVo.id}/">${postsVo.title}</a></td>
                        <td>${postsVo.username}</td>
                        <td>${postsVo.createdAt.substring(0, 16)}</td>
                       <td>${postsVo.updatedAt.substring(0, 16)}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <!--// 게시글 목록 -->
    </div>
</div>
<div class="row">
    <div class="col-12">
        <!-- 페이지네이션 -->
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <!-- 이전 페이지 -->
                <c:if test="${pagination.page > 1}">
                    <li class="page-item">
                        <a class="page-link" href="/posts/?page=1">처음</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="/posts/?page=${pagination.page - 1}">이전</a>
                    </li>
                </c:if>
                <!--// 이전 페이지 -->

                <!-- 페이지 리스트 -->
                <c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="pageNum">
                    <li class="page-item ${pagination.page == pageNum ? 'active' : ''}">
                        <a class="page-link" href="/posts/?page=${pageNum}">${pageNum}</a>
                    </li>
                </c:forEach>
                <!--// 페이지 리스트 -->

                <!-- 다음 페이지 -->
                <c:if test="${pagination.page < pagination.totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="/posts/?page=${pagination.page + 1}">다음</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="/posts/?page=${pagination.totalPages}">마지막</a>
                    </li>
                </c:if>
                <!--// 다음 페이지 -->
            </ul>
        </nav>
        <!--// 페이지네이션 -->
    </div>
</div>
<!--// 페이지 내용 -->

<%@ include file="../base/script.jsp" %>

<!-- script -->
<script>
    /* 자바스크립트 */
</script>
<!--// script -->

<%@ include file="../base/bottom.jsp" %>