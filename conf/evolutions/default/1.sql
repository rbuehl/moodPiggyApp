# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table poll (
  id                        bigserial not null,
  score                     integer,
  due_date                  timestamp,
  constraint pk_poll primary key (id))
;

create table token (
  id                        varchar(40) not null,
  poll_id                   bigint not null,
  value                     varchar(255),
  constraint pk_token primary key (id))
;

create table my_user (
  id                        bigserial not null,
  lastname                  varchar(255),
  firstname                 varchar(255),
  email                     varchar(255),
  constraint pk_my_user primary key (id))
;

alter table token add constraint fk_token_poll_1 foreign key (poll_id) references poll (id);
create index ix_token_poll_1 on token (poll_id);



# --- !Downs

drop table if exists poll cascade;

drop table if exists token cascade;

drop table if exists my_user cascade;

