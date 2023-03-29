/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import hibernate.HibernateDAO;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
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
    private int idAuteur;

    @Column
    private String Titre;

    @Column
    private int idFilm;

    @Column
    private int idPlateaux;

    @Column
    private Time DureeScene;

    @Column
    private int Statut;

    @Transient
    private List<Integer> ListePossibleIdFilm;

    @Transient
    private List<Integer> ListePossibleIdScene;

    @Transient
    private ArrayList<Deroulement> ListeDeroulement;

    @Transient
    private ArrayList<SceneActeur> ListeActeur;

    @Transient
    private String TimeDebut;

    @Transient
    private String TimeFin;

    @Transient
    private Plateaux PCorrespondant;

    @Transient
    private Planification PlanificationCorrespondante;

    public Plateaux getPCorrespondant() {
        return PCorrespondant;
    }

    public void setPCorrespondant(Plateaux PCorrespondant) {
        this.PCorrespondant = PCorrespondant;
    }

    public Planification getPlanificationCorrespondante() {
        return PlanificationCorrespondante;
    }

    public void setPlanificationCorrespondante(Planification PlanificationCorrespondante) {
        this.PlanificationCorrespondante = PlanificationCorrespondante;
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

    public int getStatut() {
        return Statut;
    }

    public void setStatut(int Statut) {
        this.Statut = Statut;
    }

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

    public void setListeDeroulement(ArrayList<Deroulement> ld) {
        //for(Deroulement d:ld) System.err.println(d.getTexte());
        this.ListeDeroulement = ld;
        System.err.println("Liste=" + this.ListeDeroulement.size());
        System.err.println("ld=" + ld.size());
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

    public Time getDureeScene() {
        return DureeScene;
    }

    public void setDureeScene(Time DureeScene) {
        this.DureeScene = DureeScene;
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
        if (idAuteur != 0) {
            lcr.add(Restrictions.eq("idFilm", idAuteur));
        }
        if (idPlateaux != 0) {
            lcr.add(Restrictions.eq("idPlateaux", idPlateaux));
        }
        if (ListePossibleIdFilm != null && !ListePossibleIdFilm.isEmpty()) {
            lcr.add(Restrictions.in("idFilm", ListePossibleIdFilm));
        }
        if (TimeDebut != null && !TimeDebut.equalsIgnoreCase("")) {
            lcr.add(Restrictions.ge("DureeScene", Time.valueOf(LocalTime.parse(TimeDebut))));
        }
        if (TimeFin != null && !TimeFin.equalsIgnoreCase("")) {
            lcr.add(Restrictions.le("DureeScene", Time.valueOf(LocalTime.parse(TimeFin))));
        }
        if (Statut != -1) {
            lcr.add(Restrictions.eq("Statut", Statut));
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

    public ArrayList<Scene> getListeSceneNonPaginer(HibernateDAO dao) throws Exception {
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
        if (ListePossibleIdFilm != null && !ListePossibleIdFilm.isEmpty()) {
            lcr.add(Restrictions.in("idFilm", ListePossibleIdFilm));
        }
        if (TimeDebut != null && !TimeDebut.equalsIgnoreCase("")) {
            lcr.add(Restrictions.ge("DureeScene", Time.valueOf(LocalTime.parse(TimeDebut))));
        }
        if (TimeFin != null && !TimeFin.equalsIgnoreCase("")) {
            lcr.add(Restrictions.le("DureeScene", Time.valueOf(LocalTime.parse(TimeFin))));
        }
        if (Statut != -1) {
            lcr.add(Restrictions.eq("Statut", Statut));
        }
        if (ListePossibleIdScene != null && !ListePossibleIdScene.isEmpty()) {
            lcr.add(Restrictions.in("id", ListePossibleIdScene));
        }
        if (idAuteur != 0) {
            lcr.add(Restrictions.eq("idFilm", idAuteur));
        }
        try {
            valiny = (ArrayList<Scene>) dao.FindByCritere(this, lcr, null);
            for (Scene s : valiny) {
                Plateaux p = new Plateaux();
                p.setId(s.getIdPlateaux());
                p.getPlateau(dao);
                s.setPCorrespondant(p);
                Planification pc = new Planification();
                pc.setIdScene(s.getId());
                pc.setStatut(1);
                pc.getPlanification(dao);
                s.setPlanificationCorrespondante(pc);
            }
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
        if (ListePossibleIdFilm != null && !ListePossibleIdFilm.isEmpty()) {
            lcr.add(Restrictions.in("idFilm", ListePossibleIdFilm));
        }
        if (ListePossibleIdScene != null && !ListePossibleIdScene.isEmpty()) {
            lcr.add(Restrictions.in("id", ListePossibleIdScene));
        }
        if (Statut != -1) {
            lcr.add(Restrictions.eq("Statut", Statut));
        }
        if (TimeDebut != null && !TimeDebut.equalsIgnoreCase("")) {
            lcr.add(Restrictions.ge("DureeScene", Time.valueOf(LocalTime.parse(TimeDebut))));
        }
        if (idAuteur != 0) {
            lcr.add(Restrictions.eq("idFilm", idAuteur));
        }
        if (TimeFin != null && !TimeFin.equalsIgnoreCase("")) {
            lcr.add(Restrictions.le("DureeScene", Time.valueOf(LocalTime.parse(TimeFin))));
        }
        try {
            valiny = dao.GetNbrParCritere(this, lcr);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }

    public String LiendeRecherche() {
        String link = "";
        if (!Titre.equalsIgnoreCase("")) {
            link += "Titre=" + Titre;
        }
        if (idPlateaux != 0) {
            if (!link.equalsIgnoreCase("")) {
                link += "&";
            }
            link += "idPlateaux=" + idPlateaux;
        }
        if (TimeDebut != null && !TimeDebut.equalsIgnoreCase("")) {
            if (!link.equalsIgnoreCase("")) {
                link += "&";
            }
            link += "TimeDebut=" + TimeDebut;
        }
        if (TimeFin != null && !TimeFin.equalsIgnoreCase("")) {
            if (!link.equalsIgnoreCase("")) {
                link += "&";
            }
            link += "TimeFin=" + TimeFin;
        }
        if (Statut != -1) {
            if (!link.equalsIgnoreCase("")) {
                link += "&";
            }
            link += "Statut=" + Statut;
        }
        return link;
    }

    public int getIdAuteur() {
        return idAuteur;
    }

    public void setIdAuteur(int idAuteur) {
        this.idAuteur = idAuteur;
    }

    public void getScene(HibernateDAO dao) throws Exception {
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
        if (idAuteur != 0) {
            lcr.add(Restrictions.eq("idFilm", idAuteur));
        }
        if (id != 0) {
            lcr.add(Restrictions.eq("id", id));
        }
        try {
            ArrayList<Scene> lsc = (ArrayList<Scene>) dao.FindByCritere(this, lcr, null);
            Scene sc = lsc.get(0);
            this.setId(sc.getId());
            this.setIdFilm(sc.getIdFilm());
            this.setIdPlateaux(sc.getIdPlateaux());
            this.setTitre(sc.getTitre());
            this.setDureeScene(sc.getDureeScene());
            this.setStatut(sc.getStatut());
            this.setIdAuteur(sc.getIdAuteur());
            Plateaux p = new Plateaux();
            p.setId(this.getIdPlateaux());
            p.getPlateau(dao);
            this.setPCorrespondant(p);
            Planification pc = new Planification();
            pc.setIdScene(this.getId());
            pc.getPlanification(dao);
            this.setPlanificationCorrespondante(pc);
        } catch (Exception e) {
            throw e;
        }
    }

    public void CreateScene(HibernateDAO dao) throws Exception {
        this.CalculDureeScene();
        dao.Create(this);
        this.getScene(dao);
        for (Deroulement d : this.getListeDeroulement()) {
            d.setIdScene(this.getId());
            dao.Create(d);
        }
        if (getListeActeur() != null && !getListeActeur().isEmpty()) {
            for (SceneActeur sc : this.getListeActeur()) {
                SceneActeur.MyEntityId m = new SceneActeur.MyEntityId(this.getId(), sc.getIdActeur());
                SceneActeur s = new SceneActeur(m);
                dao.Create(s);
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
            for (String id : idActeur) {
                SceneActeur.MyEntityId m = new SceneActeur.MyEntityId(0, Integer.parseInt(id));
                SceneActeur sa = new SceneActeur(m);
                // sa.setIdActeur(Integer.parseInt(id));
                lsc.add(sa);
            }
        }
        this.setListeActeur(lsc);
    }

    public void SupprimerActeur(String[] idActeur, HibernateDAO dao) throws Exception {
        if (idActeur.length != 0) {
            for (String id : idActeur) {
                SceneActeur sa = new SceneActeur();
                sa.setIdActeur(Integer.parseInt(id));
                sa.setIdScene(this.getId());
                System.err.println(sa.getIdScene());
                System.err.println("DELETE FROM SceneActeur WHERE idScene=" + sa.getIdScene() + " AND idActeur=" + sa.getIdActeur());
                dao.ExcecuteSQl("DELETE FROM SceneActeur WHERE idScene=" + sa.getIdScene() + " AND idActeur=" + sa.getIdActeur());
            }
        }
    }

    public void SupprimerDeroulement(String[] idDeroulement, HibernateDAO dao) throws Exception {
        if (idDeroulement.length != 0) {
            for (String id : idDeroulement) {
                Deroulement d = new Deroulement();
                d.setId(Integer.parseInt(id));
                d.getDeroulement(dao);
                //long newDuree=this.getDureeScene().getTime()-d.getDuree().getTime();
                //this.setDureeScene(new Time(newDuree));
                dao.ExcecuteSQl("DELETE FROM Deroulement WHERE id=" + d.getId());
                //dao.Delete(d);
            }
        }
    }

    public void UpdateDeroulement(String[] idDeroulement, String[] lordre, HibernateDAO dao) throws Exception {
        if (idDeroulement.length != 0) {
            for (int i = 0; i < idDeroulement.length; i++) {
                String id = idDeroulement[i];
                String ordre = lordre[i];
                Deroulement d = new Deroulement();
                d.setId(Integer.parseInt(id));
                d.getDeroulement(dao);
                d.setOrdre(Integer.parseInt(ordre));
                dao.Upadte(d);
            }
        }
    }

    public ArrayList<Profil> getListeActeur(HibernateDAO dao) throws Exception {
        ArrayList<Profil> lp = new ArrayList<>();
        SceneActeur.MyEntityId m = new SceneActeur.MyEntityId(this.getId(), 0);
        SceneActeur sc = new SceneActeur(m);
        //sc.setIdScene(this.getId());
        List<Integer> LidActeur = sc.getListeIdActeurNonPaginer(dao);
        Profil p = new Profil();
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (!LidActeur.isEmpty()) {
            lcr.add(Restrictions.in("id", LidActeur));
            lp = (ArrayList<Profil>) dao.FindByCritere(p, lcr, null);
        }
        return lp;
    }

    public void AjouterDeroulementBase(HibernateDAO dao) throws Exception {
        for (Deroulement d : this.getListeDeroulement()) {
            d.setIdScene(this.getId());
            dao.Create(d);
        }
    }

    public void AjouterActeurBase(HibernateDAO dao) throws Exception {
        for (SceneActeur sa : this.getListeActeur()) {
            SceneActeur.MyEntityId m = new SceneActeur.MyEntityId(this.getId(), sa.getIdActeur());
            SceneActeur s = new SceneActeur(m);
            dao.Create(s);
        }
    }

    /*
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
    }*/
    public void CalculDureeScene() throws Exception {
        long duree = 0;
        int h = 0;
        int m = 0;
        System.err.println("TOtal scene:" + this.getListeDeroulement().size());
        if (!this.getListeDeroulement().isEmpty()) {
            for (Deroulement d : this.getListeDeroulement()) {
                if (d.getDuree() == null) {
                    m += 1;
                } else {
                    Time t = d.getDuree();
                    h += t.getHours();
                    m += t.getMinutes();
                }
            }
            Time t = new Time(h, m, 0);
            this.setDureeScene(t);
        }
    }

    public boolean possibleTournage(Date d, HibernateDAO dao) throws Exception {
        boolean valiny = true;
        SceneActeur sc = new SceneActeur(new SceneActeur.MyEntityId(this.getId(), 0));
        if (!sc.isAllActeurDisponible(d, dao)) {
            return false;
        }
        Plateaux p = new Plateaux();
        p.setId(this.getIdPlateaux());
        if (!p.isPlateauDisponible(d, dao)) {
            return false;
        }
        return valiny;
    }

    public ArrayList<Scene> ListeSceneBySQLOrderByPlateau(HibernateDAO dao, ArrayList<Integer> lsc) throws Exception {
        ArrayList<Scene> ls = new ArrayList<>();
        /*String sql = "SELECT s.id, s.idPlateaux, FROM Scene s JOIN Plateaux p on (s.idPlateaux=p.id) WHERE s.id IN (";
        for (int i = 0; i < lsc.size(); i++) {
            sql += lsc.get(i);
            if (i != lsc.size() - 1) {
                sql += ",";
            }
        }
        sql += ") GROUP BY s.idPlateaux ";*/
        //System.err.println(sql);
        //List l=dao.ListeBySQl(sql);
        //Scene s=(Scene) l.get(0);
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (lsc != null && !lsc.isEmpty() ){
             lcr.add(Restrictions.in("id", lsc));
        }
        lcr.add(Restrictions.eq("Statut", 1));
        //System.err.println(l.get(0).getClass());
        ls = (ArrayList<Scene>) dao.FindByCritere(this, lcr, "idPlateaux");
        for(Scene s:ls){
            System.err.println(s.getTitre()+"_"+s.getIdPlateaux());
        }
        return ls;
    }

}
