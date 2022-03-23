<?php

require_once('../dao/TableDAO.php');

$method = $_SERVER["REQUEST_METHOD"];

switch ($method) {
    case 'GET':
        if (isset($_GET['idService'])) {
            if (isset($_GET['demande']) && $_GET['demande'] == "service") {
                $idService = $_GET['idService'];
                $tables = TableDAO::getAllTables($_GET['idService']);
                printf(json_encode($tables));
            } else if ($_GET['demande'] && $_GET['demande'] == "idtable") {
                $idtable = htmlspecialchars($_GET['idtable']);
                $uneTable = TableDAO::getUneTable($idtable);
                printf(json_encode($uneTable));
            }
        }
        break;
    case 'POST':
        if (!empty($_POST)) {
            $table = new TableDTO();
            $table->setIdUser($_POST['idUser']);
            $table->setDate($_POST['date']);
            $table->setIdService($_POST['idService']);
            $table->setNbConvive($_POST['nbConvive']);
            $table->setNumTable($_POST['numTable']);

            TableDAO::ajoutTable($table);
            printf(json_encode("La commande de la 5 !!!"));

        } else {
            printf(json_encode("En attend toujourd, bon ils sont partis!"));
        }
        break;

    case 'PUT':
        parse_str(file_get_contents("php://input"), $uneTable);

        if (isset($table['idTable']) && !empty($plat['idTable'])) {
            $newTable = new TableDTO();
            $newTable->setNumTable(htmlspecialchars($newTable['idTable']));
            $newTable->setNbConvive(htmlspecialchars($newTable['nbConvive']));
            $newTable->setIdService(htmlspecialchars($newTable['idService']));
            $newTable->setDate(htmlspecialchars($newTable['date']));
            $newTable->setIdUser(htmlspecialchars($newTable['idUser']));

            TableDAO::majTable($newTable);
            print(json_encode("une nouvelle table pour les touristes!"));
        } else {
            printf(json_encode("Votre table n'a pas pu etre à jour! try again !!!"));
        }
        break;

    case 'DELETE':
        parse_str(file_get_contents("php://input"), $table);

        if (!empty($table['idTable'])) {
            TableDAO::suppTable(htmlspecialchars($table['idTable']));
        }
}