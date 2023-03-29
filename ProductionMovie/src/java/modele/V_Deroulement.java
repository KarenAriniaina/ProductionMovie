/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import hibernate.HibernateDAO;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ari
 */
@Entity
@Table(name = "v_Deroulement")
public class V_Deroulement {

    @Id
    private int id;

    @Column
    private int idScene;

    @Column
    private int idActeur;
    
    @Column
    private String NomActeur;

    @Column
    private String Emotion;

    @Column
    private String Texte;

    @Column
    private int Ordre;

    @Column
    private Time Duree;

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

    public int getIdActeur() {
        return idActeur;
    }

    public void setIdActeur(int idActeur) {
        this.idActeur = idActeur;
    }

    public String getNomActeur() {
        return NomActeur;
    }

    public void setNomActeur(String NomActeur) {
        this.NomActeur = NomActeur;
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

    public Time getDuree() {
        return Duree;
    }

    public void setDuree(Time Duree) {
        this.Duree = Duree;
    }

    public ArrayList<V_Deroulement> getDeroulement(HibernateDAO dao) throws Exception{
        ArrayList<V_Deroulement> valiny=new ArrayList<>();
        ArrayList<Criterion> lcr = new ArrayList<>();
        if(idScene!=0){
            lcr.add(Restrictions.eq("idScene", idScene));
        }
        try {
             valiny = (ArrayList<V_Deroulement>) dao.FindByCritere(this, lcr,null);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }
}
