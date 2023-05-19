import daos.slotDAO_MySQL;
import model.Producte;
import model.Slot;

import java.sql.SQLException;
import java.util.Scanner;

public class inputHelper {

    private static Scanner lector = null;
    public static slotDAO_MySQL slot = new slotDAO_MySQL();
    public static float benefici;
    public inputHelper(){
        lector = new Scanner(System.in);
    }

    /**
     * Mètode per introduir les dades del producte
     * @return producte creat amb les dades
     */
    public Producte introduirDadesProducte(){
        Producte p = new Producte();
        System.out.print("Codi: ");
        p.setCodiProducte(lector.nextLine());
        System.out.print("Nom: ");
        p.setNom(lector.nextLine());
        System.out.print("Descripcio: ");
        p.setDescripcio(lector.nextLine());
        System.out.print("Preu Compra: ");
        p.setPreuCompra(Float.parseFloat(lector.nextLine()));
        System.out.print("Preu Venta: ");
        p.setPreuVenta(Float.parseFloat(lector.nextLine()));
        return p;
    }

    /**
     * Mètode per utilitzar el lector
     * @return string
     */
    public String readLine(){
        return lector.nextLine();
    }

    /**
     * Mètode per seleccionar els productes i calcular el benefici de la màquina
     * @throws SQLException
     */
    public void seleccionarProducte() throws SQLException {
        System.out.print("Escriu el nom del producte que vols: ");
        String nom = lector.nextLine();
        benefici += slot.modificarQuantitatProducte(nom);
    }

    /**
     * Mètode per seleccionar la posicio que es vol modificar
     * @throws SQLException
     */
    public void seleccionaPosicioModificar() throws SQLException {
        System.out.print("Introdueix la posició que vols modificar: ");
        int posicioActual = Integer.parseInt(lector.nextLine());
        System.out.print("Introdueix la nova posició: ");
        int novaPosicio = Integer.parseInt(lector.nextLine());
        slot.modificarPosicio(posicioActual, novaPosicio);
    }

    /**
     * Mètode per modificar l'estoc de la màquina
     * @throws SQLException
     */
    public void modificarStock() throws SQLException {
        System.out.print("Introdueix la posició del producte que vols modificar l'estoc: ");
        int posicioActual = Integer.parseInt(lector.nextLine());
        System.out.print("Introdueix l'estoc que vols posar: ");
        int quantitatStock = Integer.parseInt(lector.nextLine());
        slot.modificarStockProducte(posicioActual, quantitatStock);
    }

    /**
     * Mètode per afegir ranures a la màquina
     * @throws SQLException
     */
    public void afegirRanures() throws SQLException {
        System.out.print("Introdueix la posició del producte: ");
        int posicio = Integer.parseInt(lector.nextLine());
        System.out.print("Introdueix el codi del producte: ");
        String nomProducte = lector.nextLine();
        System.out.print("Introdueix la quantitat d'estoc: ");
        int quantitat = Integer.parseInt(lector.nextLine());
        Slot s = new Slot(posicio, quantitat, nomProducte);
        slot.createSlot(s);
    }
}
