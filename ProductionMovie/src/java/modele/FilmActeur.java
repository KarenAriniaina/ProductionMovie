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
public class FilmActeur {

    @Column
    private int idFilm;

    @Id
    private int idActeur;

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public int getIdActeur() {
        return idActeur;
    }

    public void setIdActeur(int idActeur) {
        this.idActeur = idActeur;
    }

    public ArrayList<FilmActeur> getListeFilmActeursNonPaginer(HibernateDAO dao) throws Exception {
        ArrayList<FilmActeur> valiny = new ArrayList<>();
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (idFilm != 0) {
            lcr.add(Restrictions.eq("idFilm", idFilm));
        }
        if (idActeur != 0) {
            lcr.add(Restrictions.eq("idActeur", idActeur));
        }
        try {
            valiny = (ArrayList<FilmActeur>) dao.FindByCritere(this, lcr);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }

    public List<Integer> getListeIdActeurNonPaginer(HibernateDAO dao) throws Exception {
        List<Integer> lidActeur = new ArrayList<>();
        ArrayList<FilmActeur> lsca = this.getListeFilmActeursNonPaginer(dao);
        if (!lsca.isEmpty()) {
            for (FilmActeur fa : lsca) {
                lidActeur.add(fa.getIdActeur());
            }
        }
        return lidActeur;
    }
    
    public List<Integer> getListeIdFilmNonPaginer(HibernateDAO dao) throws Exception {
        List<Integer> lidActeur = new ArrayList<>();
        ArrayList<FilmActeur> lsca = this.getListeFilmActeursNonPaginer(dao);
        if (!lsca.isEmpty()) {
            for (FilmActeur fa : lsca) {
                lidActeur.add(fa.getIdFilm());
            }
        }
        return lidActeur;
    }

}
