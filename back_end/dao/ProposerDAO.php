<?php 

require_once('../dto/ProposerDTO.php');
require_once('../dto/PlatDTO.php');
require_once('bdd.php');

/*
    * CLASS : PROPOSER DAO
    * Permet de gérer les actions des différentes proposition de plats se basant sur PROPOSER en BDD
    * Fonction de proposition (ajout ds la base d'un produit qui sera proposé pour le service du matin ou du soir)
    * Fonction d'accès -- Permet de récupérer les plats proposés (précisément ou en ensemble)
    * Fonction 
    *
*/

class ProposerDAO {

    /**
     * Fonction permettant de créer une proposition -> techniquement on ajoute à la BDD une entrée qui servira de proposition de plat sous service
     * 
     * @param PlatDTO $plat -> objet plat 
     * @param int $service -> service jour(1) / nuit(2)
     * @param Date $date -> Date de service 
     * @param ProposerDTO $proposer -> Objet proposer avec 
     * @return void - Rien ne sort d'ici (0o0)
     * @author ElMatador 
     */
    public static function create(PlatDTO $plat, int $service, Date $date, ProposerDTO $proposer) : void {
        $sql = "INSERT INTO PROPOSER(IDPLAT, IDSERVICE, DATE, QUANTITEPROPOSEE, PRIXVENTE, QUANTITEVENDUE) VALUES (?,?,?,?,?,?)";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($plat->getId(), $service, $date, $proposer->getQuantiteProposee(), $proposer->getPrixVente(), $proposer->getQuantiteVendue()));
    }   

    

}