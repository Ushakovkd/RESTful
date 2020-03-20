package com.web.audio.dao.impl;

import com.web.audio.dao.CommentDao;
import com.web.audio.entity.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Comment findCommentById(int id) {

        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));

        Session session = this.sessionFactory.getCurrentSession();
        Query<Comment> query = session.createQuery(criteriaQuery);
        return query.uniqueResult();
    }

    @Override
    public List<Comment> findSongCommentsBySongId(int songId) {

        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("songId"), songId));

        Session session = this.sessionFactory.getCurrentSession();
        Query<Comment> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public void createComment(Comment comment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(comment);
    }

    @Override
    public void removeComment(Comment comment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.remove(comment);
    }

    @Override
    public void updateCommentTextById(String text, int id) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaUpdate<Comment> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Comment.class);
        Root<Comment> commentRoot = criteriaUpdate.from(Comment.class);
        criteriaUpdate
                .set(commentRoot.get("text"), text)
                .where(criteriaBuilder.equal(commentRoot.get("id"), id));

        Session session = this.sessionFactory.getCurrentSession();
        Query<Comment> query = session.createQuery(criteriaUpdate);
        query.executeUpdate();
    }
}
