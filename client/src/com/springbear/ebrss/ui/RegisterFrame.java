package com.springbear.ebrss.ui;

import com.springbear.ebrss.entity.Code;
import com.springbear.ebrss.entity.User;
import com.springbear.ebrss.model.RequestEnum;
import com.springbear.ebrss.service.CodeService;
import com.springbear.ebrss.service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User register interface
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:09
 */
public class RegisterFrame extends JFrame {
    private final JTextField textUsername;
    private final JPasswordField fieldPwd1;
    private final JPasswordField fieldPwd2;
    private final JTextField textName;
    private final JTextField textIdCard;
    private final JRadioButton btnMale;
    private final JTextField textPhone;
    private final JTextField textMail;
    private final JTextField textCode;

    private final UserService userService = new UserService();
    private final CodeService codeService = new CodeService();

    /**
     * Create the frame.
     */
    public RegisterFrame() {
        // Set the properties of the frame
        setTitle("User register");
        setIconImage(Toolkit.getDefaultToolkit().getImage("image\\BeFree.jpg"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(555, 555);
        setLocationRelativeTo(null);
        setResizable(false);

        // The main panel of the frame
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // The content of the mail panel
        JLabel lalUsername = new JLabel("Username");
        lalUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        lalUsername.setBounds(106, 25, 72, 18);
        contentPane.add(lalUsername);
        JLabel lalPwd = new JLabel("Password");
        lalPwd.setHorizontalAlignment(SwingConstants.RIGHT);
        lalPwd.setBounds(106, 75, 72, 18);
        contentPane.add(lalPwd);
        JLabel labPwd2 = new JLabel("Password");
        labPwd2.setHorizontalAlignment(SwingConstants.RIGHT);
        labPwd2.setBounds(106, 125, 72, 18);
        contentPane.add(labPwd2);
        JLabel labName = new JLabel("Name");
        labName.setHorizontalAlignment(SwingConstants.RIGHT);
        labName.setBounds(106, 175, 72, 18);
        contentPane.add(labName);
        JLabel lblIdCard = new JLabel("ID card");
        lblIdCard.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIdCard.setBounds(106, 225, 72, 18);
        contentPane.add(lblIdCard);
        JLabel lblSex = new JLabel("Sex");
        lblSex.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSex.setBounds(106, 275, 72, 18);
        contentPane.add(lblSex);
        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPhone.setBounds(106, 325, 72, 18);
        contentPane.add(lblPhone);
        JLabel lblMail = new JLabel("Email");
        lblMail.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMail.setBounds(106, 375, 72, 18);
        contentPane.add(lblMail);
        JLabel lblCode = new JLabel("Register code");
        lblCode.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCode.setBounds(100, 425, 80, 18);
        contentPane.add(lblCode);
        textUsername = new JTextField();
        textUsername.setBounds(192, 25, 200, 24);
        contentPane.add(textUsername);
        fieldPwd1 = new JPasswordField();
        fieldPwd1.setBounds(192, 75, 200, 24);
        contentPane.add(fieldPwd1);
        fieldPwd2 = new JPasswordField();
        fieldPwd2.setBounds(192, 125, 200, 24);
        contentPane.add(fieldPwd2);
        textName = new JTextField();
        textName.setBounds(192, 175, 200, 24);
        contentPane.add(textName);
        textIdCard = new JTextField();
        textIdCard.setBounds(192, 225, 200, 24);
        contentPane.add(textIdCard);
        ButtonGroup groupSex = new ButtonGroup();
        btnMale = new JRadioButton("Man");
        groupSex.add(btnMale);
        btnMale.setSelected(true);
        btnMale.setBounds(230, 275, 55, 27);
        contentPane.add(btnMale);
        JRadioButton btnFemale = new JRadioButton("Woman");
        groupSex.add(btnFemale);
        btnFemale.setBounds(290, 275, 88, 27);
        contentPane.add(btnFemale);
        textPhone = new JTextField();
        textPhone.setBounds(192, 325, 200, 24);
        contentPane.add(textPhone);
        textMail = new JTextField();
        textMail.setBounds(192, 375, 200, 24);
        contentPane.add(textMail);
        textCode = new JTextField();
        textCode.setBounds(192, 425, 200, 24);
        contentPane.add(textCode);
        JButton btnRegister = new JButton("Register");
        btnRegister.setBounds(188, 465, 95, 27);
        contentPane.add(btnRegister);
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(297, 465, 95, 27);
        contentPane.add(btnLogin);

        // Event registration
        btnRegister.addActionListener(new RegisterButtonListener());
        btnLogin.addActionListener(new LoginButtonListener());
    }


    /**
     * Login button
     */
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new LoginFrame().setVisible(true);
            RegisterFrame.this.setVisible(false);
        }
    }

