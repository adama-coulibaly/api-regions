package com.apiregions.apiregions.Repository;

import com.apiregions.apiregions.Models.Pays;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysRepository extends JpaRepository<Pays, Long> {
   /* @Query(value =" SELECT * FROM `pays` WHERE pays.nom_pays=:nomPays",nativeQuery = true)
    List<String> VerifierPay(String nomPays);

    */

    public Pays findByNompays(String pays);


}
