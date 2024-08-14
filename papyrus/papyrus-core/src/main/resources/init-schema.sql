DROP SCHEMA IF EXISTS "PAPYRUS" CASCADE;

CREATE SCHEMA "PAPYRUS";

CREATE TABLE PAPYRUS.FILE_INFO (
    SIZE bigint,
    CONTENT_TYPE varchar(255),
    ID varchar(255) not null,
    NAME varchar(255),
    PATH varchar(255),
    primary key (ID)
);

CREATE TABLE PAPYRUS.SIGNER (
    WET_SIGNATURE_PATH varchar(255),
    FULL_NAME varchar(255),
    ID int not null,
    CERTIFICATE_PATH varchar(255),
    primary key (ID)
);

CREATE TABLE PAPYRUS.FILE_INFO_SIGNER (
    FILE_ID varchar(255) not null,
    SIGNER_ID int not null,
    ID varchar(255) not null,
    primary key (ID)
);