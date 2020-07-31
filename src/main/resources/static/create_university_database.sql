create table faculty (
	id				serial			primary key	not null,
	name			varchar(255)	not null,
	office_address 	varchar(255) 	not null
);

create table person (
	id			    serial			primary key,
	first_name	    varchar(255)	not null,
	middle_name	    varchar(255)	not null,
	last_name 	    varchar(255)	not null,
	sex			    char(1)			check(sex in ('M', 'F')),
	birth_date  	date,
	phone		    char(10)
);

create table instructor (
	id	            serial          primary key,
	person_id       int             not null references person(id) on delete cascade,
	academic_rank   varchar(255)    check(academic_rank in (
	'Lecturer', 'Assistant Professor', 'Associate Professor', 'Full Professor'))
);

create table department(
	id              serial primary key,
	name varchar(255) not null,
	faculty_id int not null references faculty (id) on delete cascade,
	head_instructor_id int references instructor (id)
);

create table subject (
	id serial primary key,
	name  varchar(255) not null,
	hours int
);

create table course (
	id serial primary key,
	name varchar(255) not null,
	subject_id int not null references subject (id) on delete cascade,
	instructor_id int references instructor (id)
);

create table study_plan (
	id serial primary key,
	semester int
);

create table course_study_plan (
	course_id int not null references course (id) on delete cascade,
	study_plan_id int not null references study_plan (id) on delete cascade
);

create table student_group (
	id serial primary key,
	name varchar(255) not null,
	department_id int not null references department (id) on delete cascade,
	semester int,
	study_plan_id int references study_plan (id)
);

create table student (
	id serial primary key,
	person_id int not null references person (id) on delete cascade,
	group_id int references student_group (id)
);





