/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import hibernate.HibernateDAO;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ari
 */
@Entity
public class Scene {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String Titre;

    @Column
    private int idFilm;

    @Column
    private int idPlateaux;

    @Column
    private Time DureeScene;

    @Transient
    private String TimeDebut;

    @Transient
    private String TimeFin;

    @Transient
    private List<Integer> ListePossibleIdFilm;

    @Transient
    private List<Integer> ListePossibleIdScene;

    @Transient
    private ArrayList<Deroulement> ListeDeroulement;

    @Transient
    private ArrayList<SceneActeur> ListeActeur;

    public List<Integer> getListePossibleIdScene() {
        return ListePossibleIdScene;
    }

    public void setListePossibleIdScene(List<Integer> ListePossibleIdScene) {
        this.ListePossibleIdScene = ListePossibleIdScene;
    }

    public List<Integer> getListePossibleIdFilm() {
        return ListePossibleIdFilm;
    }

    public void setListePossibleIdFilm(List<Integer> ListePossibleIdFilm) {
        this.ListePossibleIdFilm = ListePossibleIdFilm;
    }

    public ArrayList<Deroulement> getListeDeroulement(HibernateDAO dao) throws Exception {

        return ListeDeroulement;
    }

    public ArrayList<Deroulement> getListeDeroulement() throws Exception {
        return ListeDeroulement;
    }

    public void setListeDeroulement(ArrayList<Deroulement> ListeDeroulement) {
        this.ListeDeroulement = ListeDeroulement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String Titre) {
        this.Titre = Titre;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public int getIdPlateaux() {
        return idPlateaux;
    }

    public void setIdPlateaux(int idPlateaux) {
        this.idPlateaux = idPlateaux;
    }

    public ArrayList<Scene> getListeScene(HibernateDAO dao, int debut) throws Exception {
        ArrayList<Scene> valiny = new ArrayList<>();
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            lcr.add(Restrictions.like("Titre", "%" + this.getTitre() + "%").ignoreCase());
        }
        if (idFilm != 0) {
            lcr.add(Restrictions.eq("idFilm", idFilm));
        }
        if (idPlateaux != 0) {
            lcr.add(Restrictions.eq("idPlateaux", idPlateaux));
        }
        if (TimeDebut != null && !TimeDebut.equalsIgnoreCase("")) {
            lcr.add(Restrictions.ge("DureeScene", Time.valueOf(LocalTime.parse(TimeDebut))));
        }
        if (TimeFin != null && !TimeFin.equalsIgnoreCase("")) {
            lcr.add(Restrictions.le("DureeScene", Time.valueOf(LocalTime.parse(TimeFin))));
        }
        if (ListePossibleIdFilm != null && !ListePossibleIdFilm.isEmpty()) {
            lcr.add(Restrictions.in("idFilm", ListePossibleIdFilm));
        }
        if (ListePossibleIdScene != null && !ListePossibleIdScene.isEmpty()) {
            lcr.add(Restrictions.in("id", ListePossibleIdScene));
        }
        try {
            valiny = (ArrayList<Scene>) dao.FindByCriterePaginer(this, lcr, debut, 8, null);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }

    public int NbrCriterepaginer(HibernateDAO dao) throws Exception {
        int valiny = 0;
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            lcr.add(Restrictions.like("Titre", "%" + this.getTitre() + "%").ignoreCase());
        }
        if (idFilm != 0) {
            lcr.add(Restrictions.eq("idFilm", idFilm));
        }
        if (idPlateaux != 0) {
            lcr.add(Restrictions.eq("idPlateaux", idPlateaux));
        }
        if (TimeDebut != null && !TimeDebut.equalsIgnoreCase("")) {
            lcr.add(Restrictions.ge("DureeScene", Time.valueOf(LocalTime.parse(TimeDebut))));
        }
        if (TimeFin != null && !TimeFin.equalsIgnoreCase("")) {
            lcr.add(Restrictions.le("DureeScene", Time.valueOf(LocalTime.parse(TimeFin))));
        }
        if (ListePossibleIdFilm != null && !ListePossibleIdFilm.isEmpty()) {
            lcr.add(Restrictions.in("idFilm", ListePossibleIdFilm));
        }
        if (ListePossibleIdScene != null && !ListePossibleIdScene.isEmpty()) {
            lcr.add(Restrictions.in("id", ListePossibleIdScene));
        }
        try {
            valiny = dao.GetNbrParCritere(this, lcr);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }

    public String getTimeDebut() {
        return TimeDebut;
    }

    public void setTimeDebut(String TimeDebut) {
        this.TimeDebut = TimeDebut;
    }

    public String getTimeFin() {
        return TimeFin;
    }

    public void setTimeFin(String TimeFin) {
        this.TimeFin = TimeFin;
    }

    public Time getDureeScene() {
        return DureeScene;
    }

    public void setDureeScene(Time DureeScene) {
        this.DureeScene = DureeScene;
    }

    public String LiendeRecherche(String Titre, String TitreFilm, int idPlateaux) {
        String link = "";
        if (!Titre.equalsIgnoreCase("")) {
            link += "Titre=" + Titre;
        }

        if (!TitreFilm.equalsIgnoreCase("")) {
            if (!link.equalsIgnoreCase("")) {
                link += "&";
            }
            link += "TitreFilm=" + TitreFilm;
        }
        if (idPlateaux != 0) {
            if (!link.equalsIgnoreCase("")) {
                link += "&";
            }
            link += "idPlateaux=" + idPlateaux;
        }
        return link;
    }

    public void getScene(HibernateDAO dao) throws Exception {
        int valiny = 0;
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            lcr.add(Restrictions.like("Titre", "%" + this.getTitre() + "%").ignoreCase());
        }
        if (idFilm != 0) {
            lcr.add(Restrictions.eq("idFilm", idFilm));
        }
        if (idPlateaux != 0) {
            lcr.add(Restrictions.eq("idPlateaux", idPlateaux));
        }
        if (DureeScene != null) {
            lcr.add(Restrictions.eq("DureeScene", DureeScene));
        }
        try {
            ArrayList<Scene> lsc = (ArrayList<Scene>) dao.FindByCritere(this, lcr);
            Scene sc = lsc.get(0);
            this.setId(sc.getId());
            this.setIdFilm(sc.getIdFilm());
            this.setIdPlateaux(sc.getIdPlateaux());
            this.setTitre(sc.getTitre());
            this.setDureeScene(sc.getDureeScene());
        } catch (Exception e) {
            throw e;
        }
    }

