<?php 

require_once('../dto/UtilisateurDTO.php');
require_once('../dto/PlatDTO.php');
// require_once('../dto/CategorieDTO.php');
require_once('bdd.php');


/*
    * CLASS : Plat DAO
    * Permet de gérer les actions des différents utilisateurs en se basant sur le modèle PlatDTO
    * Fonction de création -- Permet de créer un plat  
    * Fonction d'accès -- Permet de récupérer un plat en se basant sur plat DTO / Cat DTO / User DTO
    * Fonction de modification -- Permet de modifier un plat 
    *
*/

class PlatDAO {

    /**
     * Fonction permettant de récupérer un plat et le transformer en objet / fetch assoc pour arrya 
     * 
     * @param string $id -- id du plat 
     * @return PlatDTO|null -- plat ou null
     */
    public static function getOne(int $id) : ?array {
        $req = bdd::getInstance()->prepare("SELECT * FROM PLAT WHERE idPlat = ?");
        $req->execute(array($id));
        $req->setFetchMode(PDO::FETCH_ASSOC);
        $plat = $req->fetch();
        return $plat;
    }

    /**
     * Fonction permettant de récupérer tous les plats sans distinction de catégorie ni de cuisinier 
     * 
     * @return PlatDTO[]|null -- plat ou nul si jamais 
     */
    public static function getAll() : ?array {
        $req = bdd::getInstance()->prepare("SELECT * FROM PLAT");
        $req->execute(array());
        $req->setFetchMode(PDO::FETCH_ASSOC);
        $plat = $req->fetchAll();
        return $plat;
    }

     /**
     * Fonction permettant de récupérer tous les plats sans distinction de catégorie ni de cuisinier 
     * 
     * @param int|string $idCuisinier -- Id du cuisinier 
     * @return PlatDTO[]|null -- plat ou nul si jamais 
     */
    public static function getAllByCuisto($idCuisinier) : ?array {
        $req = bdd::getInstance()->prepare("SELECT NOMPLAT, DESCRIPTIF FROM PLAT INNER JOIN UTILISATEUR AS C ON PLAT.IDUSER = C.IDUSER WHERE PLAT.IDUSER = ? ");
        $req->execute(array($idCuisinier));
        $req->setFetchMode(PDO::FETCH_ASSOC);
        $plat = $req->fetchAll();
        return $plat;
    }


     /**
     * Fonction permettant de récupérer tous les plats sans distinction de catégorie ni de cuisinier 
     * 
     * @param int|string $idCategorie -- Id de la categorie  
     * @return PlatDTO[]|null -- plat ou nul si jamais 
     */
    public static function getAllByCat($idCategorie) : ?array {
        $req = bdd::getInstance()->prepare("SELECT NOMPLAT, DESCRIPTIF, LIBELLE FROM PLAT INNER JOIN CATEGORIES AS C ON PLAT.IDCAT = C.IDCAT WHERE PLAT.IDCAT = ?");
        $req->execute(array($idCategorie));
        $req->setFetchMode(PDO::FETCH_ASSOC);
        $plat = $req->fetchAll();
        return $plat;
    }

     /**
     * Fonction permettant de récupérer tous les plats sans distinction de catégorie ni de cuisinier 
     * 
     * @param int|string $idCategorie -- id de la categorie 
     * @param int|string $idUser -- id de l'utilisateur 
     * @return PlatDTO[]|null -- plat ou nul si jamais 
     */
    public static function getAllByCuistoAndCat($idCategorie, $idUser) : ?array {
        $req = bdd::getInstance()->prepare("SELECT NOMPLAT, DESCRIPTIF, LIBELLE, NOM, PRENOM  FROM PLAT INNER JOIN CATEGORIES AS C ON PLAT.IDCAT = C.IDCAT INNER JOIN UTILISATEUR AS CUI ON PLAT.IDUSER = CUI.IDUSER WHERE PLAT.IDUSER = ? AND PLAT.IDCAT = ?");
        $req->execute(array($idUser, $idCategorie));
        $req->setFetchMode(PDO::FETCH_ASSOC);
        $plat = $req->fetchAll();
        return $plat;
    }



    // Création de la pizzaaaa

    /**
     * Fonction permettant de créer un plat dans la base de données 
     * 
     * @param PlatDTO $plat -- Objet de type plat avec plein de bonnes choses dedans 
     * @param $idCat - Id de la categorie 
     * @param $idUser - Identifiant de l'user 
     * @return void - Like my heart >:(
     */
    public static function create(PlatDTO $plat, $idCat, $idUser) : void {
        $sql = "INSERT INTO PLAT (IDCAT, IDUSER, NOMPLAT, DESCRIPTIF) VALUES (?,?,?,?)";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($idCat, $idUser, $plat->getNomPlat(), $plat->getDescriptif()));
    }

    // UPdateleu 

    /**
     * Fonction permetant de mettre à jour dans la base de données un plat donné
     * 
     * @param PlatDTO $plat - Mais que nous reserve-t-il ?
     * @return void - I'm Vengence 
     */
    public static function update(PlatDTO $plat): void  {
        $sql = "UPDATE PLAT SET IDPLAT = ?, IDCAT = ?, IDUSER = ?, NOMPLAT = ?, DESCRIPTIF = ?";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($plat->getId(), $plat->getIdCat(), $plat->getIdUser(), $plat->getNomPlat(), $plat->getDescriptif()));
    }

    // REMOVE 

    /**
     * Fonction permettant de supprimer les plats (trop long = pas bon)
     * 
     * @param $idPlat -- l'id du plat qu'on va couic 
     * @return void - mouais
     */
    public static function delete($idPlat) : void {
        $sql = "DELETE FROM PLAT WHERE IDPLAT = ?";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($idPlat));
    }


}
