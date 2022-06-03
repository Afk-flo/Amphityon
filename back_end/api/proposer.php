<?php

require_once('../dao/ProposerDAO.php');
require_once('../dao/PlatDAO.php');


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
        // Verif plus demandé
        if(isset($_POST['idPlat']) && isset($_POST['idService']) && isset($_POST['quantiteProposee'])) {

            // Préparation 
            $plat = PlatDAO::getOne(htmlspecialchars($_POST['idPlat']));
            $service = htmlspecialchars($_POST['idSerivce']);
            $date = htmlspecialchars($_POST['date']);
            $proposer = new ProposerDTO();
            $proposer->setQuantiteProposee(htmlspecialchars($_POST['quantiteProposee']));
            $proposer->setPrixVente(htmlspecialchars($_POST['prixVente']));
            $proposer->setQuantiteVendue('0');

            // Insertion 
            ProposerDAO::create($plat, $service, $date, $proposer);
            printf(json_encode("WESH BRAVO CHAKAL"));
        } else {
            printf(json_encode("nope nope nope"));
        }
        
        break;

    case 'PUT':
        // Update d'un produit proposé 
        parse_str(file_get_contents("php://input"),$prop);

        if(isset($prop['idService']) && isset($prop['idPlat']) && isset($prop['quantiteProposee']) && !isset($prop['quantiteVendue'])) {
            $plat = htmlspecialchars($prop['idPlat']);
            $service = htmlspecialchars($prop['idSerivce']);
            $date = htmlspecialchars($prop['date']);
            $proposer = new ProposerDTO();
            $proposer->setQuantiteProposee(htmlspecialchars($prop['quantiteProposee']));
            $proposer->setPrixVente(htmlspecialchars($props['prixVente']));

            // Update Bdd 
            ProposerDAO::update($plat, $service, $date, $proposer);
            printf(json_encode("yep, tout est bon"));

        } else if (isset($prop['quantiteVendue'])) {
            // S'il s'agit d'une vente

            $plat = htmlspecialchars($prop['idPlat']);
            $service = htmlspecialchars($prop['idSerivce']);
            $date = htmlspecialchars($prop['date']);
            $quantite = htmlspecialchars($prop['quantiteVendue']);

            // Mise en place des ventes effectuées
            ProposerDAO::venteProduit($plat, $service, $date, $quantite);
            printf(json_encode("yep, still good"));
            
        } else {
            printf(json_encode("ERREUR _ nope"));
        }
        break;

    case 'DELETE':
        parse_str(file_get_contents("php://input"),$prop);
        // Suppression d'un produit proposé  
        if(isset($prop['idPlat']) && isset($prop['idService']) && isset($prop['date'])) {
            $idPlat = htmlspecialchars($prop['idPlat']);
            $idService = htmlspecialchars($prop['idService']);
            $date = htmlspecialchars($prop['date']);
            
            ProposerDAO::delete($idPlat, $idService, $date);
            printf(json_encode("Et bim, à la poubelle"));
        } else {
            printf(json_encode("ERREUR _ pas deleted la deletage"));
        }

        break;

    default:
        // Alors ça, ça, ça ça c'est pas MARSEILLE ça !!!!
        header("HTTP/1.0 405 Method Not Allowed");
        printf(json_encode("ERREUR _ Méthode non accepté tu fais quoi là ?"));

    }