package dao;

import model.Contacto;
import utils.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactoDAOImpl implements ContactoDAO {

    @Override
    public void guardar(Contacto contacto) throws SQLException {
        String sql = "INSERT INTO contactos (nombre, email, telefono) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, contacto.getNombre());
            pst.setString(2, contacto.getEmail());
            pst.setString(3, contacto.getTelefono());
            pst.executeUpdate();
        }
    }

    @Override
    public List<Contacto> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM contactos";
        List<Contacto> contactos = new ArrayList<>();

        try (Connection con = ConexionBD.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Contacto contacto = new Contacto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
                contactos.add(contacto);
            }
        }
        return contactos;
    }

    @Override
    public void actualizar(Contacto contacto) throws SQLException {
        String sql = "UPDATE contactos SET nombre = ?, email = ?, telefono = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, contacto.getNombre());
            pst.setString(2, contacto.getEmail());
            pst.setString(3, contacto.getTelefono());
            pst.setInt(4, contacto.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM contactos WHERE id = ?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }

    @Override
    public Contacto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM contactos WHERE id = ?";
        Contacto contacto = null;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    contacto = new Contacto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("telefono")
                    );
                }
            }
        }
        return contacto;
    }
}