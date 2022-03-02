<?php 

require_once('Param.php');

class bdd extends PDO {

    private static $instance;

    public static function getInstance() {
        if(!self::$instance) {
            self::$instance = new bdd();
        }
        return self::$instance;
    }

    function __construct()
    {
        try {
            parent::__construct(Param::$db, Param::$user, Param::$mdp);
        } catch (Exception $e) {
            echo $e->getMessage();
            die("Connexion impossible");
        }
        
    }
}