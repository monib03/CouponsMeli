package controladores;

import com.meli.build.BuildInfo;
import play.mvc.Controller;
import play.mvc.Result;

import static play.mvc.Results.ok;

public class VersionControlador extends Controller implements IControlador {

    public Result version() {
        return ok(BuildInfo.toJson());
    }

}
