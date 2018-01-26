package com.example.gael.mestrefles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaëlPortable on 26/01/2018.
 */

public class RecuperationSmsDechiffre {

    private final List<TraiteurMessage> listTraiteurs;

    public RecuperationSmsDechiffre(DechiffreurMessage typeMessageServeur) {
        this.listTraiteurs = new ArrayList<TraiteurMessage>(

        );
    }
}
