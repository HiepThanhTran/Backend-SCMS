<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<aside class="sidebar">
    <div class="sidebar-logo">F&H Logistic</div>
   
    <ul class="sidebar-nav">
        <c:forEach var="entity" items="${entities}">
            <li class="sidebar-item">
                <a href="#" class="sidebar-link">${entity}</a>
            </li>
        </c:forEach>
    </ul>
</aside>