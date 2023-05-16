package daos;

import model.Producte;
import model.Slot;

import java.sql.*;
import java.util.ArrayList;

public class slotDAO_MySQL implements slotDAO{

    //Dades de connexió a la base de dades
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_ROUTE = "jdbc:mysql://localhost:3306/expenedora";
    private static final String DB_USER = "root";
    private static final String DB_PWD = "1234";

    private Connection conn = null;

    public slotDAO_MySQL()
    {
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_ROUTE, DB_USER, DB_PWD);
            System.out.println("Conexió establerta satisfactoriament");
        } catch (Exception e) {
            System.out.println("S'ha produit un error en intentar connectar amb la base de dades. Revisa els paràmetres");
            System.out.println(e);
        }
    }

    @Override
    public void createSlot(Slot s) throws SQLException {

        PreparedStatement ps = conn.prepareStatement("INSERT INTO slot VALUES(?,?,?)");

        ps.setInt(1,s.getPosicio());
        ps.setInt(2,s.getQuantitat());
        ps.setString(3,s.getCodi_producte());
        int rowCount = ps.executeUpdate();
    }

    @Override
    public Slot readSlot() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Slot> readSlot() throws SQLException {
        ArrayList<Slot> llistaProductes = new ArrayList<Slot>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM slot");

        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            Slot s = new Slot();
            /**
             p.setCodiProducte(rs.getString(codi_producte));
             p.setNom(rs.getString(nom));
             p.setDescripcio(rs.getString(descripcio));
             p.setPreuCompra(rs.getFloat(preu_compra));
             p.setPreuVenta(rs.getFloat(preu_venta));
             **/

            s.setPosicio(rs.getInt(1));
            s.setQuantitat(rs.getInt(2));
            s.setCodi_producte(rs.getString(3));

            llistaSlots.add(s);
        }

        return llistaSlots;
    }

    @Override
    public void updateSlot(Slot s) throws SQLException {

    }

    @Override
    public void deleteSlot(Slot s) throws SQLException {

    }

    @Override
    public void deleteSlot(String codiSlot) throws SQLException {

    }
}