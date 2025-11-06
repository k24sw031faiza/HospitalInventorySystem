import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class PatientFrame extends JFrame {

    Stack<Patient> stack = new Stack<>();

    public PatientFrame() {
        setTitle("Patient Record");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Color neonBlue = new Color(0, 255, 255);
        Color neonPink = new Color(255, 51, 153);
        Color neonPurple = new Color(170, 0, 255);
        Color darkBg = new Color(10, 10, 25);

        // LEFT PANEL - 400 width (image)
        JPanel leftPanel = new JPanel(null);
        leftPanel.setBounds(0, 0, 400, 450);

        ImageIcon icon = new ImageIcon("src/images/patient.jpeg"); // image path
        Image img = icon.getImage().getScaledInstance(400, 450, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        imgLabel.setBounds(0, 0, 400, 450);
        leftPanel.add(imgLabel);

        add(leftPanel);

        // RIGHT PANEL - 400 width (form)
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBounds(400, 0, 400, 450);
        rightPanel.setBackground(darkBg);
        add(rightPanel);

        // Form config
        int labelWidth = 100;
        int fieldWidth = 180;
        int gapBetweenLabelAndField = 10;
        int verticalGap = 38;
        int numberOfFields = 5;

        int headingHeight = 30;
        int formStartY = 40;

        JLabel heading = new JLabel("Patient Record", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(neonBlue);
        heading.setBounds(0, 10, 400, headingHeight);
        rightPanel.add(heading);

        int formWidth = labelWidth + gapBetweenLabelAndField + fieldWidth;
        int labelX = (400 - formWidth) / 2;
        int fieldX = labelX + labelWidth + gapBetweenLabelAndField;

        int currentY = formStartY + headingHeight + 10;

        JLabel idLabel = createLabel("Patient ID:");
        JTextField idField = createTextField();
        idLabel.setBounds(labelX, currentY, labelWidth, 28);
        idField.setBounds(fieldX, currentY, fieldWidth, 28);
        rightPanel.add(idLabel);
        rightPanel.add(idField);

        currentY += verticalGap;
        JLabel nameLabel = createLabel("Name:");
        JTextField nameField = createTextField();
        nameLabel.setBounds(labelX, currentY, labelWidth, 28);
        nameField.setBounds(fieldX, currentY, fieldWidth, 28);
        rightPanel.add(nameLabel);
        rightPanel.add(nameField);

        currentY += verticalGap;
        JLabel diseaseLabel = createLabel("Disease:");
        JTextField diseaseField = createTextField();
        diseaseLabel.setBounds(labelX, currentY, labelWidth, 28);
        diseaseField.setBounds(fieldX, currentY, fieldWidth, 28);
        rightPanel.add(diseaseLabel);
        rightPanel.add(diseaseField);

        currentY += verticalGap;
        JLabel genderLabel = createLabel("Gender:");
        JTextField genderField = createTextField();
        genderLabel.setBounds(labelX, currentY, labelWidth, 28);
        genderField.setBounds(fieldX, currentY, fieldWidth, 28);
        rightPanel.add(genderLabel);
        rightPanel.add(genderField);

        currentY += verticalGap;
        JLabel ageLabel = createLabel("Age:");
        JTextField ageField = createTextField();
        ageLabel.setBounds(labelX, currentY, labelWidth, 28);
        ageField.setBounds(fieldX, currentY, fieldWidth, 28);
        rightPanel.add(ageLabel);
        rightPanel.add(ageField);

        // Buttons
        int btnWidth = 100, btnHeight = 30, btnSpacing = 10;
        int btnStartX = (400 - (3 * btnWidth + 2 * btnSpacing)) / 2;
        int btnY = currentY + verticalGap + 10;

        JButton addBtn = createButton("ADD", neonPink);
        addBtn.setBounds(btnStartX, btnY, btnWidth, btnHeight);

        JButton deleteBtn = createButton("DELETE", neonPurple);
        deleteBtn.setBounds(btnStartX + btnWidth + btnSpacing, btnY, btnWidth, btnHeight);

        JButton updateBtn = createButton("UPDATE", neonBlue);
        updateBtn.setBounds(btnStartX + 2 * (btnWidth + btnSpacing), btnY, btnWidth, btnHeight);

        rightPanel.add(addBtn);
        rightPanel.add(deleteBtn);
        rightPanel.add(updateBtn);

        int bottomBtnY = btnY + btnHeight + 20;
        int bottomBtnWidth = 120, bottomBtnHeight = 35, bottomBtnSpacing = 15;
        int bottomBtnStartX = (400 - (2 * bottomBtnWidth + bottomBtnSpacing)) / 2;

        JButton displayBtn = createButton("DISPLAY", new Color(0, 200, 255));
        displayBtn.setBounds(bottomBtnStartX, bottomBtnY, bottomBtnWidth, bottomBtnHeight);

        JButton backBtn = createButton("BACK", Color.GRAY);
        backBtn.setBounds(bottomBtnStartX + bottomBtnWidth + bottomBtnSpacing, bottomBtnY, bottomBtnWidth, bottomBtnHeight);

        rightPanel.add(displayBtn);
        rightPanel.add(backBtn);

        // Button actions (same as before)...

        // ADD action
        addBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String disease = diseaseField.getText().trim();
            String gender = genderField.getText().trim();
            String age = ageField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || disease.isEmpty() || gender.isEmpty() || age.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            boolean duplicate = stack.stream().anyMatch(p -> p.getId().equals(id));
            if (duplicate) {
                JOptionPane.showMessageDialog(this, "Patient ID already exists! Use a unique ID.");
                return;
            }

            Patient p = new Patient(id, name, disease, gender, age);
            stack.push(p);
            JOptionPane.showMessageDialog(this, "Patient Added Successfully!");
            clearFields(idField, nameField, diseaseField, genderField, ageField);
        });

        // DELETE action
        deleteBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Patient ID to delete!");
                return;
            }
            if (stack.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No patients to delete!");
                return;
            }
            boolean found = false;
            Stack<Patient> temp = new Stack<>();
            StringBuilder deletedInfo = new StringBuilder();

            while (!stack.isEmpty()) {
                Patient p = stack.pop();
                if (p.getId().equals(id)) {
                    found = true;
                    deletedInfo.append(p.getId()).append(", ").append(p.getName()).append("\n");
                } else {
                    temp.push(p);
                }
            }
            while (!temp.isEmpty()) stack.push(temp.pop());

            if (found) {
                JOptionPane.showMessageDialog(this, "Patient Deleted Successfully!\n" + deletedInfo.toString());
                clearFields(idField, nameField, diseaseField, genderField, ageField);
            } else {
                JOptionPane.showMessageDialog(this, "Patient ID not found!");
            }
        });

        // UPDATE action
        updateBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            boolean found = false;
            Stack<Patient> temp = new Stack<>();

            while (!stack.isEmpty()) {
                Patient p = stack.pop();
                if (p.getId().equals(id)) {
                    p.setName(nameField.getText().trim());
                    p.setDisease(diseaseField.getText().trim());
                    p.setGender(genderField.getText().trim());
                    p.setAge(ageField.getText().trim());
                    found = true;
                }
                temp.push(p);
            }
            while (!temp.isEmpty()) stack.push(temp.pop());

            if (found) {
                JOptionPane.showMessageDialog(this, "Patient Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Patient ID not found!");
            }
            clearFields(idField, nameField, diseaseField, genderField, ageField);
        });

        // DISPLAY action
        displayBtn.addActionListener(e -> {
            if (stack.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No records found.");
                return;
            }
            JFrame displayFrame = new JFrame("All Patients");
            displayFrame.setSize(500, 350);
            displayFrame.setLayout(new BorderLayout());
            JTextArea displayArea = new JTextArea();
            displayArea.setBackground(new Color(20, 20, 40));
            displayArea.setForeground(neonBlue);
            displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
            displayArea.setEditable(false);

            StringBuilder sb = new StringBuilder();
            for (Patient p : stack)
                sb.append(p.getId()).append(", ")
                        .append(p.getName()).append(", ")
                        .append(p.getDisease()).append(", ")
                        .append(p.getGender()).append(", ")
                        .append(p.getAge()).append("\n");

            displayArea.setText(sb.toString());
            JScrollPane scrollPane = new JScrollPane(displayArea);
            displayFrame.add(scrollPane, BorderLayout.CENTER);
            displayFrame.setLocationRelativeTo(null);
            displayFrame.setVisible(true);
        });

        // BACK action
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

    static class Patient {
        private String id, name, disease, gender, age;

        public Patient(String id, String name, String disease, String gender, String age) {
            this.id = id; this.name = name; this.disease = disease; this.gender = gender; this.age = age;
        }
        public String getId() { return id; }
        public String getName() { return name; }
        public String getDisease() { return disease; }
        public String getGender() { return gender; }
        public String getAge() { return age; }
        public void setName(String name) { this.name = name; }
        public void setDisease(String disease) { this.disease = disease; }
        public void setGender(String gender) { this.gender = gender; }
        public void setAge(String age) { this.age = age; }

        @Override
        public String toString() {
            return id + " - " + name;
        }
    }

    public static void main(String[] args) {
        new PatientFrame();
    }
}
