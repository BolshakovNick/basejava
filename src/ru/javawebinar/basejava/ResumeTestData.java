package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.Month;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Григорий Кислин");
        Map<ContactType, String> contacts = resume.getContacts();
        Map<SectionType, AbstractSection> content = resume.getSections();

        contacts.put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKED_IN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACK_OVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.HOME_PAGE, "http://gkislin.ru/");

        content.put(SectionType.OBJECTIVE, new SimpleTextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        content.put(SectionType.PERSONAL, new SimpleTextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        content.put(SectionType.ACHIEVEMENT, new MarkingListSection());
        content.put(SectionType.QUALIFICATIONS, new MarkingListSection());
        content.put(SectionType.EXPERIENCE, new OrganizationSection(new ArrayList<>()));
        content.put(SectionType.EDUCATION, new OrganizationSection(new ArrayList<>()));

        List<String> achievements = ((MarkingListSection) content.get(SectionType.ACHIEVEMENT)).getMarkingLines();

        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven.\n" +
                "Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\".\n" +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio,\n" +
                "DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM,\n" +
                "CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO\n" +
                "аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT\n" +
                "(GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура,\n" +
                "JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios.\n" +
                "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay,\n" +
                "Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        List<String> qualifications = ((MarkingListSection) content.get(SectionType.QUALIFICATIONS)).getMarkingLines();

        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA\n" +
                "(Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit,\n" +
                "Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX,\n" +
                "JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita,\n" +
                "pgBouncer.");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML,\n" +
                "функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");

        List<Organization> exp = ((OrganizationSection) content.get(SectionType.EXPERIENCE)).getOrganizations();

        exp.add(new Organization(new Link("JavaOps", "http://javaops.ru/"), Arrays.asList(new Organization.Position(DateUtil.of(2013, Month.OCTOBER), DateUtil.NOW, "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."))));

        exp.add(new Organization(new Link("Wrike", "https://www.wrike.com/"), Arrays.asList(new Organization.Position(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring,\n" +
                        "MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2,\n" +
                        "JWT SSO."))));

        exp.add(new Organization(new Link("Luxoft", "http://www.luxoft.ru/"), Arrays.asList(new Organization.Position(DateUtil.of(2010, Month.DECEMBER), DateUtil.of(2012, Month.APRIL), "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper,\n" +
                        "Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для\n" +
                        "администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring,\n" +
                        "Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."))));

        exp.add(new Organization(new Link("Yota", "https://www.yota.ru/"), Arrays.asList(new Organization.Position(DateUtil.of(2008, Month.JUNE), DateUtil.of(2010, Month.DECEMBER), "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J,\n" +
                        "EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и\n" +
                        "мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"))));

        List<Organization> edu = ((OrganizationSection) content.get(SectionType.EDUCATION)).getOrganizations();

        edu.add(new Organization(new Link("Coursera", "https://www.coursera.org/course/progfun"), Arrays.asList(new Organization.Position(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY), "\"Functional Programming Principles in Scala\" by Martin Odersky", null))));

        edu.add(new Organization(new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                Arrays.asList(new Organization.Position(DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null))));

        edu.add(new Organization(new Link("Siemens", "http://www.siemens.ru/"), Arrays.asList(new Organization.Position(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.APRIL), "3 месяца обучения мобильным IN сетям (Берлин)", null))));

        edu.add(new Organization(new Link("Alcatel", "http://www.alcatel.ru/"), Arrays.asList(new Organization.Position(DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(1998, Month.MARCH), "6 месяцев обучения цифровым телефонным сетям (Москва)", null))));

        Link link = new Link("Ifmo", "http://www.ifmo.ru/");
        Organization.Position pos1 = new Organization.Position(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY), "Аспирантура (программист С, С++)", null);
        Organization.Position pos2 = new Organization.Position(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY), "Инженер (программист Fortran, C)", null);
        Organization org = new Organization(link, Arrays.asList(pos1, pos2));
        edu.add(org);

        edu.add(new Organization(new Link("School-mipt", "http://www.school.mipt.ru/"), Arrays.asList(new Organization.Position(DateUtil.of(1984, Month.SEPTEMBER), DateUtil.of(1987, Month.JUNE), "Закончил с отличием", null))));

        System.out.println(resume.getFullName());
        System.out.println();
        System.out.println();

        for (Map.Entry<ContactType, String> pair : contacts.entrySet()) {
            System.out.println(pair.getKey().getTitle() + ": " + pair.getValue());
        }
        System.out.println();
        System.out.println();

        for (Map.Entry<SectionType, AbstractSection> pair : content.entrySet()) {
            System.out.println(pair.getKey().getTitle() + '\n' + pair.getValue().toString() + '\n');
        }
    }

    public static Resume nullPointsTestResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        /*Map<ContactType, String> contacts = resume.getContacts();
        Map<SectionType, AbstractSection> content = resume.getSections();

        contacts.put(ContactType.PHONE_NUMBER, "+7(987) 654-3210");
        contacts.put(ContactType.SKYPE, "example.skype");
        contacts.put(ContactType.MAIL, "example@yandex.ru");
        contacts.put(ContactType.LINKED_IN, "https://www.linkedin.com/in/example");
        contacts.put(ContactType.GITHUB, "https://github.com/example");
        contacts.put(ContactType.STACK_OVERFLOW, "https://stackoverflow.com/users/11111");
        contacts.put(ContactType.HOME_PAGE, "http://example.ru/");

        content.put(SectionType.OBJECTIVE, new SimpleTextSection("test objective"));
        content.put(SectionType.PERSONAL, new SimpleTextSection("test personal"));
        content.put(SectionType.ACHIEVEMENT, new MarkingListSection());
        content.put(SectionType.QUALIFICATIONS, new MarkingListSection());
        content.put(SectionType.EXPERIENCE, new OrganizationSection(new ArrayList<>()));
        content.put(SectionType.EDUCATION, new OrganizationSection(new ArrayList<>()));

        List<String> achievements = ((MarkingListSection) content.get(SectionType.ACHIEVEMENT)).getMarkingLines();
        achievements.add("achievement 1");
        achievements.add("achievement 2");
        achievements.add("achievement 3");

        List<String> qualifications = ((MarkingListSection) content.get(SectionType.QUALIFICATIONS)).getMarkingLines();
        qualifications.add("qualification 1");
        qualifications.add("qualification 2");
        qualifications.add("qualification 3");

        List<Organization> exp = ((OrganizationSection) content.get(SectionType.EXPERIENCE)).getOrganizations();
        exp.add(new Organization(new Link("EXP-Organization 1", null), Collections.singletonList(new Organization.Position(DateUtil.of(2000, Month.JANUARY), DateUtil.of(2020, Month.JANUARY), "EXP-Title1",
                null))));
        exp.add(new Organization(new Link("EXP-Organization 2", null), Collections.singletonList(new Organization.Position(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.JANUARY), "EXP-Title2",
                null))));

        List<Organization> edu = ((OrganizationSection) content.get(SectionType.EDUCATION)).getOrganizations();
        edu.add(new Organization(new Link("EDU-Organization 1", null), Collections.singletonList(new Organization.Position(DateUtil.of(2000, Month.JANUARY), DateUtil.of(2020, Month.JANUARY), "EDU-Title1",
                null))));
        edu.add(new Organization(new Link("EDU-Organization 2", null), Collections.singletonList(new Organization.Position(DateUtil.of(2000, Month.JANUARY), DateUtil.of(2020, Month.JANUARY), "EDU-Title2",
                null))));
*/
        return resume;
    }

    public static Resume testResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

    /*    Map<ContactType, String> contacts = resume.getContacts();
        Map<SectionType, AbstractSection> content = resume.getSections();

        contacts.put(ContactType.PHONE_NUMBER, "+7(987) 654-3210");
        contacts.put(ContactType.SKYPE, "example.skype");
        contacts.put(ContactType.MAIL, "example@yandex.ru");
        contacts.put(ContactType.LINKED_IN, "https://www.linkedin.com/in/example");
        contacts.put(ContactType.GITHUB, "https://github.com/example");
        contacts.put(ContactType.STACK_OVERFLOW, "https://stackoverflow.com/users/11111");
        contacts.put(ContactType.HOME_PAGE, "http://example.ru/");

        content.put(SectionType.OBJECTIVE, new SimpleTextSection("test objective"));
        content.put(SectionType.PERSONAL, new SimpleTextSection("test personal"));
        content.put(SectionType.ACHIEVEMENT, new MarkingListSection());
        content.put(SectionType.QUALIFICATIONS, new MarkingListSection());
        content.put(SectionType.EXPERIENCE, new OrganizationSection(new ArrayList<>()));
        content.put(SectionType.EDUCATION, new OrganizationSection(new ArrayList<>()));

        List<String> achievements = ((MarkingListSection) content.get(SectionType.ACHIEVEMENT)).getMarkingLines();
        achievements.add("achievement 1");
        achievements.add("achievement 2");
        achievements.add("achievement 3");

        List<String> qualifications = ((MarkingListSection) content.get(SectionType.QUALIFICATIONS)).getMarkingLines();
        qualifications.add("qualification 1");
        qualifications.add("qualification 2");
        qualifications.add("qualification 3");

        List<Organization> exp = ((OrganizationSection) content.get(SectionType.EXPERIENCE)).getOrganizations();
        exp.add(new Organization(new Link("EXP-Organization 1", "http://organization1.ru/"), Collections.singletonList(new Organization.Position(DateUtil.of(2000, Month.JANUARY), DateUtil.of(2020, Month.JANUARY), "EXP-Title1",
                "EXP-Description1"))));
        exp.add(new Organization(new Link("EXP-Organization 2", "http://organization2.ru/"), Collections.singletonList(new Organization.Position(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.JANUARY), "EXP-Title2",
                "EXP-Description2"))));

        List<Organization> edu = ((OrganizationSection) content.get(SectionType.EDUCATION)).getOrganizations();
        edu.add(new Organization(new Link("EDU-Organization 1", "http://edu-organization1.ru/"), Collections.singletonList(new Organization.Position(DateUtil.of(2000, Month.JANUARY), DateUtil.of(2020, Month.JANUARY), "EDU-Title1",
                "EDU-Description1"))));
        edu.add(new Organization(new Link("EDU-Organization 2", "http://edu-organization2.ru/"), Collections.singletonList(new Organization.Position(DateUtil.of(2000, Month.JANUARY), DateUtil.of(2020, Month.JANUARY), "EDU-Title2",
                "EDU-Description2"))));*/

        return resume;
    }
}
