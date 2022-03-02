<?php 

require_once('../dto/UtilisateurDTO.php');
require_once('bdd.php');


/*
    * CLASS : UTILISATEUR DAO
    * Permet de gérer les actions des différents utilisateurs en se basant sur le modèle UTILISATEUR DTO
    * Fonction de connexion -- Associe un identifiant et mot de passe afin de retourner un utilisateur 
    * Fonction d'accès -- Permet de récupérer un utilisateur dans la base et le transformer en UTILISATEUR DTO
    *
    *
*/

class UtilisateurDAO {

    /**
     * Fonction permettant de récupérer un utilisateur et le transformer en objet 
     * 
     * @param string $token -- token d'identification
     * @return UtilisateurDTO|null -- Utilsiateur ou null
     */
    public static function getOne(string $token) : ?array {
        $req = bdd::getInstance()->prepare("SELECT * FROM UTILISATEUR WHERE TOKEN = ?");
        $req->execute(array($token));
       $req->setFetchMode(PDO::FETCH_ASSOC);
        $user = $req->fetch();
       // var_dump($user);
        return $user;
    }

    /**
     * Fonction permettant de récupérer tous les utilisateurs selon leurs fonctions
     * 
     * @param string $token -- token d'identification
     * @param string $fonction -- fonction de l'utilisateur 
     * @return array|null -- Array ou null selon le résultat 
     * 
     */
    public static function getAllByCat(string $token, string $fonction) : ?array {

    }

    /**
     * Fonction permettant d'initier une connexion pour un utilisateur
     * 
     * @param string $login -- Identifiation de l'utilisateur 
     * @param string $mdp -- Mot de passe de l'utilisateur 
     * @return UtilisateurDTO|null -- 
     */
    public static function connexion(string $login, string $mdp) : ?array {
        // Première tentative pour l'identifiant 
        $sql = "SELECT TOKEN, IDENTIFIANT, MOTDEPASSE FROM UTILISATEUR WHERE IDENTIFIANT = ?";
        $req = bdd::getInstance()->prepare($sql);
        $req->execute(array($login));
        $data = $req->fetch();


        if(!isset($data['TOKEN']) && $data['TOKEN'] == null ) {
            return null;
        } else {
            if(password_verify($mdp, $data["MOTDEPASSE"])) {
                // Si les mdps correspondent on envoie un user 
                $user = self::getOne($data['TOKEN']);
               // var_dump($user);
                return $user;
            } else {
                return null;
            }
        }
    }


    /**
     * Méthode permettant de se déconnecter 
     * 
     * @return void 
     */
    public static function deconnexion() {
        $_SESSION = [];
        session_destroy();
    }

}