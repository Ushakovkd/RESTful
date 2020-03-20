package com.web.audio.entity;


import javax.persistence.*;

@Entity
@Table(name = "artist")
public class Artist implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private int id;
    @Column(name = "artist_name")
    private String name;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Artist{" + "id=" + id + ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Artist artist = (Artist) object;
        if (this.id != artist.id) {
            return false;
        }
        if (!this.name.equals(artist.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * id + name.hashCode();
    }
}
