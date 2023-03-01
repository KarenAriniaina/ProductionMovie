/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.HibernateDAO;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Deroulement;
import modele.Emotion;
import modele.Film;
import modele.FilmActeur;
import modele.FilmRealisateur;
import modele.Plateaux;
import modele.Scene;
import modele.SceneActeur;
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
public class SceneController {

    @Autowired
    private HibernateDAO dao;

    public HibernateDAO getDao() {
        return dao;
    }

    public void setDao(HibernateDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String ListeScene(Model model, @RequestParam(value = "Titre", required = false, defaultValue = "") String Titre,
            @RequestParam(value = "TitreFilm", required = false, defaultValue = "") String TitreFilm,
            @RequestParam(value = "TimeDebut", required = false, defaultValue = "") String TimeDebut,
            @RequestParam(value = "TimeFin", required = false, defaultValue = "") String TimeFin,
            @RequestParam(value = "idPlateaux", defaultValue = "0") int idPlateaux,
            @RequestParam(value = "currentpage", defaultValue = "1") int currentpage, HttpSession session) throws Exception {
        Scene sc = new Scene();
        sc.setIdPlateaux(idPlateaux);
        sc.setTitre(Titre);
        if (!TimeDebut.equalsIgnoreCase("")) {
            sc.setTimeDebut(TimeDebut.split(",")[0]);
        }
        if (!TimeFin.equalsIgnoreCase("")) {
            sc.setTimeFin(TimeFin.split(",")[0]);
        }
        String link = "";
        link = sc.LiendeRecherche(Titre, TitreFilm, idPlateaux);
        Film f = new Film();
        f.setTitre(TitreFilm);

        if (session.getAttribute(
                "idAuteur") != null) {
            f.setIdAuteur((int) session.getAttribute("idAuteur"));
        }
        if(session.getAttribute("idRealisateur")!=null){
            FilmRealisateur fr=new FilmRealisateur();
            fr.setIdRealisateur((int) session.getAttribute("idRealisateur"));
            f.setListeIdFilm(fr.getListeIdFilmNonPaginer(dao));
        }
        if (session.getAttribute("idActeur") != null) {
            FilmActeur fa=new FilmActeur();
            fa.setIdActeur((int) session.getAttribute("idActeur"));
            f.setListeIdFilm(fa.getListeIdFilmNonPaginer(dao));
        }
        List<Integer> lidFilm = f.getListeIdSelonFilmNonPaginer(dao);
        sc.setListePossibleIdFilm(lidFilm);
        if (session.getAttribute("idActeur") != null) {
            SceneActeur sca = new SceneActeur();
            sca.setIdActeur((int) session.getAttribute("idActeur"));
            List<Integer> lidScene = sca.getListeIdSceneNonPaginer(dao);
            sc.setListePossibleIdScene(lidScene);
        }
        model.addAttribute("currentpage", currentpage);
        int nbrrecord = sc.NbrCriterepaginer(dao);
        int nbrPage = nbrrecord / 8;
        if ((nbrrecord % 8) != 0) {
            nbrPage++;
        }
        model.addAttribute("lien", link);
        model.addAttribute("nbrPage", nbrPage);
        model.addAttribute("lsc", sc.getListeScene(dao, 8 * (currentpage) - 8));
        model.addAttribute("lp", dao.FindByCritere(new Plateaux(), null));
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/AvantAjoutScene")
    public String AvantAjoutScene(Model model, HttpSession session) throws Exception {
        if (session.getAttribute("idAuteur") == null) {
            return "redirect:/";
        }
        Film f = new Film();
        f.setIdAuteur((int) session.getAttribute("idAuteur"));
        model.addAttribute("lf", f.getListeFilmNonPaginer(dao));
        return "AvantAjoutScene";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/AjoutScene")
    public String AjoutScene(Model model, HttpSession session, HttpServletRequest request) throws Exception {
        if (session.getAttribute("idAuteur") == null) {
            return "redirect:/";
        }
        int idFilm = 0;
        if (request.getParameter("idFilm") != null && !request.getParameter("idFilm").equalsIgnoreCase("")) {
            idFilm = Integer.parseInt(request.getParameter("idFilm"));
        }
        if (idFilm == 0) {
            return "redirect:/AvantAjoutScene";
        }
        Film f = new Film();
        f.setId(idFilm);
        model.addAttribute("error", request.getAttribute("error"));
        model.addAttribute("lp", dao.FindByCritere(new Plateaux(), null));
        model.addAttribute("idFilm", idFilm);
        model.addAttribute("la", f.getListeActeur(dao));
        model.addAttribute("le", new Emotion().getListeEmotions(dao));
        return "AjoutScene";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/AjoutSceneSubmit")
    public String AjoutSceneSubmit(Model model, HttpSession session, HttpServletRequest request) throws Exception {
        if (session.getAttribute("idAuteur") == null) {
            return "redirect:/";
        }
        Scene sc = new Scene();
        if (request.getParameter("idFilm") != null && !request.getParameter("idFilm").equalsIgnoreCase("")) {
            sc.setIdFilm(Integer.parseInt(request.getParameter("idFilm")));
        }
        if (request.getParameter("DureeScene") != null && !request.getParameter("DureeScene").equalsIgnoreCase("")) {
            sc.setDureeScene(Time.valueOf(LocalTime.parse(request.getParameter("DureeScene"))));
        }
        if (request.getParameter("idPlateaux") != null && !request.getParameter("idPlateaux").equalsIgnoreCase("")) {
            sc.setIdPlateaux(Integer.parseInt(request.getParameter("idPlateaux")));
        }
        if (request.getParameter("Titre") != null && !request.getParameter("Titre").equalsIgnoreCase("")) {
            sc.setTitre(request.getParameter("Titre"));
        }
        if (sc.getIdFilm() == 0) {
            return "redirect:/AvantAjoutScene";
        }
        if (sc.getDureeScene() == null || sc.getIdPlateaux() == 0 || sc.getTitre() == null) {
            return "redirect:/AjoutScene?idFilm=" + sc.getIdFilm();
        }
        if (request.getParameter("lacteur") != null && !request.getParameter("lacteur").equalsIgnoreCase("")) {
            String[] lidAuteur = request.getParameter("lacteur").split(",");
            System.err.println(lidAuteur[0]);
            sc.setListeA(lidAuteur);
        }
        sc.setListeDeroulement(this.ListeDeroulement(request));
        System.err.println(sc.getListeDeroulement().size());
        try {
            sc.CreateScene(dao);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "redirect:/AjoutScene?idFilm=" + sc.getIdFilm();
        }
        return "redirect:/";
    }

    public ArrayList<Deroulement> ListeDeroulement(HttpServletRequest request) throws Exception {
        ArrayList<Deroulement> ld = new ArrayList<>();
        int nbrD = Integer.parseInt(request.getParameter("nbrscenario"));
        while (nbrD > 0) {
            System.err.println("Textevalue" + nbrD);
            System.err.println(request.getParameter("Textevalue" + nbrD)+" "+request.getParameter("Ordrevalue" + nbrD));
            if (request.getParameter("Textevalue" + nbrD) != null && request.getParameter("Ordrevalue" + nbrD) != null) {
                        System.err.println("miditra ato");
                Deroulement d = new Deroulement();
                d.setTexte(request.getParameter("Textevalue" + nbrD));
                d.setOrdre(Integer.parseInt(request.getParameter("Ordrevalue" + nbrD)));
                if (request.getParameter("Acteurvalue" + nbrD) != null && !request.getParameter("Acteurvalue" + nbrD).equalsIgnoreCase("")) {
                    d.setIdActeur(Integer.parseInt(request.getParameter("Acteurvalue" + nbrD)));
                }
                if (request.getParameter("Emotionvalue" + nbrD) != null && !request.getParameter("Emotionvalue" + nbrD).equalsIgnoreCase("")) {
                    d.setEmotion(request.getParameter("Emotionvalue" + nbrD));
                }
                ld.add(d);
            }
            nbrD--;
        }
        return ld;
    }

}
