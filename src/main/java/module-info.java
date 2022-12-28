module com.elementzero23.rockpaperscissorsjfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.elementzero23.rockpaperscissorsjfx to javafx.fxml;
    exports com.elementzero23.rockpaperscissorsjfx;
}