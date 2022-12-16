package com.apiregions.apiregions.Controller;

import com.apiregions.apiregions.Message.ReponseMessage;
import com.apiregions.apiregions.Models.Pays;
import com.apiregions.apiregions.Models.Regions;
import com.apiregions.apiregions.Repository.RegionsRepository;
import com.apiregions.apiregions.Sevices.RegionsService;
import com.apiregions.apiregions.img.ConfigImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.io.IOException;
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

   // ICI ON TENTE DE CREER UNE REGION AVEC IMAGES
   private String nomregions;
    @Column(unique = true)
    private String coderegion;
    private String activiterregion;
    private String superficieregion;
    private String languemregion;
    private String images;
    private String description;
    private int nombrecommentaire;
    @Autowired
    private RegionsRepository regionsRepository;

    @PostMapping("/ajouterRegion")
    public ReponseMessage ajouterRegion(@Param("nomregions") String nomregions,@Param("coderegion") String coderegion, @Param("activiterregion") String activiterregion, @Param("superficieregion") String superficieregion, @Param("languemregion") String languemregion, @Param("description") String description, @Param("id_pays") Pays id_pays, @Param("file") MultipartFile file) throws IOException {
        Regions regions = new Regions();
        String nomfile = StringUtils.cleanPath(file.getOriginalFilename());

        System.out.println(nomregions);
        regions.setNomregions(nomregions);

        System.out.println(coderegion);
       regions.setCoderegion(coderegion);

        System.out.println(description);
        regions.setDescription(description);

        System.out.println(activiterregion);
       regions.setActiviterregion(activiterregion);

        System.out.println(nomfile);
        regions.setImages(nomfile);

        System.out.println(nomregions);
        regions.setNomregions(nomregions);

        System.out.println(languemregion);
        regions.setLanguemregion(languemregion);

        System.out.println(superficieregion);
        regions.setSuperficieregion(superficieregion);

        System.out.println(nombrecommentaire);
        regions.setNombrecommentaire(0);

        regions.setPays(id_pays);
        System.out.println(regions.getId_regions());
        System.out.println(regions.getNomregions());

        if(regionsRepository.findByNomregions(nomregions) == null){

          //  String u = "C:/Users/adcoulibaly/Desktop/ERP/ApplicationERPInterface/src/assets/images";
            String uploaDir = "C:/Users/adcoulibaly/Desktop/api-regions/api-regions/src/main/resources/static";
            //String uploaDir = new ClassPathResource("files/").getFile().getAbsolutePath();
            ConfigImage.saveimg(uploaDir, nomfile, file);
            //  entiteServiceImplement.ajouter(entite);
            return regionsService.ajouterRegions(regions);
        }else {
            ReponseMessage message = new ReponseMessage("Regions existe déja",false);
            return message;
        }

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

