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
import javax.persistence.Table;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ari
 */
@Entity
@Table(name = "IndisponibilitePlateaux")
public class IndisponibilitePlateau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int idPlateaux;
    
    @Column
    private Date Date;
    
    @Column
    private String Observation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPlateaux() {
        return idPlateaux;
    }

    public void setIdPlateaux(int idPlateaux) {
        this.idPlateaux = idPlateaux;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String Observation) {
        this.Observation = Observation;
    }

    public IndisponibilitePlateau() {
    }
    
    public ArrayList<IndisponibilitePlateau> ListeIndisponibilite(HibernateDAO dao) throws Exception{
        ArrayList<IndisponibilitePlateau> li=new ArrayList<>();
        ArrayList<Criterion> lc=new ArrayList<>();
        if(idPlateaux!=0) lc.add(Restrictions.eq("idPlateaux", idPlateaux));
        if(Date!=null) lc.add(Restrictions.eq("Date", Date));
        try {
            li=(ArrayList<IndisponibilitePlateau>) dao.FindOrderBy(this, lc, null);
        } catch (Exception e) {
            throw e;
        }
        return li;
    }
}
