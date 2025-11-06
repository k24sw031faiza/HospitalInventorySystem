import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Hospital Inventory System - Login");
        setSize(770, 455);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== LEFT SIDE IMAGE PANEL =====
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/hospital.jpeg"));
        Image img = icon.getImage().getScaledInstance(385, 455, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setPreferredSize(new Dimension(385, 455));
        add(imageLabel, BorderLayout.WEST);

        // ===== RIGHT SIDE LOGIN PANEL =====
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(new Color(10, 10, 25));
        add(rightPanel, BorderLayout.CENTER);

        // Neon Colors
        Color neonBlue = new Color(0, 255, 255);
        Color neonBlueHover = new Color(100, 255, 255);

        // Vertical offset for approximate vertical centering
        int yOffset = 80;

        // Load and scale login icon
        ImageIcon loginIconRaw = new ImageIcon(getClass().getResource("/images/login-icon.png"));
        Image loginIconImg = loginIconRaw.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon loginIcon = new ImageIcon(loginIconImg);

        // JLabel with only icon, no text
        JLabel title = new JLabel(loginIcon);
        // Center icon horizontally in right panel (width 385), y position adjusted with yOffset
        int iconX = (385 - 60) / 2; // center horizontally
        title.setBounds(iconX, 42 + yOffset, 60, 60);
        rightPanel.add(title);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(70, 112 + yOffset, 110, 18);
        userLabel.setForeground(neonBlue);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rightPanel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(180, 112 + yOffset, 160, 25);
        userField.setBackground(new Color(20, 20, 40));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(neonBlue);
        userField.setFont(new Font("Consolas", Font.PLAIN, 15));
        userField.setBorder(BorderFactory.createLineBorder(neonBlue, 2));
        rightPanel.add(userField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(70, 155 + yOffset, 110, 18);
        passLabel.setForeground(neonBlue);
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rightPanel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(180, 155 + yOffset, 160, 25);
        passField.setBackground(new Color(20, 20, 40));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(neonBlue);
        passField.setFont(new Font("Consolas", Font.PLAIN, 15));
        passField.setBorder(BorderFactory.createLineBorder(neonBlue, 2));
        rightPanel.add(passField);

        // Buttons - styled like labels with neon blue text and border
        int rightPanelWidth = 385;
        int btnWidth = 90;
        int btnHeight = 30;
        int gap = 15;

        int startX = (rightPanelWidth - (btnWidth * 2 + gap)) / 2;
        int buttonsY = 210 + yOffset;

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(startX, buttonsY, btnWidth, btnHeight);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(new Color(10, 10, 25));
        loginBtn.setForeground(neonBlue);
        loginBtn.setBorder(BorderFactory.createLineBorder(neonBlue, 2));
        loginBtn.setFocusPainted(false);
        rightPanel.add(loginBtn);

        JButton cancelBtn = new JButton("CANCEL");
        cancelBtn.setBounds(startX + btnWidth + gap, buttonsY, btnWidth, btnHeight);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelBtn.setBackground(new Color(10, 10, 25));
        cancelBtn.setForeground(neonBlue);
        cancelBtn.setBorder(BorderFactory.createLineBorder(neonBlue, 2));
        cancelBtn.setFocusPainted(false);
        rightPanel.add(cancelBtn);

        // Hover effect - lighter neon blue border and text on hover
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                loginBtn.setForeground(new Color(100, 255, 255));
                loginBtn.setBorder(BorderFactory.createLineBorder(new Color(100, 255, 255), 2));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                loginBtn.setForeground(neonBlue);
                loginBtn.setBorder(BorderFactory.createLineBorder(neonBlue, 2));
            }
        });

        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                cancelBtn.setForeground(new Color(100, 255, 255));
                cancelBtn.setBorder(BorderFactory.createLineBorder(new Color(100, 255, 255), 2));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                cancelBtn.setForeground(neonBlue);
                cancelBtn.setBorder(BorderFactory.createLineBorder(neonBlue, 2));
            }
        });

        // ===== LOGIN ACTION =====
        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());

            if (user.equals("Faiza") && pass.equals("2796")) {
                JOptionPane.showMessageDialog(this, "Login Successful!", "Access Granted", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new DashboardFrame();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
