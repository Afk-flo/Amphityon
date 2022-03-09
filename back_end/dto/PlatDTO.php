<?php

/*
    * CLASS : PlatDTO
    * Permet d'identifier et de gérer la structure de l'objet PLAT présent en BDD
    * GETTER & SETTER uniquement
*/

class PlatDTO {

    private $idPlat;
    private $nomPlat;
    private $descriptif;
    // PK
    private $idCat;
    private $idUser;

    public function getIdCat() {
        return $this->idCat;
    }

    public function getIdUser() {
        return $this->idUser;
    }

    public function setIdCat($data) {
        $this->idCat = $data;
    }

    public function setIdUser($data) {
        $this->idUser = $data;
    }

    public function getId() {
        return $this->id;
    }

    public function getNomPlat() {
        return $this->nomPlat;
    }

    public function getDescriptif() {
        return $this->getDescriptif;
    }

    public function setId($data) {
        $this->id = $data;
    }

    public function setNomPlat($data) {
        $this->nomPlat = $data;
    }

    public function setDescriptif($data) {
        $this->descriptif = $data;
    }

}