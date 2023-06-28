import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAdmin {
    private JButton daftar_member;
    public JPanel menupanel;
    private JButton daftaradminbutton;
    private JButton listbookingbutton;
    private JButton logoutbutton;

    public MenuAdmin() {
        daftar_member.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(daftar_member);
                    currentFrame.dispose();
                    JFrame frame = new JFrame("daftar_member");
                    frame.setContentPane(new daftar_member().Daftarmemberpanel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
            }
        });
        daftaradminbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(daftaradminbutton);
                currentFrame.dispose();
                JFrame frame = new JFrame("daftar_admin");
                frame.setContentPane(new daftar_admin().daftaradminpanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        listbookingbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(listbookingbutton);
                currentFrame.dispose();
                JFrame frame = new JFrame("bookinglist");
                frame.setContentPane(new bookinglist().bookinglistpanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800,500);
                //frame.pack();
                frame.setVisible(true);
            }
        });
        logoutbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(logoutbutton);
                currentFrame.dispose();
                JFrame frame = new JFrame("ProjectUASDesktop");
                frame.setContentPane(new ProjectUASDesktop().LoginAdminPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500,500);
                //frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MenuAdmin");
        frame.setContentPane(new MenuAdmin().menupanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        //frame.pack();
        frame.setVisible(true);
    }
}
