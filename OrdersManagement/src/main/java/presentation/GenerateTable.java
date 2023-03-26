package presentation;

import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GenerateTable<T> {

    /**
     *
     * @param list
     * @param table
     * @param model
     * @return
     */
    public JTable generateTable(List<T> list, JTable table, DefaultTableModel model){

        int i = 0;

        Field[] allFields = list.getClass().getFields();
        for(Field f: allFields){
            model.addColumn(f.getName());
            i++;
        }
        table.setModel(model);

        String[] row = new String[i];

        return table;
    }


}
