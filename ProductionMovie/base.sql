CREATE TABLE Profil (
  id    SERIAL NOT NULL, 
  Login varchar(100) NOT NULL UNIQUE, 
  Mdp   varchar(50) NOT NULL, 
  Nom   varchar(100) NOT NULL, 
  Role  int4 NOT NULL, 
  PRIMARY KEY (id)
);

INSERT INTO Profil VALUES(DEFAULT,'auteur@gmail.com','auteur','Alexandra',1); /*  1  */

INSERT INTO Profil VALUES(DEFAULT,'realisateur@gmail.com','realisateur','John',2);  /*  2  */

INSERT INTO Profil VALUES(DEFAULT,'acteur1@gmail.com','acteur1','Iris',3);  /* 3  */
INSERT INTO Profil VALUES(DEFAULT,'acteur2@gmail.com','acteur2','Talia',3); /*  4  */
INSERT INTO Profil VALUES(DEFAULT,'acteur3@gmail.com','acteur3','Auriana',3); /* 5 */

INSERT INTO Profil VALUES(DEFAULT,'acteur4@gmail.com','acteur4','Acteur4',3); 6
INSERT INTO Profil VALUES(DEFAULT,'acteur5@gmail.com','acteur5','Acteur5',3); 7
INSERT INTO Profil VALUES(DEFAULT,'acteur6@gmail.com','acteur6','Acteur6',3); 8
INSERT INTO Profil VALUES(DEFAULT,'acteur7@gmail.com','acteur7','Acteur7',3); 9
INSERT INTO Profil VALUES(DEFAULT,'acteur8@gmail.com','acteur8','Acteur8',3); 10
INSERT INTO Profil VALUES(DEFAULT,'acteur9@gmail.com','acteur9','Acteur9',3);
INSERT INTO Profil VALUES(DEFAULT,'acteur10@gmail.com','acteur10','Acteur10',3);
INSERT INTO Profil VALUES(DEFAULT,'acteur11@gmail.com','acteur11','Acteur11',3);
INSERT INTO Profil VALUES(DEFAULT,'acteur12@gmail.com','acteur12','Acteur12',3);
INSERT INTO Profil VALUES(DEFAULT,'acteur13@gmail.com','acteur13','Acteur13',3);



CREATE TABLE Plateaux (
  id  SERIAL NOT NULL, 
  Nom varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (id)
);

INSERT INTO Plateaux VALUES(DEFAULT,'Plateau1');
INSERT INTO Plateaux VALUES(DEFAULT,'Plateau2');
INSERT INTO Plateaux VALUES(DEFAULT,'Plateau3');
INSERT INTO Plateaux VALUES(DEFAULT,'Plateau4');
INSERT INTO Plateaux VALUES(DEFAULT,'Plateau5');
INSERT INTO Plateaux VALUES(DEFAULT,'Plateau6');
INSERT INTO Plateaux VALUES(DEFAULT,'Plateau7');
INSERT INTO Plateaux VALUES(DEFAULT,'Plateau8');
INSERT INTO Plateaux VALUES(DEFAULT,'Plateau9');
INSERT INTO Plateaux VALUES(DEFAULT,'Plateau10');


CREATE TABLE Deroulement (
  id       SERIAL NOT NULL, 
  idScene  int4 DEFAULT 0, 
  idActeur  int4 DEFAULT 0, 
  Emotion  varchar(150) DEFAULT NULL, 
  Texte    text NOT NULL, 
  Ordre    int4 NOT NULL, 
  Duree TIME DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Film (
  id       SERIAL NOT NULL, 
  Titre    varchar(250) NOT NULL, 
  idAuteur int4 NOT NULL, 
  PRIMARY KEY (id)
);

INSERT INTO Film(id, Titre, idAuteur) VALUES (DEFAULT, 'Film_1', 1);
INSERT INTO Film(id, Titre, idAuteur) VALUES (DEFAULT, 'Film_2', 1);
INSERT INTO Film(id, Titre, idAuteur) VALUES (DEFAULT, 'Film_3', 1);

CREATE TABLE FilmActeur (
  idFilm   int4 NOT NULL, 
  idActeur int4 NOT NULL, 
  PRIMARY KEY (idFilm, 
  idActeur)
);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,3);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,4);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,5);


INSERT INTO FilmActeur(idFilm, idActeur) VALUES (2,6);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (2,7);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (2,8);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (2,9);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (2,10);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (2,11);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (2,12);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (2,13);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (2,14);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (2,15);


INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,6);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,7);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,8);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,9);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,10);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,11);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,12);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,13);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,14);
INSERT INTO FilmActeur(idFilm, idActeur) VALUES (1,15);

CREATE TABLE FilmRealisateur (
  idFilm        int4 NOT NULL, 
  idRealisateur int4 NOT NULL, 
  PRIMARY KEY (idFilm, 
  idRealisateur)
);
INSERT INTO FilmRealisateur(idFilm, idRealisateur) VALUES (1, 2);

CREATE TABLE Scene (
  id         SERIAL NOT NULL, 
  Titre      varchar(250) NOT NULL, 
  idFilm     int4 NOT NULL, 
  idPlateaux int4 NOT NULL, 
  PRIMARY KEY (id)
);


