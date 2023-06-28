import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class bookinglist extends JFrame{
    private JTable bookingtable;
    private JButton backbutton3;
    public JPanel bookinglistpanel;
    private JTextField idbookingpilih;
    private JButton accbutton;
    private JButton notaccbutton;
    private JLabel hasilperubahan;
    private JButton refreshbutton;
    int id_member;
    int id_booking;
    String name;
    String tanggalpesan;
    String tanggalbooking;
    String email;
    String lokasihotel;
    String Hotel;
    String kamar;
    String statusbooking;
    String idbook;



public bookinglist() {
    try {
        //sambung ke server
        String url = "http://localhost:7000/databooking";
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

        DefaultTableModel tabelbooking = (DefaultTableModel) bookingtable.getModel();
        tabelbooking.addColumn("Tanggal Pesan");
        tabelbooking.addColumn("ID Booking");
        tabelbooking.addColumn("ID Member");
        tabelbooking.addColumn("Email");
        tabelbooking.addColumn("Nama");
        tabelbooking.addColumn("Pilihan Hotel");
        tabelbooking.addColumn("Lokasi Hotel");
        tabelbooking.addColumn("Kamar yang Dipilih");
        tabelbooking.addColumn("Tanggal yang Dipilih");
        tabelbooking.addColumn("Status Booking");

        JSONObject jsonParam = new JSONObject(response.toString());
        JSONArray jsonarray = jsonParam.getJSONArray("response");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject booking = jsonarray.getJSONObject(i);
            tanggalpesan = booking.getString("Tanggal_Pesan");
            id_booking = booking.getInt("ID_Booking");
            id_member = booking.getInt("ID_Member");
            email = booking.getString("Email");
            name = booking.getString("Nama");
            Hotel = booking.getString("Hotel_DiBooking");
            lokasihotel = booking.getString("Lokasi_Hotel");
            kamar = booking.getString("Kamar_DiBooking");
            tanggalbooking = booking.getString("Tanggal_DiBooking");
            statusbooking = booking.getString("Status_Booking");
            //System.out.println("Email: "+email);

            tabelbooking.addRow(new Object[]{tanggalpesan, id_booking, id_member, email, name, Hotel, lokasihotel, kamar, tanggalbooking, statusbooking});
        }
        bookingtable = new JTable(tabelbooking);
        JScrollPane scrollPane = new JScrollPane(bookingtable);

    } catch (Exception e) {
        System.out.println("Server Error");
        throw new RuntimeException(e);
    }

    backbutton3.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(backbutton3);
            currentFrame.dispose();
            JFrame frame = new JFrame("MenuAdmin");
            frame.setContentPane(new MenuAdmin().menupanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500,500);
            //frame.pack();
            frame.setVisible(true);
        }
    });
    accbutton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if((idbookingpilih.getText().length()!= 0)){
                idbook = idbookingpilih.getText();
                try{
                    String url = "http://localhost:7000/approve";
                    URL obj = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                    conn.setRequestMethod("PUT"); //PUT / DELETE
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("idbook", idbook);

                    byte[] jsData = jsonParam.toString().getBytes(StandardCharsets.UTF_8);
                    OutputStream os = conn.getOutputStream();
                    os.write(jsData);

                    int responseCode = conn.getResponseCode();
                    System.out.println("Send Put Request to : " + url);
                    System.out.println("Response Code : " + responseCode);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream())
                    );

                    StringBuilder response = new StringBuilder();
                    //System.out.println("Data : \n" + response.toString());
                    String input;
                    while ((input = in.readLine()) != null) {
                        response.append(input);
                    }
                    in.close();
                    if(Objects.equals(response.toString(), "Perubahan Berhasil")){
                        hasilperubahan.setText("Perubahan Berhasil");
                    }
                    else{
                        hasilperubahan.setText("Perubahan Tidak Berhasil");
                    }
//                    os.flush();
//                    os.close();
                } catch (Exception r) {
                    System.out.println("Server Error");
                    r.printStackTrace();
                }
            }
        }
    });
    notaccbutton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if((idbookingpilih.getText().length()!= 0)){
                idbook = idbookingpilih.getText();
                try{
                    String url = "http://localhost:7000/unapprove";
                    URL obj = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                    conn.setRequestMethod("PUT"); //PUT / DELETE
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("idbook", idbook);

                    byte[] jsData = jsonParam.toString().getBytes(StandardCharsets.UTF_8);
                    OutputStream os = conn.getOutputStream();
                    os.write(jsData);

                    int responseCode = conn.getResponseCode();
                    System.out.println("Send Put Request to : " + url);
                    System.out.println("Response Code : " + responseCode);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream())
                    );

                    StringBuilder response = new StringBuilder();
                    //System.out.println("Data : \n" + response.toString());
                    String input;
                    while ((input = in.readLine()) != null) {
                        response.append(input);
                    }
                    in.close();
                    if(Objects.equals(response.toString(), "Perubahan Berhasil")){
                        hasilperubahan.setText("Perubahan Berhasil");
                    }
                    else{
                        hasilperubahan.setText("Perubahan Tidak Berhasil");
                    }
//                    os.flush();
//                    os.close();
                } catch (Exception r) {
                    System.out.println("Server Error");
                    r.printStackTrace();
                }
            }
        }
    });
    refreshbutton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(refreshbutton);
            currentFrame.dispose();
            JFrame frame = new JFrame("bookinglist");
            frame.setContentPane(new bookinglist().bookinglistpanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800,500);
            //frame.pack();
            frame.setVisible(true);
        }
    });
}

    public static void main(String[] args) {
        JFrame frame = new JFrame("bookinglist");
        frame.setContentPane(new bookinglist().bookinglistpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);
        //frame.pack();
        frame.setVisible(true);
    }

}
