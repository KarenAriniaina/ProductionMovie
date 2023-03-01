/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.SessionFactoryImpl;

/**
 *
 * @author karen
 */
public class HibernateDAO {

    private SessionFactoryImpl SF;

    public void Create(Object o) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = getSessionFactory().openSession();
            tx = s.beginTransaction();
            s.save(o);
            tx.commit();

        } catch (Throwable e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public List getAll(Object o) throws Exception {
        Session s = null;
        //Transaction tx = null;
        List valiny = null;
        try {
            s = getSessionFactory().openSession();
            Criteria c = s.createCriteria(o.getClass());
            valiny = c.list();
        } catch (Throwable e) {
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
        }

        return valiny;
    }

    public SessionFactoryImpl getSessionFactory() throws Exception {
        /*if (SF == null) {
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
            try {
                setSessionFactory(config.buildSessionFactory());
            } catch (Throwable e) {
                throw e;
            }
        }*/
        return SF;
    }

    public void setSF(org.hibernate.internal.SessionFactoryImpl sf) {
        this.SF = sf;
    }

    public void Upadte(Object o) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = getSessionFactory().openSession();
            tx = s.beginTransaction();
            s.update(o);
            tx.commit();

        } catch (Throwable e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public List FindWhere(Object o) throws Exception {
        Session session = null;
        List results = null;
        try {
            session = getSessionFactory().openSession();
            Example example = Example.create(o).ignoreCase();
            results = session.createCriteria(o.getClass()).add(example).list();
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return results;
    }

    public List FindByCritere(Object o, ArrayList<Criterion> lc) throws Exception {
        Session s = null;
        List valiny = null;
        try {
            s = getSessionFactory().openSession();
            Criteria c = s.createCriteria(o.getClass());
            if (lc!=null && !lc.isEmpty()) {
                lc.forEach((cr) -> {
                    c.add(cr);
                });
            }
            valiny = c.list();
        } catch (Throwable e) {
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
        }

        return valiny;
    }

    public List FindByCriterePaginer(Object o, ArrayList<Criterion> lc, int debut, int nbr,String order) throws Exception {
        Session s = null;
        List valiny = null;
        try {
            s = getSessionFactory().openSession();
            Criteria c = s.createCriteria(o.getClass());
            if (!lc.isEmpty()) {
                lc.forEach((cr) -> {
                    c.add(cr);
                });
            }
            if(order!=null) c.addOrder(Order.desc(order));
            c.setFirstResult(debut)
                    .setMaxResults(nbr);
            valiny = c.list();
        } catch (Throwable e) {
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
        }

        return valiny;
    }

    public int GetNbr(Object o) throws Exception {
        Session s = null;
        int valiny = 0;
        try {
            s = getSessionFactory().openSession();
            Criteria c = s.createCriteria(o.getClass());
            c.setProjection(Projections.rowCount());
            valiny = Integer.parseInt(c.uniqueResult().toString());
        } catch (Throwable e) {
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
        }
        return valiny;
    }

    public int GetNbrParCritere(Object o, ArrayList<Criterion> lc) throws Exception {
        Session s = null;
        int valiny = 0;
        try {
            s = getSessionFactory().openSession();
            Criteria c = s.createCriteria(o.getClass());
            if (!lc.isEmpty()) {
                lc.forEach((cr) -> {
                    c.add(cr);
                });
            }
            c.setProjection(Projections.rowCount());
            valiny = Integer.parseInt(c.uniqueResult().toString());
        } catch (Throwable e) {
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
        }
        return valiny;
    }
    
     public List FindOrderBy(Object o, ArrayList<Criterion> lc,String order) throws Exception {
        Session s = null;
        List valiny = null;
        try {
            s = getSessionFactory().openSession();
            Criteria c = s.createCriteria(o.getClass());
            if (!lc.isEmpty()) {
                lc.forEach((cr) -> {
                    c.add(cr);
                });
            }
            if(order!=null) c.addOrder(Order.desc(order));
            valiny = c.list();
        } catch (Throwable e) {
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
        }

        return valiny;
    }
}
