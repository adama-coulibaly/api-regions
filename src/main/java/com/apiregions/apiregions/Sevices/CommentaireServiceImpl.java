package com.apiregions.apiregions.Sevices;

import com.apiregions.apiregions.Models.Commentaire;

import java.util.List;

public class CommentaireServiceImpl implements CommentaireService {


    private CommentaireService commentaireService;
    // INJECTION DE DEPENDANCE ICI
    public CommentaireServiceImpl(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }


    @Override
    public String addNewCommentaire(Commentaire commentaire) {
        return null;
    }

    @Override
    public String SupprimerCommentaire(Long idCommentaire) {
        return null;
    }

    @Override
    public String ModifierCommentaire(String username, Commentaire commentaire) {
        return null;
    }

    @Override
    public List<Commentaire> toutLesCommentaires() {
        return commentaireService.toutLesCommentaires();
    }
}
