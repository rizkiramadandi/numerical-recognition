package sevensegment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import static sevensegment.main.image_filtered;
import static sevensegment.main.image_original;
import static sevensegment.main.image_temp;
import static sevensegment.main.img_right;

/*@author Rizki Ramadandi*/
public class binarization extends JFrame {

    static filter filt = new filter();
    static JButton btn_apply;
    static JSlider slider_threshold;
    static BufferedImage image_temps, image_tempz;

    binarization() {
        
        image_temps = ((image_temp != null) ? image_temp : image_original);
        image_tempz = image_filtered;
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                image_filtered = image_temps;
                image_temp = image_filtered;
                ImageIcon icon_img = filt.rescaleImage(new ImageIcon(image_filtered),384,384);
                img_right.setIcon(icon_img);
            }
        });

        int min = 0;
        int max = 255;

        slider_threshold = new JSlider(JSlider.HORIZONTAL, min, max, 0);

        btn_apply = new JButton("Apply");

        setLayout(new BorderLayout());

        add(slider_threshold, BorderLayout.CENTER);
        add(btn_apply, BorderLayout.SOUTH);

        setSize(400, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        sliderApply change_slider = new sliderApply();
        
        btnApplyListener listener_apply = new btnApplyListener();

        btn_apply.addActionListener(listener_apply);
        slider_threshold.addChangeListener(change_slider);
    }

    class sliderApply implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent ce) {
            if (image_original != null) {
                int val = slider_threshold.getValue();
                image_tempz = image_temps;
                image_filtered = filt.binarization((image_temp != null) ? image_temp : image_original, val);
                image_tempz = image_filtered;
                ImageIcon icon_img = filt.rescaleImage(new ImageIcon(image_filtered),384,384);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please choose an image");
            }
        }

    }
    class btnApplyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (image_original != null) {
                image_filtered = image_tempz;
                image_temp = image_filtered;
                image_temps = image_temp;
                ImageIcon icon_img = filt.rescaleImage(new ImageIcon(image_filtered),384,384);
                img_right.setIcon(icon_img);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please choose an image");
            }
        }

    }
}
