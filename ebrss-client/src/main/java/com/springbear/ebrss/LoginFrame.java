package com.springbear.ebrss;

import com.springbear.ebrss.entity.User;
import com.springbear.ebrss.model.RequestEnum;
import com.springbear.ebrss.service.UserService;
import com.springbear.ebrss.ui.MainFrame;
import com.springbear.ebrss.ui.RegisterFrame;
import com.springbear.ebrss.util.FileUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User login interface
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 22:29
 */
public class LoginFrame extends JFrame {
    private final JTextField textUsername;
    private final JPasswordField textPwd;

    private final UserService userService = new UserService();

    /**
     * Create the frame.
     */
    public LoginFrame() {
        // Set the properties of the frame
        setSize(444, 300);
        setTitle("User login");
        setIconImage(Toolkit.getDefaultToolkit().getImage(FileUtil.getBeFreeUrl()));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The user login main panel
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        // The content of the mail panel
        JLabel lblUser = new JLabel("Username");
        lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUser.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblUser.setBounds(30, 43, 80, 40);
        contentPane.add(lblUser);
        JLabel lblPwd = new JLabel("Password");
        lblPwd.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPwd.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblPwd.setBounds(30, 125, 80, 40);
        contentPane.add(lblPwd);
        textUsername = new JTextField();
        textUsername.setFont(new Font("SimSun", Font.PLAIN, 20));
        textUsername.setBounds(124, 45, 235, 40);
        contentPane.add(textUsername);
        textPwd = new JPasswordField();
        textPwd.setFont(new Font("SimSun", Font.PLAIN, 20));
        textPwd.setBounds(124, 125, 235, 40);
        contentPane.add(textPwd);
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("SimSun", Font.PLAIN, 18));
        btnLogin.setBounds(140, 208, 80, 40);
        contentPane.add(btnLogin);
        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("SimSun", Font.PLAIN, 16));
        btnRegister.setBounds(240, 208, 100, 40);
        contentPane.add(btnRegister);

        // Event registration
        btnLogin.addActionListener(new LoginButtonListener());
        btnRegister.addActionListener(new RegisterButtonListener());
    }

    /**
     * Register button
     */
    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RegisterFrame().setVisible(true);
            LoginFrame.this.setVisible(false);
        }
    }

    /**
     * Login button
     */
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = textUsername.getText();
            String password = new String(textPwd.getPassword());

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Empty username, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Empty password, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String request = RequestEnum.VERIFY_USERNAME_AND_PASSWORD.toString();
            User user = new User(username, password);
            // Request the server verify whether the username and password are right
            if (!userService.verifyUsernameAndPassword(request, user)) {
                JOptionPane.showMessageDialog(null, "Wrong username or password, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Login successfully
            new MainFrame(username).setVisible(true);
            LoginFrame.this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }
}
