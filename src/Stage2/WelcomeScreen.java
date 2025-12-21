package Stage2;

import Stage1.AidManagement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Main welcome screen for the application
public class WelcomeScreen extends JFrame {

    private static final long serialVersionUID = 1L;
    private AidManagement manager; // Reference to the central management system

    // Colors
    private final Color backgroundColor = new Color(240, 248, 255);  
    private final Color panelColor = new Color(224, 235, 255);       
    private final Color textColor = new Color(25, 25, 112);          
    private final Color buttonBgColor = new Color(245, 245, 245);    
    private final Color buttonHoverColor = new Color(220, 220, 220); 
    private final Color buttonTextColor = new Color(25, 25, 112);    

    // Fonts 
    private final Font titleFont = new Font("Arial", Font.BOLD, 36);      
    private final Font subtitleFont = new Font("Arial", Font.ITALIC, 24);  
    private final Font paragraphFont = new Font("Arial", Font.PLAIN, 16);  
    private final Font buttonTextFont = new Font("Arial", Font.BOLD, 16);  

    // Constructor
    public WelcomeScreen(AidManagement aidManager) {
        super("Community Resource Management System");
        this.manager = aidManager;

        // Set application window icon
        try {
            ImageIcon appIcon = new ImageIcon(getClass().getResource("/icons/nonprofit-organization.png"));
            setIconImage(appIcon.getImage());
        } catch (Exception e) {
            System.err.println("Window icon not found.");
        }

        // Window size and behavior
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on screen

        // Main container panel with padding and background
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(40, 50, 40, 50));

        // Header section (Title + Subtitle)
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false); // Transparent so background shows
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Community Resource Management System");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Empowering Aid Distribution");
        subtitleLabel.setFont(subtitleFont);
        subtitleLabel.setForeground(textColor.darker());
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10)); // Add space between title & subtitle
        headerPanel.add(subtitleLabel);

        // Center section (Image + Intro Text) 
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);

        // Add project image (scaled)
        JLabel imageLabel = new JLabel();
        try {
            ImageIcon projectIcon = new ImageIcon(getClass().getResource("/Icons/nonprofit-organization.png"));
            Image scaledImage = projectIcon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            System.err.println("Project image not found: nonprofit-organization.png");
            imageLabel.setText("Image not found");
        }

        // Introductory description text area
        JTextArea introArea = new JTextArea(
                "This system is designed to streamline the management and distribution of community resources " +
                "in humanitarian and crisis-affected communities. It allows local organizations and " +
                "volunteers to efficiently register, manage, and distribute various aid packages, " +
                "including food, medicine, and clothing, to families and individuals in need."
        );
        introArea.setFont(paragraphFont);
        introArea.setForeground(textColor);
        introArea.setBackground(panelColor);
        introArea.setWrapStyleWord(true);
        introArea.setLineWrap(true);
        introArea.setEditable(false); // Read-only
        introArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(textColor.brighter(), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        centerPanel.add(imageLabel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(introArea), BorderLayout.CENTER);

        // Button section (Exit + Next)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Exit button
        JButton exitButton = createStyledButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        // Next button → goes to Login screen
        JButton nextButton = createStyledButton("Get Started");
        nextButton.addActionListener(e -> {
            dispose(); // Close current window
            new LoginScreen(manager).setVisible(true); // Open login screen
        });

        buttonPanel.add(exitButton);
        buttonPanel.add(nextButton);

        // Add all sections into main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set panel as window content
        setContentPane(mainPanel);
        setVisible(true);
    }

    // Helper method: creates styled buttons with hover effect 
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(buttonTextFont);
        button.setBackground(buttonBgColor);
        button.setForeground(buttonTextColor);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect using mouse listener
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
