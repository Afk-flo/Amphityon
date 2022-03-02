<?php 

/*
    * CLASS : UTILISATEURDTO
    * Permet d'identifier et de gérer la structure de l'objet UTILISATEUR présent en BDD
    * GETTER & SETTER uniquement
*/

class UtilisateurDTO {

    private $IDUSER;
    private $NOM;
    private $PRENOM;
    private $IDENTIFIANT;
    private $MOTDEPASSE;
    private $TOKEN;

    public function setToken($data) {
        $this->TOKEN = $data;
    }

    public function getToken() {
        return $this->TOKEN;
    }

    public function setMdp($data) {
        $this->MOTDEPASSE = $data;
    }

    public function getMdp() {
        return $this->MOTDEPASSE;
    }

    public function setIdentifiant($data) {
        $this->IDENTIFIANT = $data;
    }

    public function getIdentifiant() {
        return $this->IDENTIFIANT;
    }

    public function getPrenom() {
        return $this->PRENOM;
    }

    public function setPrenom($data) {
        $this->PRENOM = $data;
    }

    public function getNom() {
        return $this->NOM;
    }

    public function setNom($nom) {
        $this->NOM = $nom;
    }

    public function getId() {
        return $this->IDUSER;
    }

    public function setId($id) {
        $this->IDUSER = $id;
    }

}