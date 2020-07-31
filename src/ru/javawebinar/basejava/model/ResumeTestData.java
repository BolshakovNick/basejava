package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.exception.ResumeContentException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) throws ResumeContentException, MalformedURLException {
        Resume resume = new Resume("uuid1", "Григорий Кислин");
        Map<ContactType, String> contacts = resume.getContacts();
        Map<SectionType, Content> content = resume.getContent();

        contacts.put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKED_IN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACK_OVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.HOME_PAGE, "http://gkislin.ru/");

        content.put(SectionType.OBJECTIVE, new TextContent("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        content.put(SectionType.PERSONAL, new TextContent("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        content.put(SectionType.ACHIEVEMENT, new MarkingTextContent());
        content.put(SectionType.QUALIFICATIONS, new MarkingTextContent());
        content.put(SectionType.EXPERIENCE, new LinkContent());
        content.put(SectionType.EDUCATION, new LinkContent());

        MarkingTextContent achievements = (MarkingTextContent) content.get(SectionType.ACHIEVEMENT);

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

        MarkingTextContent qualifications = (MarkingTextContent) content.get(SectionType.QUALIFICATIONS);

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

        LinkContent exp = (LinkContent) content.get(SectionType.EXPERIENCE);

        exp.add(new URL("http://javaops.ru/"), "10/2013 - Сейчас    Автор проекта.\n" +
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        exp.add(new URL("https://www.wrike.com/"), "10/2014 - 01/2016\tСтарший разработчик (backend)\n" +
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring,\n" +
                "MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2,\n" +
                "JWT SSO.");
        exp.add(new URL("http://www.luxoft.ru/"), "12/2010 - 04/2012\tВедущий программист" +
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper,\n" +
                "Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для\n" +
                "администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring,\n" +
                "Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        exp.add(new URL("https://www.yota.ru/"), "06/2008 - 12/2010\tВедущий специалист\n" +
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J,\n" +
                "EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и\n" +
                "мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");

        LinkContent edu = (LinkContent) content.get(SectionType.EDUCATION);
        edu.add(new URL("https://www.coursera.org/course/progfun"), "03/2013 - 05/2013\t\"Functional Programming Principles in Scala\" by Martin Odersky");
        edu.add(new URL("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"), "Luxoft\n" +
                "03/2011 - 04/2011\tК.урс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        edu.add(new URL("http://www.siemens.ru/"), "01/2005 - 04/2005\t3 месяца обучения мобильным IN сетям (Берлин)");
        edu.add(new URL("http://www.alcatel.ru/"), "09/1997 - 03/1998\t6 месяцев обучения цифровым телефонным сетям (Москва)");
        edu.add(new URL("http://www.ifmo.ru/"), "09/1993 - 07/1996\tАспирантура (программист С, С++)\n" +
                "09/1987 - 07/1993\tИнженер (программист Fortran, C)");
        edu.add(new URL("http://www.school.mipt.ru/"), "09/1984 - 06/1987\tЗакончил с отличием");

        System.out.println(resume.getFullName());
        System.out.println();
        System.out.println(

        );
        for (Map.Entry<ContactType, String> pair : contacts.entrySet()) {
            System.out.println(pair.getKey().getTitle() + ": " + pair.getValue());
        }
        System.out.println();
        System.out.println();

        for (Map.Entry<SectionType, Content> pair : content.entrySet()) {
            System.out.println(pair.getKey().getTitle() + '\n' + pair.getValue().toString());
        }
    }
}