INSERT INTO Scene(id, Titre, idFilm, idPlateaux) VALUES (DEFAULT, 'Scene_1', 1, 1);
INSERT INTO Scene(id, Titre, idFilm, idPlateaux) VALUES (DEFAULT, 'Scene_2', 1, 2);

ALTER TABLE Scene ADD COLUMN DureeScene TIME;
UPDATE Scene SET dureeScene='00:05:00' WHERE id=4;


CREATE TABLE SceneActeur (
  idScene  int4 NOT NULL, 
  idActeur int4 NOT NULL, 
  PRIMARY KEY (idScene,idActeur)
);
ALTER TABLE FilmRealisateur ADD CONSTRAINT FKFilmRealis407737 FOREIGN KEY (idRealisateur) REFERENCES Profil (id);

ALTER TABLE SceneActeur ADD CONSTRAINT FKSceneActeu375542 FOREIGN KEY (idScene) REFERENCES Scene (id);
ALTER TABLE Scene ADD CONSTRAINT FKScene406929 FOREIGN KEY (idPlateaux) REFERENCES Plateaux (id);
ALTER TABLE Scene ADD CONSTRAINT FKScene470510 FOREIGN KEY (idFilm) REFERENCES Film (id);
ALTER TABLE Film ADD CONSTRAINT FKFilm265649 FOREIGN KEY (idAuteur) REFERENCES Profil (id);
ALTER TABLE FilmRealisateur ADD CONSTRAINT FKFilmRealis31346 FOREIGN KEY (idFilm) REFERENCES Film (id);
ALTER TABLE FilmActeur ADD CONSTRAINT FKFilmActeur83832 FOREIGN KEY (idFilm) REFERENCES Film (id);


CREATE TABLE Emotion (
  id          SERIAL NOT NULL, 
  Designation varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (id)
);

INSERT INTO Emotion(id, Designation) VALUES (DEFAULT, 'Happy');
INSERT INTO Emotion(id, Designation) VALUES (DEFAULT, 'Sad');
INSERT INTO Emotion(id, Designation) VALUES (DEFAULT, 'Neutre');


CREATE TABLE Planification (
  id             SERIAL NOT NULL, 
  idScene        int4 NOT NULL, 
  idPlateaux     int4 NOT NULL, 
  DateHeureDebut timestamp NOT NULL, 
  DateHeureFin   timestamp NOT NULL, 
  PRIMARY KEY (id)
);

ALTER TABLE Planification ADD CONSTRAINT FKPlanificat189088 FOREIGN KEY (idScene) REFERENCES Scene (id);
ALTER TABLE Planification ADD CONSTRAINT FKPlanificat36603 FOREIGN KEY (idPlateaux) REFERENCES Plateaux (id);

ALTER TABLE Plateaux ADD COLUMN Longitude DOUBLE PRECISION ;
ALTER TABLE Plateaux ADD COLUMN Latitude DOUBLE PRECISION ;

Update Plateaux SET Longitude=47.5079,Latitude=-18.8792 WHERE id=1;
Update Plateaux SET Longitude=49.4029,Latitude=-18.1499 WHERE id=2;
Update Plateaux SET Longitude=46.3167,Latitude=-15.7167 WHERE id=3;
Update Plateaux SET Longitude=43.6667,Latitude=-23.3500 WHERE id=4;
Update Plateaux SET Longitude=49.2833,Latitude=-12.2833 WHERE id=5;
Update Plateaux SET Longitude=47.0857,Latitude=-21.4524 WHERE id=6;
Update Plateaux SET Longitude=44.2833,Latitude=-20.2833 WHERE id=7;
Update Plateaux SET Longitude=50.2833,Latitude=-14.8833 WHERE id=8;
Update Plateaux SET Longitude=48.4500,Latitude=-13.6833 WHERE id=9;
Up date Plateaux SET Longitude=46.0833,Latitude=-25.1667 WHERE id=10;

/*

*/
CREATE TABLE IndisponibiliteActeur (
  id       SERIAL NOT NULL, 
  idActeur int4 NOT NULL, 
  Date   date NOT NULL, 
  PRIMARY KEY (id)
);
CREATE TABLE IndisponibilitePlateaux (
  id         SERIAL NOT NULL, 
  idPlateaux int4 NOT NULL, 
  Date     date NOT NULL, 
  Observation text DEFAULT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE IndisponibiliteActeur ADD CONSTRAINT FKIndisponib667836 FOREIGN KEY (idActeur) REFERENCES Profil (id);
ALTER TABLE IndisponibilitePlateaux ADD CONSTRAINT FKIndisponib505053 FOREIGN KEY (idPlateaux) REFERENCES Plateaux (id);

ALTER TABLE Scene ADD COLUMN Statut int4 DEFAULT 0;

CREATE OR REPLACE VIEW v_Deroulement AS
  SELECT d.*,p.Nom as NomActeur FROM Deroulement  d LEFT JOIN Profil p ON (d.idActeur=p.id);


UPDATE Scene set dureeScene='00:05:00',Statut=0 where id=3;

Alter table scene add COLUMN idAuteur int4 default 1;