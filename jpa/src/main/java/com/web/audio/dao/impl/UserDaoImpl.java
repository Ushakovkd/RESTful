package com.web.audio.dao.impl;

import com.web.audio.dao.UserDao;
import com.web.audio.entity.User;
import com.web.audio.entity.field.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> getUsers() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot);

        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public Optional<User> findUserById(int userId) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("id"), userId));

        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(criteriaQuery);
        return query.uniqueResultOptional();
    }

    @Override
    @Transactional
    public Optional<User> findUserByLogin(String login) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("login"), login));

        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(criteriaQuery);
        return query.uniqueResultOptional();
    }

    @Override
    public void saveUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    public void removeUser(int id) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaDelete<User> criteriaDelete = criteriaBuilder.createCriteriaDelete(User.class);

        Root<User> userRoot = criteriaDelete.from(User.class);
        criteriaDelete.where(criteriaBuilder.equal(userRoot.get("id"), id));

        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(criteriaDelete);
        query.executeUpdate();
    }

    @Override
    public List<User> getAllCustomers() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("role"), Role.CUSTOMER));

        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public void updateUserDiscountAndBlock(String userId, int discount, boolean block) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaUpdate<User> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
        Root<User> userRoot = criteriaUpdate.from(User.class);
        criteriaUpdate
                .set(userRoot.get("discount"), discount)
                .set(userRoot.get("block"), block)
                .where(criteriaBuilder.equal(userRoot.get("id"), userId));

        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(criteriaUpdate);
        query.executeUpdate();
    }
}
