package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE_NUMBER("тел."),
    SKYPE("Skype"),
    MAIL("Почта"),
    LINKED_IN("профиль LinkedIn"),
    GITHUB("профиль GitHub"),
    STACK_OVERFLOW("профиль StackOverflow"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
