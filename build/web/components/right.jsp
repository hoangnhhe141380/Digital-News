<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="right">
            <!--The most recent news-->
            <div>
                <p class="title-page">Digital News</p>
                <p>${mostRecentArticle.description}</p>
            </div>
            <!--Search news by news title-->
            <div>
                <p class="search-title">Search</p>
                <form class="search-form" method="POST" action="Search">
                    <input type="text" class="search-text" name="keyword" value="${requestScope.keyw}">
                    <input type="submit" class="search-submit" value="Go">
                </form>
            </div>
            <!--Top 5 most recent news-->
            <div>
                <p class="article-title">Last Articles</p>
                <ul class="list-recent">
                    <c:forEach var="article" items="${fiveRecentAticle}">
                        <li class="li-recent"><a href="viewarticle?id=${article.id}" class="recent">${article.title}</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </body>
</html>
