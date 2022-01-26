<?php 

require_once('dao/UtilisateurDAO.php');

// On vérifie que les données envoyées sont bonnes 
if(isset($_POST['login']) && !empty($_POST['login']) && isset($_POST['mdp']) && !empty($_POST['mdp'])) {

    // Nettoyage de données 
    $login = htmlspecialchars($_POST['login']);
    $login = filter_var($login, FILTER_SANITIZE_STRING);
    $login = trim($login);

    // Nettoyage de données 
    $mdp = htmlspecialchars($_POST['mdp']);
    $mdp = filter_var($mdp, FILTER_SANITIZE_STRING);
    $mdp = trim($mdp);

    // Tentative de connexion 
    $user = UtilisateurDAO::Connexion($login,$mdp);
    if(!is_null($user)) {
        // On affiche si tout est bon 
        print(json_encode($user));
    } else {
        print(json_encode("Erreur_ Login incorrect"));
    }
} else {
    printf(json_encode("Erreur_ Login incorrect"));
}