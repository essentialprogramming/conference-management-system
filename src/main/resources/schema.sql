create table if not exists proposal(
      id integer primary key identity,
      title varchar(255),
      content varchar(255)
);

alter table proposal alter column id restart with 1



