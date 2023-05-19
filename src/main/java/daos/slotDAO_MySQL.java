package daos;

import model.Slot;

import java.sql.*;
import java.util.ArrayList;

public class slotDAO_MySQL implements slotDAO{

    //Dades de connexió a la base de dades
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_ROUTE = "jdbc:mysql://localhost:3306/expenedora";
    private static final String DB_USER = "root";
    private static final String DB_PWD = "7304";
    private Connection conn = null;

    /**
     * Mètode per establir la connexio amb la base de dades
     */
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

    /**
     * Mètode per crear un slot i inserir-lo a la base de dades
     * @param s
     * @throws SQLException
     */
    @Override
    public void createSlot(Slot s) throws SQLException {

        PreparedStatement ps = conn.prepareStatement("INSERT INTO slot VALUES(?,?,?)");

        ps.setInt(1,s.getPosicio());
        ps.setInt(2,s.getQuantitat());
        ps.setString(3,s.getCodi_producte());
        int rowCount = ps.executeUpdate();
    }

    /**
     * Mètode per llegir els slots de la maquina
     * @param posicio posicio del producte
     * @return retorna l'slot
     * @throws SQLException
     */
    @Override
    public Slot readSlots(int posicio) throws SQLException {
        Slot s = new Slot();
        PreparedStatement ps = conn.prepareStatement("select * from slot where posicio = ?");
        ps.setInt(1, posicio);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            s.setPosicio(rs.getInt(1));
            s.setQuantitat(rs.getInt(2));
            s.setCodi_producte(rs.getString(3));
        }

        return s;
    }

    /**
     * Mètode per llegir els slots de la taula i afegir-los a un arrayList
     * @return retorna arraylist amb els slots
     * @throws SQLException
     */
    @Override
    public ArrayList<Slot> readSlot() throws SQLException {
        ArrayList<Slot> llistaSlots = new ArrayList<Slot>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM slot");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            Slot s = new Slot();

            s.setPosicio(rs.getInt(1));
            s.setQuantitat(rs.getInt(2));
            s.setCodi_producte(rs.getString(3));

            llistaSlots.add(s);
        }

        return llistaSlots;
    }

    /**
     * Mètode per modificar la quantitat dels productes i calcular el benefici de la màquina
     * @param nom nom del producte
     * @return float amb el benefici
     * @throws SQLException
     */
    @Override
    public float modificarQuantitatProducte(String nom) throws SQLException {
        PreparedStatement quantitatProducte = conn.prepareStatement("select quantitat from slot, producte where nom = ? ");
        quantitatProducte.setString(1, nom);
        ResultSet rs = quantitatProducte.executeQuery();
        rs.next();

        String quantitat = rs.getString(1);
        if(!quantitat.equals("0")){
            quantitatProducte = conn.prepareStatement("UPDATE slot set quantitat = quantitat-1 where codi_producte = (Select codi_producte from producte where nom = ?)");
            quantitatProducte.setString(1, nom);
            quantitatProducte.executeUpdate();
        }
        float benefici = 0;
        quantitatProducte = conn.prepareStatement("Select preu_venta - preu_copmra as benefici from producte where nom = ?");
        quantitatProducte.setString(1, nom);
        rs = quantitatProducte.executeQuery();

        if (rs.next()){
            benefici = rs.getFloat("benefici");
        }
        return benefici;
    }

    /**
     * Mètode per modificar la posicio d'un producte
     * @param posicioActual posicio actual del producte
     * @param novaPosicio nova posicio del producte
     * @throws SQLException
     */
    public void modificarPosicio(int posicioActual, int novaPosicio) throws SQLException {
        PreparedStatement posicio = conn.prepareStatement("Select posicio from slot where posicio = ?");
        posicio.setInt(1, posicioActual);
        ResultSet rs = posicio.executeQuery();
        rs.next();

        posicio = conn.prepareStatement("update slot set posicio = ? where posicio = ?");
        posicio.setInt(1, novaPosicio);
        posicio.setInt(2, posicioActual);
        posicio.executeUpdate();
    }

    /**
     * Mètode per modificar l'estoc del producte
     * @param posicio posicio del producte que volem modificar l'estoc
     * @param quantitatStock quantitat d'estoc que li volem posar
     * @throws SQLException
     */
    public void modificarStockProducte(int posicio, int quantitatStock) throws SQLException {
        PreparedStatement posicioProducte = conn.prepareStatement("Select posicio from slot where posicio = ?");
        posicioProducte.setInt(1, posicio);
        ResultSet rs = posicioProducte.executeQuery();
        rs.next();

        posicioProducte = conn.prepareStatement("update slot set quantitat = ? where posicio = ?");
        posicioProducte.setInt(1, quantitatStock);
        posicioProducte.setInt(2, posicio);
        posicioProducte.executeUpdate();
    }
}
