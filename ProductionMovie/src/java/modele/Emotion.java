/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import hibernate.HibernateDAO;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Ari
 */
@Entity
public class Emotion {
    
    @Id
    private int id;
    
    @Column
    private String Designation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String Designation) {
        this.Designation = Designation;
    }
    
    public ArrayList<Emotion> getListeEmotions(HibernateDAO dao) throws Exception{
        ArrayList<Emotion> valiny=new ArrayList<>();
        try {
            valiny=(ArrayList<Emotion>) dao.getAll(this);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }
}
