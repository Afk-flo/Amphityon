<?php 

/*
    * CLASS : ProposerDTO
    * Permet d'identifier et de gérer la structure de l'objet PROPOSER présent en BDD
    * GETTER & SETTER uniquement
*/

class ProposerDTO {

    private $IDPLAT;
    private $IDSERVICE;
    private $DATE;
    private $QUANTITEPROPOSEE;
    private $PRIXVENTE;
    private $QUANTITEVENDUE;

    public function setQuantiteVendue($data) {
        $this->QUANTITEVENDUE = $data;
    }

    public function setPrixVente($data) {
        $this->PRIXVENTE = $data;
    }

    public function setQuantiteProposee($data) {
        $this->QUANTITEPROPOSEE = $data;
    }

    public function setDate($data) {
        $this->DATE = $data;
    }

    public function setIdService($data) {
        $this->IDSERVICE = $data;
    }

    public function setIdPlat($data) {
        $this->IDPLAT = $data;
    }

    public function getIdPlat() {
        return $this->IDPLAT ;
    }

    public function getIdService() {
        return $this->IDSERVICE ;
    }

    public function getDate() {
        return $this->DATE ;
    }

    public function getQuantiteProposee() {
        return $this->QUANTITEPROPOSEE ;
    }

    public function getPrixVente() {
        return $this->PRIXVENTE ;
    }

    public function getQuantiteVendue() {
        return $this->QUANTITEVENDUE;
    }
}