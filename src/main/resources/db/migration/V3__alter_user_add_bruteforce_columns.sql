alter table users add column is_login_disabled boolean default false;
alter table users add column failed_login_attempts int default 0;