

package hu.alkfejl.view;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.sun.source.doctree.TextTree;

import hu.alkfejl.controller.OrokbefogadoController;
import hu.alkfejl.model.Allat;
import hu.alkfejl.model.Konyveles;
import hu.alkfejl.model.Orokbefogado;
import hu.alkfejl.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.Base64;
import java.util.Calendar;


public class KonyvelesAddDialog extends Stage {
    OrokbefogadoController controller;



    public KonyvelesAddDialog(OrokbefogadoController controller) {
        this.controller = controller;
        construct();
    }

    private void construct() {


        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        ObservableList<String> obNevek = FXCollections.observableArrayList();
        for(Orokbefogado o : controller.listAll()) {
            obNevek.add(o.getNev());
        }

        ComboBox<String> obNevTF = new ComboBox<>(obNevek);

        //JFXTextField obNevTF = new JFXTextField();
        ObservableList<String> allatNevek = FXCollections.observableArrayList();
        for(Allat a: controller.listAllAllat()) {
            int count = 0;
            for (Konyveles k: controller.listAllKonyveles()) {
                if(a.getNev().toLowerCase().equals(k.getAllatNev().toLowerCase())) {
                    count=1;
                }
            }
            if(count==0) {
                allatNevek.add(a.getNev());
            }
        }
        ComboBox<String> allatNevTF = new ComboBox<>(allatNevek);


        JFXTextField leirasTF = new JFXTextField();


        Label nevet_adok = new Label("Szeretném elnevezni az állatot");
        CheckBox ujnev = new CheckBox();
        JFXTextField ujnev2 = new JFXTextField();

        JFXTextField tamTipusTF = new JFXTextField();
        Spinner<Integer> tamOsszegTF = new Spinner<>();
        tamOsszegTF.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0,1000000,0,1000
        ));
        Spinner<Integer> tamMennyisegTF = new Spinner<>();
        tamMennyisegTF.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0,100,0,1
        ));
        JFXTextField tamGyakTF = new JFXTextField();
        tamGyakTF.setPromptText("hetente 1");

        gridPane.add(new Text("Nev:"), 0, 0);
        gridPane.add(obNevTF, 1, 0);
        gridPane.add(new Text("Állat neve:"), 0, 1);
        gridPane.add(allatNevTF, 1, 1);
        gridPane.add(new Text("Egyéb:"), 0, 3);
        gridPane.add(leirasTF, 1, 3);
        gridPane.add(new Text("Támogatás típusa:"), 0, 4);
        gridPane.add(tamTipusTF, 1, 4);
        gridPane.add(new Text("Összeg:"), 0, 5);
        gridPane.add(tamOsszegTF, 1, 5);
        gridPane.add(new Text("Mennyiség(kg):"), 0, 6);
        gridPane.add(tamMennyisegTF, 1, 6);
        gridPane.add(new Text("Gyakoriság:"), 0, 7);
        gridPane.add(tamGyakTF, 1, 7);
        gridPane.add(nevet_adok,0,9);
        gridPane.add(ujnev,1,9);
        gridPane.add(ujnev2,1,10);

        ujnev.disableProperty().bind(allatNevTF.valueProperty().isNotNull());
        Button okButton = new Button("OK");


        tamMennyisegTF.disableProperty().bind(tamTipusTF.textProperty().isEqualTo("pénzösszeg"));
        tamOsszegTF.disableProperty().bind(tamTipusTF.textProperty().isEqualTo("eledel"));

        okButton.disableProperty().bind(obNevTF.valueProperty().isNull().or(tamTipusTF.textProperty().isEmpty()).or(tamGyakTF.textProperty().isEmpty()));
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {

            java.sql.Date date=null;

            try {

                Calendar cal = Calendar.getInstance();


                date = new java.sql.Date(cal.getTimeInMillis());



            } catch (Exception parseException) {
                parseException.printStackTrace();
            }
            int countOrokbefogado = 0;
            for(Orokbefogado o : controller.listAll()) {
                if(obNevTF.getValue().toLowerCase().equals(o.getNev().toLowerCase())) {
                    countOrokbefogado += 1;
                }
            }
            if(countOrokbefogado==0) {
                Utils.showWarning("Nincs ilyen nevű örökbefogadó.\nElöször regisztrálni kell!");
                return;
            }

            boolean tortentAtnevezes = false;
            if(ujnev.isSelected() && ujnev2.getText().equals("")) {
                Utils.showWarning("Ha szeretné megadni az állat nevét ki kell tölteni a mezöt!");
            }else if(!ujnev.isSelected() && !ujnev2.getText().equals("")) {
                Utils.showWarning("Nem adhat meg nevet ha nem választotta ki az opciót!\n Meglehet, hogy az állatnak már van neve.");
            }else if(ujnev.isSelected() && !ujnev2.getText().equals("")) {

                for(Allat a : controller.listAllAllat()) {
                    if(a.getNev().equals("")) {
                        tortentAtnevezes = true;
                        controller.updateAllat(ujnev2.getText(),a.getFaj(),a.getFenykep(),a.getSzoveg(),a.getSzulEv(),a.getNev(),a.getFaj(),a.getSzulEv());
                    }
                }
            }
            String [] data = tamTipusTF.getText().split(" ");
            for(int i = 0; i < data.length; i++) {
                if(data[i].contains("összeg") && tamOsszegTF.getValue()==0) {
                    Utils.showWarning("Meg kell adni az összeget");
                    return;
                }else if (data[i].contains("eledel") && tamMennyisegTF.getValue()==0) {

                    Utils.showWarning("Meg kell adni a mennyiséget");
                    return;
                }else if(data[i].contains("összeg") && tamOsszegTF.getValue()!=0 && tortentAtnevezes) {
                    String concat = leirasTF.getText() + "\n elnevezte az állatot";
                    if (controller.addKonyveles(new Konyveles(obNevTF.getValue(), ujnev2.getText(),date,concat,tamTipusTF.getText(),tamOsszegTF.getValue(),0,tamGyakTF.getText()))) {
                        close();
                    } else {
                        Utils.showWarning("Nem sikerült a felvitel");
                        return;
                    }
                }else if(data[i].contains("összeg") && tamOsszegTF.getValue()!=0 && !tortentAtnevezes) {
                    Konyveles k = new Konyveles(obNevTF.getValue(),allatNevTF.getValue(),date,leirasTF.getText(),tamTipusTF.getText(),tamOsszegTF.getValue(),0,tamGyakTF.getText());

                    obNevTF.valueProperty().bindBidirectional(k.obNevProperty());
                    allatNevTF.valueProperty().bindBidirectional(k.allatNevProperty());
                    leirasTF.textProperty().bindBidirectional(k.leirasProperty());
                    tamTipusTF.textProperty().bindBidirectional(k.tamogatasTipusProperty());
                    tamOsszegTF.getValueFactory().valueProperty().bindBidirectional(k.osszegProperty().asObject());
                    tamMennyisegTF.getValueFactory().valueProperty().bindBidirectional(k.mennyisegProperty().asObject());
                    tamGyakTF.textProperty().bindBidirectional(k.tamogatasGyakorisagProperty());
                    if (controller.addKonyveles(k)) {
                        close();
                    } else {
                        Utils.showWarning("Nem sikerült a felvitel");
                        return;
                    }
                }else if (data[i].contains("eledel") && tamMennyisegTF.getValue()!=0 && tortentAtnevezes) {
                    String concat = leirasTF.getText() + "\n elnevezte az állatot";

                    Konyveles k = new Konyveles(obNevTF.getValue(), ujnev2.getText(),date,concat,tamTipusTF.getText(),0,tamMennyisegTF.getValue(),tamGyakTF.getText());
                    obNevTF.valueProperty().bindBidirectional(k.obNevProperty());
                    allatNevTF.valueProperty().bindBidirectional(k.allatNevProperty());
                    leirasTF.textProperty().bindBidirectional(k.leirasProperty());
                    tamTipusTF.textProperty().bindBidirectional(k.tamogatasTipusProperty());
                    tamOsszegTF.getValueFactory().valueProperty().bindBidirectional(k.osszegProperty().asObject());
                    tamMennyisegTF.getValueFactory().valueProperty().bindBidirectional(k.mennyisegProperty().asObject());
                    tamGyakTF.textProperty().bindBidirectional(k.tamogatasGyakorisagProperty());
                    if (controller.addKonyveles(k)) {
                        close();
                    } else {
                        Utils.showWarning("Nem sikerült a felvitel");
                        return;
                    }
                }else if(data[i].contains("eledel") && tamMennyisegTF.getValue()!=0 && !tortentAtnevezes) {
                    if (controller.addKonyveles(new Konyveles(obNevTF.getValue(), allatNevTF.getValue(),date,leirasTF.getText(),tamTipusTF.getText(),tamOsszegTF.getValue(),0,tamGyakTF.getText()))) {
                        close();
                    } else {
                        Utils.showWarning("Nem sikerült a felvitel");
                        return;
                    }
                }else {
                    Utils.showWarning("Valami hiba történt");
                    return;
                }
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

        gridPane.add(buttonPane, 0, 11, 2, 1);

        Scene scene = new Scene(gridPane);
        setScene(scene);
        setTitle("Örökbefogadás");
        show();
    }
}



