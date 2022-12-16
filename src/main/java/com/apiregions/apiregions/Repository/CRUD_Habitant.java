package com.apiregions.apiregions.Repository;

import com.apiregions.apiregions.Models.Habitants;

import java.util.List;

public interface CRUD_Habitant {
    Habitants ajouterHabitant(Habitants habitants);
    List<Habitants> maListe();
    Habitants modifierHabitant(Habitants habitants, Long id_habitants);
    void supprimerHabitant(Long id_habitants);
}
