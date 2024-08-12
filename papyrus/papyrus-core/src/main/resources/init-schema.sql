DROP SCHEMA IF EXISTS "PDF_SIGNING" CASCADE;

CREATE SCHEMA "PDF_SIGNING";

CREATE TABLE PDF_SIGNING.FILE_INFO (size bigint, content_type varchar(255), id varchar(255) not null, name varchar(255), primary key (id))