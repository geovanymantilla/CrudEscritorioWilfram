package dao;

import model.Contacto;
import java.util.List;

public interface ContactoDAO {
    void guardar(Contacto contacto) throws Exception;
    List<Contacto> obtenerTodos() throws Exception;
    void actualizar(Contacto contacto) throws Exception;
    void eliminar(int id) throws Exception;
    Contacto buscarPorId(int id) throws Exception;
}
