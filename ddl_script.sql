drop table breed if exists;

drop table sub_breed if exists;

create table breed (
    id varchar(255) not null,
    created_by varchar(255),
    created_date timestamp,
    updated_by varchar(255),
    updated_date timestamp,
    description varchar(255),
    is_sync boolean,
    name varchar(255),
    primary key (id));

create table sub_breed (
    id varchar(255) not null,
    created_by varchar(255),
    created_date timestamp,
    updated_by varchar(255),
    updated_date timestamp,
    description varchar(255),
    is_sync boolean,
    name varchar(255),
    breed_id varchar(255),
    primary key (id));

alter table sub_breed add constraint FKqkhxosq7rc6xad048lxsyq4rg foreign key (breed_id) references breed;