package com.springbear.ebrss.ui;


import com.springbear.ebrss.entity.Book;
import com.springbear.ebrss.model.CustomTableModel;
import com.springbear.ebrss.service.BookService;
import com.springbear.ebrss.service.CodeService;
import com.springbear.ebrss.util.DatabaseUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

/**
 * The administrator interface
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 22:45
 */
public class AdminFrame extends JFrame {
    /**
     * The content of the main panel
     */
    private final JTabbedPane tabbedPane;
    private final JPanel contentPane;
    private final JPanel backgroundPanel;

    /**
     * The content of the add book panel
     */
    private JTextField textIsbn;
    private JTextField textTitle;
    private JTextField textAuthor;
    private JTextField textPrice;
    private JTextField textPublisher;
    private JComboBox<Integer> comboBoxYear;
    private JComboBox<Integer> comboBoxMonth;
    private JComboBox<Integer> comboBoxEdition;
    private JComboBox<String> comboBoxCategory;
    private JTextField textKeyword;
    private JRadioButton radioButtonOn;
    private JRadioButton radioButtonOff;
    private static String url = "d:\\book\\";

    /**
     * The content of the add register code panel
     */
    private JTabbedPane codeTablePane;
    private Object[] generateCode;
    private static final String CODE_STR = "1QAZ2WSX3EDC4RFV5TGB6YHN7UJM8IK9OL0P";
    private static final int CODE_NUMS = 25;
    private static final int CODE_LENGTH = 6;

    private final BookService bookService = new BookService();
    private final CodeService codeService = new CodeService();

    /**
     * Create the frame.
     */
    public AdminFrame() {
        // Set the properties of the frame
        setTitle("\u7BA1\u7406\u5458\u754C\u9762");
        setIconImage(Toolkit.getDefaultToolkit().getImage("server\\resource\\image\\BeFree.jpg"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(777, 777);
        setResizable(false);
        setLocationRelativeTo(null);

        // The main panel of the frame
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new CardLayout(0, 0));
        // The background panel of main panel
        backgroundPanel = new BackgroundPanel();
        contentPane.add(backgroundPanel);
        // The tab of the main panel
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane);

        // The content of the main panel
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenuItem menuAddBook = new JMenuItem("Add book");
        menuBar.add(menuAddBook);
        JMenuItem menuAddCode = new JMenuItem("Add code");
        menuBar.add(menuAddCode);
        JMenuItem menuBackup = new JMenuItem("Backup");
        menuBar.add(menuBackup);

        // Event registration
        menuAddBook.addActionListener(new AddBookMenuListener());
        menuAddCode.addActionListener(new AddCodeMenuListener());
        menuBackup.addActionListener(new BackupMenuListener());
    }

