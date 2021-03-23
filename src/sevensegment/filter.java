package sevensegment;

import java.awt.BorderLayout;
import static sevensegment.main.res;
import static sevensegment.main.total_percent;
import static sevensegment.main.count;
import static sevensegment.main.listImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*@author Rizki Ramadandi*/
public class filter {

    static BufferedImage image;
    static Graphics2D g2d;

    public static void res(BufferedImage img) {

        image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        boolean visited[][] = new boolean[img.getWidth()][img.getHeight()];
        g2d = image.createGraphics();
        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        int x = 0, y = 0;
        listImage = new ArrayList();
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color rgb = new Color(img.getRGB(j, i));
                int black = rgb.getRed();
                if (black == 0 && !visited[j][i]) {
                    floodfill(j, i, black, img, visited);
                    visited[j][i] = true;
                }
            }
        }
        JFrame frame = new JFrame("Detected");
        JPanel panel = new JPanel();
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(new Dimension(600,160));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.getHorizontalScrollBar().setUnitIncrement(24);
        for (BufferedImage image : listImage) {
            panel.add(new JLabel(rescaleImage(new ImageIcon(image),60,105)));
        }
        frame.add(scroll, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(null, res + "\n" + ((double) total_percent / count) + "%");
    }

    public static void floodfill(int i, int j, int col, BufferedImage img, boolean visited[][]) {
        if (i < 0 || j < 0 || i > img.getWidth() || j > img.getHeight() || visited[i][j] || new Color(img.getRGB(i, j)).getRed() != 0) {
            return;
        } else {
            Queue<Point> q = new LinkedList<Point>();
            ArrayList<Point> list = new ArrayList<Point>();
            q.add(new Point(i, j));
            int width = 1, height = 1;
            ArrayList<Integer> x_yes = new ArrayList<Integer>();
            ArrayList<Integer> y_yes = new ArrayList<Integer>();
            int top = img.getHeight(), left = img.getWidth();
            while (!q.isEmpty()) {
                Point p = q.remove();
                if (p.x >= 0 && p.x < img.getWidth() && p.y >= 0 && p.y < img.getHeight() && !visited[p.x][p.y] && new Color(img.getRGB(p.x, p.y)).getRed() == 0) {
                    if (!x_yes.contains(p.x)) {
                        width++;
                        x_yes.add(p.x);
                    }
                    if (!y_yes.contains(p.y)) {
                        height++;
                        y_yes.add(p.y);
                    }
                    if (top > p.y) {
                        top = p.y;
                    }
                    if (left > p.x) {
                        left = p.x;
                    }
                    list.add(p);
                    visited[p.x][p.y] = true;
                    g2d.setColor(new Color(0, 0, 0));
                    g2d.drawLine(p.x, p.y, p.x, p.y);
                    q.add(new Point(p.x + 1, p.y + 1));
                    q.add(new Point(p.x + 1, p.y - 1));
                    q.add(new Point(p.x - 1, p.y - 1));
                    q.add(new Point(p.x - 1, p.y + 1));
                    q.add(new Point(p.x + 1, p.y));
                    q.add(new Point(p.x, p.y + 1));
                    q.add(new Point(p.x - 1, p.y));
                    q.add(new Point(p.x, p.y - 1));
                }
            }
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            g2d = image.createGraphics();
            g2d.setColor(new Color(255, 255, 255));
            g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
            for (int k = 0; k < list.size(); k++) {
                g2d.setColor(new Color(0, 0, 0));
                g2d.drawLine(list.get(k).x - left, list.get(k).y - top, list.get(k).x - left, list.get(k).y - top);
            }
            new image(image);
        }
    }

    public static int clipping(int col) {
        if (col > 255) {
            return 255;
        } else if (col < 0) {
            return 0;
        } else {
            return col;
        }
    }

    public static ImageIcon rescaleImage(ImageIcon image, int w, int h) {

        int max_image_width = w;
        int max_image_height = h;

        if (image.getIconWidth() < max_image_width) {
            Image imgb = image.getImage();
            imgb = imgb.getScaledInstance(max_image_width, image.getIconHeight() * max_image_width / image.getIconWidth(), Image.SCALE_SMOOTH);
            image = new ImageIcon(imgb);
        }
        if (image.getIconHeight() < max_image_height) {
            Image imgb = image.getImage();
            imgb = imgb.getScaledInstance(image.getIconWidth() * max_image_height / image.getIconHeight(), max_image_height, Image.SCALE_SMOOTH);
            image = new ImageIcon(imgb);
        }

        if (image.getIconWidth() > max_image_width) {
            Image imgb = image.getImage();
            imgb = imgb.getScaledInstance(max_image_width, image.getIconHeight() * max_image_width / image.getIconWidth(), Image.SCALE_SMOOTH);
            image = new ImageIcon(imgb);
        }
        if (image.getIconHeight() > max_image_height) {
            Image imgb = image.getImage();
            imgb = imgb.getScaledInstance(image.getIconWidth() * max_image_height / image.getIconHeight(), max_image_height, Image.SCALE_SMOOTH);
            image = new ImageIcon(imgb);
        }
        return image;
    }

    public static BufferedImage binarization(BufferedImage img, int val) {
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color rgb = new Color(img.getRGB(i, j));
                int gray = (rgb.getRed() + rgb.getGreen() + rgb.getBlue()) / 3;
                if (gray > val) {
                    gray = 255;
                } else {
                    gray = 0;
                }
                new_img.setRGB(i, j, new Color(gray, gray, gray, new Color(img.getRGB(i, j), true).getAlpha()).getRGB());
            }
        }

        return new_img;
    }

}
