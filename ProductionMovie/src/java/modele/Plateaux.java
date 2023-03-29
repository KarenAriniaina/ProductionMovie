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
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ari
 */

@Entity
public class Plateaux {

    @Id
    private int id;
    
    @Column
    private String Nom;
    
    @Column
    private double Longitude;
    
    @Column
    private double Latitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }
    
    public void getPlateau(HibernateDAO dao) throws Exception{
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (id != 0) {
            lcr.add(Restrictions.eq("id", id));
        }
        try {
            ArrayList<Plateaux> lsc = (ArrayList<Plateaux>) dao.FindByCritere(this, lcr,null);
            Plateaux p = lsc.get(0);
            this.setId(p.getId());
            this.setNom(p.getNom());
        } catch (Exception e) {
            throw e;
        }
    }
    
    public boolean isPlateauDisponible(Date d,HibernateDAO dao) throws Exception{
        boolean valiny=true;
//        String sdd=dd.toString();
//        String sdf=df.toString();
        IndisponibilitePlateau ip=new IndisponibilitePlateau();
        ip.setIdPlateaux(this.getId());
        ip.setDate(d);
        if(!ip.ListeIndisponibilite(dao).isEmpty()) return false;
        //String sql="SELECT * FROM Planification WHERE idPlateaux="+this.getId()+" AND Statut=1 AND (DateHeureDebut BETWEEN '"+sdf+"' and '"+sdd+"') OR (DateHeureFin BETWEEN '"+sdf+"' and '"+sdd+"') ";
        //System.err.println(sql);
        //if(!dao.ListeBySQl(sql,Planification.class).isEmpty()) return false;
        return valiny;
    }
    
}
