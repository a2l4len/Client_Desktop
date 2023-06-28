import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class daftar_admin {

    public JPanel daftaradminpanel;
    private JTable table2;
    private JButton backbutton2;

    int id_admin;
    String email;
    String name;


    public daftar_admin() {
    try {
        //sambung ke server
        String url = "http://localhost:7000/daftarmemberadmin";
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET"); //PUT / DELETE
        conn.connect();

        //get response from server
        int responseCode = conn.getResponseCode();
        System.out.println("Send Post Request to : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );
        String input;
        StringBuilder response = new StringBuilder();
        while ((input = in.readLine()) != null) {
            response.append(input);
        }
        in.close();

        System.out.println("Data : \n" + response);

        DefaultTableModel tabeladmin = (DefaultTableModel) table2.getModel();
        tabeladmin.addColumn("ID Admin");
        tabeladmin.addColumn("Email");
        tabeladmin.addColumn("Name");

        JSONObject jsonParam = new JSONObject(response.toString());
        JSONArray jsonarray = jsonParam.getJSONArray("response");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject admin = jsonarray.getJSONObject(i);
            id_admin = admin.getInt("ID_Admin");
            email = admin.getString("Email");
            name = admin.getString("Nama");
            //System.out.println("Email: "+email);

            tabeladmin.addRow(new Object[]{id_admin, email, name});
        }
        table2 = new JTable(tabeladmin);
        JScrollPane scrollPane = new JScrollPane(table2);

    } catch (Exception e) {
        System.out.println("Server Error");
        throw new RuntimeException(e);
    }

    backbutton2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(backbutton2);
            currentFrame.dispose();
            JFrame frame = new JFrame("MenuAdmin");
            frame.setContentPane(new MenuAdmin().menupanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500,500);
            //frame.pack();
            frame.setVisible(true);
        }
    });
}

    public static void main(String[] args) {
        JFrame frame = new JFrame("daftar_admin");
        frame.setContentPane(new daftar_admin().daftaradminpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        //frame.pack();
        frame.setVisible(true);
    }

}


