<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Calendar</title>
</head>
<body>
    <h1>Calendar Events</h1>
    <ul>
        <c:forEach items="${events}" var="event">
            <li>${event.summary} - ${event.start.dateTime}</li>
        </c:forEach>
    </ul>
</body>
</html>
