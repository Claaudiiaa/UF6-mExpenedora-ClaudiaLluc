package daos;

import model.Producte;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProducteDAO {

    public void createProducte(Producte p) throws SQLException;

    public Producte readProducte(String codiProducte) throws SQLException;

    public ArrayList<Producte> readProductes() throws SQLException;

    public void updateProducte(Producte p) throws SQLException;

    public void deleteProducte(Producte p) throws SQLException;

    public void deleteProducte(int posicio) throws SQLException;

}
