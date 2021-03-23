package sevensegment;

import static sevensegment.main.res;
import static sevensegment.main.count;
import static sevensegment.main.total_percent;
import static sevensegment.main.listImage;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*@author Rizki Ramadandi*/
public class image extends JFrame{

    static filter filt = new filter();

    static void setZero(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
    }

    void getModel(BufferedImage model[]) {
        for (int i = 0; i < model.length; i++) {
//            for (int j = 0; j < 2; j++) {
                try {
                    model[i] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/model_numeric/" + i + "_0.jpg"));
                } catch (Exception e) {

//                }
            }

        }
    }

    image(BufferedImage img) {

        Image image = img.getScaledInstance(60, 105, Image.SCALE_SMOOTH);

        BufferedImage new_img = new BufferedImage(60, 105, BufferedImage.TYPE_INT_ARGB);

        new_img = filt.binarization(new_img, 128);

        Graphics2D g2d = new_img.createGraphics();
        g2d.drawImage(image, 0, 0, null);

        int percent[] = new int[10];

//        int density_1 = 0, density_2 = 0, density_3 = 0, density_4 = 0;
//        int count = 0;
        
        int total_pixel = 60 * 105;

        setZero(percent);
        
//        for (int i = 0; i < img.getHeight()/2; i++) {
//            for (int j = 0; j < img.getWidth()/2; j++) {
//                if(new Color(img.getRGB(j, i)).getRed()==0) {
//                    density_1++;
//                }
//                count++;
//            }
//        }
        
//        System.out.println((double)density_1/count);

//        BufferedImage models[][] = new BufferedImage[10][2];
        
        BufferedImage model[] = new BufferedImage[10];

        getModel(model);

        for (int i = 0; i < model.length; i++) {
//            for (int j = 0; j < model[i].length; j++) {
                for (int k = 0; k < new_img.getWidth(); k++) {
                    for (int l = 0; l < new_img.getHeight(); l++) {
                        Color rgb_img = new Color(new_img.getRGB(k, l));
                        Color rgb_model = new Color(model[i].getRGB(k, l));

                        if (rgb_img.getRed() == rgb_model.getRed()) {
                            percent[i]++;
                        }
                    }
//                }
            }
//            percent[i] /= model[i].length;
        }

        int best = 0;

        for (int i = 0; i < percent.length; i++) {
            System.out.println(i+" :" +(double) percent[i] / total_pixel * 100+"%");
            if (percent[best] < percent[i]) {
                best = i;
            }
        }
        System.out.println("");

        res += best;
        count++;
        total_percent += (double) percent[best] / total_pixel * 100;
        
        listImage.add(new_img);

//        setVisible(true);
//        setSize(img.getWidth()*4,img.getHeight()*2);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLocationRelativeTo(null);
//        add(new JLabel(new ImageIcon(img)));
        
    }
}
