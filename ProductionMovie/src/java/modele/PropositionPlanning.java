/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Ari
 */
public class PropositionPlanning {
    private Date Date;
    private ArrayList<ArrayList<Planification>> ListePlanifications;

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public ArrayList<ArrayList<Planification>> getListePlanifications() {
        return ListePlanifications;
    }

    public void setListePlanifications(ArrayList<ArrayList<Planification>> ListePlanifications) {
        this.ListePlanifications = ListePlanifications;
    }
    
    
}
