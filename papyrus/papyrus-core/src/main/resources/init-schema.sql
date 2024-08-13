DROP SCHEMA IF EXISTS "PAPYRUS" CASCADE;

CREATE SCHEMA "PAPYRUS";

CREATE TABLE PAPYRUS.FILE_INFO (
    size bigint,
    content_type varchar(255),
    id varchar(255) not null,
    name varchar(255),
    primary key (id)
);

CREATE TABLE PAPYRUS.SIGNER (
    WET_SIGNATURE_PATH varchar(255),
    FULL_NAME varchar(255),
    id int not null,
    primary key (id)
);

CREATE TABLE PAPYRUS.FILE_INFO_SIGNER (
    FILE_ID varchar(255) not null,
    SIGNER_ID int not null,
    id varchar(255) not null,
    primary key (id)
);