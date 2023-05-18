import daos.slotDAO_MySQL;
import model.Producte;

import java.sql.SQLException;
import java.util.Scanner;

public class inputHelper {

    private static Scanner lector = null;
    public static slotDAO_MySQL slot = new slotDAO_MySQL();
    public inputHelper(){
        lector = new Scanner(System.in);
    }
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
    public String readLine(){
        return lector.nextLine();
    }

    public void seleccionarProducte() throws SQLException {
        System.out.print("Escriu el nom del producte que vols: ");
        String nom = lector.nextLine();
        slot.modificarQuantitatProducte(nom);
    }

}
