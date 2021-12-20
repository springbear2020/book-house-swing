package com.springbear.ebrss.ui;

import com.springbear.ebrss.entity.Book;
import com.springbear.ebrss.entity.Favorite;
import com.springbear.ebrss.entity.Record;
import com.springbear.ebrss.entity.User;
import com.springbear.ebrss.model.CustomTableModel;
import com.springbear.ebrss.model.RequestEnum;
import com.springbear.ebrss.service.BookService;
import com.springbear.ebrss.service.FavoriteService;
import com.springbear.ebrss.service.RecordService;
import com.springbear.ebrss.service.UserService;
import com.springbear.ebrss.util.FileUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.util.Objects;

/**
 * @author Spring-_-Bear
 * @date 2021-12-18 22:38
 */
public class MainFrame extends JFrame {
    /**
     * The content of the frame
     */
    private final JPanel contentPane;
    private final JTabbedPane tabbedPane;
    private final BackgroundPanel backgroundPanel;
    /**
     * The content of the user management panel
     */
    private JTextField textPassword;
    private JTextField textPhone;
    private JTextField textMail;
    /**
     * The content of the book download panel
     */
    private JTextField textSearch;
    private JComboBox<String> comboBoxCondition;
    private JTabbedPane booksTablePane;
    private static int booksTableSelectedRow = -1;
    private static Object[][] booksTableRowData;
    /**
     * The content of the favorite panel
     */
    private static Object[][] favoriteTableRowData;
    private static int favoriteTableSelectedRow = -1;

    /**
     * The username who log in the system
     */
    private final String loginUsername;

    private final UserService userService = new UserService();
    private final BookService bookService = new BookService();
    private final RecordService recordService = new RecordService();
    private final FavoriteService favoriteService = new FavoriteService();

    /**
     * Create the frame
     */
    public MainFrame(String loginUsername) {
        this.loginUsername = loginUsername;

        // Set the properties of the frame
        setTitle("e-book resource service system");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(777, 777);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("image\\BeFree.jpg"));
        setLocationRelativeTo(null);

        // The content of the menuBar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenuItem menuDownload = new JMenuItem("Download");
        menuBar.add(menuDownload);
        JMenuItem menuUpload = new JMenuItem("Upload");
        menuBar.add(menuUpload);
        JMenuItem menuFavorite = new JMenuItem("Favorites");
        menuBar.add(menuFavorite);
        JMenuItem menuRecord = new JMenuItem("Record");
        menuBar.add(menuRecord);
        JMenuItem menuManage = new JMenuItem("Management");
        menuBar.add(menuManage);

