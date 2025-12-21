package Stage2;

import Stage1.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginScreen extends JFrame {
    private static final long serialVersionUID = 1L;

   
    private JTextField idField;        
    private JPasswordField passwordField; 
    private AidManagement manager;     // Reference to the system manager

    //  Colors 
    private final Color backgroundColor = new Color(240, 248, 255);
    private final Color textColor = new Color(25, 25, 112);
    private final Color buttonBgColor = new Color(245, 245, 245);
    private final Color buttonHoverColor = new Color(220, 220, 220);
    private final Color borderColor = new Color(173, 216, 230);

    // Fonts 
    private final Font titleFont = new Font("Arial", Font.BOLD, 22);   
    private final Font labelFont = new Font("Arial", Font.BOLD, 15);   
    private final Font fieldFont = new Font("Arial", Font.PLAIN, 15);  
    private final Font buttonFont = new Font("Arial", Font.BOLD, 15);  

    // Constructor 
    public LoginScreen(AidManagement aidManager) {
        super("CRMS - Login");
        this.manager = aidManager;

        // Window setup
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Main container panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Title at the top 
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel for inputs
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        // User ID field with label and icon
        JLabel idLabel = new JLabel("User ID:");
        idLabel.setFont(labelFont);
        idLabel.setForeground(textColor);
        idField = new JTextField();
        JPanel idPanel = createTextFieldWithIcon(idField, "id.png");

        // Password field with label and icon
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(textColor);
        passwordField = new JPasswordField();
        JPanel passwordPanel = createTextFieldWithIcon(passwordField, "reset-password.png");

        // Add inputs into form panel
        formPanel.add(idLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(idPanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordPanel);

        //  Buttons section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Back button → return to Welcome screen
        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> {
            dispose(); // Close current screen
            new WelcomeScreen(manager); // Re-open welcome screen
        });

        // Login button → check credentials
        JButton loginButton = createStyledButton("Login");
        loginButton.addActionListener(e -> authenticateUser());

        // Register button → go to registration screen
        JButton registerButton = createStyledButton("Register");
        registerButton.addActionListener(e -> {
            dispose();
            new RegistrationScreen(manager).setVisible(true);
        });

        // Add buttons into panel
        buttonPanel.add(backButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Add form and buttons to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set content and show window
        setContentPane(mainPanel);
        setVisible(true);
    }

    //  Helper: Creates a text field with an icon on the left
    private JPanel createTextFieldWithIcon(JTextField textField, String iconName) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);

        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/" + iconName));
            Image scaledImg = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconName);
        }

        // Style the text field
        textField.setFont(fieldFont);
        textField.setForeground(textColor);
        textField.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Add icon and field to panel
        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);

        // Add border around the field
        panel.setBorder(new CompoundBorder(
            new LineBorder(borderColor, 1),
            new EmptyBorder(3, 5, 3, 5)
        ));

        return panel;
    }

    //  Authentication logic: check if user exists and password matches 
    private void authenticateUser() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validation: empty fields
        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both ID and Password.", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Find user in manager
        Person person = manager.findPersonById(id);

        // Check if user exists and password matches
        if (person != null && person.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, 
                "Login Successful! Welcome, " + person.getName() + ".", 
                "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose(); // Close login screen
            new MainMenuScreen(person, manager).setVisible(true); // Go to main menu
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid User ID or Password.", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  Helper: create styled button with hover effect
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(buttonBgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(buttonBgColor);
            }
        });
        return button;
    }
}
