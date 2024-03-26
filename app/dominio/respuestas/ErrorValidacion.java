package dominio.respuestas;

import io.vavr.collection.List;

public class ErrorValidacion implements Error {

    private String mensaje;
    private List<String> campos;

    public ErrorValidacion(String mensaje, List<String> campos) {
        this.mensaje = mensaje;
        this.campos = campos;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }

    public List<String> getCampos() {
        return campos;
    }
}

