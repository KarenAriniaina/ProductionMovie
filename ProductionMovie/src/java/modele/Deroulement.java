/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import hibernate.HibernateDAO;
import java.sql.Time;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ari
 */
@Entity
public class Deroulement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int idScene;

    @Column
    private int idActeur;

    @Column
    private String Emotion;

    @Column
    private String Texte;

    @Column
    private int Ordre;

    @Column
    private Time Duree;

    public Deroulement() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public String getEmotion() {
        return Emotion;
    }

    public void setEmotion(String Emotion) {
        this.Emotion = Emotion;
    }

    public String getTexte() {
        return Texte;
    }

    public void setTexte(String Texte) {
        this.Texte = Texte;
    }

    public int getOrdre() {
        return Ordre;
    }

    public void setOrdre(int Ordre) {
        this.Ordre = Ordre;
    }

    public int getIdActeur() {
        return idActeur;
    }

    public void setIdActeur(int idActeur) {
        this.idActeur = idActeur;
    }

    public Time getDuree() {
        return Duree;
    }

    public void setDuree(Time Duree) {
        this.Duree = Duree;
    }

    public void getDeroulement(HibernateDAO dao) throws Exception {
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (id != 0) {
            lcr.add(Restrictions.eq("id", id));
        }
        if (idScene != 0) {
            lcr.add(Restrictions.eq("idScene", idScene));
        }
        try {
            ArrayList<Deroulement> lsc = (ArrayList<Deroulement>) dao.FindByCritere(this, lcr,null);
            Deroulement sc = lsc.get(0);
            this.setId(sc.getId());
            this.setEmotion(sc.getEmotion());
            this.setTexte(sc.getTexte());
            this.setDuree(sc.getDuree());
            this.setIdActeur(sc.getIdActeur());
            this.setIdScene(sc.getIdScene());
            this.setOrdre(sc.getOrdre());
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Deroulement> getListeDeroulement(HibernateDAO dao) throws Exception {
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (id != 0) {
            lcr.add(Restrictions.eq("id", id));
        }
        if (idScene != 0) {
            lcr.add(Restrictions.eq("idScene", idScene));
        }
        try {
            return (ArrayList<Deroulement>) dao.FindByCritere(this, lcr,"Ordre");
        } catch (Exception e) {
            throw e;
        }
    }

}
