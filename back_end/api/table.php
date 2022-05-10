<?php

require_once('../dao/TableDAO.php');

$method = $_SERVER["REQUEST_METHOD"];

if (isset($_POST['idService'])) {
    if (isset($_POST['demande']) && $_POST['demande'] == "lesTables") {
        $idService = $_POST['idService'];
        $tables = TableDAO::getAllTables($idService);
        printf(json_encode($tables));
    } else if ($_POST['demande'] && $_POST['demande'] == "uneTable") {
        $idTable = htmlspecialchars($_POST['idTable']);
        $idService = $_POST['idService'];
        $uneTable = TableDAO::getUneTable($idTable, $idService);
        print(json_encode($uneTable));
    }
    else if(isset($_POST['demande']) && $_POST['demande'] =="ajoutTable" ){
        $nbConvive = $_POST['nbConvive'];
        $idService = $_POST['idService'];
        $idUser = $_POST['idUser'];
        TableDAO::ajoutTable($idService,$nbConvive,$idUser);
        print(json_encode("Une nouvelle table est apparue !"));
    }
    else if (isset($_POST['demande']) && $_POST['demande'] =="modifTable"){
        $nbConvive = $_POST['nbConvive'];
        $idService = $_POST['idService'];
        $numTable = $_POST['numTable'];
        TableDAO::majTable($idService, $nbConvive, $numTable);
        print(json_encode("une table modifiée pour les touristes!"));
    }

    else if (isset($_POST['demande']) && $_POST['demande'] =="suppTable"){
        $idTable = htmlspecialchars($_POST['numTable']);
        $idService = htmlspecialchars($_POST['idService']);
        TableDAO::suppTable($idTable,$idService);
        print(json_encode("finito la table"));
    }
    }
else{
    header("HTTP/1.0 405 Method Not Allowed");
    printf(json_encode("ERREUR _ Méthode non accepté tu fais quoi là ?"));
}