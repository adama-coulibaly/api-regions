package com.apiregions.apiregions.Sevices;

import com.apiregions.apiregions.Message.ReponseMessage;
import com.apiregions.apiregions.Models.Pays;
import com.apiregions.apiregions.Repository.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaysService {
    private PaysRepository paysRepository;


@Autowired
// ----------------------------Controllers-----------------------------------
    public PaysService(PaysRepository paysRepository)
    {
        this.paysRepository = paysRepository;
    }
    // ----------------------------Ajouter un pays-----------------------------------OK

    public ReponseMessage ajouterPays(Pays pays) {
        if (paysRepository.findByNompays(pays.getNompays()) == null) {
            this.paysRepository.save(pays);
            ReponseMessage contenu = new ReponseMessage("Pays ajouté avec succes", true);
            return contenu;
        } else {
            ReponseMessage contenu = new ReponseMessage("Pays existe déja", true);
            return contenu;
        }

    }

// ----------------------------Aficher une liste de pays-----------------------------------OK
    public List<Pays> afficheTout()
    {
        return this.paysRepository.findAll();
    }

// ----------------------------Aficher un pays-----------------------------------

    public ReponseMessage afficherUn(Long id_pays)
    {
        Optional<Pays> pays = this.paysRepository.findById(id_pays);
        if (!pays.isPresent()) {
            ReponseMessage message = new ReponseMessage("Ce pays n'est pas trouvé !", false);
            return message;
          }
        else {
             this.paysRepository.findById(id_pays);
             Optional<Pays> a = this.paysRepository.findById(id_pays);

            ReponseMessage message = new ReponseMessage( a.get().getNompays(), true);
            return message;
        }


    }
    // ----------------------------Modifier un pays-----------------------------------OK
    public ReponseMessage modifierPays(Pays pays, Long id_pays)
    {
        //ICI ON VERIFIE SI LE PAYS EXISTE
        Optional<Pays> paysExistePays = this.paysRepository.findById(id_pays);
        if (!paysExistePays.isPresent()) {
            ReponseMessage message = new ReponseMessage("Pays non trouvé !", true);
            return message;
        }
        else {
            Pays paysT = paysRepository.findById(id_pays).get();
            paysT.setNompays(pays.getNompays());
            paysRepository.saveAndFlush(paysT);
            ReponseMessage message = new ReponseMessage("Pays modifié avec success", true);
            return message;
        }

    }

    // ----------------------------Supprimer un pays-----------------------------------

    public ReponseMessage supprimerPays(Long id_pays)
    {
        Optional<Pays> pays = this.paysRepository.findById(id_pays);
        if (!pays.isPresent())
        {
            ReponseMessage message = new ReponseMessage("Ce pays n'est pas trouvé !", false);
            return message;
        }
        else{
            this.paysRepository.delete(pays.get());
            ReponseMessage message = new ReponseMessage("Suppression reussie avec succès !", true);
            return message;
        }


    }

}
