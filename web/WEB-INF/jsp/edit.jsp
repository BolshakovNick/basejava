<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.MarkingListSection" %>
<%@ page import="ru.javawebinar.basejava.model.AbstractSection" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
    <link rel='icon' type='image' src="img/pencil.png">
    <script type='text/javascript'>
        function test() {
            alert(document.getElementById('txt').value.replace(/\n+/g, ''));
        };</script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <%--<jsp:useBean id="type" type="ru.javawebinar.basejava.model.SectionType"/>--%>
            <%--<jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>--%>
            <dl>
                <dt>${type.title}</dt>
                <c:choose>
                    <c:when test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
                        <dd><input type="text" name="${type.name()}" size=50
                                   value="${resume.getSection(type)}"></dd>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                        <c:if test="${action == 'add'}">
                            <textarea id="txt" name=type rows="5" cols="50"></textarea>
                        </c:if>
                        <c:if test="${action != 'add'}">
                            <c:if test="${type == 'QUALIFICATIONS'}">
                                <textarea id="txt" name=type
                                          rows="5" cols="50"><%=String.join("\n", ((MarkingListSection) resume.getSection(SectionType.QUALIFICATIONS)).getMarkingLines())%></textarea>
                            </c:if>
                            <c:if test="${type == 'ACHIEVEMENT'}">
                                <textarea id="txt" name=type
                                          rows="5" cols="50"><%=String.join("\n", ((MarkingListSection) resume.getSection(SectionType.ACHIEVEMENT)).getMarkingLines())%></textarea>
                            </c:if>
                        </c:if>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>