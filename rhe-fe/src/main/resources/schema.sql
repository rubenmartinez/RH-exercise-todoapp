create table todo_item (
  id bigint not null,
  owner_user_id bigint not null,
  title varchar(255),
  completed boolean not null,
  created_date time,
  last_modified_date time,
  primary key (id)
);
