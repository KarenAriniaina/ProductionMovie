/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.HibernateDAO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Film;
import modele.FilmActeur;
import modele.FilmRealisateur;
import modele.Planification;
import modele.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ari
 */
@Controller
public class FilmController {

    @Autowired
    private HibernateDAO dao;

    public HibernateDAO getDao() {
        return dao;
    }

    public void setDao(HibernateDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ListeFilm")
    public String ListeFilm(Model model, HttpSession session, @RequestParam(value = "currentpage", defaultValue = "1") int currentpage) throws Exception {
        Film f = new Film();
        if (session.getAttribute("idAuteur") != null) {
            f.setIdAuteur((int) session.getAttribute("idAuteur"));
        }
        if (session.getAttribute("idRealisateur") != null) {
            FilmRealisateur fr = new FilmRealisateur();
            fr.setIdRealisateur((int) session.getAttribute("idRealisateur"));
            f.setListeIdFilm(fr.getListeIdFilmNonPaginer(dao));
        }
        if (session.getAttribute("idActeur") != null) {
            FilmActeur fa = new FilmActeur();
            fa.setIdActeur((int) session.getAttribute("idActeur"));
            f.setListeIdFilm(fa.getListeIdFilmNonPaginer(dao));
        }
        int nbrrecord = f.getNbrFilmPaginer(dao);
        int nbrPage = nbrrecord / 8;
        if ((nbrrecord % 8) != 0) {
            nbrPage++;
        }
        model.addAttribute("nbrPage", nbrPage);
        model.addAttribute("currentpage", currentpage);
        model.addAttribute("lf", f.getListeFilmPaginer(dao, 8 * (currentpage) - 8));
        return "listefilm";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Film")
    public String ListeFilm(Model model, HttpSession session, HttpServletRequest request) throws Exception {
        if (session.getAttribute("idActeur") == null && session.getAttribute("idAuteur") == null && session.getAttribute("idRealisateur") == null) {
            return "redirect:/Connexion";
        }
        Film f = new Film();
        f.setId(Integer.parseInt(request.getParameter("idFilm")));
        f.getFilm(dao);
        model.addAttribute("Film", f);
        Scene sc = new Scene();
        sc.setIdFilm(f.getId());
        //ArrayList<ArrayList<Scene>> lsc=sc.getPlanificationSceneOptimiser(dao);
        //model.addAttribute("lsc",lsc);
        return "FilmSceneDetail";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/DemandePlanification")
    public String DemandePlanification(Model model, HttpSession session, @RequestParam(value = "idFilm", required = true) int idFilm) throws Exception {
        if (session.getAttribute("idActeur") == null && session.getAttribute("idAuteur") == null && session.getAttribute("idRealisateur") == null) {
            return "redirect:/Connexion";
        }
        Film f = new Film();
        f.setId(idFilm);
        f.getFilm(dao);
        model.addAttribute("Film", f);
        Scene sc = new Scene();
        sc.setIdFilm(f.getId());
        sc.setStatut(1);
        ArrayList<Scene> lsc = sc.getListeSceneNonPaginer(dao);
        sc.setStatut(2);
        ArrayList<Scene> lscp = sc.getListeSceneNonPaginer(dao);
        model.addAttribute("lsc", lsc);
        model.addAttribute("lscp", lscp);
        return "DemandePlanification";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/DemandePlanificationSubmit")
    public String DemandePlanificationSubmit(Model model, HttpSession session, @RequestParam(value = "lscene", required = true) String lscene,
            @RequestParam(value = "Dd", required = true) Date Dd,
            @RequestParam(value = "Df", defaultValue = "", required = false) String Df) throws Exception {
        if (session.getAttribute("idActeur") == null && session.getAttribute("idAuteur") == null && session.getAttribute("idRealisateur") == null) {
            return "redirect:/Connexion";
        }
        Date daf = Dd;
        if (Df != null && !Df.equalsIgnoreCase("")) {
            daf = Date.valueOf(Df);
        }
        Film f = new Film();
        model.addAttribute("lp", f.ProgrammerScene(Dd, daf, lscene, dao));
        return "FilmSceneDetail";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/PlanificationSubmit")
    public String PlanificationSubmit(Model model, HttpSession session, @RequestParam(value = "lidP", required = true) String lidP) throws Exception {
        if (session.getAttribute("idActeur") == null && session.getAttribute("idAuteur") == null && session.getAttribute("idRealisateur") == null) {
            return "redirect:/Connexion";
        }
        if (!lidP.equalsIgnoreCase("")) {
            List<Integer> lid = new Film().getListeFromString(lidP);
            if (!lid.isEmpty()) {
                for (int id : lid) {
                    Planification p = new Planification();
                    p.setId(id);
                    p.ValiderPlanification(dao);
                }
            }
        }

        return "redirect:/ListeFilm";
    }
}
