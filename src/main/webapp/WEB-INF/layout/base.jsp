<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container container__content">
    <div class="row">
        <div class="col-3">
            <tiles:insertAttribute name="sidebar"/>
        </div>
        
          <div class="col-9">
             <tiles:insertAttribute name="content"/>
        </div>
    </div>
</div>