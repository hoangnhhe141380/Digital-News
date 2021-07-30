<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./public/css/style.css" />
        <link rel="stylesheet" type="text/css" href="./public/css/search-page.css" />
        <title>Search</title>
    </head>
    <body>
        <div>
            <div class="wrap-all">
                <%@include file="/components/header.jsp" %>
                <!--Content-->
                <div class="wrap-content">
                    <div class="container">
                        <div class="left">
                            <p id="error">${requestScope.error}</p>
                            <p id="message">${requestScope.message}</p>
                            <c:forEach var="article" items="${listSearch}">
                                <div>
                                    <p class="title">
                                        <a href="viewarticle?id=${article.id}">${article.title}</a>
                                    </p>
                                    <img
                                        src="${article.image}"
                                        class="image-article"/>
                                    <p>${article.description}</p>
                                    <p class="author">${mostRecentArticle.author} | ${mostRecentArticle.getDateFormat()}</p>
                                </div>
                            </c:forEach>
                            <div class="paginagtion">
                                <c:if test="${numberPage != 1}">
                                    <c:forEach var="page" begin="1" end="${numberPage}">

                                        <c:if test="${pageCurrent == page}">
                                            <p class="page-current">${page}</p>
                                        </c:if>

                                        <c:if test="${pageCurrent != page}">
                                            <a href="Search?page=${page}" class="page-other">${page}</a>
                                        </c:if>

                                    </c:forEach>
                                </c:if>

                            </div>
                        </div>
                        <%@include file="/components/right.jsp" %>
                    </div>
                </div>
                <!--Footer-->
                <footer class="footer">
                </footer>
            </div>
        </div>
    </body>
</html>
