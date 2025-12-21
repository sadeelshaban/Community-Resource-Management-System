package Stage2;

import Stage1.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistrationScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    private AidManagement manager;   // Central manager for registering new users
    private JTextField idField, nameField, phoneField, addressField, ageField, specialField1, specialField2;
    private JPasswordField passwordField;
    private JLabel specialLabel1, specialLabel2; // Dynamic labels (change based on user type)
    private JComboBox<String> userTypeComboBox; // Dropdown to choose type of user

    // Colors
    private final Color backgroundColor = new Color(240, 248, 255); 
    private final Color panelColor = new Color(224, 235, 255);
    private final Color textColor = new Color(25, 25, 112);       
    private final Color buttonBgColor = Color.white;    
    private final Color buttonHoverColor = new Color(240, 248, 255);   
    private final Color backButtonBgColor = Color.white; 
    private final Color backButtonHoverColor = new Color(220, 220, 220);

    // Fonts
    private final Font titleFont = new Font("Arial", Font.BOLD, 22);
    private final Font labelFont = new Font("Arial", Font.BOLD, 14);
    private final Font fieldFont = new Font("Arial", Font.PLAIN, 14);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 15);

    public RegistrationScreen(AidManagement aidManager) {
        super("Community Resource Management - Registration");
        this.manager = aidManager;

        // Frame setup
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Title
        JLabel titleLabel = new JLabel("Create New Account", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // === Input fields section ===
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        fieldsPanel.setBackground(panelColor);
        fieldsPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(173, 216, 230)), // light blue border
            new EmptyBorder(20, 20, 20, 20)          // padding inside
        ));

        // Dropdown for user type
        fieldsPanel.add(createStyledLabel("User Type:"));
        userTypeComboBox = new JComboBox<>(new String[]{"Beneficiary", "Volunteer", "Staff"});
        styleComponent(userTypeComboBox);
        fieldsPanel.add(userTypeComboBox);

        // Common fields (always visible)
        fieldsPanel.add(createStyledLabel("ID:"));
        idField = new JTextField();
        styleComponent(idField);
        fieldsPanel.add(idField);

        fieldsPanel.add(createStyledLabel("Password:"));
        passwordField = new JPasswordField();
        styleComponent(passwordField);
        fieldsPanel.add(passwordField);

        fieldsPanel.add(createStyledLabel("Full Name:"));
        nameField = new JTextField();
        styleComponent(nameField);
        fieldsPanel.add(nameField);

        fieldsPanel.add(createStyledLabel("Age:"));
        ageField = new JTextField();
        styleComponent(ageField);
        fieldsPanel.add(ageField);

        fieldsPanel.add(createStyledLabel("Phone:"));
        phoneField = new JTextField();
        styleComponent(phoneField);
        fieldsPanel.add(phoneField);

        fieldsPanel.add(createStyledLabel("Address:"));
        addressField = new JTextField();
        styleComponent(addressField);
        fieldsPanel.add(addressField);

        // Special fields (depend on user type)
        specialLabel1 = createStyledLabel("");
        specialField1 = new JTextField();
        styleComponent(specialField1);
        fieldsPanel.add(specialLabel1);
        fieldsPanel.add(specialField1);

        specialLabel2 = createStyledLabel("");
        specialField2 = new JTextField();
        styleComponent(specialField2);
        fieldsPanel.add(specialLabel2);
        fieldsPanel.add(specialField2);

        // Update special fields dynamically
        userTypeComboBox.addActionListener(e -> updateSpecialFields());
        updateSpecialFields(); 

        // Buttons (register + back) 
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(backgroundColor);

        JButton registerBtn = createStyledButton("Register", buttonBgColor, buttonHoverColor, new Color(25, 25, 112));
        registerBtn.addActionListener(e -> registerUser());

        JButton backBtn = createStyledButton("Back to Login", backButtonBgColor, backButtonHoverColor, textColor);
        backBtn.addActionListener(e -> {
            dispose();
            new LoginScreen(manager).setVisible(true);
        });

        buttonPanel.add(backBtn);
        buttonPanel.add(registerBtn);

        // Add everything to main panel
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Helper to style labels 
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(textColor);
        return label;
    }

    // Apply style to input fields 
    private void styleComponent(JComponent component) {
        component.setFont(fieldFont);
        component.setForeground(textColor);
        if (component instanceof JTextField) {
            component.setBorder(new CompoundBorder(
                new LineBorder(new Color(173, 216, 230)),
                new EmptyBorder(5, 8, 5, 8)
            ));
        } else if (component instanceof JComboBox) {
            component.setBackground(Color.WHITE);
        }
    }

    //  Create buttons with hover effect 
    private JButton createStyledButton(String text, Color bgColor, Color hoverColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 25, 10, 25));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    // Adjust fields depending on selected user type 
    private void updateSpecialFields() {
        String userType = (String) userTypeComboBox.getSelectedItem();

        // Hide all by default
        specialLabel1.setVisible(false);
        specialField1.setVisible(false);
        specialLabel2.setVisible(false);
        specialField2.setVisible(false);

        if ("Staff".equals(userType)) {
            specialLabel1.setText("Job Title:");
            specialLabel2.setText("Organization:");
            specialLabel1.setVisible(true);
            specialField1.setVisible(true);
            specialLabel2.setVisible(true);
            specialField2.setVisible(true);
        } else if ("Beneficiary".equals(userType)) {
            specialLabel1.setText("Family Size:");
            specialLabel1.setVisible(true);
            specialField1.setVisible(true);
        }

        revalidate();
        repaint();
    }

    // Handle registration logic 
    private void registerUser() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String userType = (String) userTypeComboBox.getSelectedItem();

        // Validation checks
        if (id.isEmpty() || password.isEmpty() || name.isEmpty() || ageStr.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all mandatory fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!manager.isIdUnique(id)) {
            JOptionPane.showMessageDialog(this, "This ID is already taken. Please choose another one.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!name.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(this, "Name must contain only letters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Phone must contain digits only.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            if (age <= 0) {
                JOptionPane.showMessageDialog(this, "Age must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create and register user objects 
        if ("Volunteer".equals(userType)) {
            Volunteer volunteer = new Volunteer(id, name, password, address, phone);
            manager.registerUser(volunteer);
            JOptionPane.showMessageDialog(this, "Volunteer registered successfully!");
            dispose();
            new LoginScreen(manager).setVisible(true);

        } else if ("Staff".equals(userType)) {
            String jobTitle = specialField1.getText().trim();
            String orgName = specialField2.getText().trim();

            if (!jobTitle.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(this, "Job Title must contain only letters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!orgName.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(this, "Organization Name must contain only letters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            OrganizationStaff staff = new OrganizationStaff(id, name, password, address, phone, jobTitle, orgName);
            manager.registerUser(staff);
            JOptionPane.showMessageDialog(this, "Staff member registered successfully!");
            dispose();
            new LoginScreen(manager).setVisible(true);

        } else if ("Beneficiary".equals(userType)) {
            try {
                int familySize = Integer.parseInt(specialField1.getText().trim());
                Beneficiary beneficiary = new Beneficiary(id, name, password, address, phone, familySize);
                manager.registerBeneficiary(beneficiary);
                JOptionPane.showMessageDialog(this, "Beneficiary registered successfully!");
                dispose();
                new LoginScreen(manager).setVisible(true);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Family Size must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
