<?php

require_once('../dao/TableDAO.php');

$method = $_SERVER["REQUEST_METHOD"];


if (isset($_POST['idService'])) {
    if (isset($_POST['demande']) && $_POST['demande'] == "lesAffectations") {
        $idService = $_POST['idService'];
        $affectations = TableDAO::getAffections($idService);
        print(json_encode($affectations));
    } else if ($_POST['demande'] && $_POST['demande'] == "uneAffect") {
        $idService = $_POST['idService'];
        $idUser = $_POST['idUser'];
        $uneTable = TableDAO::getUneAffection($idService,$idUser);
        print(json_encode($uneTable));
    }
    else if(isset($_POST['demande']) && $_POST['demande'] =="affecter" ){
        $idUser1 = $_POST['idUser1'];
        $idService = $_POST['idService'];
        $idUser = $_POST['idUser'];
        $numTable = $_POST['numTable'];
        TableDAO::affecter($idUser1,$idService,$idUser,$numTable);
        print(json_encode("Une nouvelle table est apparue !"));
    }
    else if (isset($_POST['demande']) && $_POST['demande'] =="modifAffect"){
        $idService = $_POST['idService'];
        $numTable = $_POST['numTable'];
        TableDAO::majAffect($idService, $numTable);
        print(json_encode("une table modifiée pour les touristes!"));
    }

    else if (isset($_POST['demande']) && $_POST['demande'] =="suppAffect"){
        $numTable = htmlspecialchars($_POST['numTable']);
        $idService = htmlspecialchars($_POST['idService']);
        $idUser = htmlspecialchars($_POST['idUser']);
        TableDAO::suppAffect($idService,$idUser,$numTable);
        print(json_encode("finito la table"));
    }
}
else if (isset($_POST['demande']) && $_POST['demande']=="getServeurs"){
    $serveurs = TableDAO::getServeurs();
    printf(json_encode($serveurs));
}
else{
    header("HTTP/1.0 405 Method Not Allowed");
    printf(json_encode("ERREUR _ Méthode non accepté tu fais quoi là ?"));
}
