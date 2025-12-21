package Stage2;

import Stage1.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAidItemScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private AidManagement manager; // Reference to system manager (database/logic layer)
    private JTextField idField, nameField, quantityField, priorityField, specialField1, specialField2;
    private JLabel specialLabel1, specialLabel2;
    private JComboBox<String> aidTypeComboBox; // Dropdown for selecting aid type

    //Colors and fonts
    private final Color backgroundColor = new Color(240, 248, 255); 
    private final Color panelColor = new Color(224, 235, 255);
    private final Color buttonColor = new Color(70, 130, 180);    
    //private final Color buttonHoverColor = new Color(100, 149, 237); 
    private final Color textColor = new Color(25, 25, 112);        
    private final Color buttonTextColor = Color.white;
    private final Color backButtonTextColor = Color.white; 

    private final Font labelFont = new Font("Arial", Font.BOLD, 14);
    private final Font fieldFont = new Font("Arial", Font.PLAIN, 14);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 14);
    // --------------------------------------------------

    // Constructor
    public AddAidItemScreen(AidManagement aidManager) {
        super("Add New Aid Item");
        this.manager = aidManager;

        // Window setup
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel (border layout: form center, buttons bottom)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Fields panel (grid layout: 2 columns)
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        fieldsPanel.setBackground(panelColor);
        fieldsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(buttonColor, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        fieldsPanel.add(createStyledLabel("Aid Type:", labelFont));
        aidTypeComboBox = new JComboBox<>(new String[]{
            "FoodPackage", "Medicine", "FemaleHygienePackage", "ClothingPackage"
        });
        styleComponent(aidTypeComboBox, fieldFont);
        fieldsPanel.add(aidTypeComboBox);

        fieldsPanel.add(createStyledLabel("ID (numbers only):", labelFont));
        idField = new JTextField();
        styleComponent(idField, fieldFont);
        fieldsPanel.add(idField);

        fieldsPanel.add(createStyledLabel("Name:", labelFont));
        nameField = new JTextField();
        styleComponent(nameField, fieldFont);
        fieldsPanel.add(nameField);

        fieldsPanel.add(createStyledLabel("Quantity:", labelFont));
        quantityField = new JTextField();
        styleComponent(quantityField, fieldFont);
        fieldsPanel.add(quantityField);

        fieldsPanel.add(createStyledLabel("Priority Level (1-10):", labelFont));
        priorityField = new JTextField();
        styleComponent(priorityField, fieldFont);
        fieldsPanel.add(priorityField);

        specialLabel1 = createStyledLabel("", labelFont);
        specialField1 = new JTextField();
        styleComponent(specialField1, fieldFont);
        fieldsPanel.add(specialLabel1);
        fieldsPanel.add(specialField1);

        specialLabel2 = createStyledLabel("", labelFont);
        specialField2 = new JTextField();
        styleComponent(specialField2, fieldFont);
        fieldsPanel.add(specialLabel2);
        fieldsPanel.add(specialField2);

        // Update fields depending on type selection
        updateSpecialFields();
        aidTypeComboBox.addActionListener(e -> updateSpecialFields());

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(backgroundColor);

        // Add button → create and store new item
        JButton addBtn = createStyledButton("Add Item", buttonColor, buttonTextColor);
        addBtn.addActionListener(e -> addItem());
        buttonPanel.add(addBtn);

        // Back button → close window only
        JButton backBtn = createStyledButton("Back", new Color(245, 245, 245), backButtonTextColor);
        backBtn.addActionListener(e -> dispose());
        buttonPanel.add(backBtn);

        // Add panels into window
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    // Helper: create styled label 
    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(textColor);
        return label;
    }

    // Style text fields and combo boxes ---
    private void styleComponent(JComponent component, Font font) {
        component.setFont(font);
        component.setForeground(textColor);
        if (component instanceof JTextField) {
            JTextField textField = (JTextField) component;
            textField.setBackground(Color.WHITE);
            textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(173, 216, 230)),
                new EmptyBorder(5, 5, 5, 5)
            ));
        } else if (component instanceof JComboBox) {
            component.setBackground(Color.WHITE);
        }
    }

    // Helper: create styled button with hover effect 
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker()); // Darken on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor); // Restore color
            }
        });
        return button;
    }

    // Updates "special fields" labels depending on Aid Type
    private void updateSpecialFields() {
        String aidType = (String) aidTypeComboBox.getSelectedItem();
        specialField1.setVisible(true);
        specialField2.setVisible(true);
        specialLabel1.setVisible(true);
        specialLabel2.setVisible(true);

        if ("FoodPackage".equals(aidType)) {
            specialLabel1.setText("Expiration Date (YYYY-MM-DD):");
            specialLabel2.setVisible(false);
            specialField2.setVisible(false);
        } else if ("Medicine".equals(aidType)) {
            specialLabel1.setText("Requires Prescription (true/false):");
            specialLabel2.setText("Expiration Date (YYYY-MM-DD):");
        } else if ("FemaleHygienePackage".equals(aidType)) {
            specialLabel1.setText("Content Description:");
            specialLabel2.setText("Items per Package:");
        } else if ("ClothingPackage".equals(aidType)) {
            specialLabel1.setText("Season:");
            specialLabel2.setText("Target Age Group:");
        }
        revalidate();
        repaint();
    }

    // --- Add Item Logic ---
    private void addItem() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String quantityStr = quantityField.getText().trim();
        String priorityStr = priorityField.getText().trim();

        // Validation: required fields
        if (id.isEmpty() || name.isEmpty() || quantityStr.isEmpty() || priorityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ID must be digits only
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID must contain numbers only.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ID must be unique in system
        if (!manager.isIdUnique(id)) {
            JOptionPane.showMessageDialog(this, "This ID is already taken. Choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            int priority = Integer.parseInt(priorityStr);

            if (priority < 1 || priority > 10) {
                JOptionPane.showMessageDialog(this, "Priority must be between 1 and 10.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create new item depending on selected type
            String aidType = (String) aidTypeComboBox.getSelectedItem();
            AidItem newItem = null;

            if ("FoodPackage".equals(aidType)) {
                Date expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(specialField1.getText().trim());
                newItem = new FoodPackage(id, name, quantity, priority, expiryDate);

            } else if ("Medicine".equals(aidType)) {
                boolean prescription = Boolean.parseBoolean(specialField1.getText().trim());
                Date expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(specialField2.getText().trim());
                newItem = new Medicine(id, name, quantity, priority, prescription, expiryDate);

            } else if ("FemaleHygienePackage".equals(aidType)) {
                String contentDescription = specialField1.getText().trim();
                int itemsPerPackage = Integer.parseInt(specialField2.getText().trim());
                newItem = new FemaleHygienePackage(id, name, quantity, priority, contentDescription, itemsPerPackage);

            } else if ("ClothingPackage".equals(aidType)) {
                String season = specialField1.getText().trim();
                String ageGroup = specialField2.getText().trim();
                newItem = new ClothingPackage(id, name, quantity, priority, season, ageGroup);
            }

            // Add item to system and open View screen
            if (newItem != null) {
                manager.addAidItem(newItem);
                JOptionPane.showMessageDialog(this, "Item added successfully!");
                dispose();
                new ViewAidScreen(manager);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for quantity, priority, or items per package.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
