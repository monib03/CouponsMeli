package dominio.respuestas;

public class ErrorValidacion implements Error {

    private String mensaje;

    public ErrorValidacion(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }

}

