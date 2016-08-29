package net.d4rkfl3yr.otp;

import javafx.application.Application;
import javafx.application.Platform;
import net.d4rkfl3yr.otp.gui.MainView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

public class OneTruePairing {

    private static final Logger LOGGER = LogManager.getLogger(OneTruePairing.class);

    OneTruePairing(@Nonnull final String[] args) {
        this.init();
    }

    private void init() {
        Platform.setImplicitExit(true);
        Application.launch(MainView.class);
    }
}
