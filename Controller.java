import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class Controller {

    @FXML private TextField txtNom;
    @FXML private TextField txtAge;
    @FXML private TextField txtDateEntree;
    @FXML private ComboBox<String> cmbType;
    @FXML private TextField txtSalaire;
    @FXML private TableView<Employe> tableView;
    @FXML private TableColumn<Employe, Integer> colId;
    @FXML private TableColumn<Employe, String> colNom;
    @FXML private TableColumn<Employe, String> colDate;
    @FXML private TableColumn<Employe, Double> colSalaire;
    @FXML private javafx.scene.control.Label lblSalaireMoyen;

    private ObservableList<Employe> employes = FXCollections.observableArrayList();
    private int nextId = 1;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateEntreeProperty());
        colSalaire.setCellValueFactory(cellData -> cellData.getValue().salaireProperty().asObject());
        tableView.setItems(employes);
        cmbType.setItems(FXCollections.observableArrayList("Producteur","Développeur","Designer","Manager"));
    }

    @FXML
    private void ajouterAction() {
        try {
            String nom = txtNom.getText();
            Integer.parseInt(txtAge.getText());
            String dateEntree = txtDateEntree.getText();
            String type = cmbType.getValue();
            double salaire = Double.parseDouble(txtSalaire.getText());

            if (nom.isEmpty() || type == null) {
                showError("Erreur", "Veuillez remplir tous les champs!");
                return;
            }

            Employe employe = new Employe(nextId++, nom, dateEntree, salaire);
            employes.add(employe);
            tableView.setItems(employes);

            txtNom.clear();
            txtAge.clear();
            txtDateEntree.clear();
            cmbType.setValue(null);
            txtSalaire.clear();

            System.out.println("Tentative de connexion MySQL...");

            Connection con = DBConnection.getConnection();

            if(con == null){
                System.out.println("ECHEC CONNEXION MYSQL !");
                return;
            }
            System.out.println("Connexion réussie !");

            String sql = "INSERT INTO employe(nom, date_entree, salaire) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nom);
            ps.setString(2, dateEntree);
            ps.setDouble(3, salaire);
            ps.executeUpdate();
            con.close();

            showInfo("Ajouter", "Employé ajouté avec succès!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierAction() {
        Employe e = tableView.getSelectionModel().getSelectedItem();
        if (e == null) return;

        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE employe SET nom=?, date_entree=?, salaire=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtNom.getText());
            ps.setString(2, txtDateEntree.getText());
            ps.setDouble(3, Double.parseDouble(txtSalaire.getText()));
            ps.setInt(4, e.getId());
            ps.executeUpdate();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void supprimerAction() {
        Employe e = tableView.getSelectionModel().getSelectedItem();
        if (e == null) return;

        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM employe WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, e.getId());
            ps.executeUpdate();
            con.close();
            employes.remove(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void afficherAction() {
        try {
            employes.clear();
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employe");

            while (rs.next()) {
                employes.add(new Employe(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("date_entree"),
                        rs.getDouble("salaire")
                ));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void salaireMoyenAction() {
        double somme = 0;
        for (Employe e : employes) somme += e.getSalaire();
        lblSalaireMoyen.setText("Salaire moyen : " + (somme / employes.size()));
    }

    private void showInfo(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setContentText(m);
        a.showAndWait();
    }

    private void showError(String t, String m) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(t);
        a.setContentText(m);
        a.showAndWait();
    }
}
