module com.example.proyecto_cuatro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    opens com.example.proyecto_cuatro.Modelo to javafx.base, org.hibernate.orm.core;


    opens com.example.proyecto_cuatro to javafx.fxml;
    exports com.example.proyecto_cuatro.Vista;
    opens com.example.proyecto_cuatro.Vista to javafx.base, org.hibernate.orm.core, javafx.fxml;
}
