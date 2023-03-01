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
public class SceneActeur {

    @Id
    private int idScene;

    @Column
    private int idActeur;

    public int getIdScene() {
        return idScene;
    }

    public void setIdScene(int idScene) {
        this.idScene = idScene;
    }

    public int getIdActeur() {
        return idActeur;
    }

    public void setIdActeur(int idActeur) {
        this.idActeur = idActeur;
    }

    public ArrayList<SceneActeur> getListeActeurSceneNonPaginer(HibernateDAO dao) throws Exception {
        ArrayList<SceneActeur> valiny = new ArrayList<>();
        ArrayList<Criterion> lcr = new ArrayList<>();
        if (idActeur != 0) {
            lcr.add(Restrictions.eq("idActeur", idActeur));
        }
        try {
            valiny = (ArrayList<SceneActeur>) dao.FindByCritere(this, lcr);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }

    public List<Integer> getListeIdSceneNonPaginer(HibernateDAO dao) throws Exception {
        List<Integer> lidScene=new ArrayList<>();
        ArrayList<SceneActeur> lsca = this.getListeActeurSceneNonPaginer(dao);
        if (!lsca.isEmpty()) {
            for (SceneActeur ca : lsca) {
                lidScene.add(ca.getIdScene());
            }
        }
        return lidScene;
    }
}
