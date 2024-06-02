package main.Images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * the images class serves as an abstract base class for managing images
 * used in the puzzle game. It provides methods for resizing images,
 * checking directory existence, and obtaining the number of images
 */
public abstract class Images {
    protected final BufferedImage img;
    protected final String route = "Images/Pieces/";

    /**
     * constructs an Images object by loading and resizing an image from a file
     */
    public Images(int num) throws IOException {
        this.img = this.rezise(ImageIO.read(new File("Images/Pieces/image" + num + ".jpg")));
    }

    /**
     * constructs an Images object with a given BufferedImage
     */
    public Images(BufferedImage img) {
        this.img = img;
    }

    /**
     * gets the BufferedImage managed by this object
     */
    public BufferedImage getImg() {
        return this.img;
    }

    /**
     * gets the width of the managed image
     */
    public int getWidth() {
        return this.img.getWidth();
    }

    /**
     * gets the height of the managed image
     */
    public int getHeight() {
        return this.img.getHeight();
    }

    /**
     * resizes the given image to 500x500 pixels
     */
    private BufferedImage rezise(BufferedImage img) {
        int sizeX = 500;
        int sizeY = 500;
        BufferedImage dimg = new BufferedImage(sizeX, sizeY, img.getType());
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(img, 0, 0, sizeX, sizeY, (ImageObserver) null);
        g2d.dispose();
        return dimg;
    }

    /**
     * checks if the directory for storing images exists, and creates it if it doesn't
     * also ensure a default image file exists
     */
    public static void existDirec() {
        File file = new File("Images/Pieces/");
        BufferedImage img;
        IOException ex;
        if (!file.exists()) {
            file.mkdirs();
            img = new BufferedImage(500, 500, 5);
            file = new File("Images/Pieces/image0.jpg");

            try {
                ImageIO.write(img, "jpg", file);
            } catch (IOException var4) {
                ex = var4;
                System.out.println(ex.getMessage());
            }
        } else {
            file = new File("Images/Pieces/image0.jpg");
            if (!file.exists()) {
                img = new BufferedImage(500, 500, 5);

                try {
                    ImageIO.write(img, "jpg", file);
                } catch (IOException var3) {
                    ex = var3;
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * gets the number of image files in the directory
     */
    public static int getNumImages() {
        File file = null;
        int cont = 0;

        while (true) {
            file = new File("Images/Pieces/image" + cont + ".jpg");
            if (!file.exists()) {
                return cont;
            }

            ++cont;
        }
    }

    /**
     * abstract method to split the image into pieces
     * this method must be implemented by subclasses
     */
    public abstract BufferedImage[] splitImage();
}
