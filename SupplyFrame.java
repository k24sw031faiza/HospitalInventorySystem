import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class SupplyFrame extends JFrame {

    Stack<Supply> stack = new Stack<>();

    public SupplyFrame() {
        setTitle("Supply Management");
        setSize(800, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color neonPink = new Color(255, 51, 153);
        Color neonPurple = new Color(170, 0, 255);
        Color neonBlue = new Color(0, 255, 255);
        Color darkBg = new Color(10, 10, 25);

        // LEFT PANEL - 300 width
        JPanel leftPanel = new JPanel(null);
        leftPanel.setBounds(0, 0, 300, 450);

        ImageIcon icon = new ImageIcon("src/images/supply.jpeg"); // replace with your image path
        Image img = icon.getImage().getScaledInstance(300, 450, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        imgLabel.setBounds(0, 0, 300, 450);
        leftPanel.add(imgLabel);

        add(leftPanel);

        // RIGHT PANEL - 500 width
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBounds(300, 0, 500, 450);
        rightPanel.setBackground(darkBg);
        add(rightPanel);

        // TITLE centered horizontally in 500 width panel
        JLabel heading = new JLabel("Supply Management", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        heading.setForeground(neonBlue);
        heading.setBounds(100, 40, 300, 40); // centered inside 500 width panel
        rightPanel.add(heading);

        // FORM fields positioning adjusted for wider panel
        int labelWidth = 140;   // increased label width
        int fieldWidth = 230;   // increased field width
        int labelX, fieldX;
        int startY = 120;
        int gap = 50;

        int formWidth = labelWidth + 10 + fieldWidth;
        int formStartX = (500 - formWidth) / 2;  // center form horizontally in 500 width
        labelX = formStartX;
        fieldX = labelX + labelWidth + 10;

        JLabel idLabel = createLabel("Supply ID:");
        JTextField idField = createTextField();
        idLabel.setBounds(labelX, startY, labelWidth, 30);
        idField.setBounds(fieldX, startY, fieldWidth, 30);

        JLabel nameLabel = createLabel("Item Name:");
        JTextField nameField = createTextField();
        nameLabel.setBounds(labelX, startY + gap, labelWidth, 30);
        nameField.setBounds(fieldX, startY + gap, fieldWidth, 30);

        JLabel qtyLabel = createLabel("Quantity:");
        JTextField qtyField = createTextField();
        qtyLabel.setBounds(labelX, startY + 2 * gap, labelWidth, 30);
        qtyField.setBounds(fieldX, startY + 2 * gap, fieldWidth, 30);

        rightPanel.add(idLabel); rightPanel.add(idField);
        rightPanel.add(nameLabel); rightPanel.add(nameField);
        rightPanel.add(qtyLabel); rightPanel.add(qtyField);

        // BUTTONS: Add, Delete, Update (top row)
        int btnWidth = 130, btnHeight = 35, spacing = 20;  // increased button width and spacing
        int totalWidthTopBtns = 3 * btnWidth + 2 * spacing;
        int btnStartXTop = (500 - totalWidthTopBtns) / 2;
        int btnYTop = startY + 3 * gap + 20;

        JButton addBtn = createButton("Add", neonPink);
        addBtn.setBounds(btnStartXTop, btnYTop, btnWidth, btnHeight);

        JButton deleteBtn = createButton("Delete", neonPurple);
        deleteBtn.setBounds(btnStartXTop + btnWidth + spacing, btnYTop, btnWidth, btnHeight);

        JButton updateBtn = createButton("Update", neonBlue);
        updateBtn.setBounds(btnStartXTop + 2 * (btnWidth + spacing), btnYTop, btnWidth, btnHeight);

        rightPanel.add(addBtn);
        rightPanel.add(deleteBtn);
        rightPanel.add(updateBtn);

        // BUTTONS: Display All, Back (bottom row)
        int btnWidthBottom = 170, btnHeightBottom = 40;  // increased width
        int spacingBottom = 30;
        int totalWidthBottomBtns = 2 * btnWidthBottom + spacingBottom;
        int btnStartXBottom = (500 - totalWidthBottomBtns) / 2;
        int btnYBottom = btnYTop + btnHeight + 30;

        JButton displayBtn = createButton("Display All", new Color(0, 200, 255));
        displayBtn.setBounds(btnStartXBottom, btnYBottom, btnWidthBottom, btnHeightBottom);

        JButton backBtn = createButton("Back", Color.GRAY);
        backBtn.setBounds(btnStartXBottom + btnWidthBottom + spacingBottom, btnYBottom, btnWidthBottom, btnHeightBottom);

        rightPanel.add(displayBtn);
        rightPanel.add(backBtn);

        // ACTION LISTENERS

        addBtn.addActionListener(e -> {
            if(idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty() || qtyField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }
            // Check duplicate ID
            boolean duplicate = stack.stream().anyMatch(s -> s.getId().equals(idField.getText().trim()));
            if (duplicate) {
                JOptionPane.showMessageDialog(this, "Supply ID already exists! Use a unique ID.");
                return;
            }
            Supply s = new Supply(idField.getText().trim(), nameField.getText().trim(), qtyField.getText().trim());
            stack.push(s);
            JOptionPane.showMessageDialog(this, "Supply Added Successfully!");
            clearFields(idField, nameField, qtyField);
        });

        deleteBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            if(id.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter Supply ID to delete!");
                return;
            }
            if(stack.isEmpty()){
                JOptionPane.showMessageDialog(this, "No supplies to delete!");
                return;
            }
            boolean found = false;
            Stack<Supply> temp = new Stack<>();
            StringBuilder deletedInfo = new StringBuilder();

            while (!stack.isEmpty()) {
                Supply s = stack.pop();
                if (s.getId().equals(id)) {
                    found = true;
                    deletedInfo.append(s.getId()).append(", ")
                            .append(s.getName()).append(", ")
                            .append(s.getQuantity()).append("\n");
                    // Don't push deleted item back
                } else {
                    temp.push(s);
                }
            }
            while (!temp.isEmpty()) stack.push(temp.pop());

            if(found){
                JOptionPane.showMessageDialog(this, "Supply Deleted Successfully!\n" );
                clearFields(idField, nameField, qtyField);
            } else {
                JOptionPane.showMessageDialog(this, "Supply ID not found!");
            }
        });

        updateBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            if(id.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter Supply ID to update!");
                return;
            }
            boolean found = false;
            Stack<Supply> tempStack = new Stack<>();
            while (!stack.isEmpty()) {
                Supply s = stack.pop();
                if (s.getId().equals(id)) {
                    s.setName(nameField.getText().trim());
                    s.setQuantity(qtyField.getText().trim());
                    found = true;
                }
                tempStack.push(s);
            }
            while (!tempStack.isEmpty()) stack.push(tempStack.pop());

            if (found) {
                JOptionPane.showMessageDialog(this, "Supply Updated Successfully!");
                clearFields(idField, nameField, qtyField);
            } else {
                JOptionPane.showMessageDialog(this, "Supply ID not found!");
            }
        });

        displayBtn.addActionListener(e -> {
            if (stack.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No records found.");
            } else {
                JFrame displayFrame = new JFrame("All Supplies");
                displayFrame.setSize(500, 400);
                displayFrame.setLayout(new BorderLayout());

                JTextArea displayArea = new JTextArea();
                displayArea.setEditable(false);
                displayArea.setBackground(new Color(20, 20, 40));
                displayArea.setForeground(neonBlue);
                displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));

                StringBuilder sb = new StringBuilder();
                for (Supply s : stack) {
                    sb.append(s.getId()).append(", ")
                            .append(s.getName()).append(", ")
                            .append(s.getQuantity()).append("\n");
                }
                displayArea.setText(sb.toString());

                JScrollPane scrollPane = new JScrollPane(displayArea);
                displayFrame.add(scrollPane, BorderLayout.CENTER);
                displayFrame.setLocationRelativeTo(null);
                displayFrame.setVisible(true);
            }
        });

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

    // Supply class
    static class Supply {
        private String id, name, quantity;

        public Supply(String id, String name, String quantity) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getQuantity() { return quantity; }

        public void setName(String name) { this.name = name; }
        public void setQuantity(String quantity) { this.quantity = quantity; }

        @Override
        public String toString() {
            return id + " - " + name + " (" + quantity + ")";
        }
    }

    public static void main(String[] args) {
        new SupplyFrame();
    }
}
