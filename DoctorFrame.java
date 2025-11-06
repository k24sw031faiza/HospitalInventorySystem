import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class DoctorFrame extends JFrame {

    Stack<Doctor> stack = new Stack<>();

    public DoctorFrame() {
        setTitle("Doctor Record");
        setSize(770, 455);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Color neonBlue = new Color(0, 255, 255);
        Color neonPink = new Color(255, 51, 153);
        Color neonPurple = new Color(170, 0, 255);
        Color darkBg = new Color(10, 10, 25);

        // Left panel with image
        JPanel leftPanel = new JPanel(null);
        leftPanel.setBounds(0, 0, 385, 455);
        ImageIcon icon = new ImageIcon("src/images/doctor1.jpeg");
        Image img = icon.getImage().getScaledInstance(385, 455, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        imgLabel.setBounds(0, 0, 385, 455);
        leftPanel.add(imgLabel);
        add(leftPanel);

        // Right panel with form and buttons
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBounds(385, 0, 385, 455);
        rightPanel.setBackground(darkBg);
        add(rightPanel);

        JLabel heading = new JLabel(" Doctor Record ", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        heading.setForeground(neonBlue);
        heading.setBounds(40, 20, 300, 40);
        rightPanel.add(heading);

        int labelX = 50, fieldX = 180;
        int startY = 90, gap = 40;

        JLabel idLabel = createLabel("Doctor ID:");
        JTextField idField = createTextField();
        idLabel.setBounds(labelX, startY, 130, 30);
        idField.setBounds(fieldX, startY, 180, 30);

        JLabel nameLabel = createLabel("Name:");
        JTextField nameField = createTextField();
        nameLabel.setBounds(labelX, startY + gap, 130, 30);
        nameField.setBounds(fieldX, startY + gap, 180, 30);

        JLabel specLabel = createLabel("Specialization:");
        JTextField specField = createTextField();
        specLabel.setBounds(labelX, startY + 2 * gap, 130, 30);
        specField.setBounds(fieldX, startY + 2 * gap, 180, 30);

        JLabel contactLabel = createLabel("Contact:");
        JTextField contactField = createTextField();
        contactLabel.setBounds(labelX, startY + 3 * gap, 130, 30);
        contactField.setBounds(fieldX, startY + 3 * gap, 180, 30);

        rightPanel.add(idLabel);
        rightPanel.add(idField);
        rightPanel.add(nameLabel);
        rightPanel.add(nameField);
        rightPanel.add(specLabel);
        rightPanel.add(specField);
        rightPanel.add(contactLabel);
        rightPanel.add(contactField);

        int btnWidth = 90, btnHeight = 30, spacing = 10;
        int totalWidth = 3 * btnWidth + 2 * spacing;
        int btnStartX = (385 - totalWidth) / 2;
        int btnY = startY + 4 * gap + 10;

        JButton addBtn = createButton("ADD", neonPink);
        addBtn.setBounds(btnStartX, btnY, btnWidth, btnHeight);

        JButton deleteBtn = createButton("DELETE", neonPurple);
        deleteBtn.setBounds(btnStartX + btnWidth + spacing, btnY, btnWidth, btnHeight);

        JButton updateBtn = createButton("UPDATE", neonBlue);
        updateBtn.setBounds(btnStartX + 2 * (btnWidth + spacing), btnY, btnWidth, btnHeight);

        rightPanel.add(addBtn);
        rightPanel.add(deleteBtn);
        rightPanel.add(updateBtn);

        int bottomBtnWidth = 110, bottomBtnHeight = 35;
        int bottomSpacing = 15;
        int bottomTotalWidth = 2 * bottomBtnWidth + bottomSpacing;
        int bottomStartX = (385 - bottomTotalWidth) / 2;
        int bottomBtnY = btnY + btnHeight + 20;

        JButton displayBtn = createButton("DISPLAY", new Color(0, 200, 255));
        displayBtn.setBounds(bottomStartX, bottomBtnY, bottomBtnWidth, bottomBtnHeight);

        JButton backBtn = createButton("BACK", Color.GRAY);
        backBtn.setBounds(bottomStartX + bottomBtnWidth + bottomSpacing, bottomBtnY, bottomBtnWidth, bottomBtnHeight);

        rightPanel.add(displayBtn);
        rightPanel.add(backBtn);

        // ADD action with duplicate ID check
        addBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String spec = specField.getText().trim();
            String contact = contactField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || spec.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            // Check duplicate ID
            boolean duplicate = stack.stream().anyMatch(d -> d.getId().equals(id));
            if (duplicate) {
                JOptionPane.showMessageDialog(this, "Error: Doctor ID already exists!");
                return;
            }

            stack.push(new Doctor(id, name, spec, contact));
            JOptionPane.showMessageDialog(this, "Doctor Added Successfully!");
            clearFields(idField, nameField, specField, contactField);
        });

        // DELETE all matching IDs
        deleteBtn.addActionListener(e -> {
            String idToDelete = idField.getText().trim();
            if (idToDelete.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Doctor ID to delete!");
                return;
            }
            if (stack.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Stack is empty!");
                return;
            }
            boolean found = false;
            Stack<Doctor> tempStack = new Stack<>();
            while (!stack.isEmpty()) {
                Doctor d = stack.pop();
                if (d.getId().equals(idToDelete)) {
                    found = true;
                    // Show delete message only once (optional)
                    // JOptionPane.showMessageDialog(this, "🗑 Deleted Doctor: " + d);
                } else {
                    tempStack.push(d);
                }
            }
            while (!tempStack.isEmpty()) stack.push(tempStack.pop());

            if (found) {
                JOptionPane.showMessageDialog(this, "Doctor Deleted successfully!");
                clearFields(idField, nameField, specField, contactField);
            } else {
                JOptionPane.showMessageDialog(this, "Doctor ID not found!");
            }
        });

        // UPDATE all matching IDs
        updateBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String spec = specField.getText().trim();
            String contact = contactField.getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Doctor ID to update!");
                return;
            }

            boolean found = false;
            Stack<Doctor> tempStack = new Stack<>();
            while (!stack.isEmpty()) {
                Doctor d = stack.pop();
                if (d.getId().equals(id)) {
                    d.setName(name);
                    d.setSpecialization(spec);
                    d.setContact(contact);
                    found = true;
                }
                tempStack.push(d);
            }
            while (!tempStack.isEmpty()) stack.push(tempStack.pop());

            if (found) {
                JOptionPane.showMessageDialog(this, "Doctor Updated Successfully!");
                clearFields(idField, nameField, specField, contactField);
            } else {
                JOptionPane.showMessageDialog(this, "Doctor ID not found!");
            }
        });

        // DISPLAY all doctors
        displayBtn.addActionListener(e -> {
            if (stack.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No records found.");
            } else {
                JFrame displayFrame = new JFrame("All Doctors");
                displayFrame.setSize(600, 400);
                displayFrame.setLayout(new BorderLayout());

                JTextArea displayArea = new JTextArea();
                displayArea.setBackground(new Color(20, 20, 40));
                displayArea.setForeground(neonBlue);
                displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
                displayArea.setEditable(false);

                StringBuilder sb = new StringBuilder();
                for (Doctor d : stack) {
                    sb.append(d.getId()).append(", ")
                            .append(d.getName()).append(", ")
                            .append(d.getSpecialization()).append(", ")
                            .append(d.getContact()).append("\n");
                }
                displayArea.setText(sb.toString());

                JScrollPane scrollPane = new JScrollPane(displayArea);
                displayFrame.add(scrollPane, BorderLayout.CENTER);
                displayFrame.setLocationRelativeTo(null);
                displayFrame.setVisible(true);
            }
        });

        // BACK button action
        backBtn.addActionListener(e -> {
            new DashboardFrame();
            dispose();
        });

        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setForeground(new Color(0, 255, 255));
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setBackground(new Color(20, 20, 40));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.CYAN);
        field.setFont(new Font("Consolas", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
        return field;
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(color);
        btn.setBackground(new Color(25, 25, 60));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(color, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
                btn.setForeground(Color.BLACK);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(25, 25, 60));
                btn.setForeground(color);
            }
        });
        return btn;
    }

    private void clearFields(JTextField... fields) {
        for (JTextField f : fields) f.setText("");
    }

    private static class Doctor {
        private String id, name, specialization, contact;

        public Doctor(String id, String name, String specialization, String contact) {
            this.id = id;
            this.name = name;
            this.specialization = specialization;
            this.contact = contact;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSpecialization() {
            return specialization;
        }

        public String getContact() {
            return contact;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSpecialization(String specialization) {
            this.specialization = specialization;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        @Override
        public String toString() {
            return id + " | " + name + " | " + specialization + " | " + contact;
        }
    }

    public static void main(String[] args) {
        new DoctorFrame();
    }
}
