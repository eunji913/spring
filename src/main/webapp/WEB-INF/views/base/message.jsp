<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty errorMessage}">
    <div class="row">
        <div class="<c:choose>
            <c:when test='${not empty message_class}'>${message_class}</c:when>
            <c:otherwise>col-12</c:otherwise>
        </c:choose>">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${not empty successMessage}">
    <div class="row">
        <div class="<c:choose>
            <c:when test='${not empty message_class}'>${message_class}</c:when>
            <c:otherwise>col-12</c:otherwise>
        </c:choose>">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </div>
    </div>
</c:if>

<!--// 성공 메시지 -->
