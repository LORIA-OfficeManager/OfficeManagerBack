-- Ce script est à faire après avoir lancer l'application back pour une première fois

insert into office (office_id, building, floor, num, size) values (0,"A",1,"01",2.0);
insert into office (office_id, building, floor, num, size) values (1,"A",1,"02",3.0);
insert into office (office_id, building, floor, num, size) values (2,"A",1,"03",1.0);

insert into status (status_id, name, size) values (0, "Défaut",1.0);
insert into status (status_id, name, size) values (1, "Doctorant", 0.66);
insert into status (status_id, name, size) values (2, "Stagiaire", 0.0);

insert into department (department_id, name) values (0, "D1");
insert into department (department_id, name) values (1, "D2");

insert into team (team_id, name, department_department_id) values (0, "Equipe 1", 0);
insert into team (team_id, name, department_department_id) values (1, "Equipe 2", 1);

insert into person (person_id, email, first_name, last_name, is_manager, start_date_contract, department_department_id, status_status_id, team_team_id) 
	values (0, "julien.bailly@loria.fr", "Julien", "Bailly", false, "2019-08-15 08:00:00", 0, 2, 0);
insert into person (person_id, email, end_date_contract, first_name, last_name, is_manager, start_date_contract, department_department_id, status_status_id, team_team_id) 
	values (1, "quentin.thouve@loria.fr", "2015-08-15 08:00:00" ,"Quentin", "Thouve", false, "2018-08-15 08:00:00", 0, 0, 0);
insert into person (person_id, email, end_date_contract, first_name, last_name, is_manager, start_date_contract, department_department_id, status_status_id, team_team_id) 
	values (2, "brice.losson@loria.fr", "2015-08-15 08:00:00" ,"Brice", "Losson", true, "2020-08-15 08:00:00", 0, 0, 0);


insert into office_assignment (office_assignment_id, end_date, start_date, office_id, person_id) values (0, "2099-12-31 00:00:00", "2010-12-31 00:00:00", 0, 0);
insert into office_assignment (office_assignment_id, end_date, start_date, office_id, person_id) values (1, "2099-12-31 00:00:00", "2010-12-31 00:00:00", 0, 1);
insert into office_assignment (office_assignment_id, end_date, start_date, office_id, person_id) values (2, "2099-12-31 00:00:00", "2010-12-31 00:00:00", 0, 2);