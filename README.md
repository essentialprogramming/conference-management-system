# Conference-management-system

### Database schema
```postgres-psql
create table if not exists event 
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	name text,
	program_id smallint,
	location_id smallint 
);

create table if not exists location
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	country text,
	city text
);

create table if not exists paper
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	title text,
	content text,
	qualifiers text[],
	topics text[],
	keywords text[]
);

create table if not exists program 
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	date DATE,
	interval text,
	abstract_deadline DATE,
	proposal_deadline DATE,
	bidding_deadline DATE
);

create table if not exists recommendation
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	text text,
	email text,
	paper_id smallint
);

create table if not exists section
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	name text,
	event_id smallint,
	supervisor_email text
);

create table if not exists user_entity
(
	email text NOT NULL primary key,
	section_id smallint,
	role text
);

create table if not exists user_event
(
	email text,
	event_id smallint
);

create table if not exists user_paper
(
	email text,
	paper_id smallint
);

CREATE TYPE qualifier AS ENUM
    ('WEAK_REJECT', 'WEAK_ACCEPT', 'STRONG_ACCEPT', 'REJECT', 'BORDERLINE_PAPER', 'ACCEPT', 'STRONG_REJECT');

CREATE TYPE role AS ENUM
    ('PARTICIPANT', 'SPEAKER', 'PROGRAM_COMMITTEE');

ALTER TABLE event 
ADD FOREIGN KEY (location_id) REFERENCES location(id);

ALTER TABLE event 
ADD FOREIGN KEY (program_id) REFERENCES program(id);

ALTER TABLE recommendation
ADD FOREIGN KEY (paper_id) REFERENCES paper(id);

ALTER TABLE section
ADD FOREIGN KEY (event_id) REFERENCES event(id);

ALTER TABLE section
ADD FOREIGN KEY (supervisor_email) REFERENCES user_entity(email);

ALTER TABLE section
ADD CONSTRAINT supervisor_unique UNIQUE(supervisor_email);

ALTER TABLE user_entity
ADD FOREIGN KEY (section_id) REFERENCES section(id);

ALTER TABLE user_event
ADD FOREIGN KEY (event_id) REFERENCES event(id);

ALTER TABLE user_event
ADD FOREIGN KEY (email) REFERENCES user_entity(email);

ALTER TABLE user_paper
ADD FOREIGN KEY (paper_id) REFERENCES paper(id);

ALTER TABLE user_paper
ADD FOREIGN KEY (email) REFERENCES user_entity(email);
```