package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producte {
    private String codiProducte;
    private String nom;
    private String descripcio;
    private float preuCompra;
    private float preuVenta;


    @Override
    public String toString() {
        return "\n............................" + "\nCodi Producte: " + codiProducte +"\nNom: " + nom + "\nDescripcio: " + descripcio +
                "\nPreu Compra: " + preuCompra + "\nPreu Venta: " + preuVenta +  "\n............................";
    }
}
