<?php

require_once('../dto/TableDTO.php');
require_once('../dto/AffectationDTO.php');
require_once('bdd.php');

class TableDAO{

    public static function getUneTable(int $numTable, $idService){
        $req = bdd::getInstance()->prepare("SELECT * FROM TABLNARMOUK WHERE numTable = ?, idService = ? AND date1 = date(now())");
        $req->execute(array($numTable,$idService));
        $req->setFetchMode(PDO::FETCH_ASSOC);
        $table = $req->fetch();
        return $table;
    }

    public static function getAllTables($idService){
        try{
            $req=  bdd::getInstance()->prepare("SELECT * FROM TABLNARMOUK WHERE IDSERVICE = ? AND date1 = date(now())");
            $req->execute(array($idService));
            $all = $req->fetchAll(PDO::FETCH_ASSOC);
            return $all;
        }
        catch(Exception $e){
            return $e;
        }


    }

    public static function majTable($idService, $nbConvive, $numTable){
        try {
            $req = bdd::getInstance()->prepare("UPDATE TABLNARMOUK SET NBCONVIVE = ? WHERE NUMTABLE = ? AND DATE1 = date(now()) AND IDSERVICE = ?");
            $req->execute(array($nbConvive, $numTable,$idService));
            $req->fetch(PDO::FETCH_ASSOC);
            return true;
        }
        catch(Exception $e){
            return false;
        }
    }

    public static function suppTable($numTable,$idService){
        try{
            $req= bdd::getInstance()->prepare("DELETE FROM TABLNARMOUK WHERE numTable=? and IDSERVICE = ? and DATE1 = date(now())");
            $req->execute(array($numTable,$idService));
            return true;
        }
        catch(Exception $e){
            return $e;
        }
    }

    public static function ajoutTable($idService, $nbConvive, $idUser){
        $req = bdd::getInstance()->prepare("INSERT INTO TABLNARMOUK (IDSERVICE,DATE1,IDUSER,NBCONVIVE) VALUES (?,date(now()),?,?)");
        $req->execute(array($idService,$idUser,$nbConvive));
    }

    public static function getAffections($idService){
        try{
            $req=  bdd::getInstance()->prepare("SELECT A.IDUSER_1, S.NOM, S.PRENOM, A.NUMTABLE FROM AFFECTER AS A INNER JOIN SERVEUR AS S ON S.IDUSER=A.IDUSER WHERE A.IDSERVICE=? AND A.DATE1=date(now());");
            $req->execute(array($idService));
            $all = $req->fetchAll(PDO::FETCH_ASSOC);
            return $all;
        }
        catch(Exception $e){
            return false." ". $e;
        }
    }

    public static function getUneAffection($idService, $idUser){
        try{
            $req=  bdd::getInstance()->prepare("SELECT A.IDUSER_1, S.NOM, S.PRENOM, A.NUMTABLE FROM AFFECTER AS A INNER JOIN SERVEUR AS S ON S.IDUSER=A.IDUSER WHERE A.IDSERVICE=? AND A.IDUSER=? and A.DATE1=date(now());");
            $req->execute(array($idService,$idUser));
            $req->setFetchMode(PDO::FETCH_ASSOC);
            $affectation = $req->fetch();
            return $affectation;
        }
        catch(Exception $e){
            return false." ". $e;
        }
    }

    public static function affecter($idUser1,$idService,$idUser,$numTable){
        try{
            $req=bdd::getInstance()->prepare("INSERT INTO AFFECTER (IDUSER_1,IDSERVICE,IDUSER, DATE1, NUMTABLE) VALUES (?,?,?,date(now()),?)");
            $req->execute(array($idUser1,$idService,$idUser,$numTable));
            $req->fetch(PDO::FETCH_ASSOC);
            return true;
        }
        catch(Exception $e){
            return false." ". $e;
        }
    }

    public static function suppAffect($idService,$idUser,$numTable)
    {
        try{
            $req = bdd::getInstance()->prepare("DELETE FROM AFFECTER WHERE IDUSER= ?, IDSERVICE = ?, date(now(), NUMTABLE = ?)");
            $req->execute(array($idUser,$idService,$numTable));
            $req->fetch(PDO::FETCH_ASSOC);
        }
        catch(Exception $e){
            return false;
        }
    }

    public static function majAffect($idUser,$idService,$numTable){
        $req=bdd::getInstance()->prepare("UPDATE AFFECTER SET IDUSER=?, IDSERVICE = ?, DATE = date(now()), NUMTABLE = ?");
        $req->execute(array($idUser,$idService,$numTable));
        $req->fetch(PDO::FETCH_ASSOC);
    }

    public static function getServeurs(){
        try{
            $req=  bdd::getInstance()->prepare("SELECT IDUSER, NOM, PRENOM FROM SERVEUR;");
            $req->execute();
            $all = $req->fetchAll(PDO::FETCH_ASSOC);
            return $all;
        }
        catch(Exception $e){
            return false." ". $e;
        }
    }


}