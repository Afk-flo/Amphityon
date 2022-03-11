<?php

require_once('../dto/TableDTO.php');
require_once('bdd.php');

class TableDAO{

    public static function getUneTable(int $numTable){
        $req = bdd::getInstance()->prepare("SELECT * FROM TABLE WHERE numTable = ?");
        $req->execute(array($numTable));
        $req->setFetchMode(PDO::FETCH_ASSOC);
        $table = $req->fetch();
        return $table;
    }

    public static function getAllTables($idService){
        try{
            $req=  bdd::getInstance()->prepare("SELECT * FROM TABLE WHERE idService = ? and date = date(now())");
            $req->execute(array($idService));
            $all = $req->fetchAll(PDO::FETCH_ASSOC,'TableDTO');
            return $all;

        }
        catch(Exception $e){
            return $e;
        }


    }

    public static function majTable(TableDTO $uneTable){
        try {
            $req = bdd::getInstance()->prepare("UPDATE TABLNARMOUK SET IDSERVICE = ?, DATE = ?, NUMTABLE = ?, IDUSER = ?, NBCONVIVE = ? WHERE NUMTABLE = ? AND DATE = ? AND IDSERVICE = ?");
            $req->execute(array($uneTable->getNumTable(), $uneTable->getDate(), $uneTable->getIdService()));
            $req->fetch(PDO::FETCH_ASSOC);
            return true;
        }
        catch(Exception $e){
            return false;
        }
    }

    public static function suppTable($numTable){
        try{
            $req= bdd::getInstance()->prepare("DELETE FROM TABLNARMOUK WHERE numTable=? ");
            $req->execute(array($numTable));
            return true;
        }
        catch(Exception $e){
            return $e;
        }
    }

    public static function affectation($idUser1, $idService, $numTable, $idUser){
        try{
            $req=bdd::getInstance()->prepare("INSERT INTO AFFECTER VALUES (?,?,date(now()),?,?)");
            $req->execute($idUser1,$idService, $numTable, $idUser);
            $req->fetch(PDO::FETCH_ASSOC);
            return true;
        }
        catch(Exception $e){
            return false." ". $e;
        }
    }

    public static function suppAffect($idUser1, $idService, $numTable, $idUser)
    {
        try{
            $req = bdd::getInstance()->prepare("DELETE FROM AFFECTER WHERE IDUSER1 = ?, IDSERVICE = ?, date(now(), NUMTABLE = ?, IDUSER = ?)");
            $req->execute(array($idUser1,$idService, $numTable, $idUser));
            $req->fetch(PDO::FETCH_ASSOC);
        }
        catch(Exception $e){
            return false;
        }
    }

    public static function majAffect($idUser1, $idService, $numTable, $idUser){
        $req=bdd::getInstance()->prepare("UPDATE AFFECTER SET IDUSER1=?, IDSERVICE = ?, DATE = date(now()), NUMTABLE = ?, idUser = ?");
        $req->execute(array($idUser1, $$idService,$numTable, $idUser));
        $req->fetch(PDO::FETCH_ASSOC);
    }


}