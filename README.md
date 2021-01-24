# Conference-management-system

In this project we will show how to use Spring Data Jpa together with Postgresql. 

### 🌀 Build and run
Build the application by running maven clean command followed by install from the maven tool window or run the following command from terminal : `mvn clean install` .
Now you can start the application by running Server class in your IDE or by running :
`java -jar uber-conference-management-system-1.0-SNAPSHOT.jar`

### ❄ Project structure
Domain model :
![Domain model diagram](src/main/resources/img/CMS-Domain-Model-Diagram_1.png)

Domain driven design : 

![DDD diagram](src/main/resources/img/DDD_for_conference_management_1.png)

### 💎 Database schema

```postgres-psql
DROP TABLE IF EXISTS event CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS program CASCADE ;
DROP TABLE IF EXISTS section CASCADE ;
DROP TABLE IF EXISTS paper CASCADE ;
DROP TABLE IF EXISTS evaluation CASCADE ;
DROP TABLE IF EXISTS recommendation CASCADE ;
DROP TABLE IF EXISTS bid CASCADE ;
DROP TABLE IF EXISTS account CASCADE ;
DROP TABLE IF EXISTS paper_pcmember CASCADE ;
DROP TYPE IF EXISTS qualifier;
DROP TYPE IF EXISTS role;
DROP TABLE IF EXISTS profile CASCADE ;
DROP TYPE IF EXISTS status;
DROP TABLE IF EXISTS paper_author CASCADE ;
DROP TABLE IF EXISTS user_event CASCADE ;

create table if not exists profile
(
	username text NOT NULL primary key,
	password text,
	first_name text,
	last_name text,
	roles text[]
);

create table if not exists event
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	name text,
	program_id smallint,
	location_id smallint
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

create table if not exists location
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	country text,
	city text
);

create table if not exists section
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	name text,
	event_id smallint,
	supervisor_email text
);


create table if not exists paper
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	title text,
	description text,
	file_name text,
	topics text[],
	keywords text[],
    section_id smallint
);

create table if not exists evaluation
(
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
    paper_id smallint,
    recommendation_id smallint,
    reviewer text,
    qualifier text
);


create table if not exists recommendation
(
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY primary key,
	text text
);

create table if not exists bid
(
	status text,
	paper_id smallint,
	bidder text
);

create table if not exists account
(
	email text NOT NULL primary key,
	type VARCHAR (20),
	section_id smallint
);

create table if not exists user_event
(
	email text,
	event_id smallint
);

create table if not exists paper_pcmember
(
	paper_id smallint,
	evaluation_id smallint,
    email text
);

create table if not exists paper_author
(
	paper_id smallint,
    email text
);

CREATE TYPE  qualifier AS ENUM
    ('WEAK_REJECT', 'WEAK_ACCEPT', 'STRONG_ACCEPT', 'REJECT', 'BORDERLINE_PAPER', 'ACCEPT', 'STRONG_REJECT');

CREATE TYPE  role AS ENUM
    ('AUTHOR', 'PC_MEMBER', 'CONFERENCE_CHAIR');

CREATE TYPE  status AS ENUM
    ('ACCEPT', 'REJECT');



ALTER TABLE bid
ADD FOREIGN KEY (bidder) REFERENCES account(email);

ALTER TABLE bid
ADD FOREIGN KEY (paper_id) REFERENCES paper(id);

ALTER TABLE paper_author
ADD FOREIGN KEY (paper_id) REFERENCES paper(id);


ALTER TABLE paper_author
ADD FOREIGN KEY (email) REFERENCES account(email);

ALTER TABLE paper_pcmember
ADD FOREIGN KEY (paper_id) REFERENCES paper(id);

ALTER TABLE paper_pcmember
ADD FOREIGN KEY (evaluation_id) REFERENCES evaluation(id);

ALTER TABLE paper_pcmember
ADD FOREIGN KEY (email) REFERENCES account(email);

ALTER TABLE evaluation
ADD FOREIGN KEY (recommendation_id) REFERENCES recommendation(id);

ALTER TABLE event
ADD FOREIGN KEY (location_id) REFERENCES location(id);

ALTER TABLE event
ADD FOREIGN KEY (program_id) REFERENCES program(id);

ALTER TABLE section
ADD FOREIGN KEY (event_id) REFERENCES event(id);

ALTER TABLE section
ADD FOREIGN KEY (supervisor_email) REFERENCES account(email);

ALTER TABLE section
ADD CONSTRAINT supervisor_unique UNIQUE(supervisor_email);

ALTER TABLE account
ADD FOREIGN KEY (section_id) REFERENCES section(id);

ALTER TABLE user_event
ADD FOREIGN KEY (event_id) REFERENCES event(id);

ALTER TABLE user_event
ADD FOREIGN KEY (email) REFERENCES account(email);

ALTER TABLE paper
ADD FOREIGN KEY (section_id) REFERENCES section(id);
```

##### Database tables:

![DB table diagram](src/main/resources/img/CMS-Database-Diagram.png)

### 🚀 Use Postman to test the REST apis
There are 4 main API URLs, depending on which controller is responsible to handle the request : 

- `http://localhost:8080/api/conference` for `ConferenceManagementController`
- `http://localhost:8080/api/paper` for `PaperController`
- `http://localhost:8080/api/programCommittee` for `ProgramCommitteeController`
- `http://localhost:8080/api/user` for `UserController`

The `ConferenceManagementController` is used to handle requests for conference information, like adding an event, a program, a location and multiple sections for a certain event. Also we can update event's program, postpone date for program, get all events or sections.

The `PaperController` is used to handle requests regarding papers. We can submit a paper, update papers's content, get all papers.

The `ProgramCommitteeController` contain requests that are used to organize the participants. For example we can update users role, assign paper to a user, review a paper, update supervisor for specific section, add recommendation for a paper.

The `UserController` is used to manage users actions: register, bid proposal if the user want to write a review for that paper. Also we can update the section in which the user wants to participate.
