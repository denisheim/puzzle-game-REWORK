package main.Images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Images {
    protected final BufferedImage img;
    protected final String route = "Images/Pieces/";

    public Images(int num) throws IOException {
        this.img = this.rezise(ImageIO.read(new File("Images/Pieces/image" + num + ".jpg")));
    }

    public Images(BufferedImage img) {
        this.img = img;
    }

    public BufferedImage getImg() {
        return this.img;
    }

    public int getWidth() {
        return this.img.getWidth();
    }

    public int getHeight() {
        return this.img.getHeight();
    }

    private BufferedImage rezise(BufferedImage img) {
        int sizeX = 500;
        int sizeY = 500;
        BufferedImage dimg = new BufferedImage(sizeX, sizeY, img.getType());
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(img, 0, 0, sizeX, sizeY, (ImageObserver)null);
        g2d.dispose();
        return dimg;
    }

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

    public static int getNumImages() {
        File file = null;
        int cont = 0;

        while(true) {
            file = new File("Images/Pieces/image" + cont + ".jpg");
            if (!file.exists()) {
                return cont;
            }

            ++cont;
        }
    }

    public abstract BufferedImage[] splitImage();
}
