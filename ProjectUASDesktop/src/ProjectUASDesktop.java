import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;

public class ProjectUASDesktop {
    public JPanel LoginAdminPanel;
    private JTextField tFUsername;
    private JTextField tFPassword;
    private JButton loginButton;
    private JPasswordField pFPassword;
    private JLabel ErrorCheck;
    String EmailAddress,Password;

    public ProjectUASDesktop() {
    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if((tFUsername.getText().length()!= 0)||(pFPassword.getPassword().length!=0)){
                EmailAddress = tFUsername.getText();
                Password = String.valueOf(pFPassword.getPassword());
                post();

            }
            else{
                ErrorCheck.setText("Ada bagian yang masih kosong");
            }

        }
    });
    }
    public void post(){
        try{
            getID();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getID() throws Exception {
        String url = "http://localhost:7000/loginadminreq";
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("POST"); //PUT / DELETE
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("EmailAddress", tFUsername.getText());
        jsonParam.put("Password", String.valueOf(pFPassword.getPassword()));

        byte[] jsData = jsonParam.toString().getBytes(StandardCharsets.UTF_8);
        OutputStream os = conn.getOutputStream();
        os.write(jsData);

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
        System.out.println("Data : \n" + response.toString());
//        JSONArray myArray = new JSONArray(response.toString());
        if(Objects.equals(response.toString(), "Login Gagal")){
            ErrorCheck.setText("password atau username salah");
        }

        if(!Objects.equals(response.toString(), "Login Gagal")){
            try {

                FileWriter file = new FileWriter("tes.json");
                file.write(response.toString());
                file.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            dispose();
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(loginButton);
            currentFrame.dispose();
            JFrame frame = new JFrame("MenuAdmin");
            frame.setContentPane(new MenuAdmin().menupanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500,500);
            //frame.pack();
            frame.setVisible(true);

        }
        os.flush();
        os.close();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ProjectUASDesktop");
        frame.setContentPane(new ProjectUASDesktop().LoginAdminPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        //frame.pack();
        frame.setVisible(true);
    }
}
