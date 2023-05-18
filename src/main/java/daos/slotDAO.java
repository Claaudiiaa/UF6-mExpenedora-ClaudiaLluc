package daos;

import model.Slot;

import java.sql.SQLException;
import java.util.ArrayList;

public interface slotDAO {
    public void createSlot(Slot s) throws SQLException;
    public Slot readSlots(int posicio) throws SQLException;
    public ArrayList<Slot> readSlot() throws SQLException;
    public void updateSlot(Slot s) throws SQLException;
    public void deleteSlot(Slot s) throws SQLException;
    public void deleteSlot(int posicio) throws  SQLException;

    void modificarQuantitatProducte(String nom) throws SQLException;
}