        // The main panel of the frame
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new CardLayout(0, 0));
        // The background panel of the frame
        backgroundPanel = new BackgroundPanel();
        contentPane.add(backgroundPanel);
        // The tab of the main panel
        tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane);

        // Event registration
        menuManage.addActionListener(new ManageAccountButtonListener());
        menuDownload.addActionListener(new BookDownloadMenuListener());
        menuRecord.addActionListener(new RecordMenuListener());
        menuFavorite.addActionListener(new FavoriteMenuListener());
        menuUpload.addActionListener(new UploadMenuListener());
    }

    /**
     * Book upload menu
     */
    private class UploadMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tabbedPane.removeAll();

            // User choose the path of the file that user want to upload
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                File filePath = fileChooser.getSelectedFile();
                String request = RequestEnum.UPLOAD_BOOK_FILE.toString();
                if (bookService.uploadBookFile(request, FileUtil.getFileDataFromDisk(filePath.toString()))) {
                    JOptionPane.showMessageDialog(null, "Book file upload successfully, thanks", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Book file upload failed, try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Favorite menu
     */
    private class FavoriteMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            contentPane.remove(backgroundPanel);
            tabbedPane.removeAll();

            // Favorite panel
            JPanel favoritePanel = new JPanel();
            tabbedPane.add(favoritePanel, loginUsername + "'s favorites");
            favoritePanel.setLayout(null);
            // The content of the favorite panel
            JTabbedPane favoriteTablePane = new JTabbedPane();
            favoriteTablePane.setBounds(0, 0, 760, 567);
            favoritePanel.add(favoriteTablePane);
            JButton btnDownload = new JButton("Download");
            btnDownload.setBounds(250, 616, 100, 27);
            favoritePanel.add(btnDownload);
            JButton btnCancel = new JButton("Delete");
            btnCancel.setBounds(400, 616, 107, 27);
            favoritePanel.add(btnCancel);

            String request = RequestEnum.QUERY_USER_FAVORITE.toString();
            Favorite favorite = new Favorite(null, null, null, null, null, null, loginUsername);
            // Request the server query user's favorite record by the username
            favoriteTableRowData = favoriteService.queryFavoritesByUsername(request, favorite);
            if (favoriteTableRowData == null) {
                JOptionPane.showMessageDialog(null, "There is no favorite record of you", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create table to display the user's favorite record
            String[] columnNames = new String[]{"ISBN", "Title", "Author", "Keyword", "Category", "Time"};
            CustomTableModel tableModel = new CustomTableModel(favoriteTableRowData, columnNames);
            JTable favoriteTable = new JTable(tableModel);
            favoriteTable.setRowHeight(33);
            favoriteTable.setSelectionBackground(Color.CYAN);
            // Set the table font to be displayed in the center
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(JLabel.CENTER);
            favoriteTable.setDefaultRenderer(Object.class, cellRenderer);
            // Only one row can be selected
            favoriteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            favoriteTable.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(favoriteTable);
            favoriteTablePane.add(scrollPane, "Favorites");

            // Reset the selected number
            favoriteTableSelectedRow = -1;

            // Listening the row number that user selected
            favoriteTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    favoriteTableSelectedRow = favoriteTable.getSelectedRow();
                }
            });

            // Event Registration
            btnDownload.addActionListener(new FavoritePanelDownloadButtonListener());
            btnCancel.addActionListener(new FavoriteCancelButtonListener());
        }
    }

    /**
     * Favorite cancel button of the favorite panel
     */
    private class FavoriteCancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (favoriteTableSelectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Please choose the book you want to cancel favorite", "NOTICE", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            String isbn = (String) favoriteTableRowData[favoriteTableSelectedRow][0];
            String request = RequestEnum.DELETE_USER_FAVORITE.toString();
            Favorite favorite = new Favorite(isbn, null, null, null, null, null, loginUsername);
            // Request the server delete the user's favorite record of the user selected
            if (favoriteService.deleteUserFavorite(request, favorite)) {
                JOptionPane.showMessageDialog(null, "Delete successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                favoriteTableSelectedRow = -1;
            } else {
                JOptionPane.showMessageDialog(null, "DELETE failed, try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            // Reset the selected row number
            favoriteTableSelectedRow = -1;
        }
    }

    /**
     * Download button of the favorite panel
     */
    private class FavoritePanelDownloadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (favoriteTableSelectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Please choose the book you want to download", "ERROR", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            String isbn = (String) favoriteTableRowData[favoriteTableSelectedRow][0];
            Book book = new Book(isbn, null, null, null, null, null, null, null, null, null);
            String request = RequestEnum.DOWNLOAD_BOOK_BY_ISBN.toString();
            // Request the server give client book data of the book's isbn user selected
            byte[] bookData = bookService.downloadBookByIsbn(request, book);

            if (bookData == null) {
                JOptionPane.showMessageDialog(null, "Book file download failed, try again later", "ERROR", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            // Save book data to the disk, user choose file save path
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                String fileName = favoriteTableRowData[favoriteTableSelectedRow][1] + ".pdf";
                // Combine the file path and the file name to file full path
                String fullPath = filePath + "\\" + fileName;
                if (FileUtil.saveFileToDisk(bookData, fullPath)) {
                    // User download book successfully, Request server auto add the user's download record
                    request = RequestEnum.AUTO_SAVE_DOWNLOAD_RECORD.toString();
                    Record record = new Record((String) favoriteTableRowData[favoriteTableSelectedRow][1], (String) favoriteTableRowData[favoriteTableSelectedRow][2], new Date(), loginUsername);
                    recordService.autoAddDownloadRecord(request, record);
                    JOptionPane.showMessageDialog(null, fileName + " successfully save to " + filePath, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Book file save failed", "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            }
            // Reset the selected row number
            favoriteTableSelectedRow = -1;
        }
    }

    /**
     * Download record menu
     */
    private class RecordMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            contentPane.remove(backgroundPanel);
            tabbedPane.removeAll();

            String request = RequestEnum.QUERY_RECORD_BY_USERNAME.toString();
            Record record = new Record(null, null, null, loginUsername);
            // Request the server query the user's download record by the username
            Object[][] recordRowData = recordService.queryRecordByUsername(request, record);
            if (recordRowData == null) {
                JOptionPane.showMessageDialog(null, "There is no download record of you", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create the table to show to download record info
            String[] columnNames = new String[]{"Title", "Author", "Download time"};
            CustomTableModel tableModel = new CustomTableModel(recordRowData, columnNames);
            JTable recordTable = new JTable(tableModel);
            recordTable.setRowHeight(33);
            recordTable.setSelectionBackground(Color.CYAN);
            // Set the table font to be displayed in the center
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(JLabel.CENTER);
            recordTable.setDefaultRenderer(Object.class, cellRenderer);
            // Only one row that user can choose
            recordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            recordTable.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(recordTable);
            tabbedPane.add(scrollPane, "Download record");
        }
    }

    /**
     * Download menu
     */
    private class BookDownloadMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            contentPane.remove(backgroundPanel);
            tabbedPane.removeAll();

            // Book download panel
            JPanel downloadPanel = new JPanel();
            downloadPanel.setLayout(null);
            tabbedPane.add(downloadPanel, "Book download");

            // The content of the book download panel
            JLabel lblSearch = new JLabel("Book search");
            lblSearch.setBounds(100, 25, 72, 18);
            downloadPanel.add(lblSearch);
            textSearch = new JTextField();
            textSearch.setBounds(186, 22, 367, 24);
            downloadPanel.add(textSearch);
            textSearch.setColumns(10);
            JButton btnSearch = new JButton("Search");
            btnSearch.setBounds(567, 22, 80, 24);
            downloadPanel.add(btnSearch);
            JLabel labCondition = new JLabel("Choice");
            labCondition.setBounds(270, 75, 72, 18);
            downloadPanel.add(labCondition);
            comboBoxCondition = new JComboBox<>();
            comboBoxCondition.setBounds(345, 75, 77, 24);
            String[] filterConditions = new String[]{"All", "Title", "Author", "Keyword", "Category"};
            for (String filterCondition : filterConditions) {
                comboBoxCondition.addItem(filterCondition);
            }
            downloadPanel.add(comboBoxCondition);
            booksTablePane = new JTabbedPane();
            booksTablePane.setBounds(0, 120, 777, 456);
            downloadPanel.add(booksTablePane);
            JButton btnDownload = new JButton("Download now");
            btnDownload.setBounds(250, 616, 125, 27);
            downloadPanel.add(btnDownload);
            JButton btnFavorite = new JButton("Add to favorites");
            btnFavorite.setBounds(400, 616, 125, 27);
            downloadPanel.add(btnFavorite);

            // Event registration
            btnSearch.addActionListener(new SearchButtonListener());
            btnDownload.addActionListener(new BookDownloadPanelDownLoadButtonListener());
            btnFavorite.addActionListener(new AddFavoritesButtonListener());
        }
    }

    /**
     * Add to favorite button of the book download panel
     */
    private class AddFavoritesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (booksTableSelectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Please choose the book you want to add to favorites", "NOTICE", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            // Get the info that user want to add the favorite
            String isbn = (String) booksTableRowData[booksTableSelectedRow][0];
            String title = (String) booksTableRowData[booksTableSelectedRow][1];
            String author = (String) booksTableRowData[booksTableSelectedRow][2];
            String keyword = (String) booksTableRowData[booksTableSelectedRow][3];
            String category = (String) booksTableRowData[booksTableSelectedRow][4];
            Date time = new Date();

            String request = RequestEnum.SAVE_USER_FAVORITE.toString();
            Favorite favorite = new Favorite(isbn, title, author, keyword, category, time, loginUsername);
            // Request the server save the user's favorite record
            if (favoriteService.saveUserFavorite(request, favorite)) {
                JOptionPane.showMessageDialog(null, "Add to personal favorites successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "The favorite record has exists, can't add repeatedly", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            // Reset the number of the selected row
            booksTableSelectedRow = -1;
        }
    }

    /**
     * Download button of the book download panel
     */
    private class BookDownloadPanelDownLoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (booksTableSelectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Please choose the book you want to download", "NOTICE", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            String isbn = (String) booksTableRowData[booksTableSelectedRow][0];
            Book book = new Book(isbn, null, null, null, null, null, null, null, null, null);
            String request = RequestEnum.DOWNLOAD_BOOK_BY_ISBN.toString();
            // Request the server give the book byte data that user want to download
            byte[] bookData = bookService.downloadBookByIsbn(request, book);
            if (bookData == null) {
                JOptionPane.showMessageDialog(null, "Book file download failed, try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save the book byte data to the disk
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                String fileName = booksTableRowData[booksTableSelectedRow][1] + ".pdf";
                // Combine the file path and file name to file full path
                String fullPath = filePath + "\\" + fileName;
                if (FileUtil.saveFileToDisk(bookData, fullPath)) {
                    // User download book successfully, Request server auto add the user's download record
                    request = RequestEnum.AUTO_SAVE_DOWNLOAD_RECORD.toString();
                    Record record = new Record((String) booksTableRowData[booksTableSelectedRow][1], (String) booksTableRowData[booksTableSelectedRow][2], new Date(), loginUsername);
                    recordService.autoAddDownloadRecord(request, record);
                    JOptionPane.showMessageDialog(null, fileName + " successfully save to " + filePath, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Book file save failed", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            // Reset the number of the selected row
            booksTableSelectedRow = -1;
        }
    }

    /**
     * Search button of the book download panel
     */
    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String condition = (String) comboBoxCondition.getSelectedItem();
            String content = textSearch.getText();

            // if not query all books' info, user must type content into to search field
            String all = "All";
            if ((!(all.equals(condition))) && content.length() == 0) {
                JOptionPane.showMessageDialog(null, "Empty filter condition, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert the user' select condition to the standard request
            String request = null;
            Book book = null;
            switch (Objects.requireNonNull(condition)) {
                case "All":
                    request = RequestEnum.QUERY_ALL_BOOKS_FROM_VIEW.toString();
                    book = new Book(null, null, null, null, null, null, null, null, null, null);
                    break;
                case "Title":
                    request = RequestEnum.QUERY_BOOKS_BY_TITLE_FROM_VIEW.toString();
                    book = new Book(null, content, null, null, null, null, null, null, null, null);
                    break;
                case "Author":
                    request = RequestEnum.QUERY_BOOKS_BY_AUTHOR_FROM_VIEW.toString();
                    book = new Book(null, null, content, null, null, null, null, null, null, null);
                    break;
                case "Category":
                    request = RequestEnum.QUERY_BOOKS_BY_CATEGORY_FROM_VIEW.toString();
                    book = new Book(null, null, null, null, null, null, null, null, content, null);
                    break;
                case "Keyword":
                    request = RequestEnum.QUERY_BOOKS_BY_KEYWORD_FROM_VIEW.toString();
                    book = new Book(null, null, null, null, null, null, content, null, null, null);
                    break;
                default:
            }

            // Request the server query books' info by the user selected filter condition
            booksTableRowData = bookService.queryBooksByCondition(request, book);
            if (booksTableRowData == null) {
                JOptionPane.showMessageDialog(null, "There is no book matched", "ERROR", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            String[] columnNames = new String[]{"ISBN", "Title", "Author", "Keyword", "Category"};

            // Create table to display the book's info
            booksTablePane.removeAll();
            CustomTableModel tableModel = new CustomTableModel(booksTableRowData, columnNames);
            JTable booksTable = new JTable(tableModel);
            booksTable.setRowHeight(33);
            booksTable.setSelectionBackground(Color.CYAN);
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(JLabel.CENTER);
            booksTable.setDefaultRenderer(Object.class, cellRenderer);
            booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            booksTable.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(booksTable);
            booksTablePane.add(scrollPane, "Book info");

            // Listening the row that user selected
            booksTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    booksTableSelectedRow = booksTable.getSelectedRow();
                }
            });
        }
    }

    /**
     * Account management menu
     */
    private class ManageAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            contentPane.remove(backgroundPanel);
            tabbedPane.removeAll();

            // Personal info panel
            JPanel managePanel = new JPanel();
            tabbedPane.add(managePanel, "Personal information");
            managePanel.setLayout(null);
            // The content of the personal info panel
            JLabel lblUsername = new JLabel("Username");
            lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
            lblUsername.setFont(new Font("SimSun", Font.PLAIN, 18));
            lblUsername.setBounds(225, 50, 72, 22);
            managePanel.add(lblUsername);
            JLabel lalPassword = new JLabel("Password");
            lalPassword.setHorizontalAlignment(SwingConstants.RIGHT);
            lalPassword.setFont(new Font("SimSun", Font.PLAIN, 18));
            lalPassword.setBounds(225, 125, 72, 22);
            managePanel.add(lalPassword);
            JLabel lblName = new JLabel("Name");
            lblName.setHorizontalAlignment(SwingConstants.RIGHT);
            lblName.setFont(new Font("SimSun", Font.PLAIN, 22));
            lblName.setBounds(225, 204, 72, 22);
            managePanel.add(lblName);
            JLabel lblSex = new JLabel("Sex");
            lblSex.setHorizontalAlignment(SwingConstants.RIGHT);
            lblSex.setFont(new Font("SimSun", Font.PLAIN, 22));
            lblSex.setBounds(225, 279, 72, 22);
            managePanel.add(lblSex);
            JLabel lblIdCard = new JLabel("ID card");
            lblIdCard.setHorizontalAlignment(SwingConstants.RIGHT);
            lblIdCard.setFont(new Font("SimSun", Font.PLAIN, 22));
            lblIdCard.setBounds(209, 354, 88, 22);
            managePanel.add(lblIdCard);
            JLabel lblPhone = new JLabel("Phone");
            lblPhone.setHorizontalAlignment(SwingConstants.RIGHT);
            lblPhone.setFont(new Font("SimSun", Font.PLAIN, 22));
            lblPhone.setBounds(209, 429, 88, 22);
            managePanel.add(lblPhone);
            JLabel lblMail = new JLabel("Email");
            lblMail.setHorizontalAlignment(SwingConstants.RIGHT);
            lblMail.setFont(new Font("SimSun", Font.PLAIN, 22));
            lblMail.setBounds(209, 504, 88, 22);
            managePanel.add(lblMail);
            JLabel lblCode = new JLabel("Code");
            lblCode.setHorizontalAlignment(SwingConstants.RIGHT);
            lblCode.setFont(new Font("SimSun", Font.PLAIN, 22));
            lblCode.setBounds(209, 579, 88, 22);
            managePanel.add(lblCode);
            JTextField textUsername = new JTextField();
            textUsername.setFont(new Font("SimSun", Font.PLAIN, 22));
            textUsername.setHorizontalAlignment(SwingConstants.CENTER);
            textUsername.setBounds(311, 46, 250, 30);
            textUsername.setEditable(false);
            managePanel.add(textUsername);
            JTextField textName = new JTextField();
            textName.setFont(new Font("SimSun", Font.PLAIN, 22));
            textName.setHorizontalAlignment(SwingConstants.CENTER);
            textName.setBounds(311, 200, 250, 30);
            textName.setEditable(false);
            managePanel.add(textName);
            JTextField textSex = new JTextField();
            textSex.setFont(new Font("SimSun", Font.PLAIN, 22));
            textSex.setHorizontalAlignment(SwingConstants.CENTER);
            textSex.setBounds(311, 275, 250, 30);
            textSex.setEditable(false);
            managePanel.add(textSex);
            JTextField textIdCard = new JTextField();
            textIdCard.setFont(new Font("SimSun", Font.PLAIN, 22));
            textIdCard.setHorizontalAlignment(SwingConstants.CENTER);
            textIdCard.setBounds(311, 350, 250, 30);
            textIdCard.setEditable(false);
            managePanel.add(textIdCard);
            JTextField textCode = new JTextField();
            textCode.setFont(new Font("SimSun", Font.PLAIN, 22));
            textCode.setHorizontalAlignment(SwingConstants.CENTER);
            textCode.setBounds(311, 575, 250, 30);
            textCode.setEditable(false);
            managePanel.add(textCode);
            textPassword = new JTextField();
            textPassword.setFont(new Font("SimSun", Font.PLAIN, 22));
            textPassword.setHorizontalAlignment(SwingConstants.CENTER);
            textPassword.setBounds(311, 117, 250, 30);
            managePanel.add(textPassword);
            textPhone = new JTextField();
            textPhone.setFont(new Font("SimSun", Font.PLAIN, 22));
            textPhone.setHorizontalAlignment(SwingConstants.CENTER);
            textPhone.setBounds(311, 425, 250, 30);
            managePanel.add(textPhone);
            textMail = new JTextField();
            textMail.setFont(new Font("SimSun", Font.PLAIN, 20));
            textMail.setHorizontalAlignment(SwingConstants.CENTER);
            textMail.setBounds(311, 500, 250, 30);
            managePanel.add(textMail);
            JButton btnModify = new JButton();
            btnModify.setText("Modify");
            btnModify.setFont(new Font("SimSun", Font.PLAIN, 20));
            btnModify.setHorizontalAlignment(SwingConstants.CENTER);
            btnModify.setBounds(315, 630, 120, 30);
            managePanel.add(btnModify);
            JButton btnCancel = new JButton();
            btnCancel.setText("Logout");
            btnCancel.setFont(new Font("SimSun", Font.PLAIN, 20));
            btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
            btnCancel.setBounds(444, 630, 120, 30);
            managePanel.add(btnCancel);

            // Request the server query user info by the username
            String request = RequestEnum.QUERY_USER_BY_USERNAME.toString();
            User sendUser = new User(loginUsername);
            User user = userService.queryUserByUsername(request, sendUser);

            textUsername.setText(user.getUsername());
            textName.setText(user.getName());
            textSex.setText(user.getSex());
            textIdCard.setText(user.getIdCard());
            textCode.setText(user.getRegisterCode());
            textPassword.setText(user.getPassword());
            textPhone.setText(user.getPhone());
            textMail.setText(user.getMail());

            // Get the old content before user type in
            String oldPassword = textPassword.getText();
            String oldPhone = textPhone.getText();
            String oldMail = textMail.getText();

            // Event Registration
            btnModify.addActionListener(new ModifyButtonListener(oldPassword, oldPhone, oldMail));
            btnCancel.addActionListener(new LogoutButtonListener());
        }
    }

    /**
     * Modify info button of the account management panel
     */
    private class ModifyButtonListener implements ActionListener {
        private final String oldPassword;
        private final String oldPhone;
        private final String oldMail;

        public ModifyButtonListener(String oldPassword, String oldPhone, String oldMail) {
            this.oldPassword = oldPassword;
            this.oldPhone = oldPhone;
            this.oldMail = oldMail;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String newPassword = textPassword.getText();
            String newPhone = textPhone.getText();
            String newMail = textMail.getText();

            // Password length must be greater than or equal to six
            String pwdRegExp = "[\\S]{5,}";
            if (!newPassword.matches(pwdRegExp)) {
                JOptionPane.showMessageDialog(null, "Invalid password, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // The phone number is must equals to eleven digits
            String phoneRegExp = "[\\d]{11}";
            if (!newPhone.matches(phoneRegExp)) {
                JOptionPane.showMessageDialog(null, "Invalid phone number, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // The format of the mail
            String mailRegExt = "\\w+@\\w+\\.(com|net.cn)";
            if (!newMail.matches(mailRegExt)) {
                JOptionPane.showMessageDialog(null, "Invalid email address, please input again", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Nothing changed, user can't change his info
            if (oldPassword.equals(newPassword) && oldPhone.equals(newPhone) && oldMail.equals(newMail)) {
                JOptionPane.showMessageDialog(null, "Nothing changed, can't modify", "ERROR", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            User user = new User(loginUsername, newPassword, null, null, null, newPhone, newMail, null);
            String request = RequestEnum.UPDATE_USER_INFORMATION.toString();
            // Request the server update the user's info
            if (userService.updateUserInfo(request, user)) {
                JOptionPane.showMessageDialog(null, "Account info update successfully", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Info modify failed, please try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Logout button of manage account panel
     */
    private class LogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure cancel this account?", "NOTICE", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == 0) {
                String request = RequestEnum.ACCOUNT_CANCELLATION.toString();
                User user = new User(loginUsername);
                /*
                 * Request the server cancel the current user,
                 * if cancel successfully, then notice the user exit the system
                 */
                if (userService.userCancellation(request, user)) {
                    JOptionPane.showMessageDialog(null, "Logout successfully, try using again", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    MainFrame.this.setVisible(false);
                    new LoginFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Account logout failed, try again later", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Custom background panel, draw a background picture
     */
    private static class BackgroundPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(Toolkit.getDefaultToolkit().getImage("resource\\image\\BeFree.jpg"), 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }
}