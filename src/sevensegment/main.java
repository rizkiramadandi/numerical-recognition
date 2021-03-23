package sevensegment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/*@author Rizki Ramadandi*/
public class main extends JFrame {

    static String res = "";
    static ArrayList<BufferedImage> listImage;
    static int count = 0, total_percent = 0;
    static filter filt = new filter();
    static main frame;
    static JMenuItem item_file_open;
    static JMenu menu_file;
    static JPanel panel_img, panel_step;
    static JLabel img_left, img_right;
    static JButton btn_binarization, btn_reset, btn_res;
    static BufferedImage image_original, image_temp, image_filtered;

    main() {
        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.WHITE);

        panel_step = new JPanel(new GridLayout(1, 3));

        btn_reset = new JButton("Reset");
        btn_binarization = new JButton("Binarization");
        btn_res = new JButton("Result");

        panel_step.add(btn_reset);
        panel_step.add(btn_binarization);
        panel_step.add(btn_res);

        panel_img = new JPanel(new GridLayout(1, 2));

        img_left = new JLabel();
        img_left.setHorizontalAlignment(JLabel.CENTER);
        img_right = new JLabel();
        img_right.setHorizontalAlignment(JLabel.CENTER);

        panel_img.add(img_left);
        panel_img.add(img_right);

        menu_file = new JMenu("File");

        item_file_open = new JMenuItem("Open Image");

        menu_file.add(item_file_open);

        menubar.add(menu_file);

        setLayout(new BorderLayout());

        add(menubar, BorderLayout.NORTH);
        add(panel_img, BorderLayout.CENTER);
        add(panel_step, BorderLayout.SOUTH);

        setSize(1000, 500);
        setTitle("Digital Numeric Recognition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        btnOpenListener listener_open = new btnOpenListener();
        btnBinarizationListener listener_binary = new btnBinarizationListener();
        btnResetListener listener_reset = new btnResetListener();
        btnResultListener listener_result = new btnResultListener();

        btn_reset.addActionListener(listener_reset);
        item_file_open.addActionListener(listener_open);
        btn_binarization.addActionListener(listener_binary);
        btn_res.addActionListener(listener_result);
    }

    public static void main(String[] args) {
        frame = new main();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
    }

    class btnOpenListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
            try {
                chooser.setCurrentDirectory(new File("./test"));
            }catch(Exception e) {
                System.out.println(e);
            }
            chooser.setAcceptAllFileFilterUsed(false);
            int res = chooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = new File(String.valueOf(chooser.getSelectedFile()));
                try {
                    image_original = ImageIO.read(file);
                    image_filtered = image_original;
                    image_temp = null;
                    ImageIcon icon_img = filt.rescaleImage(new ImageIcon(image_original),384,384);
                    img_left.setIcon(icon_img);
                    img_right.setIcon(icon_img);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(rootPane, "Silakan Pilih Gambar");
                }
            } else {
                System.out.println("Image tidak dipilih");
            }
        }

    }

    class btnBinarizationListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            new binarization();
        }

    }

    class btnResultListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                filt.res((image_temp != null) ? image_temp : image_original);
                res = "";
                total_percent = 0;
                count = 0;
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please choose an image");
            }
        }
    }

    class btnResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                image_filtered = image_original;
                image_temp = null;
                ImageIcon icon_img = filt.rescaleImage(new ImageIcon(image_original),384,384);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please choose an image");
            }
        }

    }
}
