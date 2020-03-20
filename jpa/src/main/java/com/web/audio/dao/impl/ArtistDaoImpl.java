package com.web.audio.dao.impl;

import com.web.audio.dao.ArtistDao;
import com.web.audio.entity.Artist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Component
public class ArtistDaoImpl implements ArtistDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Artist> findArtistById(int id) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Artist> criteriaQuery = criteriaBuilder.createQuery(Artist.class);
        Root<Artist> artistRoot = criteriaQuery.from(Artist.class);

        criteriaQuery.select(artistRoot).where(criteriaBuilder.equal(artistRoot.get("id"), id));

        Session session = this.sessionFactory.getCurrentSession();
        Query<Artist> query = session.createQuery(criteriaQuery);
        return query.uniqueResultOptional();
    }

    @Override
    public Optional<Artist> findArtistByName(String name) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Artist> criteriaQuery = criteriaBuilder.createQuery(Artist.class);
        Root<Artist> artistRoot = criteriaQuery.from(Artist.class);

        criteriaQuery.select(artistRoot).where(criteriaBuilder.equal(artistRoot.get("name"), name));

        Session session = this.sessionFactory.getCurrentSession();
        Query<Artist> query = session.createQuery(criteriaQuery);
        return query.uniqueResultOptional();
    }

    @Override
    public Integer createArtist(Artist artist) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Integer) session.save(artist);
    }
    @Override
    public void removeArtist(Artist artist) {
        Session session = this.sessionFactory.getCurrentSession();
        session.remove(artist);
    }
    @Override
    public void updateArtistNameById(String name, int id) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaUpdate<Artist> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Artist.class);
        Root<Artist> artistRoot = criteriaUpdate.from(Artist.class);
        criteriaUpdate
                .set(artistRoot.get("name"), name)
                .where(criteriaBuilder.equal(artistRoot.get("id"), id));

        Session session = this.sessionFactory.getCurrentSession();
        Query<Artist> query = session.createQuery(criteriaUpdate);
        query.executeUpdate();
    }
}
