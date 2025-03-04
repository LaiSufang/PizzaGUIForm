
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;


public class PizzaGUIFrame extends JFrame {
    JPanel mainPanel, optionsPanel, orderPanel, crustPanel, sizePanel, toppingsPanel, receiptPanel, buttonPanel;
    JRadioButton thinCrust, regularCrust, deepDishCrust;
    JComboBox sizeComboBox;
    JCheckBox BloodyHell, JalapenoEyeballs, MushroomSkulls, GhostPeppers, SpiderLegs, PeperoniJackOLanterns;
    JTextArea receiptTextArea;
    JScrollPane receiptScrollPane;
    JButton orderButton, clearButton, quitButton;

    private final double SMALL_PRICE = 8;
    private final double MEDIUM_PRICE = 12;
    private final double LARGE_PRICE = 16;
    private final double SUPER_PRICE = 20;
    private final double INGREDIENT_PRICE = 1;
    private final double TAX_RATE = 0.07;


    public PizzaGUIFrame() {
        setTitle("Pizza GUI");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3,1));
        orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());

        createCrustPanel();
        optionsPanel.add(crustPanel);

        createSizePanel();
        optionsPanel.add(sizePanel);

        createToppingsPanel();
        optionsPanel.add(toppingsPanel);

        createReceiptPanel();
        orderPanel.add(receiptPanel, BorderLayout.CENTER);

        createButtonPanel();
        orderPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(optionsPanel);
        mainPanel.add(orderPanel);

        add(mainPanel);

        //get screen dimensions
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        int screenWidth = screenDimension.width;
        int screenHeight = screenDimension.height;

        //set location of frame to center of screen
        setLocation((screenWidth - getWidth()) / 2, (screenHeight - getHeight()) / 2);

        setVisible(true);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout((new GridLayout(1,3)));
        buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Options"));

        orderButton = new JButton("Order");
        orderButton.addActionListener((ActionEvent ae) -> createOrderReceipt());

        clearButton = new JButton("Clear");
        clearButton.addActionListener((ActionEvent ae) -> clearOrder());

        quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent ae) -> {
            int result = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

    }

    private void clearOrder() {
        thinCrust.setSelected(true);
        sizeComboBox.setSelectedIndex(0);

        BloodyHell.setSelected(false);
        JalapenoEyeballs.setSelected(false);
        MushroomSkulls.setSelected(false);
        GhostPeppers.setSelected(false);
        SpiderLegs.setSelected(false);
        PeperoniJackOLanterns.setSelected(false);

        receiptTextArea.setText("");
    }

    private void createOrderReceipt() {
        String orderReceipt = "===========================================\n";

        String crustAndSize = "";
        if (thinCrust.isSelected()) {
            crustAndSize = "Thin";
        } else if (regularCrust.isSelected()) {
            crustAndSize = "Regular";
        } else if (deepDishCrust.isSelected()) {
            crustAndSize = "Deep Dish";
        }

        crustAndSize += " & ";

        String size = (String) sizeComboBox.getSelectedItem();
        crustAndSize += size;

        double price = 0;
        if (size.equals("Small")) {
            price = SMALL_PRICE;
        } else if (size.equals("Medium")) {
            price = MEDIUM_PRICE;
        } else if (size.equals("Large")) {
            price = LARGE_PRICE;
        } else if (size.equals("Super")) {
            price = SUPER_PRICE;
        }

        orderReceipt += String.format("%-30s $%4.2f\n", crustAndSize, price);

        if (BloodyHell.isSelected()) {
            orderReceipt += String.format("%-30s $%4.2f\n", "Bloody Hell", INGREDIENT_PRICE);
            price += INGREDIENT_PRICE;
        }
        if (JalapenoEyeballs.isSelected()) {
            orderReceipt += String.format("%-30s $%4.2f\n", "Jalapeno Eyeballs", INGREDIENT_PRICE);
            price += INGREDIENT_PRICE;
        }
        if (MushroomSkulls.isSelected()) {
            orderReceipt += String.format("%-30s $%4.2f\n", "Mushroom Skulls", INGREDIENT_PRICE);
            price += INGREDIENT_PRICE;
        }
        if (GhostPeppers.isSelected()) {
            orderReceipt += String.format("%-30s $%4.2f\n", "Ghost Peppers", INGREDIENT_PRICE);
            price += INGREDIENT_PRICE;
        }
        if (SpiderLegs.isSelected()) {
            orderReceipt += String.format("%-30s $%4.2f\n", "Spider Legs", INGREDIENT_PRICE);
            price += INGREDIENT_PRICE;
        }
        if (PeperoniJackOLanterns.isSelected()) {
            orderReceipt += String.format("%-30s $%4.2f\n", "Peperoni Jack-O-Lanterns", INGREDIENT_PRICE);
            price += INGREDIENT_PRICE;
        }

        orderReceipt += "\n\n";

        orderReceipt += String.format("%-30s $%4.2f\n", "Subtotal", price);
        orderReceipt += "-------------------------------------------\n";

        double tax = price * TAX_RATE;
        orderReceipt += String.format("%-30s $%4.2f\n", "Tax", tax);

        double total = price + tax;
        orderReceipt += String.format("%-30s $%4.2f\n", "Total", total);

        orderReceipt += "===========================================\n";

        receiptTextArea.setText(orderReceipt);
    }

    private void createReceiptPanel() {
        receiptPanel = new JPanel();
        receiptTextArea = new JTextArea(23,44);
        receiptTextArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        receiptTextArea.setEditable(false);
        receiptScrollPane = new JScrollPane(receiptTextArea);
        receiptPanel.add(receiptScrollPane);
    }

    private void createToppingsPanel() {
        toppingsPanel = new JPanel();
        toppingsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Toppings"));
        toppingsPanel.setLayout(new GridLayout(3,2));

        BloodyHell = new JCheckBox("Bloody Hell");
        JalapenoEyeballs = new JCheckBox("Jalapeno Eyeballs");
        MushroomSkulls = new JCheckBox("Mushroom Skulls");
        GhostPeppers = new JCheckBox("Ghost Peppers");
        SpiderLegs = new JCheckBox("Spider Legs");
        PeperoniJackOLanterns = new JCheckBox("Peperoni Jack-O-Lanterns");

        toppingsPanel.add(BloodyHell);
        toppingsPanel.add(JalapenoEyeballs);
        toppingsPanel.add(MushroomSkulls);
        toppingsPanel.add(GhostPeppers);
        toppingsPanel.add(SpiderLegs);
        toppingsPanel.add(PeperoniJackOLanterns);
    }

    private void createSizePanel() {
        sizePanel = new JPanel();
        sizePanel.setBorder(new TitledBorder(new EtchedBorder(), "Size"));

        sizeComboBox = new JComboBox();
        sizeComboBox.addItem("Small");
        sizeComboBox.addItem("Medium");
        sizeComboBox.addItem("Large");
        sizeComboBox.addItem("Super");

        sizePanel.add(sizeComboBox);

    }

    private void createCrustPanel() {
        crustPanel = new JPanel();
        crustPanel.setLayout(new GridLayout(1,3));
        crustPanel.setBorder(new TitledBorder(new EtchedBorder(), "Crust"));

        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep Dish");

        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        thinCrust.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(thinCrust);
        group.add(regularCrust);
        group.add(deepDishCrust);
    }


}
