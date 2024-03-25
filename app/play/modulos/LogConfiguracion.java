import com.google.inject.Inject;
import com.typesafe.config.Config;
import play.Logger;

public class LogConfiguracion {

    private Config config;

    @Inject
    public LogConfiguracion(Config config) {
        this.config = config;
        log();
    }

    private void log() {
        Logger.info("Lista de configuraciones");
        Logger.info("Url base de datos: " + config.getString("db.default.url"));
    }
}