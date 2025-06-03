-- V8__add_foreign_keys_after_users_created.sql
ALTER TABLE fixyou.tb_institution
ADD CONSTRAINT fk_institution_created_by
FOREIGN KEY (created_by) REFERENCES fixyou.tb_users(id);
