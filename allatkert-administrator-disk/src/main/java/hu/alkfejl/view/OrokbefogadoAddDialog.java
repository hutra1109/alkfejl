package hu.alkfejl.view;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.sun.source.doctree.TextTree;
import hu.alkfejl.controller.OrokbefogadoController;
import hu.alkfejl.model.Orokbefogado;
import hu.alkfejl.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.validator.EmailValidator;


import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrokbefogadoAddDialog extends Stage {
    OrokbefogadoController controller;

    private JFXTextField textField;

    public OrokbefogadoAddDialog(OrokbefogadoController controller) {
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
    private void construct() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        JFXTextField nevTF = new JFXTextField();
        JFXTextField emailTF = new JFXTextField();
        emailBox(emailTF);
        JFXTextField telefonTF = new JFXTextField();
        telefonTF.setPromptText("06309384272");
        JFXTextField usernameTF = new JFXTextField();
        PasswordField passwordTF = new PasswordField();

        gridPane.add(new Text("Nev:"), 0, 0);
        gridPane.add(nevTF, 1, 0);
        gridPane.add(new Text("Email:"), 0, 1);
        gridPane.add(emailTF, 1, 1);
        gridPane.add(new Text("Telefonszám:"), 0, 3);
        gridPane.add(telefonTF, 1, 3);
        gridPane.add(new Text("Username:"),0,4);
        gridPane.add(usernameTF,1,4);
        gridPane.add(new Text("Password:"),0,5);
        gridPane.add(passwordTF,1,5);

        Button okButton = new Button("OK");

        okButton.disableProperty().bind(nevTF.textProperty().isEmpty().or(emailTF.textProperty().isEmpty().and(telefonTF.textProperty().isEmpty())).or(usernameTF.textProperty().isEmpty().or(passwordTF.textProperty().isEmpty())));
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})");
            Matcher m = p.matcher(passwordTF.getText());
            if(!m.matches()) {
                Utils.showWarning("A jelszónak tartalmaznia kell legalább egy kis- és nagybetűt,egy speciális karaktert,számot és 6-15 között kell lennie!");
                return;
            }
            Pattern p2 = Pattern.compile("[0-9]{11}");
            Matcher m2 = p2.matcher(telefonTF.getText());
            if(!(m2.find()&&m2.group().equals(telefonTF.getText())) && !telefonTF.getText().equals("")) {
                Utils.showWarning("A telefonszám nem valid");
                return;
            }
            for(Orokbefogado o: controller.listAllUser()) {
                if(o.getUsername().equals(usernameTF.getText())) {
                    Utils.showWarning("Már létezik ilyen nevű felhasználó");
                }
            }

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
            Orokbefogado o = new Orokbefogado(nevTF.getText(), eredmeny,date,usernameTF.getText(),passwordTF.getText());
            nevTF.textProperty().bindBidirectional(o.nevProperty());
            emailTF.textProperty().bindBidirectional(o.elerhetosegProperty());
            telefonTF.textProperty().bindBidirectional(o.elerhetosegProperty());
            usernameTF.textProperty().bindBidirectional(o.usernameProperty());
            passwordTF.textProperty().bindBidirectional(o.passwordProperty());


            if (controller.addOrokbefogado(o)) {
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

        gridPane.add(buttonPane, 0, 6, 2, 1);

        Scene scene = new Scene(gridPane);
        setScene(scene);
        setTitle("Örökbefogadó felvetele");
        show();
    }
}
