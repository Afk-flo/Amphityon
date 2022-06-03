<?php

/*
    * CLASS : CategorieDTO
    * Permet d'identifier et de gérer la structure de l'objet Categorie présent en BDD
    * GETTER & SETTER uniquement
*/

class CategorieDTO {

    private $idCat;
    private $Libelle;

    public function getId() {
        return $this->id;
    }

    public function getLibelle() {
        return $this->Libelle;
    }

    public function setId($data) {
        $this->idCat = $data;
    }

    public function setLibelle($data) {
        $this->Libelle = $data;
    }

}
