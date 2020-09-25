package hu.alkfejl.view;

import com.jfoenix.controls.JFXTextField;
import hu.alkfejl.controller.OrokbefogadoController;
import hu.alkfejl.model.Allat;
import hu.alkfejl.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;
import java.util.Calendar;

public class AllatAddDialog extends Stage {
    OrokbefogadoController controller;
    //kepes dolgok
    private File file;
    private FileChooser fileChooser;
    private javafx.scene.image.Image image;
    private javafx.scene.image.ImageView imageView;



    private String encodedString = null;



    public AllatAddDialog(OrokbefogadoController controller) {
        this.controller = controller;
        construct();
    }

    private void construct() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg")
        );
        TextArea asd = new TextArea();

        Button browse = new Button("browse");
        browse.setOnAction(e-> {
            file = fileChooser.showOpenDialog(this);
            if(file!=null) {
             //   asd.setText(file.getAbsolutePath());
             //   this.image = new Image(file.toURI().toString());
             //   this.imageView.setImage(image);
                //    fileContent = FileUtils.readFileToByteArray(file);
               //     encodedString = Base64.getEncoder().encodeToString(filexContent);
                  //  decodedBytes = Base64.getDecoder().decode(encodedString);
                    //image = new Image(file.toURI().toString());


            }
        });
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        JFXTextField nevTF = new JFXTextField();
        JFXTextField fajTF = new JFXTextField();
        TextArea bemutatkozasTF = new TextArea();

        Spinner<Integer> szulevTF = new Spinner<>();
        szulevTF.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1900,Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.YEAR)-5
        ));


        gridPane.add(new Text("Neve:"), 0, 0);
        gridPane.add(nevTF, 1, 0);
        gridPane.add(new Text("Faja:"), 0, 1);
        gridPane.add(fajTF, 1, 1);
        gridPane.add(new Text("Bemutatkozó szöveg:"), 0, 4);
        gridPane.add(bemutatkozasTF, 1, 4);
        gridPane.add(new Text("Születési éve:"), 0, 5);
        gridPane.add(szulevTF, 1, 5);
        gridPane.add(new Text("képe"),0,6);
        gridPane.add(asd,1,6);
        gridPane.add(browse,2,6);


        Button okButton = new Button("OK");

        okButton.disableProperty().bind(fajTF.textProperty().isEmpty());
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if(file!=null) {
                this.encodedString = Utils.encodeBase64(file);

            }
            Allat a = new Allat(nevTF.getText(),fajTF.getText(),encodedString,bemutatkozasTF.getText(),szulevTF.getValue());
            nevTF.textProperty().bindBidirectional(a.nevProperty());
            fajTF.textProperty().bindBidirectional(a.fajProperty());
            bemutatkozasTF.textProperty().bindBidirectional(a.szovegProperty());
            szulevTF.getValueFactory().valueProperty().bindBidirectional(a.szulEvProperty().asObject());

            if (controller.addAllat(a)) {
                close();
            } else {
                Utils.showWarning("Nem sikerült a felvitel");
                return;
            }


        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(e -> {
            close();
        });

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(okButton, cancelButton);

        gridPane.add(buttonPane, 0, 7, 2, 1);

        Scene scene = new Scene(gridPane);
        setScene(scene);
        setTitle("Állat felvetele");
        show();
    }

}
