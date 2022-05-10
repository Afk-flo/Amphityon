<?php
/*
    * CLASS : TableDTO
    * Permet d'identifier et de gérer la structure de l'objet TABLE présent en BDD
    * GETTER & SETTER uniquement
*/

class TableDTO{
    private $idService;
    private $numTable;
    private $date;
    private $nbConvive;

    public function getIdService()
    {
        return $this->idService;
    }

    public function setIdService($idService): void
    {
        $this->idService = $idService;
    }

    public function getDate()
    {
        return $this->date;
    }

    public function setDate($date): void
    {
        $this->date = $date;
    }

    public function getNumTable()
    {
        return $this->numTable;
    }

    public function setNumTable($numTable): void
    {
        $this->numTable = $numTable;
    }

    public function getNbConvive()
    {
        return $this->nbConvive;
    }

    public function setNbConvive($nbConvive): void
    {
        $this->nbConvive = $nbConvive;
    }


}