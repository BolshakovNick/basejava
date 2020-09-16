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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        String action = request.getParameter("action");
        Resume r;
        boolean isSaving = false;
        if ("edit".equals(action)) {
            r = storage.get(uuid);
        } else {
            isSaving = true;
            r = new Resume("");
            initEmptyResume(r);
        }
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.getContacts().remove(type);
            } else {
                r.addContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value == null || value.trim().length() == 0) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(type, new SimpleTextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = Arrays.stream(value.split("\\n"))
                                .map(s -> "\r".equals(s) || " ".equals(s) || "\n".equals(s) ? s = "" : s)
                                .collect(Collectors.toList());
                        r.addSection(type, new MarkingListSection(list));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        break;
                }
            }
        }
        if (isSaving) {
            storage.save(r);
        } else {
            storage.update(r);
            response.sendRedirect("resume");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
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
                r = new Resume("");
                initEmptyResume(r);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private void initEmptyResume(Resume r) {
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    r.addSection(type, new SimpleTextSection(""));
                    break;
                case QUALIFICATIONS:
                case ACHIEVEMENT:
                    r.addSection(type, new MarkingListSection(new ArrayList<>()));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    r.addSection(type, new OrganizationSection(new ArrayList<>()));
                    break;
            }
        }
    }
}