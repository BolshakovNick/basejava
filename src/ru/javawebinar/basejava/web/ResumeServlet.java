package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        final Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(type, new SimpleTextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list =Arrays.asList(value.split("\n"));
                        for (String line : value.split("\n")) {
                            r.addSection(type, new MarkingListSection(list));
                        }
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        
                }
            } else {
                r.getContacts().remove(type);
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                r = Resume.EMPTY_RESUME;
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    /*private static void printResume(Resume resume, Writer writer) throws IOException {
        writer.write("<html>\n" +
                " <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                "  <title>Resume Table</title>\n" +
                " </head>\n" +
                " <body>\n" +
                "  <table border=\"1\" cellpadding=\"10\">\n" +
                "   <tr>\n" +
                "    <th>UUID</th>\n" +
                "    <th>FULL_NAME</th>\n" +
                "    <th>CONTACTS</th>\n" +
                "    <th>CONTENT</th>\n" +
                "   </tr>\n" +
                "   <tr>\n" +
                "    <td>" + resume.getUuid() + "</td>\n" +
                "    <td>" + resume.getFullName() + "</td>\n");
        String contacts = "";
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            contacts = String.join("", contacts, entry.getKey().name(), ": ", entry.getValue(), "<br>");
        }
        writer.write("<td>" + contacts + "</td>\n");

        String content = "";
        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            SectionType type = entry.getKey();
            AbstractSection section = entry.getValue();
            content = String.join("", content, entry.getKey().name(), "<br>");
            if (type == SectionType.PERSONAL || type == SectionType.OBJECTIVE) {
                content = String.join("", content, ((SimpleTextSection) section).getText(), "<br>");
            } else if (type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS) {
                List<String> lines = ((MarkingListSection) section).getMarkingLines();
                for (String s : lines) {
                    content = String.join("", content, " - ", s, "<br>");
                }
            }
            content = String.join("", content, "<br>");
        }
        writer.write("<td>" + content + "</td>\n" +
                "  </tr>\n" +
                " </table>\n" +
                " </body>\n" +
                "</html>");
    }*/
}