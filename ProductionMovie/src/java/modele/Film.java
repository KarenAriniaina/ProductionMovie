/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import hibernate.HibernateDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ari
 */
@Entity
public class Film {

    @Id
    private int id;

    @Column
    private String Titre;

    @Column
    private int idAuteur;
    
    @Transient
    private List<Integer> ListeIdFilm;

    public List<Integer> getListeIdFilm() {
        return ListeIdFilm;
    }

    public void setListeIdFilm(List<Integer> ListeIdFilm) {
        this.ListeIdFilm = ListeIdFilm;
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

    public int getIdAuteur() {
        return idAuteur;
    }

    public void setIdAuteur(int idAuteur) {
        this.idAuteur = idAuteur;
    }

    public ArrayList<Film> getListeFilmNonPaginer(HibernateDAO dao) throws Exception {
        ArrayList<Film> valiny = new ArrayList<>();
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            lcr.add(Restrictions.like("Titre", "%" + this.getTitre() + "%").ignoreCase());
        }
        if (idAuteur != 0) {
            lcr.add(Restrictions.eq("idAuteur", idAuteur));
        }
        if(ListeIdFilm!=null && !ListeIdFilm.isEmpty()){
            lcr.add(Restrictions.in("id", ListeIdFilm));
        }
        try {
            valiny = (ArrayList<Film>) dao.FindByCritere(this, lcr);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }
    
    public ArrayList<Film> getListeFilmPaginer(HibernateDAO dao,int debut) throws Exception {
        ArrayList<Film> valiny = new ArrayList<>();
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            lcr.add(Restrictions.like("Titre", "%" + this.getTitre() + "%").ignoreCase());
        }
        if (idAuteur != 0) {
            lcr.add(Restrictions.eq("idAuteur", idAuteur));
        }
        if(ListeIdFilm!=null && !ListeIdFilm.isEmpty()){
            lcr.add(Restrictions.in("id", ListeIdFilm));
        }
        try {
            valiny = (ArrayList<Film>) dao.FindByCriterePaginer(this, lcr,debut,8,null);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }
    
    public int getNbrFilmPaginer(HibernateDAO dao) throws Exception {
        int valiny = 0;
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            lcr.add(Restrictions.like("Titre", "%" + this.getTitre() + "%").ignoreCase());
        }
        if (idAuteur != 0) {
            lcr.add(Restrictions.eq("idAuteur", idAuteur));
        }
        if(ListeIdFilm!=null && !ListeIdFilm.isEmpty()){
            lcr.add(Restrictions.in("id", ListeIdFilm));
        }
        try {
            valiny = dao.GetNbrParCritere(this, lcr);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }

    public List<Integer> getListeIdSelonFilmNonPaginer(HibernateDAO dao) throws Exception {
        List<Integer> lidFilm = new ArrayList<>();
        ArrayList<Film> lf = this.getListeFilmNonPaginer(dao);

        if (!lf.isEmpty()) {
            for (Film fi : lf) {
                lidFilm.add(fi.getId());
            }
        }
        return lidFilm;
    }

    public ArrayList<Profil> getListeActeur(HibernateDAO dao) throws Exception {
        ArrayList<Profil> lp =new ArrayList<>();
        FilmActeur fa=new FilmActeur();
        fa.setIdFilm(this.getId());
        List<Integer> LidActeur=fa.getListeIdActeurNonPaginer(dao);
        Profil p=new Profil();
        ArrayList<Criterion> lcr=new ArrayList<>();
        if(!LidActeur.isEmpty()){
            lcr.add(Restrictions.in("id", LidActeur));
        }
        lp=(ArrayList<Profil>) dao.FindByCritere(p, lcr);
        return lp;
    }

    public void getFilm(HibernateDAO dao) throws Exception{
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            lcr.add(Restrictions.like("Titre", "%" + this.getTitre() + "%").ignoreCase());
        }
        if (id != 0) {
            lcr.add(Restrictions.eq("id", id));
        }
        try {
            ArrayList<Film> lf = (ArrayList<Film>) dao.FindByCritere(this, lcr);
            Film f = lf.get(0);
            this.setId(f.getId());
            this.setTitre(f.getTitre());
        } catch (Exception e) {
            throw e;
        }
    }
}
