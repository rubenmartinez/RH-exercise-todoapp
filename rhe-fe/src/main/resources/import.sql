-- TODO

insert into roles(id,name) values (1, 'ADMIN');
insert into roles(id,name) values (2, 'USER');

insert into users(id, account_non_expired, account_non_locked, credentials_non_expired, email, enabled, name, password) values (1, TRUE, TRUE, TRUE, 'admin', TRUE, 'Admin', '{bcrypt}$2a$10$VGeMq0QQJw7bCUQBsv3S7e6sYm34a9D48rHbvs9r19Y7A4WOvKMPm');

insert into user_roles(user_id, roles_id) values (1, 1);

-- insert into todo_list(id, name, owner_user_id) values (1, 'Test List 1', 1);
-- insert into todo_list(id, name, owner_user_id) values (2, 'Test List 2', 1);

-- insert into todo_item(id, name, todo_list_id, completed, owner_user_id) values (1, 'Item 1', 1, FALSE, 1);
-- insert into todo_item(id, name, todo_list_id, completed, owner_user_id) values (2, 'Item 2', 1, FALSE, 1);
-- insert into todo_item(id, name, todo_list_id, completed, owner_user_id) values (3, 'Item 3', 1, FALSE, 1);

-- insert into todo_item(id, name, todo_list_id, completed, owner_user_id) values (4, 'Item A', 2, FALSE, 1);
-- insert into todo_item(id, name, todo_list_id, completed, owner_user_id) values (5, 'Item B', 2, FALSE, 1);
-- insert into todo_item(id, name, todo_list_id, completed, owner_user_id) values (6, 'Item C', 2, FALSE, 1);