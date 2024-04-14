package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;
    private static final Logger LOGGER = Logger.getLogger("UserDao");

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    public User getUserByCar(String model, int series) {
        String hql = "from User where car.model = '" + model + "' and car.series = " + series;
        try {
            TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
            return query.getSingleResult();
        } catch (NoResultException e){
            LOGGER.log(Level.WARNING, "Entity not found");
        }
        return null;
    }

}
