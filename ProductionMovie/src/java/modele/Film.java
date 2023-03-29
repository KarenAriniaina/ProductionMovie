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
import java.time.DayOfWeek;
import java.time.LocalDate;
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
        if (ListeIdFilm != null && !ListeIdFilm.isEmpty()) {
            lcr.add(Restrictions.in("id", ListeIdFilm));
        }
        try {
            valiny = (ArrayList<Film>) dao.FindByCritere(this, lcr, null);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }

    public ArrayList<Film> getListeFilmPaginer(HibernateDAO dao, int debut) throws Exception {
        ArrayList<Film> valiny = new ArrayList<>();
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            lcr.add(Restrictions.like("Titre", "%" + this.getTitre() + "%").ignoreCase());
        }
        if (idAuteur != 0) {
            lcr.add(Restrictions.eq("idAuteur", idAuteur));
        }
        if (ListeIdFilm != null && !ListeIdFilm.isEmpty()) {
            lcr.add(Restrictions.in("id", ListeIdFilm));
        }
        try {
            valiny = (ArrayList<Film>) dao.FindByCriterePaginer(this, lcr, debut, 8, null);
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
        if (ListeIdFilm != null && !ListeIdFilm.isEmpty()) {
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
        ArrayList<Profil> lp = new ArrayList<>();
        FilmActeur fa = new FilmActeur();
        fa.setIdFilm(this.getId());
        List<Integer> LidActeur = fa.getListeIdActeurNonPaginer(dao);
        Profil p = new Profil();
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (!LidActeur.isEmpty()) {
            lcr.add(Restrictions.in("id", LidActeur));
        }
        lp = (ArrayList<Profil>) dao.FindByCritere(p, lcr, null);
        return lp;
    }

    public void getFilm(HibernateDAO dao) throws Exception {
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            lcr.add(Restrictions.like("Titre", "%" + this.getTitre() + "%").ignoreCase());
        }
        if (id != 0) {
            lcr.add(Restrictions.eq("id", id));
        }
        try {
            ArrayList<Film> lf = (ArrayList<Film>) dao.FindByCritere(this, lcr, null);
            Film f = lf.get(0);
            this.setId(f.getId());
            this.setTitre(f.getTitre());
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Integer> getListeFromString(String lscene) {
        ArrayList<Integer> valiny = new ArrayList<>();
        String[] Liste = lscene.split(",");
        if (Liste.length != 0) {
            for (String i : Liste) {
                valiny.add(Integer.valueOf(i));
            }
        }
        return valiny;
    }

    public ArrayList<PropositionPlanning> ProgrammerScene(Date Dd, Date Df, String lscene, HibernateDAO dao) throws Exception {
        ArrayList<Integer> ls = this.getListeFromString(lscene);
        // ArrayList<Planification> lp = new ArrayList<>();
        ArrayList<PropositionPlanning> pr = new ArrayList<>();
        ArrayList<ArrayList<Planification>> lpr = new ArrayList<>();
        ArrayList<Planification> Intermediaire = new ArrayList<>();
        ArrayList<Scene> lsc = new Scene().ListeSceneBySQLOrderByPlateau(dao, ls);
        if (ls.isEmpty()) {
            throw new Exception("Aucun scene saisie");
        }
        Time Debut = new Time(8, 0, 0);
        Date d = Dd;
        int nbrp = 0;
        LocalDate ld = LocalDate.parse(d.toString());
        LocalDate ldf = LocalDate.parse(Df.toString());
        while (!ld.isAfter(ldf)) {
            Intermediaire = new ArrayList<>();
            if (this.isWeekend(ld)) {
                ld = ld.plusDays(1);
            } else {
                PropositionPlanning pl = new PropositionPlanning();
                pl.setDate(Date.valueOf(ld));
                int plateaux = 0;
                for (int i = 0; i < lsc.size(); i++) {
                    Scene s = lsc.get(i);
                    if (s.getStatut() != 2) {
                        if (s.possibleTournage(Date.valueOf(ld), dao)) {
                            Time t = s.getDureeScene();
                            Debut = new Time(t.getHours() + Debut.getHours(), t.getMinutes() + Debut.getMinutes(), 0);
                            Planification p = new Planification();
                            p.setDate(Date.valueOf(ld));
                            p.setIdScene(s.getId());
                            p.setSceneTourner(s);
                            p.setStatut(0);
                            Plateaux pa = new Plateaux();
                            pa.setId(s.getIdPlateaux());
                            pa.getPlateau(dao);
                            p.setPlateauxTourner(pa);
                            lsc.get(i).setStatut(2);
                            System.err.println(plateaux);
                            if (plateaux == 0) {
                                plateaux = s.getIdPlateaux();
                                nbrp = 1;
                            } else {
                                if (s.getIdPlateaux() != plateaux) {
                                    lpr.add(Intermediaire);
                                    Intermediaire = new ArrayList<>();
                                    plateaux = s.getIdPlateaux();
                                    nbrp++;
                                }
                            }
                            if (nbrp <= 2) {
                                lsc.get(i).setStatut(2);
                                dao.Create(p);
                                p.getPlanification(dao);
                                Intermediaire.add(p);
                            }
                            //id++;
                        }
                    }
                    if (nbrp >= 2 || Debut.after(Time.valueOf("08:15:00")) || Debut.compareTo(Time.valueOf("08:15:00")) == 0) {
                        if(nbrp>=2){
                            nbrp=0;
                        }
                        break;
                    }
                }
                if (!Intermediaire.isEmpty()) {
                    lpr.add(Intermediaire);
                }
                if (!lpr.isEmpty()) {
                    pl.setListePlanifications(lpr);
                }
                lpr = new ArrayList<>();
                pr.add(pl);
                ld = ld.plusDays(1);
                Debut = new Time(8, 0, 0);
            }
        }
//        for (PropositionPlanning p : pr) {
//            System.err.println(p.getDate());
//            if (p.getListePlanifications() != null && !p.getListePlanifications().isEmpty()) {
//                for (ArrayList<Planification> pl : p.getListePlanifications()) {
//                    System.err.println(pl.get(0).getPlateauxTourner().getNom());
//                    for (Planification pa : pl) {
//                        System.err.println(pa.getSceneTourner().getTitre());
//                    }
//                }
//            }
//        }
        return pr;
    }

    public boolean isWeekend(LocalDate d) throws Exception {
        return d.getDayOfWeek() == DayOfWeek.SATURDAY || d.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}

//                for (int i = 0; i < lsc.size(); i++) {
//                    Scene s = lsc.get(i);
//                    if (s.getStatut() != 2) {
//                        while (!Debut.after(Time.valueOf("18:00:00"))) {
//                            Time dures = s.getDureeScene();
//                            Time fin = new Time(Debut.getHours() + dures.getHours(), Debut.getMinutes() + dures.getMinutes(), 0);
//                            Timestamp de = Timestamp.valueOf(Date.valueOf(ld).toString() + " " + Debut.toString());
//                            Timestamp df = Timestamp.valueOf(Date.valueOf(ld).toString() + " " + fin.toString());
//                            if (s.possibleTournage(d, de, df, dao)) {
//                                System.err.println("miditra ato");
//                                Planification p = new Planification();
//                                p.setDateHeureDebut(de);
//                                p.setDateHeureFin(df);
//                                p.setDateTournage(d);
//                                p.setSceneTourner(s);
//                                p.setIdPlateaux(s.getIdPlateaux());
//                                p.setIdScene(s.getId());
//                                p.setStatut(0);
//                                dao.Create(p);
//                                lp.add(p);
//                                lsc.get(i).setStatut(2);
//                                break;
//                            }
//                            Debut = new Time(Debut.getHours(), Debut.getMinutes() + 1, 0);
//                        }
//                    }
//                }
//while (!ld.isAfter(ldf)) {
//            if (this.isWeekend(ld)) {
//                ld = ld.plusDays(1);
//            } else {
//                for (int i = 0; i < lsc.size(); i++) {
//                    Scene s = lsc.get(i);
//                    if (s.getStatut() != 2) {
//                        if(s.possibleTournage(d,dao)){
//                            Time t=s.getDureeScene();
//                            Debut=new Time(t.getHours()+Debut.getHours(), t.getMinutes()+Debut.getMinutes(), 0);
//                            Planification p=new Planification();
//                            p.setDate(Date.valueOf(ld));
//                            p.setIdScene(s.getId());
//                            p.setSceneTourner(s);
//                            p.setStatut(0);
//                            //dao.Create(p);
//                            //p.getPlanification(dao);
//                            lp.add(p);
//                        }
//                    }
//                    if(Debut.after(Time.valueOf("10:00:00"))) break;
//                }
//                ld = ld.plusDays(1);
//                Debut = new Time(8, 0, 0);
//            }
//        }
