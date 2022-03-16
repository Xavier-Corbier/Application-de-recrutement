module com.jobvxs.jobvxs {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.mail;

    opens com.jobvxs.ui to javafx.fxml;
    exports com.jobvxs.ui;
    exports com.jobvxs.ui.jobseeker;
    opens com.jobvxs.ui.jobseeker to javafx.fxml;
    exports com.jobvxs.ui.company;
    opens com.jobvxs.ui.company to javafx.fxml;
    exports com.jobvxs.ui.admin;
    opens com.jobvxs.ui.admin to javafx.fxml;
}