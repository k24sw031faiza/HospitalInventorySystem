import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("Hospital Inventory System - Dashboard");
        setSize(770, 455);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(10, 10, 25)); // dark bg

        // Neon colors for buttons
        Color neonPink = new Color(255, 51, 153);
        Color neonPurple = new Color(170, 0, 255);
        Color neonBlue = new Color(0, 255, 255);
        Color neonGray = new Color(150, 150, 150); // for logout or neutral button

        // ===== LEFT PANEL: Image =====
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(20, 20, 50));

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/dashboard.jpeg"));
        Image img = icon.getImage().getScaledInstance(385, 455, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // ===== RIGHT PANEL =====
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(10, 10, 25));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        // Dashboard label
        JLabel dashboardLabel = new JLabel("Dashboard");
        dashboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dashboardLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        dashboardLabel.setForeground(neonBlue);

        rightPanel.add(dashboardLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Buttons panel (vertical box)
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(10, 10, 25));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        int btnWidth = 140;
        int btnHeight = 30;
        int btnGap = 20;

        // Create buttons with distinct neon colors
        JButton btnDoctor = createNeonButton("Doctors", neonPink);
        JButton btnPatient = createNeonButton("Patients", neonPurple);
        JButton btnSupply = createNeonButton("Supplies", neonBlue);
        JButton btnLogout = createNeonButton("Logout", neonGray);

        JButton[] buttons = {btnDoctor, btnPatient, btnSupply, btnLogout};
        for (JButton btn : buttons) {
            btn.setMaximumSize(new Dimension(btnWidth, btnHeight));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonsPanel.add(btn);
            buttonsPanel.add(Box.createRigidArea(new Dimension(0, btnGap)));
        }

        rightPanel.add(buttonsPanel);

        // JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(385);
        splitPane.setEnabled(false);
        splitPane.setDividerSize(0);

        add(splitPane);

        // Button actions
        btnDoctor.addActionListener(e -> {
            new DoctorFrame().setVisible(true);
            dispose();
        });
        btnPatient.addActionListener(e -> {
            new PatientFrame().setVisible(true);
            dispose();
        });
        btnSupply.addActionListener(e -> {
            new SupplyFrame().setVisible(true);
            dispose();
        });
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private JButton createNeonButton(String text, Color neonColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(neonColor);
        btn.setBackground(new Color(20, 20, 40));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(neonColor, 2));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(neonColor);
                btn.setForeground(Color.BLACK);
                btn.setBorder(BorderFactory.createLineBorder(neonColor.brighter(), 3));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(20, 20, 40));
                btn.setForeground(neonColor);
                btn.setBorder(BorderFactory.createLineBorder(neonColor, 2));
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        new DashboardFrame();
    }
}
