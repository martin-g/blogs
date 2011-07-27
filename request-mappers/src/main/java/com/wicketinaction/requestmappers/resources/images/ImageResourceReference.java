package com.wicketinaction.requestmappers.resources.images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.StringValue;

/**
 * A resource reference which serves images by their name.
 */
public class ImageResourceReference extends ResourceReference {

    public ImageResourceReference() {
        super(ImageResourceReference.class, "imagesDemo");
    }

    @Override
    public IResource getResource() {
        return new ImageResource();
    }

    /**
     * A resource which shows how to return back the image as bytes.
     * For the demo it generates the image on the fly but in real life the
     * image can be read from DB, file system, Internet, ...
     */
    private static class ImageResource extends DynamicImageResource {

        @Override
        protected byte[] getImageData(Attributes attributes) {

            PageParameters parameters = attributes.getParameters();
            StringValue name = parameters.get("name");
            
            byte[] imageBytes = null;
            
            if (name.isEmpty() == false) {
                imageBytes = getImageAsBytes(name.toString());

            }
            return imageBytes;
        }
        
        /**
         * Generates an image with a label.
         * For real life application this method will read the image bytes from 
         * external source.
         * 
         * @param label
         *      the image text to render
         * @return
         */
        private byte[] getImageAsBytes(String label)
        {
            BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) image.getGraphics();
            g.setColor(Color.BLACK);
            g.setBackground(Color.WHITE);
            g.clearRect(0, 0, image.getWidth(), image.getHeight());
            g.setFont(new Font("SansSerif", Font.PLAIN, 48));
            g.drawString(label, 50, 50);
            g.dispose();
            
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = writers.next();
            if (writer == null) {
                throw new RuntimeException("JPG not supported?!");
            }

            final ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] imageBytes = null;
            try {

                ImageOutputStream imageOut = ImageIO.createImageOutputStream(out);
                writer.setOutput(imageOut);
                writer.write(image);
                imageOut.close();
                imageBytes = out.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageBytes;
        }
        
        // Needed by ResourceMapper to be able to match the request Url with
        // the mounted ResourceReference
        @Override
        public boolean equals(Object that) {
            return that instanceof ImageResource;
        }
    }
}
