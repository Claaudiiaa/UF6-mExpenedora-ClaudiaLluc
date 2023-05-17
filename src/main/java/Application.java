import daos.*;
import model.Producte;
import model.Slot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    //Passar al DAO -->     //TODO: llegir les propietats de la BD d'un fitxer de configuració (Properties)
    //En general -->        //TODO: Afegir un sistema de Logging per les classes.

    private static ProducteDAO producteDAO = new ProducteDAO_MySQL();   //TODO: passar a una classe DAOFactory
    private static slotDAO slotDao = new slotDAO_MySQL();
    private static DAOFactory df = DAOFactory.getInstance();

    public static void main(String[] args) throws SQLException {

        Scanner lector = new Scanner(System.in);     //TODO: passar Scanner a una classe InputHelper
        int opcio = 0;

        do {
            mostrarMenu();
            opcio = Integer.parseInt(lector.nextLine());

            switch (opcio) {
                case 1 -> mostrarMaquina();
                case 2 -> comprarProducte();
                case 10 -> mostrarInventari();
                case 11 -> afegirProductes(lector);
                case 12 -> modificarMaquina();
                case 13 -> mostrarBenefici();
                case -1 -> System.out.println("Bye...");
                default -> System.out.println("Opció no vàlida");
            }

        } while (opcio != -1);

    }

    private static void modificarMaquina() {
        /**
         * Ha de permetre:
         *      - modificar les posicions on hi ha els productes de la màquina (quin article va a cada lloc)
         *      - modificar stock d'un producte que hi ha a la màquina
         *      - afegir més ranures a la màquina
         */

        Scanner lector = new Scanner(System.in);
        int opcio = 0;

        do {
            mostrarMenuModificacioMaquina();
            opcio = Integer.parseInt(lector.nextLine());

            switch (opcio) {
                case 1 -> modificarPosicioProducte();
                case 2 -> modificarStockProducte();
                case 3 -> afegirRanures();
                case 0 -> System.out.println("Tornant al menú principal...");
                default -> System.out.println("Opció no vàlida");
            }
        } while (opcio != 0);
    }

    private static void mostrarMenuModificacioMaquina() {
        System.out.print("""
                Menú de modificació de la màquina
                =================================
                [1] Modificar posició del producte
                [2] Modificar stock del producte
                [3] Afegir ranures
                [0] Tornar al menú principal
                """);
    }

    private static void modificarPosicioProducte() {

    }

    private static void modificarStockProducte() {

    }

    private static void afegirRanures() {

    }

    private static void afegirProductes(Scanner lector) {

        /**
         *      Crear un nou producte amb les dades que ens digui l'operari
         *      Agefir el producte a la BD (tenir en compte les diferents situacions que poden passar)
         *          El producte ja existeix
         *              - Mostrar el producte que té el mateix codiProducte
         *              - Preguntar si es vol actualitzar o descartar l'operació
         *          El producte no existeix
         *              - Afegir el producte a la BD
         *
         *     Podeu fer-ho amb llenguatge SQL o mirant si el producte existeix i després inserir o actualitzar
         */
            String continuar = null;
            Producte p;
            do {
                p = new Producte();
                System.out.print("Codi: ");
                String codiProducte = lector.nextLine();
                if (comprovarSiExisteix(codiProducte)) {
                    System.out.println("El producte ja existeix amb el mateix codi.");
                    System.out.print("Vols actualitzar el producte? (s/n): ");
                    String resposta = lector.nextLine();
                    if (resposta.equalsIgnoreCase("s")) {
                        // Actualitzar el producte existent
                    } else {
                        // Descartar l'operació
                    }
                } else {
                    p.setCodiProducte(codiProducte);
                    System.out.print("Nom: ");
                    p.setNom(lector.nextLine());
                    System.out.print("Descripcio: ");
                    p.setDescripcio(lector.nextLine());
                    System.out.print("Preu Compra: ");
                    p.setPreuCompra(Float.parseFloat(lector.nextLine()));
                    System.out.print("Preu Venta: ");
                    p.setPreuVenta(Float.parseFloat(lector.nextLine()));
                    try {
                        // Demanem de guardar el producte p a la BD
                        producteDAO.createProducte(p);
                        // Agafem tots els productes de la BD i els mostrem (per comprovar que s'ha afegit)
                        ArrayList<Producte> productes = producteDAO.readProductes();
                        for (Producte prod : productes) {
                            System.out.println(prod);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println(e.getErrorCode());
                    }
                }
                System.out.print("Vols continuar introduint productes? (s/n): ");
                continuar = lector.nextLine();
            } while (continuar.equalsIgnoreCase("s"));
        }
    private static boolean comprovarSiExisteix(String codiProducte) {

        try {
            // Consultar la base de dades per comprovar si el producte existeix
            Producte producte = producteDAO.readProducte(codiProducte);

            if (producte != null) {
                // El producte existeix
                return true;
            } else {
                // El producte no existeix
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

<<<<<<< HEAD
    private static void comprovarSiExisteix(String codiProducte) {
    }
=======
>>>>>>> d003c790460968e5e8c4d192778b995baaa7dc06

    private static void mostrarInventari() {

        try {
            //Agafem tots els productes de la BD i els mostrem
            ArrayList<Producte> productes = producteDAO.readProductes();
            for (Producte prod : productes) {
                System.out.println(prod);
            }

        } catch (SQLException e) {          //TODO: tractar les excepcions
            e.printStackTrace();
        }
    }

    private static void comprarProducte() throws SQLException {
        mostrarMaquina();
        //Comprovar que hi hagi productes en stock

        /**
         * Mínim: es realitza la compra indicant la posició on es troba el producte que es vol comprar
         * Ampliació (0.5 punts): es permet entrar el NOM del producte per seleccionar-lo (abans cal mostrar els
         * productes disponibles a la màquina)
         *
         * Tingueu en compte que quan s'ha venut un producte HA DE QUEDAR REFLECTIT a la BD que n'hi ha un menys.
         * (stock de la màquina es manté guardat entre reinicis del programa)
         */

    }

    /**
     * Mètode per mostrar els slots amb el nom del producte
     * @throws SQLException
     */
    private static void mostrarMaquina() throws SQLException {
        ArrayList<Slot> llistaSlots = slotDao.readSlot();
        ArrayList<Producte> llistaProductes = producteDAO.readProductes();
        System.out.print("""
                Posicio      Producte                Quantitat disponible
                ===========================================================
                """);
        for (Slot s : llistaSlots){
            System.out.printf("%-13s", s.getPosicio());
            for (Producte p :llistaProductes){
                if(p.getCodiProducte().equals(s.getCodi_producte())){
                    System.out.printf("%-25s", p.getNom());
                }
            }
            System.out.printf("%d\n", s.getQuantitat());
        }

    }

    /**
     * Mètode per mostrar el menú principal de l'aplicació
     */
    private static void mostrarMenu() {
        System.out.println("\nMenú de la màquina expenedora");
        System.out.println("=============================");
        System.out.println("Selecciona la operació a realitzar introduïnt el número corresponent: \n");

        //Opcions per client / usuari
        System.out.println("[1] Mostrar Posició / Nom producte / Stock de la màquina");
        System.out.println("[2] Comprar un producte");

        //Opcions per administrador / manteniment
        System.out.println();
        System.out.println("[10] Mostrar llistat productes disponibles (BD)");
        System.out.println("[11] Afegir productes disponibles");
        System.out.println("[12] Assignar productes / stock a la màquina");
        System.out.println("[13] Mostrar benefici");

        System.out.println();
        System.out.println("[-1] Sortir de l'aplicació");
    }

    private static void mostrarBenefici() {

        /** Ha de mostrar el benefici de la sessió actual de la màquina, cada producte té un cost de compra
         * i un preu de venda. La suma d'aquesta diferència de tots productes que s'han venut ens donaran el benefici.
         *
         * Simplement s'ha de donar el benefici actual des de l'últim cop que s'ha engegat la màquina. (es pot fer
         * amb un comptador de benefici que s'incrementa per cada venda que es fa)
         */

        /** AMPLIACIÓ **
         * En entrar en aquest menú ha de permetre escollir entre dues opcions: veure el benefici de la sessió actual o
         * tot el registre de la màquina.
         *
         * S'ha de crear una nova taula a la BD on es vagi realitzant un registre de les vendes o els beneficis al
         * llarg de la vida de la màquina.
         */
    }
}
