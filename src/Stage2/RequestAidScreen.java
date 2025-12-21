package Stage2;

import Stage1.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This screen allows a Beneficiary to request an aid item from the system.
 * The beneficiary selects an available aid item and specifies the quantity to request.
 */
public class RequestAidScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    // Reference to the aid management system
    private AidManagement manager;

    // The beneficiary who is requesting aid
    private Beneficiary beneficiary;

    private JComboBox<String> aidCombo;  // Dropdown for available aid items
    private JTextField quantityField;    // Field to input quantity

    //Colors
    private final Color backgroundColor = new Color(240, 248, 255); // AliceBlue
    private final Color panelColor = new Color(224, 235, 255);      // Light panel background
    private final Color textColor = new Color(25, 25, 112);         // Dark blue text

    private final Color buttonBgColor = new Color(46, 84, 145);       // Submit button color
    private final Color buttonHoverColor = new Color(70, 130, 180);   // Hover color
    private final Color cancelBgColor = new Color(245, 245, 245);     // Cancel button color
    private final Color cancelHoverColor = new Color(220, 220, 220);  // Cancel hover
    private final Color buttonTextColor = new Color(25, 25, 112);     // Button text color

    // Fonts
    private final Font titleFont = new Font("Arial", Font.BOLD, 22);
    private final Font labelFont = new Font("Arial", Font.BOLD, 15);
    private final Font fieldFont = new Font("Arial", Font.PLAIN, 15);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 15);

    /**
     * Constructor builds the Request Aid screen UI.
     */
    public RequestAidScreen(AidManagement aidManager, Beneficiary b) {
        super("Request Aid");
        this.manager = aidManager;
        this.beneficiary = b;

        // Window size and behavior
        setSize(550, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Title label
        JLabel titleLabel = new JLabel("Submit New Aid Request", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel for input fields
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        fieldsPanel.setBackground(panelColor);
        fieldsPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(173, 216, 230)),  // Light blue border
                new EmptyBorder(20, 20, 20, 20)
        ));

        // Aid item selection
        fieldsPanel.add(createStyledLabel("Select Aid Item:"));
        aidCombo = new JComboBox<>();
        // Populate dropdown with available aid items
        for (AidItem item : manager.getAvailableAid()) {
            // Format: "ID - Name (Type)"
            aidCombo.addItem(item.getId() + " - " + item.getName() + " (" + item.getClass().getSimpleName() + ")");
        }
        styleComponent(aidCombo);
        fieldsPanel.add(aidCombo);

        // Quantity input
        fieldsPanel.add(createStyledLabel("Quantity:"));
        quantityField = new JTextField();
        styleComponent(quantityField);
        fieldsPanel.add(quantityField);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(backgroundColor);

        // Submit Request button
        JButton requestBtn = createStyledButton("Submit Request", buttonBgColor, buttonHoverColor, buttonTextColor);
        requestBtn.addActionListener(e -> submitRequest());

        // Cancel button
        JButton cancelBtn = createStyledButton("Cancel", cancelBgColor, cancelHoverColor, textColor);
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(cancelBtn);
        buttonPanel.add(requestBtn);

        // Add subpanels to main panel
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

     //Creates a styled JLabel with consistent font and color
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(textColor);
        return label;
    }

    
     //Styles text fields and combo boxes consistently

    private void styleComponent(JComponent component) {
        component.setFont(fieldFont);
        component.setForeground(textColor);

        if (component instanceof JTextField) {
            component.setBorder(new CompoundBorder(
                    new LineBorder(new Color(173, 216, 230)), // Light blue border
                    new EmptyBorder(5, 8, 5, 8)
            ));
        } else if (component instanceof JComboBox) {
            component.setBackground(Color.WHITE);
        }
    }

     // Creates a JButton with hover effect and custom colors
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

    /**
     * Handles submitting the aid request
     */
    private void submitRequest() {
        String selected = (String) aidCombo.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "No aid item available to request.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String id = selected.split(" - ")[0]; // Extract aid ID from dropdown
        try {
            int qty = Integer.parseInt(quantityField.getText().trim());
            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be a positive number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }

            AidItem item = manager.findAidById(id);
            if (item != null) {
                boolean success = manager.requestAid(beneficiary, item, qty);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Request submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Request failed. The requested quantity might exceed availability.", "Request Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }
}
