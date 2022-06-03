<?php

require_once('../dao/PlatDAO.php');

// Gestion selon le type de requêtage 
$method = $_SERVER["REQUEST_METHOD"];

switch($method) {
    case 'GET' :
        // Récupération de tous les plats OU plats selon IDCAT ou IDUSER 
        // Check $_GET['id']
        // Puis vérification n2 pour l'utilisateur ou la catégorie 
        if(isset($_GET['id'])) {
            if(isset($_GET['demande']) && $_GET['demande'] === "user") {
                // Si on veut avoir le choix par user 
                $idUser = htmlspecialchars($_GET['id']);
                $plats = PlatDAO::getAllByCuisto($idUser);
                printf(json_encode($plats));

            } else if (isset($_GET['demande']) &&  $_GET['demande'] === "cat") {
                // Si on veut avoir le choix par cat 
                $idCat = htmlspecialchars($_GET['id']);
                $plats = PlatDAO::getAllByCat($idCat);
                printf(json_encode($plats));

            } else if (isset($_GET['demande']) &&  $_GET['demande'] === "both") {
                // Si on veut  avoir le choix par cat et cuisto
                $idCat = htmlspecialchars($_GET['id']);
                $idUser = htmlspecialchars($_GET['idUser']);
                $plats = PlatDAO::getAllByCuistoAndCat($idCat, $idUser);
                printf(json_encode($plats));
            
            }else {
                $idProduit = htmlspecialchars($_GET['id']);
                $plat = PlatDAO::getOne($idProduit);
                printf(json_encode($plat));
            }

        } else {
            // Si pas d'id, on envoie tous les plats aussi simple que cela paraisse (oui oui)
            $data = PlatDAO::getAll();
            printf(json_encode($data));
        }
        break;

    case 'POST' : 
        // Insérer un nouvo plat
        if(!empty($_POST)) {   
            $plat = new PlatDTO();
            $plat->setNomPlat(htmlspecialchars($_POST['nomPlat']));
            $plat->setDescriptif(htmlspecialchars($_POST['descriptif']));
            $idCat = htmlspecialchars($_POST['idCat']);
            $idUser = htmlspecialchars($_POST['idUser']);
            // On envoie les frites
            PlatDAO::create($plat, $idCat, $idUser);
            printf(json_encode("ALLEZ ON COMMANDE LA OU KOI "));

        } else {
            printf(json_encode("ERREUR _ pourquoi tu es vide ?"));
        }
        break;


    case 'PUT' : 
        // Le PUT n'est pas non plus pris en compte par le PHP - Zuper :( 
        parse_str(file_get_contents("php://input"),$plat);

        // Update 
        if(isset($plat['id']) && !empty($plat['id'])) {
            // Si tout est bon, on demande la maj 
            $nvPlat = new PlatDTO();
        
            $nvPlat->setId(htmlspecialchars($plat['id']));
            $nvPlat->setNomPlat(htmlspecialchars($plat['nomPlat']));
            $nvPlat->setDescriptif(htmlspecialchars($plat['descriptif']));
            $nvPlat->setIdCat(htmlspecialchars($plat['idCat']));
            $nvPlat->setIdUser(htmlspecialchars($plat['idUser']));
            // Update dès que l'objet est ready .. Nasa ...9 ... 8...7....
            PlatDAO::update($nvPlat);
            printf(json_encode("ALLEZ LOM ALLEZ LOM GOGO MARSEILLE"));
        } else {
            printf(json_encode("ERREUR _ Impossible de reconnaitre ce que toi tu dis là oh"));
        }
        
        break;


    case 'DELETE' :
        // Pas de lecture de DELETE EN PHP 
        parse_str(file_get_contents("php://input"),$plat);

        if(!empty($plat['id']) && is_int($plat['id'])) {
            // Si l'id est présent, on delete tout ça 
            PlatDAO::delete(htmlspecialchars($plat['id']));
            printf(json_encode("WESH LE FOOT - BRAVO LE SANG"));
        } else {
            printf(json_encode("ERREUR _ Impossible de reconnaitre ce que toi tu dis là oh"));
        }
        break;


    default:
        // Requête invalide
        header("HTTP/1.0 405 Method Not Allowed");
        printf(json_encode("ERREUR _ Méthode non accepté tu fais quoi là ?"));
        break;
}
