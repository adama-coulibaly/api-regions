package com.apiregions.apiregions.Controller;

import com.apiregions.apiregions.Message.ReponseMessage;
import com.apiregions.apiregions.Models.Regions;
import com.apiregions.apiregions.Sevices.RegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/projet/odk/Regions", name = "Regions")
public class RegionsControler {

    @Autowired
    RegionsService regionsService;
    //Controller
            /*
            @Autowired
            public RegionsControler(RegionsService regionsService)
            {
                this.regionsService=regionsService;
            }
             */
//Classe d'ajout des regions

    @PostMapping(path = "/creer", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasRole('ADMIN')")
    public ReponseMessage add(@RequestBody Regions regions) {
        return this.regionsService.ajouterRegions(regions);
    }
//Classe afficher toute les regions


    @GetMapping(path = "/liste", name = "list")
    @ResponseStatus(HttpStatus.OK) //Permet de monter l'etat de notre requete
    public List<Regions> list() {
        return this.regionsService.afficherRegions();
    }
//Classe afficher une regions


    @GetMapping(path = "/uneRegion/{id_regions}", name = "lire")
    @ResponseStatus(HttpStatus.OK)
    public ReponseMessage lire(@PathVariable Long id_regions) {
        return this.regionsService.afficherUneRegion(id_regions);
    }
//Classe permettant de modifier les regions

    @PutMapping(path = "/modifier/{id_regions}", name = "modifier")
    @ResponseStatus(HttpStatus.OK)
    public ReponseMessage modifier(@RequestBody Regions regions, @PathVariable Long id_regions) {
        return this.regionsService.modifierRegions(regions, id_regions);
    }

    //Classe permettant de supprimer une region
    @DeleteMapping(path = "/supprimer/{id_regions}", name = "supprimer")
    //  @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id_regions) {
        this.regionsService.supprimer(id_regions);
    }

    // La liste des regions sans pays

    @GetMapping(path = "/regionssansPays")
    public Iterable<Object[]> mesRegions() {
        return this.regionsService.mesRegions();
    }


    // La liste des regions avec pays

    @GetMapping(path = "/regionsavecPays")
    public Iterable<Object[]> mesRegionsAvecPays() {
        return this.regionsService.mesRegionsAvecPays();
    }
/*
            // Generale
            @ApiOperation(value = "Crée une Region en generale")
            @PostMapping(path ="/ajouter", name = "create")
            public Regions Generale(Regions regions) {
                return this.regionsService.Generale(regions);
            }

 */


}

