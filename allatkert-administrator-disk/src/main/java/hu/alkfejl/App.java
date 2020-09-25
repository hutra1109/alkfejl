package hu.alkfejl;

import com.jfoenix.controls.JFXTextField;
import hu.alkfejl.controller.OrokbefogadoController;
import hu.alkfejl.model.Allat;
import hu.alkfejl.model.Konyveles;
import hu.alkfejl.model.Orokbefogado;
import hu.alkfejl.utils.Utils;
import hu.alkfejl.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.util.Base64;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JavaFX App
 */
public class App extends Application {

    private OrokbefogadoController controller = new OrokbefogadoController();


    //innentöl kezdpdik
    TextField searchField = new TextField();
    TextField searchFieldForAdmin = new TextField();

    final ObservableList<Konyveles> data = FXCollections.observableArrayList();
    final ObservableList<Konyveles> dataForUser = FXCollections.observableArrayList();
    final ObservableList<Allat> data2 = FXCollections.observableArrayList();
    final ObservableList<Allat> data3 = FXCollections.observableArrayList();
    //eddig tart





    private TableView<Orokbefogado> table = new TableView<>();
    private TableView<Orokbefogado> tableForAdmin = new TableView<>();

    private TableView<Konyveles> konyvelesTable = new TableView<>();
    private TableView<Konyveles> konyvelesTableForAdmin = new TableView<>();
    private TableView<Allat> allatTable = new TableView<>();
    private TableView<Allat> allatTableForAdmin = new TableView<>();
    private Scene orokbefogadoScene;
    private Scene orokbefogadoSceneForAdmin;
    private Scene konyvelesScene;
    private Scene konyvelesSceneForAdmin;
    private Scene allatScene;
    private Scene allatSceneForAdmin;
    private Scene newscene;
    private Scene loginScene;
    private Scene adminScene;
    private TextField username;
    private PasswordField password;



