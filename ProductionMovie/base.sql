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
  DureeScene Time NOT NULL;
  PRIMARY KEY (id)
);

ALTER TABLE Scene ADD COLUMN DureeScene TIME;

INSERT INTO Scene(id, Titre, idFilm, idPlateaux) VALUES (DEFAULT, 'Scene_1', 1, 1);
UPDATE Scene SET DureeScene='00:05:00' WHERE id=1;
INSERT INTO Scene(id, Titre, idFilm, idPlateaux) VALUES (DEFAULT, 'Scene_2', 1, 2);
UPDATE Scene SET DureeScene='00:07:00' WHERE id=2;



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



/*
    ALTER TABLE Deroulement ADD CONSTRAINT FKDeroulemen176807 FOREIGN KEY (idActeur) REFERENCES Profil (id);
    ALTER TABLE Deroulement ADD CONSTRAINT FKDeroulemen654703 FOREIGN KEY (idScene) REFERENCES Scene (id);
*/


