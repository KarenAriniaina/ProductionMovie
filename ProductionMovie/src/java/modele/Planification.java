/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import hibernate.HibernateDAO;
import java.sql.Date;
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
public class Planification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int Statut;

    @Column
    private int idScene;

    @Column
    private Date Date;

    @Transient
    private Scene SceneTourner;

    @Transient
    private Plateaux PlateauxTourner;

    public Plateaux getPlateauxTourner() {
        return PlateauxTourner;
    }

    public void setPlateauxTourner(Plateaux PlateauxTourner) {
        this.PlateauxTourner = PlateauxTourner;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public Scene getSceneTourner() {
        return SceneTourner;
    }

    public void setSceneTourner(Scene SceneTourner) {
        this.SceneTourner = SceneTourner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdScene() {
        return idScene;
    }

    public void setIdScene(int idScene) {
        this.idScene = idScene;
    }

    public ArrayList<Planification> ListePlanificationNonPaginer(HibernateDAO dao) throws Exception {
        ArrayList<Planification> lp = new ArrayList<>();
        ArrayList<Criterion> lc = new ArrayList<>();
        if (Statut != -1) {
            lc.add(Restrictions.eq("Statut", Statut));
        }
        return lp;
    }

    public int getStatut() {
        return Statut;
    }

    public void setStatut(int Statut) {
        this.Statut = Statut;
    }

    public void getPlanification(HibernateDAO dao) throws Exception {
        ArrayList<Criterion> lc = new ArrayList<>();
        if (Statut != -1) {
            lc.add(Restrictions.eq("Statut", Statut));
        }
        if (idScene != 0) {
            lc.add(Restrictions.eq("idScene", idScene));
        }
        if (Date != null) {
            lc.add(Restrictions.eq("Date", Date));
        }
        if (id != 0) {
            lc.add(Restrictions.eq("id", id));
        }
        try {
            ArrayList<Planification> lp = (ArrayList<Planification>) dao.FindByCritere(this, lc, null);
            if (!lp.isEmpty()) {
                System.err.println("ato ary izy");
                Planification p = lp.get(0);
                this.setId(p.getId());
                this.setDate(p.getDate());
                this.setIdScene(p.getIdScene());
                this.setStatut(p.getStatut());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void ValiderPlanification(HibernateDAO dao) throws Exception {
        this.getPlanification(dao);
        if (this.idScene != 0) {
            this.setStatut(1);
            dao.Upadte(this);
            Scene s = new Scene();
            s.setId(this.getIdScene());
            s.getScene(dao);
            s.setStatut(2);
            dao.Upadte(s);
            IndisponibilitePlateau ip = new IndisponibilitePlateau();
            ip.setDate(this.getDate());
            ip.setIdPlateaux(s.getIdPlateaux());
            ip.setObservation("tournage");
            dao.Create(ip);
            SceneActeur sa = new SceneActeur(new SceneActeur.MyEntityId(s.getId(), 0));
            List<Integer> idAct = sa.getListeIdActeurNonPaginer(dao);
            if (!idAct.isEmpty()) {
                for (int ida : idAct) {
                    IndisponibiliteActeur ia = new IndisponibiliteActeur();
                    ia.setDate(this.getDate());
                    ia.setIdActeur(ida);
                    dao.Create(ia);
                }
            }
        }
    }

}
