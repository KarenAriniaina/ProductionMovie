/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import hibernate.HibernateDAO;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Ari
 */
@Entity
public class Profil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String Login;

    @Column
    private String Mdp;

    @Column
    private String Nom;

    @Column
    private int Role; // 1:Auteur 2:Realisateur 3:Acteur

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) throws Exception {
        if (Login.equalsIgnoreCase("")) {
            throw new Exception("Login vide");
        }
        this.Login = Login;
    }

    public String getMdp() {
        return Mdp;
    }

    public void setMdp(String Mdp) throws Exception {
        if (Mdp.equalsIgnoreCase("")) {
            throw new Exception("Mdp vide");
        }
        this.Mdp = Mdp;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int Role) {
        this.Role = Role;
    }

    public void Login(HibernateDAO dao) throws Exception {
        try {
            boolean verif = false;
            int i = 0;
            while (i < 3) {
                this.setRole(i+1);
                List la = dao.FindWhere(this);
                System.err.println(la.size());
                if (la != null && !la.isEmpty()) {
                    Profil p = (Profil) la.get(0);
                    this.setId(p.getId());
                    this.setLogin(p.getLogin());
                    this.setMdp(p.getMdp());
                    this.setNom(p.getNom());
                    this.setRole(p.getRole());
                    verif = true;
                    break;
                }
                i++;
            }
            if (!verif) {
                throw new Exception("There was an error");
            }

        } catch (Exception e) {
            throw e;
        }
    }

}
