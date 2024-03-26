package controladores;

import com.meli.build.BuildInfo;
import play.mvc.Controller;
import play.mvc.Result;

public class VersionControlador extends Controller implements IControlador {

    public Result version() {
        return ok(BuildInfo.toJson());
    }

}
