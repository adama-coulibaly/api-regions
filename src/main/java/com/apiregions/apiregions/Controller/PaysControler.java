package com.apiregions.apiregions.Controller;
import com.apiregions.apiregions.Message.ReponseMessage;
import com.apiregions.apiregions.Models.Pays;
import com.apiregions.apiregions.Sevices.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/projet/odk/Pays",name ="Pays")

public class PaysControler {
    private PaysService paysService;

    // ----------------------------Controller-----------------------------------
    @Autowired
    public  PaysControler(PaysService paysService) {
        this.paysService = paysService;
    }
    // ----------------------------Request Post-----------------------------------

    @PostMapping(path ="/creer", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public ReponseMessage add (@RequestBody Pays pays)
    {
        return paysService.ajouterPays(pays);

    }


    // ----------------------------Request Get-----------------------------------

    @GetMapping(path ="/liste", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<Pays> list()
    {
        return this.paysService.afficheTout();
    }


    // ----------------------------Request lIRE PAR PAYS-----------------------------------

    @GetMapping(path ="/unPays/{id_pays}", name = "lire")
    @ResponseStatus(HttpStatus.OK)
    public ReponseMessage lire(@PathVariable Long id_pays)
    {
        return  this.paysService.afficherUn(id_pays);
    }

    // ----------------------------Request modifier-----------------------------------

    @PutMapping(path ="/modifier/{id_pays}", name = "modifier")
    @ResponseStatus(HttpStatus.OK)
    public ReponseMessage modifier(@RequestBody Pays pays, @PathVariable Long id_pays)
    {

        return  this.paysService.modifierPays(pays, id_pays);
    }

    // ----------------------------Request supprimer-----------------------------------

    @DeleteMapping(path ="/supprimer/{id_pays}", name = "supprimer")
   //  @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id_pays)
    {
         this.paysService.supprimerPays(id_pays);
    }

}
