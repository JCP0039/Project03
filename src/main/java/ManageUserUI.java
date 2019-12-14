import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageUserUI {

    public JFrame view;

    public JButton btnLoad = new JButton("Load User");
    public JButton btnSave = new JButton("Save User");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtPassword = new JTextField(20);
    public JTextField txtFullname = new JTextField(20);
    public JTextField txtUsertype = new JTextField(20);


    public ManageUserUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update Product Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Username "));
        line1.add(txtUsername);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Password "));
        line2.add(txtPassword);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Fullname "));
        line3.add(txtFullname);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("UserType "));
        line4.add(txtUsertype);
        view.getContentPane().add(line4);


        btnLoad.addActionListener(new LoadButtonListerner());

        btnSave.addActionListener(new SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();
            String id = txtUsername.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "UserName cannot be null!");
                return;
            }

            try {
                user.mUsername = id;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "UserName is invalid!");
                return;
            }

            // call data access!

            user = StoreManager.getInstance().getDataAdapter().loadUser(user.mUsername);

            if (user == null) {
                JOptionPane.showMessageDialog(null, "User does NOT exists!");
            } else {
                txtPassword.setText(user.mPassword);
                txtFullname.setText(user.mFullname);
                txtUsertype.setText(Integer.toString(user.mUserType));
            }
        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();
            String id = txtUsername.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be null!");
                return;
            }

            try {
                user.mUsername = id;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Username is invalid!");
                return;
            }

            String password = txtPassword.getText();
            if (password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty!");
                return;
            }

            user.mPassword = password;

            String fullname = txtFullname.getText();
            if (fullname.length() == 0) {
                JOptionPane.showMessageDialog(null, "Fullname cannot be empty!");
                return;
            }

            user.mFullname = fullname;

            String usertype = txtUsertype.getText();
            try {
                user.mUserType = Integer.parseInt(usertype);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Usertype is invalid!");
                return;
            }


            int  res = StoreManager.getInstance().getDataAdapter().saveUser(user);

            if (res == IDataAdapter.PRODUCT_SAVE_FAILED)
                JOptionPane.showMessageDialog(null, "Product is NOT saved successfully!");
            else
                JOptionPane.showMessageDialog(null, "Product is SAVED successfully!");
        }
    }
}
