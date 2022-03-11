<?php

require_once("bdd.php");

class AffectationDAO{

    public static function getUneAffectation($idService){
        $req = bdd::getInstance()->prepare("SELECT * FROM AFFECTATION WHERE DATE =date(now()), idService");
        $req->execute(array($idService));
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
