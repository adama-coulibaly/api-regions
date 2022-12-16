package com.apiregions.apiregions.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcommentaire;
    private String objet;
    private String description;


    @JoinColumn(name = "id_users") // ICI IMPORTANT
    @ManyToOne()//cascade=CascadeType.ALL
    private UsersApp usersApp;

    /*
    @JoinColumn(name = "id_regions") // ICI IMPORTANT
    @ManyToOne(cascade=CascadeType.ALL)
     */
    @ManyToOne
    @JoinColumn(name = "regions_id_regions")
    private Regions regions;

}
