package com.apiregions.apiregions.Repository;

import com.apiregions.apiregions.Models.Commentaire;
import org.springframework.data.jpa.mapping.JpaPersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire,Long> {

    Commentaire findByObjet(Commentaire objets);
}
