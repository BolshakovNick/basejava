create table if not exists resume
(
    uuid      char(36) not null
        constraint resume_pkey
            primary key,
    full_name text     NOT NULL
);

create table if not exists contact
(
    id          serial   not null
        constraint contact_pk
            primary key,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    type        text     not null,
    value       text     not null
);
create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

create table if not exists section
(
    id serial not null
        constraint section_pk
            primary key,
    resume_uuid char(36) not null
        constraint section_resume_uuid_fk
            references resume on delete cascade,
    type text not null,
    content text not null
);

alter table section owner to postgres;

create unique index if not exists section_id_uindex
    on section (id);