    /**
     * Register button
     */
    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = textUsername.getText();
            String password1 = new String(fieldPwd1.getPassword());
            String password2 = new String(fieldPwd2.getPassword());
            String name = textName.getText();
            String idCard = textIdCard.getText();
            String sex = btnMale.isSelected() ? "M" : "W";
            String phone = textPhone.getText();
            String mail = textMail.getText();
            String registerCode = textCode.getText();

            // Determine whether there is a null value in the registration input
            if (judgeInputContentNull(username, password1, password2, name, idCard, phone, mail, registerCode)) {
                return;
            }
            // Determine whether the registered input content conforms to the format
            if (!judgeInputContentValid(username, password1, password2, name, idCard, phone, mail)) {
                return;
            }

            // User info that user register input
            User user = new User(username, password1, name, sex, idCard, phone, mail, registerCode);

            // Request the server verify the username's existence
            String request = RequestEnum.VERIFY_USERNAME_EXISTENCE.toString();
            if (userService.verifyUsernameExistence(request, user)) {
                JOptionPane.showMessageDialog(null, "Username has exists, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Request the server verify the register code's existence
            request = RequestEnum.VERIFY_CODE_EXISTENCE.toString();
            Code code = new Code(registerCode);
            if (!codeService.verifyCodeExistence(request, code)) {
                JOptionPane.showMessageDialog(null, "Register code doesn't exists, please get register code first", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Request the server save the user register info
            request = RequestEnum.SAVE_REGISTER_USER.toString();
            if (userService.saveRegisterUserInfo(request, user)) {
                JOptionPane.showMessageDialog(null, name + " - register successfully, congratulations", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Register failed, try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Determine whether there is a null value in the registration input
     *
     * @return true - some content is empty of false
     */
    private boolean judgeInputContentNull(String username, String password1, String password2, String name, String idCard, String phone, String mail, String code) {
        if (username.length() == 0) {
            JOptionPane.showMessageDialog(null, "Empty username, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (password1.length() == 0) {
            JOptionPane.showMessageDialog(null, "Empty password, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (password2.length() == 0) {
            JOptionPane.showMessageDialog(null, "Empty password, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (name.length() == 0) {
            JOptionPane.showMessageDialog(null, "Empty name, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (idCard.length() == 0) {
            JOptionPane.showMessageDialog(null, "Empty ID card, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (phone.length() == 0) {
            JOptionPane.showMessageDialog(null, "Empty phone number, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (mail.length() == 0) {
            JOptionPane.showMessageDialog(null, "Empty email address, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (code.length() == 0) {
            JOptionPane.showMessageDialog(null, "Empty register code, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * Determine whether the registered input content conforms to the format
     *
     * @return true - the format is right or false
     */
    private boolean judgeInputContentValid(String username, String password1, String password2, String name, String idCard, String phone, String mail) {
        /*
         * Username can only contain numbers, English letters and underscores,
         * and can only start with English letters
         */
        String digitLetterRegExp = "[^(0-9|_)][\\w]*";
        if (!username.matches(digitLetterRegExp)) {
            JOptionPane.showMessageDialog(null, "Invalid username, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Verify that the passwords entered twice are the same
        if (!password1.equals(password2)) {
            JOptionPane.showMessageDialog(null, "Password don't matched，please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Password length must be greater than or equal to six
        String pwdRegExp = "[\\S]{5,}";
        if (!password1.matches(pwdRegExp)) {
            JOptionPane.showMessageDialog(null, "Invalid password, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // The name can only be in pure Chinese or pure English
        String chinesEnglishRegExp = "[\\u4E00-\\u9FA5]+|[a-zA-Z]+";
        if (!name.matches(chinesEnglishRegExp)) {
            JOptionPane.showMessageDialog(null, "Invalid name, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // The ID number can only be eighteen digits
        String idCardRegExp = "[\\d]{18}";
        if (!idCard.matches(idCardRegExp)) {
            JOptionPane.showMessageDialog(null, "Invalid ID card, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // The phone number can only be eleven digits
        String phoneRegExp = "[\\d]{11}";
        if (!phone.matches(phoneRegExp)) {
            JOptionPane.showMessageDialog(null, "Invalid phone number, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Verify the format of the mail
        String mailRegExt = "\\w+@\\w+\\.(com|net.cn)";
        if (!mail.matches(mailRegExt)) {
            JOptionPane.showMessageDialog(null, "Invalid email address, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
