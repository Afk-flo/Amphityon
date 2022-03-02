<?php 
error_reporting(E_ALL);
ini_set("display_errors", 1);

require_once('../dao/UtilisateurDAO.php');

// On vérifie que les données envoyées sont bonnes 
$log = "a";
$mpd = "a";

$_POST['login'] = $log;
$_POST['mdp'] = $mpd;

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
    $user = UtilisateurDAO::connexion($login,$mdp);
    if(!is_null($user)) {
        // On affiche si tout est bon 
        print(json_encode($user));
    } else {
        print(json_encode("Erreur_ Login incorrect"));
        

    }
} else {
    printf(json_encode("Erreur_ Login incorrect"));
}