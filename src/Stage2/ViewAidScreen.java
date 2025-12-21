package Stage2;

import Stage1.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This screen displays all available aid items in a table format.
 * Users can view ID, Name, Quantity, Priority, and additional Details.
 */
public class ViewAidScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    // Colors
    private final Color backgroundColor = new Color(240, 248, 255); 
    private final Color textColor = new Color(25, 25, 112);         
    private final Color tableHeaderBgColor = new Color(46, 84, 145); 
    private final Color tableHeaderFgColor = Color.WHITE;
    private final Color tableGridColor = new Color(211, 211, 211);     
    private final Color tableSelectionBgColor = new Color(70, 130, 180); 
    private final Color closeButtonBgColor = new Color(245, 245, 245);
    private final Color closeButtonHoverColor = new Color(220, 220, 220);

    // Fonts
    private final Font titleFont = new Font("Arial", Font.BOLD, 24);
    private final Font tableFont = new Font("Arial", Font.PLAIN, 14);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 15);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 15);

    /**
     * Constructor builds the View Aid Items screen.
     */
    public ViewAidScreen(AidManagement manager) {
        super("Available Aid Items");

        setSize(800, 600); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("All Available Aid Items", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columnNames = {"ID", "Name", "Quantity", "Priority", "Details"};

        // Table model (non-editable cells)
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // prevent editing
            }
        };

        // Populate table with aid items
        for (AidItem item : manager.getAvailableAid()) {
            tableModel.addRow(new Object[]{
                    item.getId(),
                    item.getName(),
                    item.getQuantity(),
                    item.getPriorityLevel(),
                    item.getInfo() // Additional details
            });
        }

        // Create table and apply styling
        JTable table = new JTable(tableModel);
        styleTable(table); 

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Close button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        buttonPanel.setBackground(backgroundColor);
        JButton closeBtn = createStyledButton("Close", closeButtonBgColor, closeButtonHoverColor, textColor);
        closeBtn.addActionListener(e -> dispose());
        buttonPanel.add(closeBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    /**
     * Applies styling to the JTable
     */
    private void styleTable(JTable table) {
        table.setFont(tableFont);
        table.setRowHeight(30); 
        table.setGridColor(tableGridColor);
        table.setSelectionBackground(tableSelectionBgColor);
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(tableHeaderFont);
        header.setBackground(tableHeaderBgColor);
        header.setForeground(tableHeaderFgColor);
        
        // Make the Details column wider
        table.getColumnModel().getColumn(4).setPreferredWidth(300);
    }

    /**
     * Creates a JButton with hover effect and custom colors
     */
    private JButton createStyledButton(String text, Color bgColor, Color hoverColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 40, 10, 40)); // wider button

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
}
