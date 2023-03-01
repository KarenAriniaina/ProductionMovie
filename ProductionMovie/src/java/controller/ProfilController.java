/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.HibernateDAO;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;
import modele.Profil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ari
 */
@Controller
public class ProfilController {

    @Autowired
    private HibernateDAO dao;

    public HibernateDAO getDao() {
        return dao;
    }

    public void setDao(HibernateDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Connexion")
    public String Connexion(HttpSession session) throws Exception {
        if (session.getAttribute("idActeur") != null || session.getAttribute("idAuteur") != null || session.getAttribute("idRealisateur") != null) {
            //redirect to accueil
            return "redirect:/";
        }
        return "Connexion";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/Deconnexion")
    public String Deconnexion(HttpSession session) throws Exception {
        session.removeAttribute("idActeur");
        session.removeAttribute("idAuteur");
        session.removeAttribute("idRealisateur");
        session.removeAttribute("Nom");
        return "redirect:/Connexion";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ConnexionSubmit")
    public String ConnexionSubmit(Model model, @ModelAttribute Profil p, HttpSession session) throws Exception {
        if (session.getAttribute("idActeur") != null || session.getAttribute("idAuteur") != null || session.getAttribute("idRealisateur") != null) {
            //redirect to accueil
            return "redirect:/";
        }
        try {
            p.Login(dao);
            if(p.getRole()==1)session.setAttribute("idAuteur", p.getId());
            if(p.getRole()==2)session.setAttribute("idRealisateur", p.getId());
            if(p.getRole()==3)session.setAttribute("idActeur", p.getId());
            session.setAttribute("Nom", p.getNom());
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "Connexion";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String Test() throws Exception {

        return "test";
    }

}
