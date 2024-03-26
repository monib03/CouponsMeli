package controladores;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

public class VersionControladorTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testVersion() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/coupon/version");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void testVersionInvalido() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/coupon/versionn");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

}