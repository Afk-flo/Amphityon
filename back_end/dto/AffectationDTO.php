<?php

class AffectationDTO {
    private $idUser_1;
    private $idService;
    private $date;
    private $numtable;
    private $idUser;


    /**
     * @return mixed
     */
    public function getDate()
    {
        return $this->date;
    }

    /**
     * @return mixed
     */
    public function getIdUser()
    {
        return $this->idUser;
    }

    /**
     * @return mixed
     */
    public function getIdService()
    {
        return $this->idService;
    }

    /**
     * @return mixed
     */
    public function getIdUser1()
    {
        return $this->idUser_1;
    }

    /**
     * @return mixed
     */
    public function getNumtable()
    {
        return $this->numtable;
    }

    /**
     * @param mixed $date
     */
    public function setDate($date): void
    {
        $this->date = $date;
    }

    /**
     * @param mixed $idUser
     */
    public function setIdUser($idUser): void
    {
        $this->idUser = $idUser;
    }

    /**
     * @param mixed $idService
     */
    public function setIdService($idService): void
    {
        $this->idService = $idService;
    }

    /**
     * @param mixed $idUser_1
     */
    public function setIdUser1($idUser_1): void
    {
        $this->idUser_1 = $idUser_1;
    }

    /**
     * @param mixed $numtable
     */
    public function setNumtable($numtable): void
    {
        $this->numtable = $numtable;
    }
}