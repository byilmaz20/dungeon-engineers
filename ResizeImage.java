import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ResizeImage {
    public static void main(String[] args) {
        // Input and output file paths
        String inputImagePath = "/Users/begumyilmaz/Documents/okul/koç/4.1/comp302/project/projectrepo/a.png"; // Replace with your input file path
        String outputImagePath = "output.png"; // Replace with your output file path
        int scaledWidth = 1200; // Desired width
        int scaledHeight = 900; // Desired height

        try {
            // Read the original image
            BufferedImage originalImage = ImageIO.read(new File(inputImagePath));

            // Resize the image
            BufferedImage resizedImage = resizeImage(originalImage, scaledWidth, scaledHeight);

            // Save the resized image to a new file
            ImageIO.write(resizedImage, "png", new File(outputImagePath));

            System.out.println("Image resized and saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        // Create a new buffered image with desired dimensions
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());

        // Draw the original image to the resized image
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose(); // Release resources

        return resizedImage;
    }
}