    /**
     * Backup menu
     */
    private static class BackupMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // User choose the path of the file want to save
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                File filePath = fileChooser.getSelectedFile();
                DatabaseUtil.databaseBackup(filePath.toString());
            }
        }
    }

    /**
     * Add register code menu
     */
    private class AddCodeMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            contentPane.remove(backgroundPanel);
            tabbedPane.removeAll();

            // The mail panel of the add register code
            JPanel addCodePanel = new JPanel();
            tabbedPane.add(addCodePanel, "Add register code");
            addCodePanel.setLayout(null);

            // The content of the panel
            JButton btnGenerate = new JButton("Generate randomly");
            btnGenerate.setBounds(111, 44, 555, 34);
            addCodePanel.add(btnGenerate);
            JButton btnAddCode = new JButton("Add");
            btnAddCode.setBounds(111, 606, 555, 34);
            addCodePanel.add(btnAddCode);
            codeTablePane = new JTabbedPane(JTabbedPane.TOP);
            codeTablePane.setBounds(111, 125, 555, 436);
            addCodePanel.add(codeTablePane);

            // Event registration
            btnGenerate.addActionListener(new GenerateCodeButtonListener());
            btnAddCode.addActionListener(new AddCodeButtonListener());
        }
    }

    /**
     * Random generate button of the add register code panel
     */
    private class GenerateCodeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            codeTablePane.removeAll();

            generateCode = randomGenerateCode();
            int matrixCount = 5;
            Object[][] rowData = new Object[matrixCount][matrixCount];
            for (int i = 0; i < matrixCount; i++) {
                System.arraycopy(generateCode, i * matrixCount, rowData[i], 0, matrixCount);
            }
            String[] columnNames = new String[]{"A", "B", "C", "D", "E"};
            CustomTableModel codeTable = new CustomTableModel(rowData, columnNames);
            JTable table = new JTable(codeTable);
            table.setRowHeight(77);
            table.setSelectionBackground(Color.CYAN);
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.setDefaultRenderer(Object.class, cellRenderer);
            JScrollPane scrollPane = new JScrollPane(table);
            codeTablePane.add(scrollPane, "Register code");
        }
    }

    /**
     * Add code button of the add register code panel
     */
    private class AddCodeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (generateCode == null || generateCode.length == 0) {
                JOptionPane.showMessageDialog(null, "Please generate register code at first", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (codeService.batchAddRegisterCode(generateCode)) {
                JOptionPane.showMessageDialog(null, "Register code batch add successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Register code add failed, try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Generate twenty and five register code randomly
     *
     * @return The array of the code
     */
    private Object[] randomGenerateCode() {
        Object[] code = new Object[CODE_NUMS];
        for (int i = 0; i < CODE_NUMS; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 1; j <= CODE_LENGTH; j++) {
                int randomNum = new Random().nextInt(36);
                builder.append(CODE_STR.charAt(randomNum));
            }
            code[i] = builder.toString();
        }

        return code;
    }

    /**
     * Add book menu
     */
    private class AddBookMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            contentPane.remove(backgroundPanel);
            tabbedPane.removeAll();

            // The main panel of the add book menu
            JPanel addBookPanel = new JPanel();
            tabbedPane.add(addBookPanel, "Add book");
            addBookPanel.setLayout(null);

            // The content of the add book menu panel
            JLabel lblIsbn = new JLabel("ISBN");
            lblIsbn.setHorizontalAlignment(SwingConstants.RIGHT);
            lblIsbn.setBounds(200, 50, 72, 18);
            addBookPanel.add(lblIsbn);
            JLabel lblTitle = new JLabel("Title");
            lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
            lblTitle.setBounds(200, 110, 72, 18);
            addBookPanel.add(lblTitle);
            JLabel lblAuthor = new JLabel("Author");
            lblAuthor.setHorizontalAlignment(SwingConstants.RIGHT);
            lblAuthor.setBounds(200, 170, 72, 18);
            addBookPanel.add(lblAuthor);
            JLabel lblPrice = new JLabel("Price");
            lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
            lblPrice.setBounds(200, 230, 72, 18);
            addBookPanel.add(lblPrice);
            JLabel lblPublisher = new JLabel("Publisher");
            lblPublisher.setHorizontalAlignment(SwingConstants.RIGHT);
            lblPublisher.setBounds(200, 290, 72, 18);
            addBookPanel.add(lblPublisher);
            JLabel lblDate = new JLabel("Edition");
            lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
            lblDate.setBounds(200, 350, 72, 18);
            addBookPanel.add(lblDate);
            JLabel lblCategory = new JLabel("Category");
            lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
            lblCategory.setBounds(200, 410, 72, 18);
            addBookPanel.add(lblCategory);
            JLabel lblKeyword = new JLabel("Keyword");
            lblKeyword.setHorizontalAlignment(SwingConstants.RIGHT);
            lblKeyword.setBounds(200, 470, 72, 18);
            addBookPanel.add(lblKeyword);
            JLabel lblState = new JLabel("State");
            lblState.setHorizontalAlignment(SwingConstants.RIGHT);
            lblState.setBounds(200, 525, 72, 18);
            addBookPanel.add(lblState);
            textIsbn = new JTextField();
            textIsbn.setBounds(286, 47, 250, 24);
            addBookPanel.add(textIsbn);
            textTitle = new JTextField();
            textTitle.setBounds(286, 107, 250, 24);
            addBookPanel.add(textTitle);
            textAuthor = new JTextField();
            textAuthor.setBounds(286, 167, 250, 24);
            addBookPanel.add(textAuthor);
            textPrice = new JTextField();
            textPrice.setBounds(286, 227, 250, 24);
            addBookPanel.add(textPrice);
            textPublisher = new JTextField();
            textPublisher.setBounds(286, 287, 250, 24);
            addBookPanel.add(textPublisher);
            textKeyword = new JTextField();
            textKeyword.setBounds(286, 467, 250, 24);
            addBookPanel.add(textKeyword);
            comboBoxYear = new JComboBox<>();
            comboBoxYear.setBounds(286, 347, 60, 24);
            addBookPanel.add(comboBoxYear);
            int endYear = 1970;
            LocalDate localDate = LocalDate.now();
            int startYear = localDate.getYear();
            for (int i = startYear; i >= endYear; i--) {
                comboBoxYear.addItem(i);
            }
            JLabel lblYear = new JLabel("Y");
            lblYear.setBounds(355, 350, 20, 18);
            addBookPanel.add(lblYear);
            comboBoxMonth = new JComboBox<>();
            comboBoxMonth.setBounds(380, 347, 40, 24);
            addBookPanel.add(comboBoxMonth);
            JLabel lblMonth = new JLabel("M  No.");
            lblMonth.setBounds(430, 350, 51, 18);
            addBookPanel.add(lblMonth);
            comboBoxEdition = new JComboBox<>();
            comboBoxEdition.setBounds(470, 347, 40, 24);
            addBookPanel.add(comboBoxEdition);
            int maxMonth = 12;
            for (int i = 1; i <= maxMonth; i++) {
                comboBoxMonth.addItem(i);
                comboBoxEdition.addItem(i);
            }
            JLabel lblEdition = new JLabel("E");
            lblEdition.setBounds(516, 350, 20, 18);
            addBookPanel.add(lblEdition);
            // 分类下拉选择框
            comboBoxCategory = new JComboBox<>();
            comboBoxCategory.setBounds(286, 407, 250, 24);
            addBookPanel.add(comboBoxCategory);
            final String[] bookCategories = new String[]{
                    "A 马列主义、毛泽东思想、邓小平理论", "B 哲学、宗教", "C 社会科学总论",
                    "D 政治、法律", "E 军事", "F 经济", "G 文化、科学、教育、体育", "H 语言、文字",
                    "I 文学", "J 艺术", "K 历史、地理", "N 自然科学总论", "O 数理科学和化学",
                    "P 天文学、地球科学", "Q 生物科学", "R 医药、卫生", "S 农业科学", "T 工业技术",
                    "U 交通运输", "V 航空、航天", "X 环境科学、劳动保护科学(安全科学)", "Z 综合性图书"
            };
            for (String bookCategory : bookCategories) {
                comboBoxCategory.addItem(bookCategory);
            }
            ButtonGroup buttonGroup = new ButtonGroup();
            radioButtonOn = new JRadioButton("On");
            buttonGroup.add(radioButtonOn);
            radioButtonOn.setBounds(339, 525, 59, 27);
            radioButtonOn.setSelected(true);
            addBookPanel.add(radioButtonOn);
            radioButtonOff = new JRadioButton("Off");
            buttonGroup.add(radioButtonOff);
            radioButtonOff.setBounds(435, 525, 59, 27);
            addBookPanel.add(radioButtonOff);
            JButton btnGather = new JButton("Add");
            btnGather.setBounds(308, 600, 95, 27);
            addBookPanel.add(btnGather);
            JButton btnReset = new JButton("Reset");
            btnReset.setBounds(408, 600, 95, 27);
            addBookPanel.add(btnReset);

            // Event registration
            btnReset.addActionListener(new ResetButtonListener());
            btnGather.addActionListener(new GatherButtonListener());
        }
    }

    /**
     * Gather book button of the add book panel
     */
    private class GatherButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String isbn = textIsbn.getText();
            String title = textTitle.getText();
            String author = textAuthor.getText();
            double price = Double.parseDouble(textPrice.getText());
            String publisher = textPublisher.getText();
            String edition = Objects.requireNonNull(comboBoxYear.getSelectedItem()) + "年" + Objects.requireNonNull(comboBoxMonth.getSelectedItem()) + "月第" + Objects.requireNonNull(comboBoxEdition.getSelectedItem()) + "版";
            String keyword = textKeyword.getText();
            String category = (String) comboBoxCategory.getSelectedItem();
            category = String.valueOf(Objects.requireNonNull(category).charAt(0));
            String bookState = radioButtonOn.isSelected() ? "on" : "off";
            url = url + title + ".pdf";

            Book book = new Book(isbn, title, author, price, publisher, edition, keyword, url, category, bookState);

            if (bookService.saveBook(book)) {
                JOptionPane.showMessageDialog(null, "Add book successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add book, try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            url = "d:\\book\\";
        }
    }

    /**
     * The reset button of the add book menu
     */
    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textIsbn.setText("");
            textTitle.setText("");
            textAuthor.setText("");
            textPrice.setText("");
            textPublisher.setText("");
            comboBoxYear.setSelectedIndex(0);
            comboBoxMonth.setSelectedIndex(0);
            comboBoxEdition.setSelectedIndex(0);
            comboBoxCategory.setSelectedIndex(0);
            textKeyword.setText("");
            radioButtonOn.setSelected(true);
            radioButtonOff.setSelected(false);
        }
    }

    /**
     * Custom background panel, draw a background picture
     */
    private static class BackgroundPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(Toolkit.getDefaultToolkit().getImage("server\\resource\\image\\BeFree.jpg"), 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    public static void main(String[] args) {
        new AdminFrame().setVisible(true);
    }
}
