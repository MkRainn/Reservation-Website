import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.FontFormatException;
import javax.swing.border.TitledBorder;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

class Room {
    String roomNumber;
    double price;
    String imagePath;

    Room(String roomNumber, double price, String imagePath) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.imagePath = imagePath;
    }
}

public class Lihim {
    private JFrame frame;
    private List<Room> rooms;
    private JTextField nameField, phoneField, emailField, roomNameField, roomPriceField, roomImageField;
    private JDateChooser checkInChooser, checkOutChooser;
    private JLabel roomImageLabel, roomPriceLabel;
    private JTextArea bookingInfoArea;
    private JComboBox<String> roomDropdown;
    private Font customFont;

    public Lihim() {

        // Font
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/RhodiumLibre-Regular.ttf")).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        rooms = new ArrayList<>();
        rooms.add(new Room("Seaside Villa", 32800.0, "images/Seaside Villa.jpg"));
        rooms.add(new Room("Luxury Villa", 28000.0, "images/Luxury Villa.jpg"));
        rooms.add(new Room("Presidential Villa", 51000.0, "images/Presidential Villa.jpg"));

        frame = new JFrame("Lihim Resort - Room Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#213737"));

        // Title Logo and Lihim Resort
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0, 40, 950, 50);
        titlePanel.setBackground(Color.decode("#213737"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JLabel logoLabel = new JLabel();
        String logoPath = "images/Logo.png";
        ImageIcon logoIcon = new ImageIcon(logoPath);
        logoLabel.setIcon(logoIcon);
        logoLabel.setPreferredSize(new Dimension(100, 50));

        JLabel titleLabel = new JLabel("Lihim Resort", SwingConstants.CENTER);
        titleLabel.setFont(customFont.deriveFont(Font.BOLD, 24f));
        titleLabel.setForeground(Color.WHITE);

        titlePanel.add(logoLabel);
        titlePanel.add(titleLabel);
        frame.add(titlePanel);

        // Guest Information
        JPanel guestPanel = new JPanel(null);
        guestPanel.setBounds(350, 120, 550, 230);
        guestPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Guest Info", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, customFont, Color.WHITE));
        guestPanel.setBackground(Color.decode("#213737"));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(customFont);
        nameLabel.setBounds(20, 30, 100, 25);
        guestPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 30, 400, 25);
        guestPanel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(customFont);
        emailLabel.setBounds(20, 70, 100, 25);
        guestPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(120, 70, 400, 25);
        guestPanel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone No.:");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setFont(customFont);
        phoneLabel.setBounds(20, 110, 100, 25);
        guestPanel.add(phoneLabel);

        phoneField = new JTextField("+63");
        phoneField.setBounds(120, 110, 400, 25);
        guestPanel.add(phoneField);

        // Check-in and Check-out Dates
        JLabel checkInLabel = new JLabel("Check-in Date:");
        checkInLabel.setForeground(Color.WHITE);
        checkInLabel.setFont(customFont);
        checkInLabel.setBounds(20, 150, 120, 25);
        guestPanel.add(checkInLabel);

        checkInChooser = new JDateChooser();
        checkInChooser.setBounds(140, 150, 250, 30);
        guestPanel.add(checkInChooser);

        JLabel checkOutLabel = new JLabel("Check-out Date:");
        checkOutLabel.setForeground(Color.WHITE);
        checkOutLabel.setFont(customFont);
        checkOutLabel.setBounds(20, 190, 120, 25);
        guestPanel.add(checkOutLabel);

        checkOutChooser = new JDateChooser();
        checkOutChooser.setBounds(140, 190, 250, 30);
        guestPanel.add(checkOutChooser);

        // Booking Information
        bookingInfoArea = new JTextArea();
        bookingInfoArea.setEditable(false);
        bookingInfoArea.setBounds(350, 370, 550, 150);
        bookingInfoArea.setBorder(BorderFactory.createTitledBorder("Booking Information"));

        // Room Description
        JPanel roomPanel = new JPanel(null);
        roomPanel.setBounds(30, 120, 300, 400);
        roomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Room Description", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, customFont, Color.WHITE));
        roomPanel.setBackground(Color.decode("#213737"));

        roomImageLabel = new JLabel();
        roomImageLabel.setBounds(30, 30, 240, 160);
        roomImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roomPanel.add(roomImageLabel);

        roomDropdown = new JComboBox<>();
        for (Room room : rooms) {
            roomDropdown.addItem(room.roomNumber);
        }
        roomDropdown.setBounds(30, 250, 240, 25);
        roomDropdown.addActionListener(e -> updateRoomInfo());
        roomPanel.add(roomDropdown);

        roomPriceLabel = new JLabel();
        roomPriceLabel.setForeground(Color.WHITE);
        roomPriceLabel.setFont(customFont);
        roomPriceLabel.setBounds(30, 285, 240, 25);
        roomPanel.add(roomPriceLabel);

        // Book now Button
        JButton bookNowButton = new JButton("Book Now");
        bookNowButton.setBounds(30, 320, 240, 30);
        bookNowButton.setMargin(new Insets(0, 0, 0, 0));
        bookNowButton.addActionListener(e -> bookNowAction());
        roomPanel.add(bookNowButton);

        // Create Room
        JPanel createRoomPanel = new JPanel(null);
        createRoomPanel.setBounds(30, 520, 872, 200);
        createRoomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Create Room", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, customFont, Color.WHITE));
        createRoomPanel.setBackground(Color.decode("#213737"));

        JLabel roomNameLabel = new JLabel("Room Name:");
        roomNameLabel.setForeground(Color.WHITE);
        roomNameLabel.setFont(customFont);
        roomNameLabel.setBounds(20, 30, 100, 25);
        createRoomPanel.add(roomNameLabel);

        roomNameField = new JTextField();
        roomNameField.setBounds(125, 30, 200, 25);
        createRoomPanel.add(roomNameField);

        JLabel roomPriceFieldLabel = new JLabel("Price:");
        roomPriceFieldLabel.setForeground(Color.WHITE);
        roomPriceFieldLabel.setFont(customFont);
        roomPriceFieldLabel.setBounds(20, 90, 100, 25);
        createRoomPanel.add(roomPriceFieldLabel);

        roomPriceField = new JTextField();
        roomPriceField.setBounds(125, 90, 200, 25);
        createRoomPanel.add(roomPriceField);

        JLabel roomImageLabel = new JLabel("Image Path:");
        roomImageLabel.setForeground(Color.WHITE);
        roomImageLabel.setFont(customFont);
        roomImageLabel.setBounds(20, 150, 100, 25);
        createRoomPanel.add(roomImageLabel);

        roomImageField = new JTextField();
        roomImageField.setBounds(125, 150, 200, 25);
        createRoomPanel.add(roomImageField);

        // Add Room Button
        JButton addRoomButton = new JButton("Add Room");
        addRoomButton.setBounds(380, 30, 120, 25);
        addRoomButton.addActionListener(e -> addRoomAction());
        createRoomPanel.add(addRoomButton);

        // Reset Button
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(380, 90, 120, 25);
        resetButton.addActionListener(e -> resetFields());
        createRoomPanel.add(resetButton);

        // Update Room Button
        JButton updateRoomButton = new JButton("Update Room");
        updateRoomButton.setBounds(530, 30, 120, 25);
        updateRoomButton.addActionListener(e -> updateRoomAction());
        createRoomPanel.add(updateRoomButton);

        // Delete Room Button
        JButton deleteRoomButton = new JButton("Delete Room");
        deleteRoomButton.setBounds(530, 90, 120, 25);
        deleteRoomButton.addActionListener(e -> deleteRoomAction());
        createRoomPanel.add(deleteRoomButton);

        JButton openWebsiteButton = new JButton("Visit our Website");
        openWebsiteButton.setBounds(380, 150, 270, 25);
        openWebsiteButton.addActionListener(e -> openWebsite("https://genshin.hoyoverse.com/en/gift"));
        createRoomPanel.add(openWebsiteButton);

        frame.add(guestPanel);
        frame.add(roomPanel);
        frame.add(createRoomPanel);
        frame.add(bookingInfoArea);
        frame.setVisible(true);
    }

    private void loadImage(String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image scaledImage = img.getScaledInstance(roomImageLabel.getWidth(), roomImageLabel.getHeight(), Image.SCALE_SMOOTH);
            roomImageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            roomImageLabel.setIcon(null);
            roomImageLabel.setText("Image not found");
        }
    }

    private void updateRoomInfo() {
        String selectedRoom = (String) roomDropdown.getSelectedItem();
        for (Room room : rooms) {
            if (room.roomNumber.equals(selectedRoom)) {
                roomPriceLabel.setText("Price: ₱" + room.price);
                loadImage(room.imagePath);
                roomNameField.setText(room.roomNumber);
                roomPriceField.setText(String.valueOf(room.price));
                roomImageField.setText(room.imagePath);
                break;
            }
        }
    }

    private void bookNowAction() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        Date checkInDate = checkInChooser.getDate();
        Date checkOutDate = checkOutChooser.getDate();
        String selectedRoom = (String) roomDropdown.getSelectedItem();

        System.out.println("Selected Room: " + selectedRoom);

        if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && checkInDate != null && checkOutDate != null) {
            if (checkInDate.after(checkOutDate)) {
                JOptionPane.showMessageDialog(frame, "Check-out date must be after check-in date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double roomPrice = rooms.stream()
                    .filter(room -> room.roomNumber.equals(selectedRoom))
                    .findFirst().orElse(new Room("Unknown", 0, "")).price;

            System.out.println("Room Price: " + roomPrice);

            StringBuilder bookingDetails = new StringBuilder();
            bookingDetails.append("Booking Details:\n")
                    .append("Name: ").append(name).append("\n")
                    .append("Email: ").append(email).append("\n")
                    .append("Phone: ").append(phone).append("\n")
                    .append("Room: ").append(selectedRoom).append("\n")
                    .append("Price: ").append(roomPrice).append("\n")
                    .append("Check-in: ").append(checkInDate).append("\n")
                    .append("Check-out: ").append(checkOutDate).append("\n");

            bookingInfoArea.setText(bookingDetails.toString());
            JOptionPane.showMessageDialog(frame, "Booking confirmed!");
        } else {
            JOptionPane.showMessageDialog(frame, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetRoomFields() {
        roomNameField.setText("");
        roomPriceField.setText("");
        roomImageField.setText("");
    }

    private void addRoomAction() {
        String roomName = roomNameField.getText();
        String roomPriceText = roomPriceField.getText();
        String imagePath = roomImageField.getText();

        if (!roomName.isEmpty() && !roomPriceText.isEmpty() && !imagePath.isEmpty()) {
            try {
                double price = Double.parseDouble(roomPriceText);
                rooms.add(new Room(roomName, price, imagePath));
                roomDropdown.addItem(roomName);
                resetRoomFields();
                JOptionPane.showMessageDialog(frame, "Room added successfully!");
                updateRoomInfo();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRoomAction() {
        String selectedRoom = (String) roomDropdown.getSelectedItem();
        String roomPriceText = roomPriceField.getText();
        String imagePath = roomImageField.getText();

        if (selectedRoom != null && !roomPriceText.isEmpty() && !imagePath.isEmpty()) {
            try {
                double price = Double.parseDouble(roomPriceText);
                for (Room room : rooms) {
                    if (room.roomNumber.equals(selectedRoom)) {
                        room.price = price;
                        room.imagePath = imagePath;
                        JOptionPane.showMessageDialog(frame, "Room updated successfully!");
                        resetRoomFields();
                        break;
                    }
                }
                updateRoomInfo();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a room and fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRoomAction() {
        String selectedRoom = (String) roomDropdown.getSelectedItem();
        if (selectedRoom != null) {
            rooms.removeIf(room -> room.roomNumber.equals(selectedRoom));
            roomDropdown.removeItem(selectedRoom);
            resetRoomFields();
            updateRoomInfo();
            JOptionPane.showMessageDialog(frame, "Room deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a room to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("+63");
        checkInChooser.setDate(null);
        checkOutChooser.setDate(null);
        bookingInfoArea.setText("");
        resetRoomFields();
    }

    private void openWebsite(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to open website.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Lihim::new);
    }
}
