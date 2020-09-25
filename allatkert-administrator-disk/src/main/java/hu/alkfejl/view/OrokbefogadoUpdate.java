package hu.alkfejl.view;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import hu.alkfejl.controller.OrokbefogadoController;
import hu.alkfejl.model.Konyveles;
import hu.alkfejl.model.Orokbefogado;
import hu.alkfejl.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrokbefogadoUpdate extends Stage {
    OrokbefogadoController controller;
    JFXTextField textField;

    public OrokbefogadoUpdate(OrokbefogadoController controller) {
        this.controller = controller;
        construct();


    }
    public void emailBox(JFXTextField texfield1) {
        this.textField = texfield1;
        this.doEmailValidate();

        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        textField.setValidators(valid);
        textField.getValidators().get(0).setMessage("Email is not valid!");
    }
    private void doEmailValidate() {
        // emailValidator = new EmailValidator();
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1) {
                    boolean val = textField.validate();
                }
            }
        });
    }

    private void dialog(Orokbefogado o) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));
        String kulcs = o.getElerhetoseg();
        String regiNev = o.getNev();
        java.sql.Date dateAlap = o.getFelvetelIdopontja();

        JFXTextField nevTF = new JFXTextField();
        nevTF.setText(o.getNev());
        JFXTextField emailTF = new JFXTextField();
        JFXTextField telefonTF = new JFXTextField();
        String [] data = null;
        for(Orokbefogado asd : controller.listAll()) {
            if(asd.getElerhetoseg().equals(o.getElerhetoseg())) {
                data = o.getElerhetoseg().split(";");
                if(data.length==1) {
                    if(data[0].contains("@")) {
                        emailTF.setText(data[0]);
                    } else {
                        telefonTF.setText(data[0]);
                    }
                }else if(data.length==2) {
                    emailTF.setText(data[0]);
                    telefonTF.setText(data[1]);
                }

            }

        }

        emailBox(emailTF);



        gridPane.add(new Text("Nev:"), 0, 0);
        gridPane.add(nevTF, 1, 0);
        gridPane.add(new Text("Email:"), 0, 1);
        gridPane.add(emailTF, 1, 1);
        gridPane.add(new Text("Telefonszám:"), 0, 3);
        gridPane.add(telefonTF, 1, 3);

        Button okButton = new Button("OK");

        okButton.disableProperty().bind(nevTF.textProperty().isEmpty().or(emailTF.textProperty().isEmpty().and(telefonTF.textProperty().isEmpty())));
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {

            if (emailTF.getText().contentEquals("")) {
                if(telefonTF.getText().contentEquals("")) {
                    Utils.showWarning("Emailt vagy telefont meg kell adni");
                    return;
                }

            }

            String eredmeny = null;
            if(emailTF.getText().length()!=0 && telefonTF.getText().length()!=0) {

                eredmeny = emailTF.getText() + ";" + telefonTF.getText();
            }else if(emailTF.getText().length()!=0 && telefonTF.getText().length()==0) {

                eredmeny = emailTF.getText();
            }else if(emailTF.getText().length()==0 && telefonTF.getText().length()!=0) {
                eredmeny = telefonTF.getText();
            }
            java.sql.Date date=null;

            try {
                SimpleDateFormat asd = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();


                date = new java.sql.Date(cal.getTimeInMillis());



            } catch (Exception parseException) {
                parseException.printStackTrace();
            }
            o.setNev(nevTF.getText());
            o.setElerhetoseg(eredmeny);
            o.setFelvetelIdopontja(date);
            if (controller.updateOrokbefogado(o.getNev(),o.getElerhetoseg(),dateAlap,kulcs)) {
                if(!o.getNev().equals(regiNev)) {
                    for(Konyveles k : controller.listAllKonyveles()) {
                        if(k.getObNev().equals(regiNev)) {
                            String obNev = k.getObNev();
                            String allatNev = k.getAllatNev();
                            java.sql.Date mikor = k.getMikor();
                            String leiras = k.getLeiras();
                            String tamTipus = k.getTamogatasTipus();
                            int osszeg = k.getOsszeg();
                            int mennyiseg = k.getMennyiseg();
                            String gyakorisag = k.getTamogatasGyakorisag();
                            controller.deleteKonyveles(k);
                            controller.addKonyveles(new Konyveles(o.getNev(),allatNev,mikor,leiras,tamTipus,osszeg,mennyiseg,gyakorisag));
                        }
                    }
                }


                close();
            } else {
                Utils.showWarning("Nem sikerült a módosítás");
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

        gridPane.add(buttonPane, 0, 4, 2, 1);

        Scene scene = new Scene(gridPane);
        setScene(scene);
        setTitle("Örökbefogadó módosítása");
        show();
    }


    private void construct() {
        JFXTextField elerhetoseg = new JFXTextField();
        Label asd = new Label("Add meg a módosítani kívánt személy elérhetőségét");
        Button ok = new Button("Update");
        ok.setDefaultButton(true);

        ok.setOnAction(e -> {

            String [] data = null;
            for(Orokbefogado o : controller.listAll()) {
                data = o.getElerhetoseg().split(";");
                if(data.length==1) {
                    if(elerhetoseg.getText().equals(data[0])) {

                        for(Orokbefogado asd1: controller.listAll()) {
                            if(asd1.getElerhetoseg().contains(data[0])) {
                                dialog(asd1);
                            }
                        }

                    }
                }else if(data.length==2) {
                    if(elerhetoseg.getText().equals(data[0]) || elerhetoseg.getText().equals(data[1])) {

                        for(Orokbefogado asd2: controller.listAll()) {
                            if(asd2.getElerhetoseg().contains(data[0]) || asd2.getElerhetoseg().contains(data[1])) {
                                dialog(asd2);
                            }
                        }
                    }
                }
            }



        });


        Button cancel = new Button("Cancel");
        cancel.setCancelButton(true);
        cancel.setOnAction(e -> {
            close();
        });
        HBox hbox = new HBox(ok,cancel);
        hbox.setAlignment(Pos.CENTER);
        VBox root = new VBox(asd,elerhetoseg,hbox);
        Scene scene = new Scene(root,400,400);
        setScene(scene);
        setTitle("Örökbefogadó módosítása");
        show();

    }

}

