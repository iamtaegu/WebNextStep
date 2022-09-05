<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<c:forEach items="${users}" var="user" varStatus="status">
    <tr>
        <th scope="row">${status.count}</th>
        <td>${user.userId}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td><a href="#" class="btn btn-sucess" role="button">수정</a>
        </td>
    <tr/>
</c:forEach>

</body>
</html>