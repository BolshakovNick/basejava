<%@ page import="ru.javawebinar.basejava.model.MarkingListSection" %>
<%@ page import="ru.javawebinar.basejava.model.SimpleTextSection" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
        <h2>
            <%=sectionEntry.getKey().getTitle()%>
        </h2>
        <p>
            <c:set var="type" value="<%=sectionEntry.getKey()%>"/>
            <c:if test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
                <%=((SimpleTextSection)sectionEntry.getValue()).getText()%>
            </c:if>
            <c:if test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                <%=String.join("<br>", ((MarkingListSection)sectionEntry.getValue()).getMarkingLines())%>
            </c:if>
        </p>
    </c:forEach>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>