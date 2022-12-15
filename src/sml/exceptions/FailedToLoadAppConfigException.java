package sml.exceptions;

import sml.AppConfig;

import java.io.IOException;

/**
 * FailedToLoadAppConfigException is thrown if the app properties cannot be read from the config file.
 *
 * @author Marton Vago
 */
public class FailedToLoadAppConfigException extends IOException {
    public FailedToLoadAppConfigException(IOException e) {
        super("Could not load app config from " + AppConfig.FILE_NAME, e);
    }
}
