<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- -------------------------------------------------------------- -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
pageContext.setAttribute("result", "hello");

%>


<body>
	${requestScope.result}입니다.<br >
	${names[0] }
	${names[1] }<br>
	${notice.title}<br>
	${result}<br>
	${param.n ge 3} <br>
	${empty param.n} <br>
	${param.n?'값이 비어있습니다.':'안비어잇음'} <br>
	${param.n/2} <br>
	${header.accept }
</body>
</html>