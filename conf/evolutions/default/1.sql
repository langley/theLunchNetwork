# Tasks schema
 
# --- !Ups

CREATE SEQUENCE task_id_seq;
CREATE TABLE task (
    id integer NOT NULL DEFAULT nextval('task_id_seq'),
    label varchar(255)
);

CREATE TABLE user ( 
	id varchar(255), -- UUID
	name varchar(255),
	email varchar(255),
	password varchar(255),
	profileLink varchar(255),
	description varchar(255),
	phone varchar(255),
	organization varchar(255),
	nextAvailableDateBegin int, -- should be timestamp
	nextAvailableDateEnd   int  -- should be timestamp
);

CREATE TABLE lunch ( 
	id varchar(255), -- UUID
	dateOfLunch timestamp,
	user1Id	   varchar(255), -- UUID
	user2Id	   varchar(255), -- UUID 
	comments   varchar(255)
);

CREATE TABLE lunchComments ( 
	id varchar(255), -- UUID 
	comment varchar(255),
	userId  varchar(255), -- UUID 
	lunchId BIGINT -- UUID
);


 
# --- !Downs
DROP TABLE task;
DROP SEQUENCE task_id_seq;

DROP TABLE user;
DROP TABLE lunch;
DROP TABLE lunchComments;

