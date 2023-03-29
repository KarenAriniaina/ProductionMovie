/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import hibernate.HibernateDAO;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ari
 */
@Entity
@IdClass(SceneActeur.MyEntityId.class)
@Table(name = "SceneActeur")
public class SceneActeur {

    @Id
    @Column(name = "idScene")
    private int idScene;

    @Id
    @Column(name = "idActeur")
    private int idActeur;

    SceneActeur() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
        if (idScene != 0) {
            lcr.add(Restrictions.eq("idScene", idScene));
        }
        try {
            valiny = (ArrayList<SceneActeur>) dao.FindByCritere(this, lcr,null);
        } catch (Exception e) {
            throw e;
        }
        return valiny;
    }

    public List<Integer> getListeIdActeurNonPaginer(HibernateDAO dao) throws Exception {
        List<Integer> lidActeur = new ArrayList<>();
        ArrayList<SceneActeur> lsca = this.getListeActeurSceneNonPaginer(dao);
        if (!lsca.isEmpty()) {
            for (SceneActeur fa : lsca) {
                lidActeur.add(fa.getIdActeur());
            }
        }
        return lidActeur;
    }

    public List<Integer> getListeIdSceneNonPaginer(HibernateDAO dao) throws Exception {
        List<Integer> lidScene = new ArrayList<>();
        ArrayList<SceneActeur> lsca = this.getListeActeurSceneNonPaginer(dao);
        if (!lsca.isEmpty()) {
            for (SceneActeur ca : lsca) {
                lidScene.add(ca.getIdScene());
            }
        }
        return lidScene;
    }

    public SceneActeur(MyEntityId ei) {
        this.setIdActeur(ei.getIdActeur());
        this.setIdScene(ei.getIdScene());
    }
    
    public boolean isAllActeurDisponible(Date d,HibernateDAO dao) throws  Exception{
        boolean valiny=true;
        ArrayList<Integer> LidActeur=(ArrayList<Integer>) this.getListeIdActeurNonPaginer(dao);
        IndisponibiliteActeur ia=new IndisponibiliteActeur();
        ia.setDate(d);
        if(!ia.ListeIndisponibilite(dao, LidActeur).isEmpty()) return false;
        return valiny;
    }
    
    public static class MyEntityId implements Serializable {

        private int idScene;
        private int idActeur;

        
        // Getters, setters, equals, and hashCode methods

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

        public MyEntityId(int idScene, int idActeur) {
            this.idScene = idScene;
            this.idActeur = idActeur;
        }

        public MyEntityId() {
        }
        
    }
}
