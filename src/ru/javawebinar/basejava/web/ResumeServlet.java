package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.get().getSqlStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
//      response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//      String name = request.getParameter("name");
//      response.getWriter().write(name == null ? "Hello Resumes!" : "Hello, " + name + '!');
        String uuid = request.getParameter("uuid");
        Writer writer = response.getWriter();
        if (uuid != null) {
            Resume resume = storage.get(uuid);
            printResume(resume, writer);
        } else {
            for (Resume resume : storage.getAllSorted()) {
                printResume(resume, writer);
            }
        }
    }

    private static void printResume(Resume resume, Writer writer) throws IOException {
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
    }
}