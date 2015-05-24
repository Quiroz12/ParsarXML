package com.example.quiroz.parsarxml;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;



public class MainActivity extends ListActivity {

    // All static variables
    static final String URL = "http://resources.260mb.net/bbdd.xml";
    // XML node keys http://
    static final String KEY_PRESTAMOS = "prestamo"; // parent node
    static final String KEY_CLAVE_PRESTAMO = "clave_prestamo";
    static final String KEY_FECHA = "fecha";
    static final String KEY_NOMBRE_SOL = "nombre_sol";
    static final String KEY_AREA_SOL = "area_sol";
    static final String KEY_DESC = "descripcion";
    static final String KEY_REC = "recibido";
    static final String KEY_ENT = "entregado";
    ///NOTA cabiar los demas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

        XMLParser parser = new XMLParser();
        String xml = parser.getXmlFromUrl(URL); // getting XML
        Document doc = parser.getDomElement(xml); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_PRESTAMOS);

        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(KEY_CLAVE_PRESTAMO, parser.getValue(e, KEY_CLAVE_PRESTAMO));
            map.put(KEY_FECHA, parser.getValue(e, KEY_FECHA));
            map.put(KEY_NOMBRE_SOL, parser.getValue(e, KEY_NOMBRE_SOL));
            map.put(KEY_AREA_SOL, parser.getValue(e, KEY_AREA_SOL));
            map.put(KEY_DESC, parser.getValue(e, KEY_DESC));
            map.put(KEY_REC, parser.getValue(e, KEY_REC));
            map.put(KEY_ENT, parser.getValue(e, KEY_ENT));

            // adding HashList to ArrayList
            menuItems.add(map);

    }


        // Adding menuItems to ListView
        ListAdapter adapter = new SimpleAdapter(this, menuItems,
                R.layout.lista_prestamos,
                new String[] { KEY_CLAVE_PRESTAMO, KEY_NOMBRE_SOL, KEY_FECHA, KEY_AREA_SOL, KEY_DESC , KEY_REC, KEY_ENT}, new int[] {
                R.id.id_v_prestamo, R.id.v_nombre,R.id.v_fecha, R.id.v_area_sol, R.id.v_descripcion, R.id.v_recibido, R.id.v_entregado});

        setListAdapter(adapter);

        // selecting single ListView item
        ListView lv = getListView();

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String tx_calvepres = ((TextView) view.findViewById(R.id.id_v_prestamo)).getText().toString();
                String tx_nom = ((TextView) view.findViewById(R.id.v_nombre)).getText().toString();
                String txt_fe=((TextView) view.findViewById(R.id.v_fecha)).getText().toString();
                String txtarea=((TextView) view.findViewById(R.id.v_area_sol)).getText().toString();
                String txt_des=((TextView) view.findViewById(R.id.v_descripcion)).getText().toString();
                String txt_rec=((TextView) view.findViewById(R.id.v_recibido)).getText().toString();
                String txt_ent=((TextView) view.findViewById(R.id.v_entregado)).getText().toString();


                // Starting new intent
                Intent in = new Intent(getApplicationContext(), vista_indiv.class);
                in.putExtra(KEY_CLAVE_PRESTAMO, tx_calvepres);
                in.putExtra(KEY_NOMBRE_SOL, tx_nom);
                in.putExtra(KEY_FECHA, txt_fe);
                in.putExtra(KEY_AREA_SOL, txtarea);
                in.putExtra(KEY_DESC, txt_des);
                in.putExtra(KEY_REC, txt_rec);
                in.putExtra(KEY_ENT, txt_ent);


                startActivity(in);

            }
        });
    }
}