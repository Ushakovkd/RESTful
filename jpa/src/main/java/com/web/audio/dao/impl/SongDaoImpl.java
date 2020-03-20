package com.web.audio.dao.impl;

import com.web.audio.dao.SongDao;
import com.web.audio.entity.*;
import com.web.audio.entity.field.Genre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SongDaoImpl implements SongDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer saveSong(Song song) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Integer) session.save(song);
    }

    @Override
    public List<Song> getAllSongs() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Song> criteriaQuery = criteriaBuilder.createQuery(Song.class);
        Root<Song> songRoot = criteriaQuery.from(Song.class);
        criteriaQuery.select(songRoot);

        Session session = this.sessionFactory.getCurrentSession();
        Query<Song> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public List<Song> findSongsByVariableNumberParameters(String artistName, String title, Genre genre) {

        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Song> criteriaQuery = criteriaBuilder.createQuery(Song.class);
        Root<Song> songRoot = criteriaQuery.from(Song.class);

        List<Predicate> predicates = new ArrayList<>();
        if (artistName != null) {
            predicates.add(criteriaBuilder.equal(songRoot.get("artist").get("name"), artistName));
        }
        if (title != null) {
            predicates.add(criteriaBuilder.equal(songRoot.get("title"), title));
        }
        if (genre != null) {
            predicates.add(criteriaBuilder.equal(songRoot.get("genre"), genre));
        }
        criteriaQuery.select(songRoot)
                .where(predicates.toArray(new Predicate[]{}));

        Session session = this.sessionFactory.getCurrentSession();
        Query<Song> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public Optional<Song> findSongById(int songId) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Song> criteriaQuery = criteriaBuilder.createQuery(Song.class);
        Root<Song> songRoot = criteriaQuery.from(Song.class);
        criteriaQuery.select(songRoot).where(criteriaBuilder.equal(songRoot.get("id"), songId));

        Session session = this.sessionFactory.getCurrentSession();
        Query<Song> query = session.createQuery(criteriaQuery);
        return query.uniqueResultOptional();
    }

    @Override
    public void updateSongTitleGenrePriceById(int id, String title, Genre genre, BigDecimal price) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaUpdate<Song> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Song.class);
        Root<Song> songRoot = criteriaUpdate.from(Song.class);
        criteriaUpdate
                .set(songRoot.get("title"), title)
                .set(songRoot.get("genre"), genre)
                .set(songRoot.get("price"), price)
                .where(criteriaBuilder.equal(songRoot.get("id"), id));

        Session session = this.sessionFactory.getCurrentSession();
        Query<Song> query = session.createQuery(criteriaUpdate);
        query.executeUpdate();
    }

    @Override
    public void removeSong(Song song) {
        Session session = this.sessionFactory.getCurrentSession();
        session.remove(song);
    }
}
