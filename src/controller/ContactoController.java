package controller;

import dao.ContactoDAO;
import dao.ContactoDAOImpl;
import model.Contacto;
import view.ContactoView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ContactoController {

    private ContactoView vista;
    private ContactoDAO contactoDAO;

    public ContactoController(ContactoView vista) {
        this.vista = vista;
        this.contactoDAO = new ContactoDAOImpl();
        agregarEventos();
        leerContactos(); // Cargar la tabla al iniciar la aplicación
        vista.deshabilitarCampoId(); // Deshabilitar el campo ID al inicio
    }

    private void agregarEventos() {
        vista.getBtnCrear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearContacto();
            }
        });

        vista.getBtnLeer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leerContactos();
            }
        });

        vista.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarContacto();
            }
        });

        vista.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarContacto();
            }
        });

        // Añadir listener para detectar selección en la tabla
        vista.getTblContactos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) { // Evitar cambios intermedios
                    cargarContactoSeleccionado();
                }
            }
        });
    }

    private void crearContacto() {
        if (!validarDatos()) return;
        try {
            String nombre = vista.getTxtNombre().getText();
            String email = vista.getTxtEmail().getText();
            String telefono = vista.getTxtTelefono().getText();

            Contacto contacto = new Contacto(0, nombre, email, telefono);
            contactoDAO.guardar(contacto);

            JOptionPane.showMessageDialog(vista, "Contacto guardado exitosamente.");
            limpiarCampos();
            leerContactos(); // Actualizar la lista después de crear un contacto
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar el contacto: " + e.getMessage());
        }
    }

    private void leerContactos() {
        try {
            List<Contacto> contactos = contactoDAO.obtenerTodos();
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Email", "Teléfono"});

            for (Contacto contacto : contactos) {
                modelo.addRow(new Object[]{contacto.getId(), contacto.getNombre(), contacto.getEmail(), contacto.getTelefono()});
            }

            vista.getTblContactos().setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al leer los contactos: " + e.getMessage());
        }
    }

    private void actualizarContacto() {
        if (!validarDatos()) return;
        try {
            int id = Integer.parseInt(vista.getTxtId().getText());
            String nombre = vista.getTxtNombre().getText();
            String email = vista.getTxtEmail().getText();
            String telefono = vista.getTxtTelefono().getText();

            Contacto contacto = new Contacto(id, nombre, email, telefono);
            contactoDAO.actualizar(contacto);

            JOptionPane.showMessageDialog(vista, "Contacto actualizado exitosamente.");
            limpiarCampos();
            leerContactos(); // Actualizar la lista después de actualizar un contacto
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el contacto: " + e.getMessage());
        }
    }

    private void eliminarContacto() {
        try {
            int id = Integer.parseInt(vista.getTxtId().getText());
            contactoDAO.eliminar(id);
            JOptionPane.showMessageDialog(vista, "Contacto eliminado exitosamente.");
            limpiarCampos();
            leerContactos(); // Actualizar la lista después de eliminar un contacto
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar el contacto: " + e.getMessage());
        }
    }

    private void cargarContactoSeleccionado() {
        int selectedRow = vista.getTblContactos().getSelectedRow();
        if (selectedRow != -1) { // Verificar que haya una fila seleccionada
            String id = vista.getTblContactos().getValueAt(selectedRow, 0).toString();
            String nombre = vista.getTblContactos().getValueAt(selectedRow, 1).toString();
            String email = vista.getTblContactos().getValueAt(selectedRow, 2).toString();
            String telefono = vista.getTblContactos().getValueAt(selectedRow, 3).toString();

            // Rellenar los campos de texto con los datos del contacto seleccionado
            vista.getTxtId().setText(id);
            vista.getTxtNombre().setText(nombre);
            vista.getTxtEmail().setText(email);
            vista.getTxtTelefono().setText(telefono);

            // Deshabilitar el campo ID para la actualización
            vista.deshabilitarCampoId();
        }
    }

    private void limpiarCampos() {
        vista.getTxtId().setText(""); // Limpiar el campo ID
        vista.getTxtNombre().setText("");
        vista.getTxtEmail().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTblContactos().clearSelection(); // Limpiar la selección de la tabla

        // Habilitar el campo ID para la creación de nuevos contactos
        vista.habilitarCampoId();
    }

    private boolean validarDatos() {
        if (vista.getTxtNombre().getText().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre no puede estar vacío.");
            return false;
        }
        if (!vista.getTxtEmail().getText().matches("\\S+@\\S+\\.\\S+")) {
            JOptionPane.showMessageDialog(vista, "El email no es válido.");
            return false;
        }
        try {
            Integer.parseInt(vista.getTxtId().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El ID debe ser un número.");
            return false;
        }
        return true;
    }
}