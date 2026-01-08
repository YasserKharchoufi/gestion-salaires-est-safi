import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

public class Employe {
    private final IntegerProperty id;
    private final StringProperty nom;
    private final StringProperty dateEntree;
    private final DoubleProperty salaire;

    public Employe(int id, String nom, String dateEntree, double salaire) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.dateEntree = new SimpleStringProperty(dateEntree);
        this.salaire = new SimpleDoubleProperty(salaire);
    }

    // Getters et Setters pour les propriétés
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getDateEntree() {
        return dateEntree.get();
    }

    public void setDateEntree(String dateEntree) {
        this.dateEntree.set(dateEntree);
    }

    public StringProperty dateEntreeProperty() {
        return dateEntree;
    }

    public double getSalaire() {
        return salaire.get();
    }

    public void setSalaire(double salaire) {
        this.salaire.set(salaire);
    }

    public DoubleProperty salaireProperty() {
        return salaire;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id.get() +
                ", nom='" + nom.get() + '\'' +
                ", dateEntree='" + dateEntree.get() + '\'' +
                ", salaire=" + salaire.get() +
                '}';
    }
}