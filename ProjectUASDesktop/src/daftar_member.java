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

public class daftar_member {
    private JTable table1;
    public JPanel Daftarmemberpanel;
    private JButton backbutton;

    int id_member;
    String name;
    String email;


    public daftar_member() {

        try {
            //sambung ke server
            String url = "http://localhost:7000/daftarmember";
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

            DefaultTableModel tabelmember = (DefaultTableModel) table1.getModel();
            tabelmember.addColumn("ID Member");
            tabelmember.addColumn("Email");
            tabelmember.addColumn("Name");

            JSONObject jsonParam = new JSONObject(response.toString());
            JSONArray jsonarray = jsonParam.getJSONArray("response");
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject member = jsonarray.getJSONObject(i);
                id_member = member.getInt("ID_Member");
                email = member.getString("Email");
                name = member.getString("Nama");
                //System.out.println("Email: "+email);

                tabelmember.addRow(new Object[]{id_member, email, name});
            }
            table1 = new JTable(tabelmember);
            JScrollPane scrollPane = new JScrollPane(table1);

        } catch (Exception e) {
            System.out.println("Server Error");
            throw new RuntimeException(e);
        }
        backbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(backbutton);
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
        JFrame frame = new JFrame("daftar_member");
        frame.setContentPane(new daftar_member().Daftarmemberpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        //frame.pack();
        frame.setVisible(true);
    }
}
