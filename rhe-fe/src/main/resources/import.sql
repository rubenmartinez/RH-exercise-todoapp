-- TODO

insert into roles(id,name) values (1, 'ADMIN');
insert into roles(id,name) values (2, 'USER');

insert into users(id, account_non_expired, account_non_locked, credentials_non_expired, email, enabled, name, password) values (1, TRUE, TRUE, TRUE, 'admin', TRUE, 'Admin User', '{bcrypt}$2a$10$VGeMq0QQJw7bCUQBsv3S7e6sYm34a9D48rHbvs9r19Y7A4WOvKMPm');
insert into user_roles(user_id, roles_id) values (1, 1);

insert into users(id, account_non_expired, account_non_locked, credentials_non_expired, email, enabled, name, password) values (2, TRUE, TRUE, TRUE, 'user', TRUE, 'Example User', '{bcrypt}$2a$10$HyVznccVjXjxX3fQZYXzmuKiMPMcTveaJMlPywfvcRCqtQE/qW/p2');
insert into user_roles(user_id, roles_id) values (2, 2);