    @Override
    public void start(Stage stage) {


        //innen kezdodik
        for(Konyveles k : controller.listAllKonyveles()) {
            data.add(k);
            dataForUser.add(k);
        }

        for(Allat a: controller.listAllAllat()) {
            for(Konyveles k: controller.listAllKonyveles()) {
                if(a.getNev().toLowerCase().equals(k.getAllatNev().toLowerCase())) {
                    data2.add(a);
                }
            }

        }

        for(Allat a: controller.listAllAllat()) {
            int count = 0;
            for (Konyveles k: controller.listAllKonyveles()) {
                if(a.getNev().toLowerCase().equals(k.getAllatNev().toLowerCase())) {
                    count=1;
                }
            }
            if(count==0) {
                data3.add(a);
            }
        }


        searchField.setPromptText("Search");
        searchField.setMaxWidth(200);
        searchField.setPadding(new Insets(10));
        searchField.getStyleClass().add("my");
        searchFieldForAdmin.setPromptText("Search");
        searchFieldForAdmin.setMaxWidth(200);
        searchFieldForAdmin.setPadding(new Insets(10));
        searchFieldForAdmin.getStyleClass().add("my");
        VBox fields = new VBox();
        VBox fieldsForAdmin = new VBox();

        BorderPane layout = new BorderPane();
        BorderPane layoutForAdmin = new BorderPane();




        FilteredList<Konyveles> filteredData = new FilteredList<>(dataForUser, e-> true);
        FilteredList<Konyveles> filteredDataForAdmin = new FilteredList<>(data,e->true);
        FilteredList<Allat> filteredData2 = new FilteredList<>(data2, e-> true);
        FilteredList<Allat> filteredData3 = new FilteredList<>(data3,e-> true);

        Button orokbefogadottak = new Button("Fogadott");
        orokbefogadottak.setOnAction(e-> {

            allatTable.setItems(filteredData2);
        });
        Button orokbefogadottakForAdmin = new Button("Fogadott");
        orokbefogadottakForAdmin.setOnAction(e->{
            allatTableForAdmin.setItems(filteredData2);
        });
        Button nemorokbefogadottak = new Button("Nincs örökbefogadva");
        nemorokbefogadottak.setOnAction(e-> {
            allatTable.setItems(filteredData3);
        });
        Button nemorokbefogadottakForAdmin = new Button("Nincs örökbefogadva");
        nemorokbefogadottakForAdmin.setOnAction(e-> {
            allatTableForAdmin.setItems(filteredData3);
        });

        searchField.setOnKeyPressed(e -> searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Konyveles>) konvyveles -> {
                if(newValue==null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(konvyveles.getObNev().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if(konvyveles.getAllatNev().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if(konvyveles.getMikor().toString().toLowerCase().equals(lowerCaseFilter)) {

                    return true;
                }else if(konvyveles.getTamogatasTipus().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            SortedList<Konyveles> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(konyvelesTable.comparatorProperty());
            konyvelesTable.setItems(sortedData);
        }));


        searchFieldForAdmin.setOnKeyPressed(e -> searchFieldForAdmin.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredDataForAdmin.setPredicate((Predicate<? super Konyveles>) konvyveles -> {
                if(newValue==null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(konvyveles.getObNev().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if(konvyveles.getAllatNev().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if(konvyveles.getMikor().toString().toLowerCase().equals(lowerCaseFilter)) {

                    return true;
                }else if(konvyveles.getTamogatasTipus().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            SortedList<Konyveles> sortedData = new SortedList<>(filteredDataForAdmin);
            sortedData.comparatorProperty().bind(konyvelesTableForAdmin.comparatorProperty());
            konyvelesTableForAdmin.setItems(sortedData);
        }));


        ScrollPane sp = new ScrollPane(konyvelesTable);
        ScrollPane spForAdmin = new ScrollPane(konyvelesTableForAdmin);
        sp.setPrefSize(600,200);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setHmax(3);
        sp.setHvalue(0);
        sp.setDisable(false);
        spForAdmin.setPrefSize(600,200);
        spForAdmin.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spForAdmin.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spForAdmin.setFitToHeight(true);
        spForAdmin.setHmax(3);
        spForAdmin.setHvalue(0);
        spForAdmin.setDisable(false);
        layout.setRight(sp);
        layoutForAdmin.setRight(spForAdmin);
        Label label = new Label("Itt tudsz keresni az örökbefogadó neve,\n állat neve, támogatás típusa\nValamint az örökbefogadás ideje szerint.");
        Label labelForAdmin = new Label("Itt tudsz keresni az örökbefogadó neve,\n állat neve, támogatás típusa\nValamint az örökbefogadás ideje szerint.");
        label.getStyleClass().add("my");
        labelForAdmin.getStyleClass().add("my");



        BorderPane.setMargin(sp,new Insets(0,10,10,10));
        BorderPane.setMargin(spForAdmin,new Insets(0,10,10,10));

        //eddig tart
        constructTable();
        constructKonyvelesTable();
        constructAllatTable();

        Button delete = new Button("Delete");
        delete.setOnAction(event -> {
            Orokbefogado o = tableForAdmin.getSelectionModel().getSelectedItem();
            deleteOrokbefogado(o);
            tableForAdmin.setItems(FXCollections.observableArrayList(controller.listAll()));
            for(Konyveles k: controller.listAllKonyveles()) {
                if(k.getObNev().equals(o.getNev())) {
                    controller.deleteKonyveles(k);
                }
            }
            konyvelesTableForAdmin.setItems(FXCollections.observableArrayList(controller.listAllKonyveles()));

        });
        Button delete2 = new Button("Delete");
        delete2.setOnAction(event -> {
            Allat a = allatTableForAdmin.getSelectionModel().getSelectedItem();
            deleteAllat(a);
            allatTableForAdmin.setItems(FXCollections.observableArrayList(controller.listAllAllat()));
            for(Konyveles k : controller.listAllKonyveles()) {
                if(k.getAllatNev().equals(a.getNev())) {
                    controller.deleteKonyveles(k);
                }
            }
            konyvelesTableForAdmin.setItems(FXCollections.observableArrayList(controller.listAllKonyveles()));


        });
        Button delete3 = new Button("Delete");
        delete3.setOnAction(event -> {
            Konyveles k = konyvelesTableForAdmin.getSelectionModel().getSelectedItem();
            deleteKonyveles(k);
            konyvelesTableForAdmin.setItems(FXCollections.observableArrayList(controller.listAllKonyveles()));
        });
        Button update = new Button("Update");
        update.setOnAction(event -> {
            new OrokbefogadoUpdate(controller);


        });
        Button update2 = new Button("Update");
        update2.setOnAction(event -> {
            new AllatUpdate(controller);
        });

        Button refresh = new Button("Refresh");
        refresh.setOnAction(event -> table.setItems(FXCollections.observableArrayList(controller.listAll())));

        Button refreshForAdmin = new Button("Refresh");
        refreshForAdmin.setOnAction(e-> tableForAdmin.setItems(FXCollections.observableArrayList(controller.listAll())));

        Button refresh2 = new Button("Refresh");
        refresh2.setOnAction(event -> konyvelesTable.setItems(FXCollections.observableArrayList(controller.listAllKonyveles())));

        Button refresh2ForAdmin = new Button("Refresh");
        refresh2ForAdmin.setOnAction(e-> konyvelesTableForAdmin.setItems(FXCollections.observableArrayList(controller.listAllKonyveles())));

        Button refresh3 = new Button("Refresh");
        refresh3.setOnAction(event -> allatTable.setItems(FXCollections.observableArrayList(controller.listAllAllat())));

        Button refresh3ForAdmin = new Button("Refresh");
        refresh3ForAdmin.setOnAction(event -> allatTableForAdmin.setItems(FXCollections.observableArrayList(controller.listAllAllat())));

        HBox gombok = new HBox(refresh);
        HBox gombokForAdmin = new HBox(refreshForAdmin,delete,update);
        HBox gombok2 = new HBox(refresh3,orokbefogadottak,nemorokbefogadottak);
        HBox gombok2ForAdmin = new HBox(refresh3ForAdmin,delete2,update2,orokbefogadottakForAdmin,nemorokbefogadottakForAdmin);
        HBox gombok3 = new HBox(refresh2);
        HBox gombok3ForAdmin = new HBox(refresh2ForAdmin,delete3);
        fields.getChildren().addAll(label,searchField,gombok3);
        fieldsForAdmin.getChildren().addAll(labelForAdmin,searchFieldForAdmin,gombok3ForAdmin);

        layout.setLeft(fields);
        layoutForAdmin.setLeft(fieldsForAdmin);

        gombok.setSpacing(5);
        gombok2.setSpacing(5);
        gombok3.setSpacing(5);
        gombok.setPadding(new Insets(10));
        gombok2.setPadding(new Insets(10));
        gombok3.setPadding(new Insets(10));
        gombokForAdmin.setSpacing(5);
        gombok2ForAdmin.setSpacing(5);
        gombok3ForAdmin.setSpacing(5);
        gombokForAdmin.setPadding(new Insets(10));
        gombok2ForAdmin.setPadding(new Insets(10));
        gombok3ForAdmin.setPadding(new Insets(10));


        VBox root = new VBox(createMenuBar(stage), table, gombok);
        VBox rootForAdmin = new VBox(createMenuBarForAdmin(stage),tableForAdmin,gombokForAdmin);
        rootForAdmin.setSpacing(10);
        root.setSpacing(10);
        VBox root2 = new VBox(createMenuBar(stage), konyvelesTable);
        VBox root2ForAdmin = new VBox(createMenuBarForAdmin(stage),konyvelesTableForAdmin);
        root2.setSpacing(10);
        root2ForAdmin.setSpacing(10);



        orokbefogadoScene = new Scene(root, 400, 400);
        orokbefogadoSceneForAdmin = new Scene(rootForAdmin,400,400);
        orokbefogadoScene.getStylesheets().add("css/style.css");
        orokbefogadoSceneForAdmin.getStylesheets().add("css/style.css");
        konyvelesScene = new Scene(root2, 800, 400);
        konyvelesSceneForAdmin = new Scene(root2ForAdmin,800,400);
        konyvelesScene.getStylesheets().add("css/style.css");
        konyvelesSceneForAdmin.getStylesheets().add("css/style.css");

        Button kepBetolt = new Button("Kép betoltése");
        kepBetolt.setOnAction(e-> {
            Allat allat = allatTable.getSelectionModel().getSelectedItem();
            byte[] decodedbítes = Base64.getDecoder().decode(allat.getFenykep());
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                FileUtils.writeByteArrayToFile(new File("img"),decodedbítes);
                System.out.println(allat.getFenykep());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });

        VBox root3 = new VBox(createMenuBar(stage),allatTable,gombok2);
        VBox root3ForAdmin = new VBox(createMenuBarForAdmin(stage),allatTableForAdmin,gombok2ForAdmin);
        HBox root3seged = new HBox(root3,kepBetolt);
        HBox root3segedForAdmin = new HBox(root3ForAdmin,kepBetolt);
        root.setSpacing(10);
        allatScene = new Scene(root3seged,700,500);
        allatScene.getStylesheets().add("css/style.css");
        allatSceneForAdmin = new Scene(root3segedForAdmin,700,500);
        allatSceneForAdmin.getStylesheets().add("css/style.css");
        newscene = new Scene(layout,900,400);
        adminScene = new Scene(layoutForAdmin,900,400);

        //login
        username = new TextField();
        password = new PasswordField();
        GridPane gp = new GridPane();
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);
        gp.add(new Text("Username:"),0,0);
        gp.add(username,1,0);
        gp.add(new Text("Password:"),0,1);
        gp.add(password,1,1);
        Button login = new Button("Log in");
        Button regist = new Button("Create Account");
        login.setOnAction(e-> {

            if(password.getText().equals("admin") && username.getText().equals("admin")) {
                stage.setScene(adminScene);
                return;
            }
            int count=0;
            for(Orokbefogado o: controller.listAllUser()) {
                if(o.getPassword().equals(password.getText()) && o.getUsername().equals(username.getText())) {
                    count=1;
                    stage.setScene(newscene);
                }

            }
            if(count==0) {
                Utils.showWarning("Nincs ilyen nevü felhasználó, elöször regisztrálj!");
            }

        });
        password.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.ENTER) {
                if(password.getText().equals("admin") && username.getText().equals("admin")) {
                    stage.setScene(adminScene);
                    return;
                }
                int count = 0;
                for(Orokbefogado o: controller.listAllUser()) {

                    if(o.getPassword().equals(password.getText()) && o.getUsername().equals(username.getText())) {
                        count = 1;
                        stage.setScene(newscene);
                    }
                    if(count==0){
                        Utils.showWarning("Nincs ilyen nevü felhasználó, elöször regisztrálj!");
                    }
                }
            }
        });

        regist.setOnAction(e-> {
            new OrokbefogadoAddDialog(controller);
        });
        HBox registAndLogin = new HBox(login,regist);
        registAndLogin.setSpacing(10);
        registAndLogin.setPadding(new Insets(10));
        gp.add(registAndLogin,0,2,2,1);

        loginScene = new Scene(gp,400,200);
        loginScene.getStylesheets().add("css/style.css");

        //login vége


        layout.setTop(createMenuBar(stage));
        layoutForAdmin.setTop(createMenuBarForAdmin(stage));
        stage.setTitle("Állatkert kezdőoldala");
        stage.setScene(loginScene);
        stage.show();
    }



    private void constructAllatTable() {
        allatTable.setEditable(false);
        allatTableForAdmin.setEditable(false);

        TableColumn<Allat, String> nameCol = new TableColumn<>("Név");
        nameCol.setCellValueFactory(new PropertyValueFactory<Allat, String>("nev"));

        TableColumn<Allat, String> emailCol = new TableColumn<>("Faj");
        emailCol.setCellValueFactory(new PropertyValueFactory<Allat, String>("faj"));
        TableColumn<Allat, Blob> asd2 = new TableColumn<>("Kép");
        asd2.setCellValueFactory(new PropertyValueFactory<Allat,Blob>("fenykep"));

        TableColumn<Allat, String> birthCol = new TableColumn<>("Bemutatkozás");
        birthCol.setCellValueFactory(new PropertyValueFactory<Allat, String>("szoveg"));

        TableColumn<Allat, Integer> asd = new TableColumn<>("Születési éve");
        asd.setCellValueFactory(new PropertyValueFactory<Allat, Integer>("szulEv"));


        allatTable.getColumns().addAll(nameCol, emailCol, birthCol,asd);
        allatTable.setItems(FXCollections.observableArrayList(controller.listAllAllat()));
        allatTableForAdmin.getColumns().addAll(nameCol, emailCol, birthCol,asd);
        allatTableForAdmin.setItems(FXCollections.observableArrayList(controller.listAllAllat()));
    }
    private void constructKonyvelesTable() {
        konyvelesTable.setEditable(false);
        konyvelesTableForAdmin.setEditable(false);

        TableColumn<Konyveles, String> nameCol = new TableColumn<>("Név");
        nameCol.setCellValueFactory(new PropertyValueFactory<Konyveles, String>("obNev"));

        TableColumn<Konyveles, String> allatNameCol = new TableColumn<>("Állat neve");
        allatNameCol.setCellValueFactory(new PropertyValueFactory<Konyveles, String>("allatNev"));

        TableColumn<Konyveles, Date> mikor = new TableColumn<>("Ideje");
        mikor.setCellValueFactory(new PropertyValueFactory<Konyveles,Date>("mikor"));

        TableColumn<Konyveles, String> leiras = new TableColumn<>("Egyéb");
        leiras.setCellValueFactory(new PropertyValueFactory<Konyveles, String>("leiras"));

        TableColumn<Konyveles, String> tipus = new TableColumn<>("Támogatás típusa");
        tipus.setCellValueFactory(new PropertyValueFactory<Konyveles, String>("tamogatasTipus"));

        TableColumn<Konyveles, Integer> osszeg = new TableColumn<>("Összeg");
        osszeg.setCellValueFactory(new PropertyValueFactory<Konyveles, Integer>("osszeg"));

        TableColumn<Konyveles, Integer> mennyiseg = new TableColumn<>("Mennyiség");
        mennyiseg.setCellValueFactory(new PropertyValueFactory<Konyveles, Integer>("mennyiseg"));

        TableColumn<Konyveles, String> gyakorisag = new TableColumn<>("Támogatás gyakorisága");
        gyakorisag.setCellValueFactory(new PropertyValueFactory<Konyveles, String>("tamogatasGyakorisag"));



        konyvelesTable.getColumns().addAll(nameCol,allatNameCol,mikor,leiras,tipus,osszeg,mennyiseg,gyakorisag);
        konyvelesTableForAdmin.getColumns().addAll(nameCol,allatNameCol,mikor,leiras,tipus,osszeg,mennyiseg,gyakorisag);
        konyvelesTable.setItems(FXCollections.observableArrayList(controller.listAllKonyveles()));
        konyvelesTableForAdmin.setItems(FXCollections.observableArrayList(controller.listAllKonyveles()));

    }

    private void constructTable() {
        table.setEditable(false);
        tableForAdmin.setEditable(false);

        TableColumn<Orokbefogado, String> nameCol = new TableColumn<>("Név");
        nameCol.setCellValueFactory(new PropertyValueFactory<Orokbefogado, String>("nev"));

        TableColumn<Orokbefogado, String> emailCol = new TableColumn<>("Email/telefon");
        emailCol.setCellValueFactory(new PropertyValueFactory<Orokbefogado, String>("elerhetoseg"));

        TableColumn<Orokbefogado, String> birthCol = new TableColumn<>("Regisztráció dátuma");
        birthCol.setCellValueFactory(new PropertyValueFactory<Orokbefogado, String>("felvetelIdopontja"));

        table.getColumns().addAll(nameCol, emailCol, birthCol);
        tableForAdmin.getColumns().addAll(nameCol, emailCol, birthCol);
        table.setItems(FXCollections.observableArrayList(controller.listAll()));
        tableForAdmin.setItems(FXCollections.observableArrayList(controller.listAll()));


    }

    private MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu personMenu = new Menu("Örökbefogadó"); // menu letrehozasa

        Menu konyvelesMenu = new Menu("Örökbefogadás");
        Menu allatMenu = new Menu("Állatok");
        Menu kijelentkezesMenu = new Menu("Kijelentkezés");
        menuBar.getMenus().addAll(personMenu,allatMenu,konyvelesMenu,kijelentkezesMenu); // menu hozzaadasa a menubarhoz

        MenuItem listSzemelyMenuItem = new MenuItem("Örökbefogadó listázása"); // menupont letrehozasa
        MenuItem listAllat = new MenuItem("Állatok listázása");
        MenuItem addKonyveles = new MenuItem("Örökbefogadok egy állatot");
        MenuItem listKonyveles = new MenuItem("Örökbefogadók listája");
        MenuItem kijelentkezes = new MenuItem("Kijelentekzés");

        addKonyveles.setOnAction(e -> new KonyvelesAddDialog(controller));
        listSzemelyMenuItem.setOnAction(e -> {
            table.setItems(FXCollections.observableArrayList(controller.listAll()));

            stage.setScene(orokbefogadoScene);


        });
        listKonyveles.setOnAction(e -> {
            konyvelesTable.setItems(FXCollections.observableArrayList(controller.listAllKonyveles()));

            stage.setScene(newscene);

        });
        listAllat.setOnAction(e -> {
            allatTable.setItems(FXCollections.observableArrayList(controller.listAllAllat()));

            stage.setScene(allatScene);

        });
        kijelentkezes.setOnAction(e-> {
            stage.setScene(loginScene);
        });


        personMenu.getItems().add(listSzemelyMenuItem); // menupot hozzaadasa a menuhoz
        konyvelesMenu.getItems().addAll(addKonyveles, listKonyveles);
        allatMenu.getItems().addAll(listAllat);
        kijelentkezesMenu.getItems().add(kijelentkezes);

        return menuBar;
    }
    private MenuBar createMenuBarForAdmin(Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu personMenu = new Menu("Örökbefogadó"); // menu letrehozasa

        Menu konyvelesMenu = new Menu("Örökbefogadás");
        Menu allatMenu = new Menu("Állatok");
        Menu kijelentkezesMenu = new Menu("Kijelentkezés");
        menuBar.getMenus().addAll(personMenu,allatMenu,konyvelesMenu,kijelentkezesMenu); // menu hozzaadasa a menubarhoz

        MenuItem addSzemelyMenuItem = new MenuItem("Örökbefogadó felvétele"); // menupont letrehozasa
        MenuItem listSzemelyMenuItem = new MenuItem("Örökbefogadó listázása"); // menupont letrehozasa
        MenuItem addAllat = new MenuItem("Új állat felvitele");
        MenuItem listAllat = new MenuItem("Állatok listázása");
        MenuItem addKonyveles = new MenuItem("Örökbefogadok egy állatot");
        MenuItem listKonyveles = new MenuItem("Örökbefogadók listája");
        MenuItem kijelentkezes = new MenuItem("Kijelentkezés");

        addSzemelyMenuItem.setOnAction(e -> new OrokbefogadoAddDialog(controller)); // menupont esemenykezelese
        addKonyveles.setOnAction(e -> new KonyvelesAddDialog(controller));
        addAllat.setOnAction(e -> new AllatAddDialog(controller));
        listSzemelyMenuItem.setOnAction(e -> {
            tableForAdmin.setItems(FXCollections.observableArrayList(controller.listAll()));

            stage.setScene(orokbefogadoSceneForAdmin);


        });
        listKonyveles.setOnAction(e -> {
            konyvelesTableForAdmin.setItems(FXCollections.observableArrayList(controller.listAllKonyveles()));

            stage.setScene(adminScene);

        });
        listAllat.setOnAction(e -> {
            allatTableForAdmin.setItems(FXCollections.observableArrayList(controller.listAllAllat()));

            stage.setScene(allatSceneForAdmin);

        });
        kijelentkezes.setOnAction(e-> {
            stage.setScene(loginScene);
        });


        personMenu.getItems().addAll(addSzemelyMenuItem, listSzemelyMenuItem); // menupot hozzaadasa a menuhoz
        konyvelesMenu.getItems().addAll(addKonyveles, listKonyveles);
        allatMenu.getItems().addAll(addAllat,listAllat);
        kijelentkezesMenu.getItems().add(kijelentkezes);

        return menuBar;
    }


    public void deleteOrokbefogado(Orokbefogado o) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Biztos, hogy ki akarod törölni " + o.getNev() + " nevű örökbefogadót?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)) {
                controller.deleteOrokbefogado(o);
            }
        });
    }
    public void deleteAllat(Allat a) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Biztos, hogy ki akarod törölni " + a.getNev() + " nevű állatot?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)) {
                controller.deleteAllat(a);

            }
        });
    }
    public void deleteKonyveles(Konyveles k) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Biztos, hogy ki akarod törölni a " + k.getAllatNev() + "-hez tartozó örökbefogadást?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)) {
                controller.deleteKonyveles(k);
            }
        });
    }


    public static void main(String[] args) {
        launch();
    }
}