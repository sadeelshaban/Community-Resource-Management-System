package Stage2;

import Stage1.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private Person currentUser;       // The logged-in user (could be staff, volunteer, or beneficiary)
    private AidManagement manager;    // Manages all aid-related operations

    // Colors 
    private final Color backgroundColor = new Color(245, 248, 250);
    private final Color textColor = new Color(30, 30, 30);
    
    private final Color primaryButtonBg = new Color(135, 206, 250);   
    private final Color primaryButtonHover = new Color(50, 135, 255); 

    private final Color logoutButtonBg = new Color(220, 53, 69);      
    private final Color logoutButtonHover = new Color(225, 80, 95);   
    
    private final Color buttonTextColor = Color.DARK_GRAY;

    // Fonts 
    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 28);
    private final Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);

    public MainMenuScreen(Person user, AidManagement aidManager) {
        super("Community Resource Management - Main Menu");
        this.currentUser = user;
        this.manager = aidManager;

        // Frame setup
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main layout container
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // 1. Header panel: Welcome message + Logout button 
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getName() + "!");
        welcomeLabel.setFont(titleFont);
        welcomeLabel.setForeground(textColor);

        JButton logoutBtn = createStyledButton("Logout", logoutButtonBg, logoutButtonHover);
        try {
            // Try to add a small logout icon if available
            ImageIcon logoutIcon = new ImageIcon(getClass().getResource("/icons/logout.png"));
            Image scaledImg = logoutIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            logoutBtn.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            System.err.println("Icon not found: logout.png");
        }
        logoutBtn.addActionListener(e -> logout());

        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutBtn, BorderLayout.EAST);

        // 2. Main content panel (depends on user type) 
        JPanel contentPanel;
        if (currentUser instanceof OrganizationStaff || currentUser instanceof Volunteer) {
            // Staff/Volunteers get full management features
            contentPanel = createUserPanel();
        } else {
            // Beneficiaries get limited options
            contentPanel = createBeneficiaryPanel();
        }
        contentPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        // Add to frame
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    // Panel for staff/volunteers 
    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 20, 20)); 
        panel.setOpaque(false);

        panel.add(createMenuButton("Add New Aid Item", () -> new AddAidItemScreen(manager).setVisible(true)));
        panel.add(createMenuButton("View All Available Aid", () -> new ViewAidScreen(manager).setVisible(true)));
        panel.add(createMenuButton("Assign Aid", () -> new AssignAidScreen(manager).setVisible(true)));
        panel.add(createMenuButton("View Distribution Report", () -> new DistributionReportScreen(manager).setVisible(true)));
        
        return panel;
    }
    
    // Panel for beneficiaries 
    private JPanel createBeneficiaryPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 20, 20));
        panel.setOpaque(false);
        
        panel.add(createMenuButton("View Available Aid", () -> new ViewAidScreen(manager).setVisible(true)));
        panel.add(createMenuButton("Request Aid", () -> new RequestAidScreen(manager, (Beneficiary) currentUser).setVisible(true)));

        return panel;
    }

    // Reusable menu button with action 
    private JButton createMenuButton(String text, Runnable action) {
        JButton button = createStyledButton(text, primaryButtonBg, primaryButtonHover);
        button.setPreferredSize(new Dimension(200, 65)); 
        button.addActionListener(e -> action.run());
        return button;
    }

    // Styled button factory 
    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(bgColor);
        button.setForeground(buttonTextColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));

        // Hover effects
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

    //  Logout logic 
    private void logout() {
        int response = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", "Confirm Logout", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (response == JOptionPane.YES_OPTION) {
            dispose(); // close current screen
            new LoginScreen(manager).setVisible(true); // return to login
        }
    }
}
