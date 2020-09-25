package hu.alkfejl.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Utils {
    public static void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
    public static String encodeBase64(File file) {
        try {
            return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Image decodeBase64(String string) {
        if (string != null) {
            try {
                byte[] arr = Base64.getDecoder().decode(string);
                File out = new File("img.png");
                FileUtils.writeByteArrayToFile(out, arr);
                return new Image(out.toURI().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
