create table activation_tokens
(
    id         bigserial primary key,
    value      varchar(32) unique not null,
    expires_at timestamp          not null,
    created_at timestamp          not null default current_timestamp,
    updated_at timestamp          not null default current_timestamp
);

create table users
(
    id                     bigserial primary key,
    first_name             varchar(255)        not null,
    last_name              varchar(255)        not null,
    username               varchar(255) unique not null,
    email                  varchar(255) unique not null,
    password               varchar(60)         not null,
    is_expired             boolean             not null default false,
    is_locked              boolean             not null default false,
    is_credentials_expired boolean             not null default false,
    is_enabled             boolean             not null default false,
    activation_token_id    bigint unique       not null,
    created_at             timestamp           not null default current_timestamp,
    updated_at             timestamp           not null default current_timestamp,

    foreign key (activation_token_id) references activation_tokens (id)
);

create table roles
(
    id         bigserial primary key,
    name       varchar(50) unique not null,
    created_at timestamp          not null default current_timestamp,
    updated_at timestamp          not null default current_timestamp
);


create table user_roles
(
    user_id bigint not null,
    role_id bigint not null,

    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id),
    primary key (user_id, role_id)
);

create table todos
(
    id        bigserial primary key,
    text      varchar(255) not null,
    user_id   bigint       not null,
    is_done   boolean      not null default false,
    timestamp timestamp    not null default current_timestamp,

    foreign key (user_id) references users (id)
);