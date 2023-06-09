import daos.*;
import model.Producte;
import model.Slot;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    //Passar al DAO -->     //TODO: llegir les propietats de la BD d'un fitxer de configuració (Properties)
    //En general -->        //TODO: Afegir un sistema de Logging per les classes.

    private static ProducteDAO producteDAO = new ProducteDAO_MySQL();   //TODO: passar a una classe DAOFactory
    private static slotDAO slotDao = new slotDAO_MySQL();

    static inputHelper input = new inputHelper();

    public static void main(String[] args) throws SQLException {

        int opcio = 0;

        do {
            mostrarMenu();
            opcio = Integer.parseInt(input.readLine());

            switch (opcio) {
                case 1 -> mostrarMaquina();
                case 2 -> comprarProducte();
                case 10 -> mostrarInventari();
                case 11 -> afegirProductes();
                case 12 -> modificarMaquina();
                case 13 -> mostrarBenefici();
                case -1 -> System.out.println("Bye...");
                default -> System.out.println("Opció no vàlida");
            }

        } while (opcio != -1);

    }

    /**
     * Mètode amb switch per modificar la màquina
     * @throws SQLException
     */
    private static void modificarMaquina() throws SQLException {
        /**
         * Ha de permetre:
         *      - modificar les posicions on hi ha els productes de la màquina (quin article va a cada lloc)
         *      - modificar stock d'un producte que hi ha a la màquina
         *      - afegir més ranures a la màquina
         */

        int opcio;

        do {
            mostrarMenuModificacioMaquina();
            opcio = Integer.parseInt(input.readLine());

            switch (opcio) {
                case 1 -> modificarPosicioProducte();
                case 2 -> modificarStockProducte();
                case 3 -> afegirRanures();
                case 0 -> System.out.println("Tornant al menú principal...");
                default -> System.out.println("Opció no vàlida");
            }
        } while (opcio != 0);
    }

    /**
     * Mètode que mostra el menu per modificar la màquina
     */
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

    /**
     * Mètode per modificar la posicio dels productes
     * @throws SQLException
     */
    private static void modificarPosicioProducte() throws SQLException {
        input.seleccionaPosicioModificar();
    }

    /**
     * Mètode per modificar l'estoc de la màquina
     * @throws SQLException
     */
    private static void modificarStockProducte() throws SQLException {
        input.modificarStock();
    }

    /**
     * Mètode per afegir noves ranures a la màquina
     * @throws SQLException
     */
    private static void afegirRanures() throws SQLException {
        input.afegirRanures();
    }

    /**
     * Mètode per afegir productes a la base de dades
     */
    private static void afegirProductes() throws SQLException {

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
        String continuar;
        do {
            Producte p = input.introduirDadesProducte();

            try {
                // Demanem de guardar el producte p a la BD
                producteDAO.createProducte(p);
                // Agafem tots els productes de la BD i els mostrem (per comprovar que s'ha afegit)
                ArrayList<Producte> productes = producteDAO.readProductes();
                for (Producte prod : productes) {
                    System.out.println(prod);
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062){
                    modificarProducteExistent(p);
                }else {
                    e.printStackTrace();
                    System.out.println(e.getErrorCode());
                }
            }

            System.out.print("Vols continuar introduint productes? (s/n): ");
            continuar = input.readLine();
        } while (continuar.equalsIgnoreCase("s"));
    }

    /**
     * Mètode per modificar el codi del producte si ja existeix
     * @param p Producte que li actualitzarem el codi
     * @throws SQLException
     */
    private static void modificarProducteExistent(Producte p) throws SQLException {
        System.out.print("El producte ja existeix, vols modificar el codi? (s/n)");
        String modificar = input.readLine();
        if(modificar.equalsIgnoreCase("s")){
            System.out.print("Introdueix el nou codi: ");
            String codi = input.readLine();
            p.setCodiProducte(codi);
            producteDAO.createProducte(p);
        }
    }


    /**
     * Mètode per mostrar l'inventari de la màquina expenedora
     */
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

    /**
     * Mètode per comprar els productes, un cop s'introdueix el nom d'un producte
     * es resta 1 unitat a la quantitat del producte.
     * @throws SQLException
     */
    private static void comprarProducte() throws SQLException {

        /**
         * Mínim: es realitza la compra indicant la posició on es troba el producte que es vol comprar
         * Ampliació (0.5 punts): es permet entrar el NOM del producte per seleccionar-lo (abans cal mostrar els
         * productes disponibles a la màquina)
         *
         * Tingueu en compte que quan s'ha venut un producte HA DE QUEDAR REFLECTIT a la BD que n'hi ha un menys.
         * (stock de la màquina es manté guardat entre reinicis del programa)
         */
        mostrarMaquina();
        input.seleccionarProducte();
    }


    /**
     * Mètode per mostrar els slots amb el nom del producte
     *
     * @throws SQLException
     */
    private static void mostrarMaquina() throws SQLException {
        ArrayList<Slot> llistaSlots = slotDao.readSlot();
        ArrayList<Producte> llistaProductes = producteDAO.readProductes();
        System.out.print("""
                Posicio      Producte                Quantitat disponible
                ===========================================================
                """);
        for (Slot s : llistaSlots) {
            System.out.printf("%-13s", s.getPosicio());
            for (Producte p : llistaProductes) {
                if (p.getCodiProducte().equals(s.getCodi_producte())) {
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

    /**
     * Mètode per mostrar el benefici de la màquina
     */
    private static void mostrarBenefici() {
        System.out.printf("El benefici de la màquina és %3f", inputHelper.benefici);
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
