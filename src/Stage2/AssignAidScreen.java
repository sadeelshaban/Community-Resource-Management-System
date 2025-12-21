package Stage2;

import Stage1.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AssignAidScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private AidManagement manager;  // Reference to the AidManagement system
    private JComboBox<String> beneficiaryCombo;  // Dropdown to select beneficiary
    private JTextArea resultArea;  // Area to display results of assignments

    // Colors and Fonts 
    private final Color backgroundColor = new Color(240, 248, 255); 
    private final Color panelColor = new Color(224, 235, 255);       
    private final Color buttonColor = new Color(70, 130, 180);       
    private final Color buttonHoverColor = new Color(100, 149, 237); 
    private final Color textColor = new Color(25, 25, 112);          
    private final Color buttonTextColor = new Color(25, 25, 112);    

    private final Font labelFont = new Font("Arial", Font.BOLD, 16);
    private final Font fieldFont = new Font("Arial", Font.PLAIN, 14);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 14);
    private final Font resultFont = new Font("Monospaced", Font.PLAIN, 14);

    // Constructor to build the screen
    public AssignAidScreen(AidManagement aidManager) {
        super("Assign Aid to Beneficiary");
        this.manager = aidManager;

        setSize(550, 450);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top panel for beneficiary selection 
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(panelColor);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(buttonColor, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Label above the combo box
        JLabel selectLabel = new JLabel("Select Beneficiary:");
        selectLabel.setFont(labelFont);
        selectLabel.setForeground(textColor);
        topPanel.add(selectLabel, BorderLayout.NORTH);

        // Dropdown (ComboBox) for beneficiaries
        beneficiaryCombo = new JComboBox<>();
        beneficiaryCombo.setFont(fieldFont);
        beneficiaryCombo.setForeground(textColor);
        beneficiaryCombo.setBackground(Color.WHITE);

        // Fill dropdown with all beneficiaries from manager
        for (Beneficiary b : manager.getAllBeneficiaries()) {
            beneficiaryCombo.addItem(b.getId() + " - " + b.getName());
        }
        topPanel.add(beneficiaryCombo, BorderLayout.CENTER);

        // "Assign Aid" button
        JButton assignBtn = createStyledButton("Assign Aid");
        assignBtn.addActionListener(e -> assignAid());
        topPanel.add(assignBtn, BorderLayout.SOUTH);

        // Result text area to show output 
        resultArea = new JTextArea();
        resultArea.setEditable(false);  // User cannot type here
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setFont(resultFont);
        resultArea.setForeground(textColor);
        resultArea.setBackground(Color.WHITE);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(173, 216, 230)),
            new EmptyBorder(10, 10, 10, 10)
        ));

        // Scroll pane for the result area
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(null);

        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add to JFrame
        add(mainPanel);
        setVisible(true);
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(buttonColor);
        button.setForeground(buttonTextColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonHoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonColor);
            }
        });
        return button;
    }

    // Method that assigns aid to selected beneficiary 
    private void assignAid() {
        String selected = (String) beneficiaryCombo.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "No beneficiary selected or available.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Extract ID from "ID - Name"
        String id = selected.split(" - ")[0];
        Beneficiary b = manager.findBeneficiaryById(id);

        if (b != null) {
            boolean success = manager.assignAidToBeneficiary(b); // Call manager to assign
            String message;
            if (success) {
                message = "Successfully assigned the highest priority aid to " + b.getName() + ".\n";
                JOptionPane.showMessageDialog(this, "Aid assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                message = "No suitable aid found for " + b.getName() + " based on their needs.\n";
                JOptionPane.showMessageDialog(this, "No matching aid found.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
            resultArea.append(">> " + message); // Append result to text area
        } else {
            resultArea.append("Error: Could not find beneficiary with ID " + id + ".\n");
        }
    }
}
