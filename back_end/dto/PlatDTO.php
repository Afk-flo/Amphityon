<?php

/*
    * CLASS : PlatDTO
    * Permet d'identifier et de gérer la structure de l'objet PLAT présent en BDD
    * GETTER & SETTER uniquement
*/

class PlatDTO {

    private $IDPLAT;
    private $NOMPLAT;
    private $DESCRIPTIF;
    // PK
    private $IDCAT;
    private $IDUSER;

    public function getIdCat() {
        return $this->IDCAT;
    }

    public function getIdUser() {
        return $this->IDUSER;
    }

    public function setIdCat($data) {
        $this->IDCAT = $data;
    }

    public function setIdUser($data) {
        $this->IDUSER = $data;
    }

    public function getId() {
        return $this->IDPLAT;
    }

    public function getNomPlat() {
        return $this->NOMPLAT;
    }

    public function getDescriptif() {
        return $this->DESCRIPTIF;
    }

    public function setId($data) {
        $this->IDPLAT = $data;
    }

    public function setNomPlat($data) {
        $this->NOMPLAT = $data;
    }

    public function setDescriptif($data) {
        $this->DESCRIPTIF = $data;
    }

}