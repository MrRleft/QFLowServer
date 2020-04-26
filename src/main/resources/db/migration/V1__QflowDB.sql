create table queue
(
    id                  serial not null
        constraint queue_pk
            primary key,
    join_id             varchar(255),
    name                varchar(255),
    description         varchar(255),
    business_associated varchar(255),
    date_finished       timestamp,
    date_created        timestamp,
    capacity            integer,
    current_position    integer,
    is_locked           boolean
);

alter table queue
    owner to postgres;

create unique index queue_join_id_uindex
    on queue (join_id);

create table active_period
(
    id                serial not null
        constraint active_period_pk
            primary key,
    date_activation   timestamp,
    date_deactivation timestamp,
    id_queue_ap_fk    integer
        constraint id_queue_ap_fk
            references queue
);

alter table active_period
    owner to postgres;

create table rate_queue
(
    id      serial not null
        constraint rate_queue_pk
            primary key,
    rate    integer,
    comment varchar(255)
);

alter table rate_queue
    owner to postgres;

create table users
(
    id              serial not null
        constraint users_pk
            primary key,
    token           varchar(255),
    email           varchar(255),
    is_admin        boolean,
    name_lastname   varchar(255),
    password        varchar(255),
    profile_picture varchar(255),
    username        varchar(255)
);

alter table users
    owner to postgres;

create unique index users_token_uindex
    on users (token);

create table info_user_queue
(
    id              serial not null
        constraint info_user_queue_pk
            primary key,
    id_queue_iuq_fk integer
        constraint id_queue_iuq_fk
            references queue,
    id_user_iuq_fk  integer
        constraint id_user_iqu_fk
            references users,
    is_rate_iuq_fk  integer
        constraint is_rate_iqu_fk
            references rate_queue,
    date_access     timestamp,
    date_success    timestamp,
    unattended      boolean
);

alter table info_user_queue
    owner to postgres;

create unique index info_user_queue_id_uindex
    on info_user_queue (id);

create table queue_user
(
    id             serial not null
        constraint queue_user_pk
            primary key,
    is_active      boolean,
    is_admin       boolean,
    id_queue_qu_fk integer
        constraint id_queue_qu_fk
            references queue,
    id_user_qu_fk  integer
        constraint id_user_qu_fk
            references users
);

alter table queue_user
    owner to postgres;

create unique index queue_user_id_uindex
    on queue_user (id);
