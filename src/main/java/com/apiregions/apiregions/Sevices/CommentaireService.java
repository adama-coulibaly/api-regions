package com.apiregions.apiregions.Sevices;

import com.apiregions.apiregions.Models.Commentaire;
import com.apiregions.apiregions.Models.UserRoles;
import com.apiregions.apiregions.Models.UsersApp;

import java.util.List;

public interface CommentaireService {

    String addNewCommentaire(Commentaire commentaire);
    String SupprimerCommentaire(Long idCommentaire);
    String ModifierCommentaire(String username,Commentaire commentaire);
    List<Commentaire> toutLesCommentaires();
}
