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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ari
 */
@Entity
public class FilmRealisateur {

    @Column
    private int idRealisateur;
    
    @Id
    private int idFilm;

    public int getIdRealisateur() {
        return idRealisateur;
    }

    public void setIdRealisateur(int idRealisateur) {
        this.idRealisateur = idRealisateur;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }
    
    public ArrayList<FilmRealisateur> getListeFilmRealisateursNonPaginer(HibernateDAO dao) throws Exception {
        ArrayList<FilmRealisateur> valiny = new ArrayList<>();
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (idFilm != 0) {
            lcr.add(Restrictions.eq("idRealisateur", idRealisateur));
        }
        try {
            valiny = (ArrayList<FilmRealisateur>) dao.FindByCritere(this, lcr);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }

    public List<Integer> getListeIdFilmNonPaginer(HibernateDAO dao) throws Exception {
        List<Integer> lidActeur=new ArrayList<>();
        ArrayList<FilmRealisateur> lsca = this.getListeFilmRealisateursNonPaginer(dao);
        if (!lsca.isEmpty()) {
            for (FilmRealisateur fa : lsca) {
                lidActeur.add(fa.getIdFilm());
            }
        }
        return lidActeur;
    }
}
