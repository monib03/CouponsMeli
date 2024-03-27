package controladores;

import infraestructura.acl.dto.RedimirCouponsDTO;
import io.vavr.collection.List;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;


public class CouponsControladorTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void consultarTopItemsFavoritos() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .header("Content-Type", "application/json")
                .uri("/coupon/stats");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void redimirCupon() {
        RedimirCouponsDTO redimirCouponsDTO = new RedimirCouponsDTO(
                List.of("ID1", "ID2", "ID3", "ID4", "ID5" ), 500);
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(Json.toJson(redimirCouponsDTO))
                .header("Content-Type", "application/json")
                .uri("/coupon");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

}
