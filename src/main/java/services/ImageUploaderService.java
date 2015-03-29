package services;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.ws.rs.core.MediaType;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;
import java.util.UUID;

/**
 * Created by rafa on 29/03/15.
 */
public class ImageUploaderService {

    public static final String IMAGES_LOCATION = "../../src/main/webapp/uploads/";

    public String uploadImage(InputStream image, String format){
        try {
            BufferedImage bi = ImageIO.read(image);
            String imageName = UUID.randomUUID().toString();

            File outFile = new File( IMAGES_LOCATION + imageName );
            ImageIO.write(bi, format, outFile);
            return imageName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
        }
    }
}
