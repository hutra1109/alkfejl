package hu.alkfejl.view;

import com.jfoenix.controls.JFXTextField;
import hu.alkfejl.controller.OrokbefogadoController;
import hu.alkfejl.model.Allat;
import hu.alkfejl.model.Konyveles;
import hu.alkfejl.model.Orokbefogado;
import hu.alkfejl.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;

public class AllatUpdate extends Stage {
    OrokbefogadoController controller;
    public static File file;
    private FileChooser fileChooser;
    private javafx.scene.image.Image image;
    private javafx.scene.image.ImageView imageView;
    public static FileInputStream fis;
    //eddig
    public static byte[] fileContent;
    public static String encodedString=null;




    public AllatUpdate(OrokbefogadoController controller) {
        this.controller = controller;
        construct();
    }
    private void dialog(Allat a) {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg")
        );
        TextArea asd = new TextArea();
        asd.setText(a.getFenykep());

        Button browse = new Button("browse");
        browse.setOnAction(e-> {
            file = fileChooser.showOpenDialog(this);
            if(file!=null) {
                asd.setText(file.getAbsolutePath());
                try {
                    fileContent = FileUtils.readFileToByteArray(file);
                    encodedString = Base64.getEncoder().encodeToString(fileContent);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        String kulcs1 = a.getNev();
        String kulcs2 = a.getFaj();
        int kulcs3 = a.getSzulEv();

        JFXTextField nevTF = new JFXTextField();
        nevTF.setText(a.getNev());
        JFXTextField fajTF = new JFXTextField();
        fajTF.setText(a.getFaj());
        TextArea bemutatkozasTF = new TextArea();
        bemutatkozasTF.setText(a.getSzoveg());
        Spinner<Integer> szulevTF = new Spinner<>();
        szulevTF.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1900,Calendar.getInstance().get(Calendar.YEAR),a.getSzulEv()
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

            a.setNev(nevTF.getText());
            a.setFaj(fajTF.getText());
            a.setFenykep(encodedString);
            a.setSzoveg(bemutatkozasTF.getText());
            a.setSzulEv(szulevTF.getValue());

            if (controller.updateAllat(a.getNev(),a.getFaj(),a.getFenykep(),a.getSzoveg(),a.getSzulEv(),kulcs1,kulcs2,kulcs3)) {


                close();
            } else {
                Utils.showWarning("Nem sikerült a felvitel");
                System.out.println(a.getNev());
                System.out.println(a.getFaj());
                System.out.println(a.getSzoveg());
                System.out.println(a.getSzulEv());
                System.out.println(kulcs1);
                System.out.println(kulcs2);
                System.out.println(kulcs3);
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


    private void construct() {
        JFXTextField nev = new JFXTextField();
        JFXTextField faj = new JFXTextField();
        JFXTextField szulEv = new JFXTextField();
        Label asd = new Label("Add meg a módosítani kívánt állat adatait");
        Button ok = new Button("Update");
        ok.setDefaultButton(true);

        ok.setOnAction(e -> {
            for(Allat a : controller.listAllAllat()) {
                    if(nev.getText().equals(a.getNev()) && faj.getText().equals(a.getFaj()) && szulEv.getText().equals(String.valueOf(a.getSzulEv()))) {
                        System.out.println(String.valueOf(a.getSzulEv()));
                        System.out.println(a.getFaj());
                        System.out.println(a.getNev());
                        dialog(a);
                    }
                }
        });

        Button cancel = new Button("Cancel");
        cancel.setCancelButton(true);
        cancel.setOnAction(e -> close());
        HBox hbox = new HBox(ok,cancel);
        hbox.setAlignment(Pos.CENTER);
        Label label = new Label("Neve");
        Label label2 = new Label("Faja");
        Label label3 = new Label("Születési éve");
        VBox box1 = new VBox(asd,label,nev);
        VBox box2 = new VBox(label2,faj);
        VBox box3 = new VBox(label3,szulEv);
        VBox root = new VBox(box1,box2,box3,hbox);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root,400,400);
        setScene(scene);
        setTitle("Állat módosítása");
        show();

    }
    }


