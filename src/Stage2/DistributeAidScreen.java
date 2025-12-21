package Stage2;

import Stage1.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class DistributeAidScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private AidManagement manager;       // Reference to the aid management system
    private AidItem itemToDistribute;    // The specific aid item being distributed

    private JTable beneficiaryTable;     // Table to display beneficiaries
    private DefaultTableModel tableModel;// Table model for handling rows/columns
    private JTextField searchField;      // Search bar for beneficiaries
    private JLabel itemInfoLabel;        // Label showing item info (name, ID, quantity)

    // Colors 
    private final Color backgroundColor = new Color(240, 248, 255);
    private final Color panelColor = new Color(224, 235, 255);
    private final Color buttonColor = new Color(70, 130, 180);
    private final Color buttonHoverColor = new Color(100, 149, 237);
    private final Color textColor = new Color(25, 25, 112);
    private final Color buttonTextColor = new Color(25, 25, 112);
    private final Color backButtonTextColor = new Color(25, 25, 112);

    // Fonts
    private final Font titleFont = new Font("Arial", Font.BOLD, 16);
    private final Font labelFont = new Font("Arial", Font.PLAIN, 14);
    private final Font fieldFont = new Font("Arial", Font.PLAIN, 14);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 14);
    private final Font tableFont = new Font("Arial", Font.PLAIN, 12);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 14);

    // --- Constructor ---
    public DistributeAidScreen(AidManagement aidManager, AidItem item) {
        super("Distribute Aid Item");
        this.manager = aidManager;
        this.itemToDistribute = item;

        setSize(750, 550);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main container panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- Top panel: item info + search bar ---
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(panelColor);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(buttonColor),
            new EmptyBorder(15, 15, 15, 15)
        ));

        // Display item details (name, ID, quantity) using styled HTML
        String labelText =
            "<html>" +
                "<div style='text-align:center; font-family:Arial; color:#191970;'>" +
                    "<b style='font-size:14pt;'>Distributing Item:</b> " + itemToDistribute.getName() +
                    " <span style='color:#4682B4;'>(ID: " + itemToDistribute.getId() + ")</span><br><br>" +
                    "<b style='font-size:12pt;'>Available Quantity:</b> " +
                    "<span style='background-color:#E6FFE6; color:#006400; font-weight:bold; padding:3px 8px; border-radius:6px;'>" +
                    itemToDistribute.getQuantity() +
                    "</span>" +
                "</div>" +
            "</html>";

        itemInfoLabel = new JLabel(labelText);
        itemInfoLabel.setFont(titleFont);
        itemInfoLabel.setForeground(textColor);
        topPanel.add(itemInfoLabel, BorderLayout.NORTH);

        // --- Search panel for filtering beneficiaries by ID ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        searchPanel.setBackground(panelColor);

        JLabel searchLabel = new JLabel("Search Beneficiary by ID:");
        searchLabel.setFont(labelFont);
        searchLabel.setForeground(textColor);
        searchPanel.add(searchLabel);

        searchField = new JTextField(20);
        searchField.setFont(fieldFont);
        searchPanel.add(searchField);

        JButton searchBtn = createStyledButton("Search", buttonColor, buttonTextColor);
        searchBtn.addActionListener(e -> populateBeneficiaryTable(searchField.getText().trim()));
        searchPanel.add(searchBtn);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        //  Table for listing beneficiaries 
        String[] columnNames = {"ID", "Name", "Address", "Phone", "Family Size"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent editing cells
            }
        };

        beneficiaryTable = new JTable(tableModel);
        styleTable(beneficiaryTable); // Apply custom styling
        JScrollPane scrollPane = new JScrollPane(beneficiaryTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with action buttons 
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(backgroundColor);

        JButton distributeBtn = createStyledButton("Distribute to Selected", buttonColor, buttonTextColor);
        distributeBtn.addActionListener(e -> distributeItem());
        buttonPanel.add(distributeBtn);

        JButton backBtn = createStyledButton("Back", new Color(245, 245, 245), backButtonTextColor);
        backBtn.addActionListener(e -> dispose()); // Close the window
        buttonPanel.add(backBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Initially populate the table with all beneficiaries
        populateBeneficiaryTable("");
    }

    // Style the JTable (rows + headers) 
    private void styleTable(JTable table) {
        table.setFont(tableFont);
        table.setRowHeight(25);
        table.setGridColor(new Color(211, 211, 211));
        table.setSelectionBackground(buttonHoverColor);
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(tableHeaderFont);
        header.setBackground(buttonColor);
        header.setForeground(buttonTextColor);
        header.setReorderingAllowed(false); // Prevent column drag
    }

    // Create styled button with hover effect 
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover color change
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    // Fill the beneficiary table (with or without search keyword) 
    private void populateBeneficiaryTable(String keyword) {
        tableModel.setRowCount(0); // Clear table
        for (Beneficiary beneficiary : manager.getAllBeneficiaries()) {
            if (keyword.isEmpty() || beneficiary.getId().contains(keyword)) {
                tableModel.addRow(new Object[]{
                    beneficiary.getId(),
                    beneficiary.getName(),
                    beneficiary.getAddress(),
                    beneficiary.getPhone(),
                    beneficiary.getFamilySize()
                });
            }
        }
    }

    // Distribute item to selected beneficiary 
    private void distributeItem() {
        int selectedRow = beneficiaryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a beneficiary from the table.", "No Beneficiary Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get beneficiary by ID
        String beneficiaryId = (String) tableModel.getValueAt(selectedRow, 0);
        Beneficiary selectedBeneficiary = manager.findBeneficiaryById(beneficiaryId);

        if (selectedBeneficiary != null) {
            // In real system, you’d also update the quantity in AidManagement
            JOptionPane.showMessageDialog(this, "Item distributed to " + selectedBeneficiary.getName() + " successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close screen after successful distribution
        } else {
            JOptionPane.showMessageDialog(this, "Failed to find the selected beneficiary.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
