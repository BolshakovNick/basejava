<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.Resume" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.storage.SqlStorage" %>
<%@ page import="ru.javawebinar.basejava.storage.Storage" %>
<%@ page import="java.util.Map" %>
<%@ page import="ru.javawebinar.basejava.model.AbstractSection" %><%--
  Created by IntelliJ IDEA.
  User: Local
  Date: 03.09.2020
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Resume</title>
    <link rel=\"stylesheet\" href=\"css/style.css\">
</head>
<body>
<table border="1">
    <%
        Resume resume = (Resume) request.getAttribute("resume");
        Storage storage = (Storage) request.getAttribute("storage");
    %>
    <tr>
        <th>uuid</th>
        <th>full_name</th>
        <th>contact_type</th>
        <th>contact_value</th>
        <th>section_type</th>
        <th>section_value</th>
    </tr>
    <% if (resume != null) {%>
    <tr>
        <td>
            <%=resume.getUuid()%>
        </td>
        <td>
            <%=resume.getFullName()%>
        </td>
        <%
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
        %>
        <td>
            <%=entry.getKey().name()%>
        </td>
        <td>
            <%=entry.getValue()%>
        </td>
        <%
            }
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
        %>
        <td>
            <%=entry.getKey().name()%>
        </td>
        <td>
            <%=entry.getValue().toString()%>
        </td>
    </tr>
    <%
        }
    } else {
        for (Resume r : storage.getAllSorted()) {
    %>
    <tr>
        <td>
            <%=r.getUuid()%>
        </td>
        <td>
            <%=r.getFullName()%>
        </td>
        <%
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
        %>
        <td>
            <%=entry.getKey().name()%>
        </td>
        <td>
            <%=entry.getValue()%>
        </td>
        <%
            }
            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
        %>
        <td>
            <%=entry.getKey().name()%>
        </td>
        <td>
            <%=entry.getValue().toString()%>
        </td>
        <%
                }
            }%>
    </tr>
    <%
        }
    %>

</table>
</body>
</html>
