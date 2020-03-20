package com.web.audio.dao.impl;

import com.web.audio.dao.MusicCollectionDao;
import com.web.audio.entity.*;
import com.web.audio.entity.field.CollectionType;
import com.web.audio.entity.field.Genre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MusicCollectionDaoImpl implements MusicCollectionDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer saveCollection(MusicCollection collection) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Integer) session.save(collection);
    }

    @Override
    public List<MusicCollection> getAllCollections() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<MusicCollection> criteriaQuery = criteriaBuilder.createQuery(MusicCollection.class);
        Root<MusicCollection> collectionRoot = criteriaQuery.from(MusicCollection.class);

        criteriaQuery.select(collectionRoot);

        Session session = this.sessionFactory.getCurrentSession();
        Query<MusicCollection> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public List<MusicCollection> findMusicCollectionsByVariableNumberParameters(CollectionType type, String artistName,
                                                                                String title, Genre genre) {

        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<MusicCollection> criteriaQuery = criteriaBuilder.createQuery(MusicCollection.class);
        Root<MusicCollection> collectionRoot = criteriaQuery.from(MusicCollection.class);

        List<Predicate> predicates = new ArrayList<>();

        if (artistName != null) {
            predicates.add(criteriaBuilder.equal(collectionRoot.get("artist").get("name"), artistName));
        }
        if (title != null) {
            predicates.add(criteriaBuilder.equal(collectionRoot.get("title"), title));
        }
        if (genre != null) {
            predicates.add(criteriaBuilder.equal(collectionRoot.get("genre"), genre));
        }
        if (type != null) {
            predicates.add(criteriaBuilder.equal(collectionRoot.get("type"), type));
        }
        criteriaQuery.select(collectionRoot)
                .where(predicates.toArray(new Predicate[]{}));

        Session session = this.sessionFactory.getCurrentSession();
        Query<MusicCollection> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public MusicCollection findCollectionById(int id) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<MusicCollection> criteriaQuery = criteriaBuilder.createQuery(MusicCollection.class);
        Root<MusicCollection> collectionRoot = criteriaQuery.from(MusicCollection.class);

        criteriaQuery.select(collectionRoot)
                .where(criteriaBuilder.equal(collectionRoot.get("id"), id));

        Session session = this.sessionFactory.getCurrentSession();
        Query<MusicCollection> query = session.createQuery(criteriaQuery);
        return query.uniqueResult();
    }

    @Override
    public void updateCollectionTitleGenreById(int id, String title, Genre genre) {

        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaUpdate<MusicCollection> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(MusicCollection.class);
        Root<MusicCollection> collectionRoot = criteriaUpdate.from(MusicCollection.class);
        criteriaUpdate
                .set(collectionRoot.get("title"), title)
                .set(collectionRoot.get("genre"), genre)
                .where(criteriaBuilder.equal(collectionRoot.get("id"), id));

        Session session = this.sessionFactory.getCurrentSession();
        session.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public void removeCollection(MusicCollection collection) {
        Session session = this.sessionFactory.getCurrentSession();
        session.remove(collection);
    }
}
