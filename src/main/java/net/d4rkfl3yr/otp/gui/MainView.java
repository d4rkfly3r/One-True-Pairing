package net.d4rkfl3yr.otp.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.d4rkfl3yr.otp.OneTruePairing;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.net.URL;

public class MainView extends Application {

    private static final Logger LOGGER = LogManager.getLogger(MainView.class);

    @Override
    public void start(@Nonnull Stage primaryStage) {
        LOGGER.debug("Loaded main view!");
        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        URL resource = OneTruePairing.class.getClassLoader().getResource("web/index.html");

        if (resource != null) {
            String url = resource.toExternalForm();
            System.out.println(url);
            webEngine.load(url);

            webEngine.getLoadWorker().workDoneProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.intValue() == 100) {
                    // TODO Start JavaScript binder here.
                }
            });

            webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) ->
            {
                JSObject window = (JSObject) webEngine.executeScript("window");
                JavaBridge bridge = new JavaBridge();
                window.setMember("_java", bridge);
                webEngine.executeScript("console.log = function(message){_java.log(message);}");
            });

            primaryStage.setScene(new Scene(webView));
            primaryStage.show();
        } else {
            JOptionPane.showMessageDialog(null, "Cannot find the internal web and gui interface files.", "Error!", JOptionPane.ERROR_MESSAGE);
            Platform.exit();
        }
    }

    /**
     * TODO Change this to be its own class with a list of consumers xD
     */
    private class JavaBridge {
        public void log(String text) {
            System.out.println(text);
        }
    }
}
