package Stage2;

import Stage1.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;

public class DistributionReportScreen extends JFrame {
    private static final long serialVersionUID = 1L;
   private AidManagement manager;

    // Colors 
    private final Color backgroundColor = new Color(240, 248, 255); // AliceBlue
    //private final Color panelColor = new Color(224, 235, 255);
    private final Color buttonColor = new Color(70, 130, 180);      // SteelBlue
    private final Color buttonHoverColor = new Color(100, 149, 237); // CornflowerBlue
    private final Color textColor = new Color(25, 25, 112);         // MidnightBlue
    private final Color buttonTextColor = new Color(25, 25, 112);

    // Fonts 
    private final Font titleFont = new Font("Arial", Font.BOLD, 18);
    private final Font tableFont = new Font("Arial", Font.PLAIN, 13);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 14);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 14);

    public DistributionReportScreen(AidManagement manager) {
        super("Distribution Report");
        this.manager = manager;

        setSize(850, 450);
        setLocationRelativeTo(null); // center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Distribution Report", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        
        String[] columns = {"Beneficiary Name", "Item ID", "Item Type", "Quantity", "Distribution Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        java.util.List<Distribution> distributions = manager.getDistributions();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Distribution d : distributions) {
            Object[] row = {
                d.getBeneficiary().getName(),
                d.getDistributedItem().getId(),
                d.getDistributedItem().getClass().getSimpleName(), // Food / Clothes / Medicine ...
                d.getQuantity(),
                sdf.format(d.getDistributionDate())
            };
            tableModel.addRow(row);
        }

        table.setFont(tableFont);
        table.setRowHeight(25);
        table.setGridColor(new Color(211, 211, 211));
        table.setSelectionBackground(buttonHoverColor);
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(tableHeaderFont);
        header.setBackground(buttonColor);
        header.setForeground(buttonTextColor);
        header.setReorderingAllowed(false); // disable dragging of columns

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(buttonFont);
        closeBtn.setBackground(buttonColor);
        closeBtn.setForeground(buttonTextColor);
        closeBtn.setFocusPainted(false);
        closeBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeBtn.setBackground(buttonHoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeBtn.setBackground(buttonColor);
            }
        });

        closeBtn.addActionListener(e -> dispose()); // close window when clicked

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(backgroundColor);
        btnPanel.add(closeBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        // Add to frame 
        add(mainPanel);
        setVisible(true);
    }
}