    public void CreateScene(HibernateDAO dao) throws Exception {
        dao.Create(this);
        this.getScene(dao);
        for (Deroulement d : this.getListeDeroulement()) {
            d.setIdScene(this.getId());
            System.err.println(this.getId());
            dao.Create(d);
        }
        if (getListeActeur() != null && !getListeActeur().isEmpty()) {
            for (SceneActeur sc : this.getListeActeur()) {
                sc.setIdScene(this.getId());
                dao.Create(sc);
            }
        }
    }

    public ArrayList<SceneActeur> getListeActeur() {
        return ListeActeur;
    }

    public void setListeActeur(ArrayList<SceneActeur> ListeActeur) {
        this.ListeActeur = ListeActeur;
    }

    public void setListeA(String[] idActeur) {
        ArrayList<SceneActeur> lsc = new ArrayList<>();
        if (idActeur.length != 0) {
            System.err.println("niditra ato amn liste");
            for (String id : idActeur) {
                SceneActeur sa = new SceneActeur();
                sa.setIdActeur(Integer.parseInt(id));
                lsc.add(sa);
            }
        }
        this.setListeActeur(lsc);
    }

    public ArrayList<ArrayList<Scene>> getPlanificationSceneOptimiser(HibernateDAO dao) throws Exception {
        ArrayList<ArrayList<Scene>> lsc = new ArrayList<>();
        double dureeScene = 0;
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (idFilm != 0) {
            lcr.add(Restrictions.eq("idFilm", idFilm));
        }
        ArrayList<Scene> listescene = (ArrayList<Scene>) dao.FindOrderBy(this, lcr, "idPlateaux");
        ArrayList<Scene> listescene2 = new ArrayList<>();
        for (Scene sc : listescene) {
            listescene2.add(sc);
        }
        ArrayList<Scene> lintermediaire = new ArrayList<>();
        int idPlateaux = listescene.get(0).getIdPlateaux();
        while (!listescene2.isEmpty()) {
            for (Scene sc : listescene) {
                double duree = sc.getDureeScene().getHours() + sc.getDureeScene().getMinutes() / 60;
                if (sc.getIdPlateaux() == idPlateaux && dureeScene + 1 + duree <= 5) {
                    dureeScene += 1 + duree;
                    lintermediaire.add(sc);
                    listescene2.remove(sc);
                    if (duree >= 5) {
                        lsc.add(lintermediaire);
                        lintermediaire = new ArrayList<>();
                        dureeScene = 0;
                    }
                }
                if (sc.getIdPlateaux() != idPlateaux) {
                    if (dureeScene != 0 && dureeScene + 2 + duree <= 5) {
                        dureeScene += 2 + duree;
                        lintermediaire.add(sc);
                        listescene2.remove(sc);
                    } else if (dureeScene == 0) {
                        dureeScene += 1 + duree;
                        lintermediaire.add(sc);
                        listescene2.remove(sc);
                    }
                    if (duree >= 5) {
                        lsc.add(lintermediaire);
                        lintermediaire = new ArrayList<>();
                        dureeScene = 0;
                    }
                    if (sc.getId() == listescene2.get(0).getId()) {
                        idPlateaux = sc.getIdPlateaux();
                    }
                    if (listescene2.isEmpty()) {
                        break;
                    }
                }
            }
            if (dureeScene > 0) {
                lsc.add(lintermediaire);
                lintermediaire = new ArrayList<>();
                dureeScene = 0;
                listescene = new ArrayList<>();
                for (Scene sc : listescene2) {
                    listescene.add(sc);
                }
            }
        }
        if (!lintermediaire.isEmpty()) {
            lsc.add(lintermediaire);
        }
        return lsc;
    }

}
