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
import modele.V_Deroulement;
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
            @RequestParam(value = "TimeDebut", required = false, defaultValue = "") String TimeDebut,
            @RequestParam(value = "TimeFin", required = false, defaultValue = "") String TimeFin,
            @RequestParam(value = "idPlateaux", defaultValue = "0") int idPlateaux,
            @RequestParam(value = "Statut", defaultValue = "-1") int Statut,
            @RequestParam(value = "currentpage", defaultValue = "1") int currentpage, HttpSession session) throws Exception {
        Scene sc = new Scene();
        sc.setIdPlateaux(idPlateaux);
        sc.setTitre(Titre);
        sc.setStatut(Statut);
        if (!TimeDebut.equalsIgnoreCase("")) {
            sc.setTimeDebut(TimeDebut.split(",")[0]);
        }
        if (!TimeFin.equalsIgnoreCase("")) {
            sc.setTimeFin(TimeFin.split(",")[0]);
        }
        String link = "";
        link = sc.LiendeRecherche();
        Film f = new Film();
        if (session.getAttribute("idAuteur") != null) {
            sc.setIdAuteur((int) session.getAttribute("idAuteur"));
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
        List<Integer> lidFilm = f.getListeIdSelonFilmNonPaginer(dao);
        if (session.getAttribute("idActeur") != null) {
            SceneActeur.MyEntityId m = new SceneActeur.MyEntityId(0, (int) session.getAttribute("idActeur"));
            SceneActeur sca = new SceneActeur(m);
            //sca.setIdActeur((int) session.getAttribute("idActeur"));
            List<Integer> lidScene = sca.getListeIdSceneNonPaginer(dao);
            sc.setListePossibleIdScene(lidScene);
            sc.setListePossibleIdFilm(lidFilm);
        }
        if (session.getAttribute("idRealisateur") != null) {
            sc.setListePossibleIdFilm(lidFilm);
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
        model.addAttribute("lp", dao.FindByCritere(new Plateaux(), null, null));
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
        model.addAttribute("lp", dao.FindByCritere(new Plateaux(), null, null));
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
        if (request.getParameter("idPlateaux") != null && !request.getParameter("idPlateaux").equalsIgnoreCase("")) {
            sc.setIdPlateaux(Integer.parseInt(request.getParameter("idPlateaux")));
        }
        if (request.getParameter("Statut") != null && !request.getParameter("Statut").equalsIgnoreCase("")) {
            sc.setStatut(Integer.parseInt(request.getParameter("Statut")));
        }
        if (request.getParameter("Titre") != null && !request.getParameter("Titre").equalsIgnoreCase("")) {
            sc.setTitre(request.getParameter("Titre"));
        }
        if (sc.getIdFilm() == 0) {
            return "redirect:/AvantAjoutScene";
        }
        if (sc.getIdPlateaux() == 0 || sc.getTitre() == null) {
            return "redirect:/AjoutScene?idFilm=" + sc.getIdFilm();
        }
        sc.setIdAuteur((int) session.getAttribute("idAuteur"));
        if (request.getParameter("lacteur") != null && !request.getParameter("lacteur").equalsIgnoreCase("")) {
            String[] lidAuteur = request.getParameter("lacteur").split(",");
            sc.setListeA(lidAuteur);
        }
        sc.setListeDeroulement(this.ListeDeroulement(request));
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
        if (request.getParameter("nbrscenario") != null && !request.getParameter("nbrscenario").equalsIgnoreCase("")) {
            int nbrD = Integer.parseInt(request.getParameter("nbrscenario"));
            while (nbrD > 0) {
                if (request.getParameter("Textevalue" + nbrD) != null && request.getParameter("Ordrevalue" + nbrD) != null) {
                    Deroulement d = new Deroulement();
                    d.setTexte(request.getParameter("Textevalue" + nbrD));
                    d.setOrdre(Integer.parseInt(request.getParameter("Ordrevalue" + nbrD)));
                    if (request.getParameter("Acteurvalue" + nbrD) != null && !request.getParameter("Acteurvalue" + nbrD).equalsIgnoreCase("")) {
                        d.setIdActeur(Integer.parseInt(request.getParameter("Acteurvalue" + nbrD)));
                    }
                    if (request.getParameter("Emotionvalue" + nbrD) != null && !request.getParameter("Emotionvalue" + nbrD).equalsIgnoreCase("")) {
                        d.setEmotion(request.getParameter("Emotionvalue" + nbrD));
                    }
                    if (request.getParameter("Dureevalue" + nbrD) != null && !request.getParameter("Dureevalue" + nbrD).equalsIgnoreCase("")) {
                        d.setDuree(Time.valueOf(LocalTime.parse(request.getParameter("Dureevalue" + nbrD))));
                    }
                    ld.add(d);
                }
                nbrD--;
            }
        }
        return ld;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/DetailScene")
    public String DetailScene(Model model, @RequestParam(value = "id", required = true, defaultValue = "") int id, HttpSession session, HttpServletRequest request) throws Exception {
        if (id == 0) {
            return "redirect:/";
        }
        Scene sc = new Scene();
        sc.setId(id);
        sc.getScene(dao);
        Plateaux p = new Plateaux();
        p.setId(sc.getIdPlateaux());
        p.getPlateau(dao);
        V_Deroulement v = new V_Deroulement();
        v.setIdScene(sc.getId());
        System.err.println(sc.getId());
        Film f = new Film();
        f.setId(sc.getIdFilm());
        model.addAttribute("error", request.getAttribute("error"));
        model.addAttribute("lp", dao.FindByCritere(new Plateaux(), null, null));
        model.addAttribute("la", f.getListeActeur(dao));
        model.addAttribute("le", new Emotion().getListeEmotions(dao));
        model.addAttribute("lae", sc.getListeActeur(dao));
        model.addAttribute("ld", v.getDeroulement(dao));
        model.addAttribute("p", p);
        model.addAttribute("scene", sc);
        if (session.getAttribute("idAuteur") != null && sc.getStatut() == 0) {
            return "DetailScene";
        } else {
            return "DetailSceneNonUpdate";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ModfifSceneSubmit")
    public String ModifSceneSubmit(Model model, HttpSession session, HttpServletRequest request) throws Exception {
        if (session.getAttribute("idAuteur") == null) {
            return "redirect:/";
        }
        Scene sc = new Scene();
        if (request.getParameter("idFilm") != null && !request.getParameter("idFilm").equalsIgnoreCase("")) {
            sc.setIdFilm(Integer.parseInt(request.getParameter("idFilm")));
        }
        if (request.getParameter("idScene") != null && !request.getParameter("idScene").equalsIgnoreCase("")) {
            sc.setId(Integer.parseInt(request.getParameter("idScene")));
        }
        if (request.getParameter("idPlateaux") != null && !request.getParameter("idPlateaux").equalsIgnoreCase("")) {
            sc.setIdPlateaux(Integer.parseInt(request.getParameter("idPlateaux")));
        }
        if (request.getParameter("Statut") != null && !request.getParameter("Statut").equalsIgnoreCase("")) {
            sc.setStatut(Integer.parseInt(request.getParameter("Statut")));
        }
        if (request.getParameter("Titre") != null && !request.getParameter("Titre").equalsIgnoreCase("")) {
            sc.setTitre(request.getParameter("Titre"));
        }
        if (sc.getIdFilm() == 0) {
            return "redirect:/AvantAjoutScene";
        }
        if (sc.getIdPlateaux() == 0 || sc.getTitre() == null) {
            return "redirect:/AjoutScene?idFilm=" + sc.getIdFilm();
        }
        if (request.getParameter("lacteursuppr") != null && !request.getParameter("lacteursuppr").equalsIgnoreCase("")) {
            String[] lidAuteur = request.getParameter("lacteursuppr").split(",");
            sc.SupprimerActeur(lidAuteur, dao);
        }
        if (request.getParameter("lacteur") != null && !request.getParameter("lacteur").equalsIgnoreCase("")) {
            String[] lidAuteur = request.getParameter("lacteur").split(",");
            sc.setListeA(lidAuteur);
            sc.AjouterActeurBase(dao);
        }
        if (request.getParameter("lderoulementmodif") != null && !request.getParameter("lderoulementmodif").equalsIgnoreCase("")
                && request.getParameter("lordremodif") != null && !request.getParameter("lordremodif").equalsIgnoreCase("")) {
            String[] lidderoulement = request.getParameter("lderoulementmodif").split(",");
            String[] lordre = request.getParameter("lordremodif").split(",");
            sc.UpdateDeroulement(lidderoulement, lordre, dao);
        }
        if (request.getParameter("lderoulementsuppr") != null && !request.getParameter("lderoulementsuppr").equalsIgnoreCase("")) {
            String[] lidderoulement = request.getParameter("lderoulementsuppr").split(",");
            sc.SupprimerDeroulement(lidderoulement, dao);
        }
        sc.setListeDeroulement(this.ListeDeroulement(request));
        sc.AjouterDeroulementBase(dao);
        Deroulement d = new Deroulement();
        d.setIdScene(sc.getId());
        sc.setListeDeroulement(d.getListeDeroulement(dao));
        sc.CalculDureeScene();
        try {
            dao.Upadte(sc);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/DetailScene?id=" + sc.getId();
    }

}
