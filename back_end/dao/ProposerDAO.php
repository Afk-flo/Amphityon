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
    public static function create(PlatDTO $plat, int $service, $date, ProposerDTO $proposer) : void {
        $sql = "INSERT INTO PROPOSER(IDPLAT, IDSERVICE, DATE, QUANTITEPROPOSEE, PRIXVENTE, QUANTITEVENDUE) VALUES (?,?,?,?,?,?)";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($plat->getId(), $service, $date, $proposer->getQuantiteProposee(), $proposer->getPrixVente(), $proposer->getQuantiteVendue()));
    }   

    /**
     * Fonction permettant de supprimer un plat d'un service, y'en a plus désolé >:( 
     * 
     * @param int $idPlat -- Id du plat 
     * @param int $service -- id sur service actuel 
     * @param Date $date -- date ? La précision est vraiment nécessaire ici ???? hmmm 
     * @return void -- oé 
     */
    public static function delete(int $idPlat, int $service, $date) : void {
        $sql = "DELETE FROM PROPOSER WHERE IDPLAT = ? AND IDESERVICE = ? AND DATE = ?";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($idPlat, $service, $date));
    }

    /**
     * Fonction permetant de récupérer un plat proposé et donc obtenir de manière précise : la quanttié, le prix, et toutikuanti 
     * 
     * @param int $idPlat - miam
     * @param int $service - id service 
     * @param Date $date -- $date
     * @return ProposerDTO||null -- Si proposer sinon non
     * 
     */
    public static function getProposer(int $idPlat, int $service, $date) : ?ProposerDTO {
        $sql = "SELECT IDPLAT, NOMPLAT, DESCRIPTIF, LIBELLE, IDSERVICE, QUANTITEPROPOSEE, PRIXVENTE, QUANTITEVENDUE, DATE  FROM PROPOSER WHERE IDPLAT = ? AND IDESERVICE = ? AND DATE = ?";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($idPlat, $service, $date));
        $req->setFetchMode(PDO::FETCH_ASSOC);
        $proposer = $req->fetch();
        return $proposer;
    }

    /**
     * Fonction permettant de récupérer tous les plats selon le service et la date 
     * 
     * @param int $idService
     * @param Date $date
     * @return array -- les Plats et les infos sur les quantités et prix proposés 
     */
    public static function getAllByService(int $idService, $date) : ?array {
        $sql = "SELECT IDPLAT, NOMPLAT, DESCRIPTIF, LIBELLE, IDSERVICE, QUANTITEPROPOSEE, PRIXVENTE, QUANTITEVENDUE, DATE FROM PROPOSER WHERE IDSERVICE = ? AND DATE = ?";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($idService, $date));
        $req->setFetchMode(PDO::FETCH_ASSOC);
        $proposers = $req->fetchAll();
        return $proposers;
    }   

    /**
     * Fonction permettant de mettre à jour un produit dans la Database (SQL)
     * 
     * @param int $idPlat - identifiant du plat 
     * @param int $service - Identifiant du service
     * @param ProposerDTO $proposer - Objet proposer avec les infos 
     * @return void -- oui 
     */
    public static function update(int $idPlat,  int $service, $date, ProposerDTO $proposer ) : void {
        $sql = "UPDATE PROPOSER SET QUANTITEPROPOSEE = ?, PRIXVENTE = ? WHERE IDPLAT = ? AND IDESERVICE = ? AND DATE = ?";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($proposer->getQuantiteProposee(), $proposer->getPrixVente(), $idPlat, $service, $date));
    }


    /**
     * Fonction permettant de mettre à jour le nombre de produits vendus  
     * 
     * @param int $idPlat - identifiant du plat 
     * @param int $service - Identifiant du service
     * @param int $quantite - la quantité vendue 
     * @return void -- oui 
     */
    public static function venteProduit(int $idPlat, int $service, $date, int $quantite)  : void {
        // On récupère puis on update la valeur, tu coco les bailles - En vrai tmtc on pourrait faire un trigger et tout mais hmm, c'est pas drole 
        $sql = "SELECT QUANTITEVENDUE FROM PROPOSER WHERE  WHERE IDPLAT = ? AND IDESERVICE = ? AND DATE = ?";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($idPlat, $service, $date));
        $req->setFetchMode(PDO::FETCH_ASSOC);
        $oldQtt = $req->fetch();

        // You're comming home with me 
        $quantite += $oldQtt["QUANTITEVENDUE"];

        $sql = "UPDATE PROPOSER SET QUANTITEVENDUE = ? WHERE IDPLAT = ? AND IDESERVICE = ? AND DATE = ?";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($quantite , $idPlat, $service, $date));
    }



    


}