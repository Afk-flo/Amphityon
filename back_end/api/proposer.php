<?php

require_once('../dao/ProposerDAO.php');

// vive marseille 
$method = $_SERVER["REQUEST_METHOD"];

switch($method) {
    case 'GET' :
    /* Affichage pour : 
        - 1 plat en particulier avec ID + service + date 
        - Tous les plats avec service + date 
    */
    if(isset($_GET['service']) && isset($_GET['date'])) {
        // première proposition, avec id 
        $service = htmlspecialchars($_GET['service']);
        $date = htmlspecialchars($_GET['date']);
        if(isset($_GET['id'])) {
            $id = htmlspecialchars($_GET['id']);
            $proposer = ProposerDAO::getProposer($id,$service,$date);
            printf(json_encode($proposer));
        } else {
            $proposer = ProposerDAO::getAllByService($service, $date);
            printf(json_encode($proposer));
        }
    } else {
        printf(json_encode("ERREUR _ Il manque des informations"));
    }
        break;

    case 'POST':
        // Création d'une mise en proposition
        
        break;

    case 'PUT':
        // Update d'un produit proposé ET VENTE 

        break;

    case 'DELETE':
        // Suppression d'un produit proposé  

        break;

    default:
        // Alors ça, ça, ça ça c'est pas MARSEILLE ça !!!!
        header("HTTP/1.0 405 Method Not Allowed");
        printf(json_encode("ERREUR _ Méthode non accepté tu fais quoi là ?"));

    }