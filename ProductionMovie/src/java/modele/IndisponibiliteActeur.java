/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import hibernate.HibernateDAO;
import java.sql.Date;
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
public class IndisponibiliteActeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
    private int idActeur;
    
    @Column
    private Date Date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdActeur() {
        return idActeur;
    }

    public void setIdActeur(int idActeur) {
        this.idActeur = idActeur;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }
    
    
    public ArrayList<IndisponibiliteActeur> ListeIndisponibilite(HibernateDAO dao,ArrayList<Integer> lidActeur) throws Exception{
        ArrayList<IndisponibiliteActeur> li=new ArrayList<>();
        ArrayList<Criterion> lc=new ArrayList<>();
        if(!lidActeur.isEmpty()) lc.add(Restrictions.in("idActeur", lidActeur));
        if(Date!=null) lc.add(Restrictions.eq("Date", Date));
        try {
            li=(ArrayList<IndisponibiliteActeur>) dao.FindOrderBy(this, lc, null);
        } catch (Exception e) {
            throw e;
        }
        return li;
    }
    
}
