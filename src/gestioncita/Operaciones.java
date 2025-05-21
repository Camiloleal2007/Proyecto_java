package gestioncita;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Operaciones {

    public static boolean ejecutarCRUD(String accion, String documento, String nombre, String telefono,
            String especialidad, String correo, String fechaNacimiento,
            String contraseña, String rol) {
        boolean exito = false;

        try (Connection con = ConexionDB.conectar(); CallableStatement cstmt = con.prepareCall("{CALL CRUD_Usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            cstmt.setString(1, accion);
            cstmt.setString(2, documento);
            cstmt.setString(3, nombre);
            cstmt.setString(4, telefono);
            cstmt.setString(5, especialidad);
            cstmt.setString(6, correo);
            cstmt.setString(7, fechaNacimiento);
            cstmt.setString(8, contraseña);
            cstmt.setString(9, rol);
            cstmt.registerOutParameter(10, Types.TINYINT);

            cstmt.execute();
            exito = cstmt.getByte(10) == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exito;
    }

    public static String validarLogin(String identidad, String contraseña) {
        String rol = null;

        try (Connection con = ConexionDB.conectar(); CallableStatement cstmt = con.prepareCall("{CALL validar_login(?, ?, ?)}")) {

            cstmt.setString(1, identidad);
            cstmt.setString(2, contraseña);

            cstmt.registerOutParameter(3, Types.VARCHAR);

            cstmt.execute();

            rol = cstmt.getString(3);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rol;
    }

    public static boolean esAdministradorValido(String documento, String contraseña) {
        try (Connection con = ConexionDB.conectar()) {
            String sql = "SELECT * FROM Usuario WHERE documento = ? AND contraseña = ? AND Rol = 'Admin'";
            var ps = con.prepareStatement(sql);
            ps.setString(1, documento);
            ps.setString(2, contraseña);
            var rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String SolicitarMedico(String especialidad) {
        String resultado = "";
        try (Connection con = ConexionDB.conectar()) {
            String sql = "SELECT obtenerMedicoPorEspecialidad(?)";
            CallableStatement stmt = con.prepareCall(sql);
            stmt.setString(1, especialidad);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultado = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<Object[]> obtenerCitasDelMedico(String medicoNombre) {
        List<Object[]> citas = new ArrayList<>();
        String query = "{ CALL obtenerCitasMedicasPorMedico(?) }";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query)) {
            stmt.setString(1, medicoNombre);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    citas.add(new Object[]{
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getString("paciente_nombre"),
                        rs.getString("medico_nombre"),
                        rs.getString("tipo_cita")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    public List<Object[]> obtenerCitasMedicas(String pacienteNombre) {
        List<Object[]> citas = new ArrayList<>();
        String query = "{ CALL obtenerCitasMedicas(?) }";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query)) {
            stmt.setString(1, pacienteNombre);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[4];
                    fila[0] = rs.getString("fecha");
                    fila[1] = rs.getString("hora");
                    fila[2] = rs.getString("medico_nombre");
                    fila[3] = rs.getString("tipo_cita");
                    citas.add(fila);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return citas;
    }

    public static List<Object[]> obtenerPacientesRecurrentes() {
        List<Object[]> pacientes = new ArrayList<>();
        String query = "{ CALL obtenerDatosCitasRecurrentes() }";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[5];
                    fila[0] = rs.getString("paciente_nombre");
                    fila[1] = rs.getInt("cantidad_citas");
                    fila[2] = rs.getString("hora_mas_recurrente");
                    fila[3] = rs.getString("tipo_cita_mas_recurrente");
                    fila[4] = rs.getString("medico_mas_recurrente");
                    pacientes.add(fila);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }

    public List<Object[]> obtenerCitasAtendidas(String medicoNombre) {
        List<Object[]> citas = new ArrayList<>();
        String query = "{ CALL obtenerCitasAtendidas(?) }";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query)) {
            stmt.setString(1, medicoNombre);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    citas.add(new Object[]{
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getString("paciente_nombre"),
                        rs.getString("medico_nombre"),
                        rs.getString("tipo_cita"),
                        rs.getString("diagnostico"),
                        rs.getString("solucion")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    public List<Object[]> obtenerCitasMedicasmedico(String medicoNombre) {
        List<Object[]> citas = new ArrayList<>();
        String query = "{ CALL obtenerCitasMedicasmedico(?) }";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query)) {

            stmt.setString(1, medicoNombre);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[4];
                    fila[0] = rs.getString("fecha");
                    fila[1] = rs.getString("hora");
                    fila[2] = rs.getString("paciente_nombre");
                    fila[3] = rs.getString("tipo_cita");
                    citas.add(fila);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return citas;
    }

    public String agregarcita(int modo, String paciente, String medico, String fecha, String hora, String tipoCita) {
        String mensaje = "";
        try (Connection con = ConexionDB.conectar()) {
            CallableStatement cs = con.prepareCall("{CALL GestionarCitaMedica(?, ?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, modo);
            cs.setString(2, paciente);
            cs.setString(3, medico);
            cs.setDate(4, java.sql.Date.valueOf(fecha));
            cs.setString(5, hora);
            cs.setString(6, tipoCita);
            cs.registerOutParameter(7, java.sql.Types.VARCHAR);
            cs.execute();
            mensaje = cs.getString(7);
            System.out.println(mensaje);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mensaje;
    }

    public static String Buscar(int numero, String documento) {
        String resultado = "";
        try (Connection con = ConexionDB.conectar()) {
            String sql = "CALL Procedimientos(?, ?)";
            CallableStatement stmt = con.prepareCall(sql);
            stmt.setInt(1, numero);
            stmt.setString(2, documento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultado = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static boolean atenderCita(String paciente, String medico, String fecha, String hora, String tipoCita, String diagnostico, String solucion) {
        boolean exito = false;
        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall("{CALL AtenderCita(?, ?, ?, ?, ?, ?, ?, ?)}")) {

            stmt.setString(1, paciente);
            stmt.setString(2, medico);
            stmt.setDate(3, java.sql.Date.valueOf(fecha));
            stmt.setString(4, hora);
            stmt.setString(5, tipoCita);
            stmt.setString(6, diagnostico);
            stmt.setString(7, solucion);
            stmt.registerOutParameter(8, Types.VARCHAR);

            stmt.execute();
            String mensaje = stmt.getString(8);

            if (mensaje.equals("Cita atendida correctamente y guardada en CitasAtendidas.")) {
                exito = true;
            }
            System.out.println(mensaje);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exito;
    }

    public static boolean eliminarCitaActiva(String paciente, String medico, String fecha, String hora, String tipoCita) {
        boolean exito = false;
        String query = "{CALL EliminarCitaActiva(?, ?, ?, ?, ?)}";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query)) {
            stmt.setString(1, paciente);
            stmt.setString(2, medico);
            stmt.setDate(3, java.sql.Date.valueOf(fecha));
            stmt.setString(4, hora);
            stmt.setString(5, tipoCita);

            stmt.execute();
            exito = true;
            System.out.println("Cita eliminada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }

    public static int[] obtenerReporteCitas(String modo, LocalDate fechaBase) {
        int[] resultados = new int[3];

        String query = "{CALL ReporteCitas(?, ?)}";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query)) {

            stmt.setString(1, modo.toLowerCase());
            stmt.setDate(2, java.sql.Date.valueOf(fechaBase));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    resultados[0] = rs.getInt("citas_activas");
                    resultados[1] = rs.getInt("citas_atendidas");
                    resultados[2] = rs.getInt("total_citas");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el reporte de citas: " + e.getMessage());
            e.printStackTrace();
        }

        return resultados;
    }

    public static List<Object[]> obtenerTotalCitasPorEspecialidad() {
        List<Object[]> especialidades = new ArrayList<>();
        String query = "{ CALL TotalCitasPorEspecialidad() }";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getString("tipo_cita");
                fila[1] = rs.getInt("total_citas");
                especialidades.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidades;
    }

    public List<Object[]> obtenerHorario() {
        List<Object[]> horarios = new ArrayList<>();
        String query = "{ CALL ObtenerHorario() }";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[8];
                    fila[0] = rs.getString("medico");
                    fila[1] = rs.getString("lunes");
                    fila[2] = rs.getString("martes");
                    fila[3] = rs.getString("miercoles");
                    fila[4] = rs.getString("jueves");
                    fila[5] = rs.getString("viernes");
                    fila[6] = rs.getString("sabado");
                    fila[7] = rs.getString("domingo");

                    horarios.add(fila);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return horarios;
    }

    public List<Object[]> obtenerMovimientos() {
        List<Object[]> movimientos = new ArrayList<>();
        String query = "{ CALL ObtenerMovimientos() }";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[8];
                    fila[0] = rs.getString("nombre");
                    fila[1] = rs.getString("telefono");
                    fila[2] = rs.getString("especialidad");
                    fila[3] = rs.getString("correo");
                    fila[4] = rs.getString("Fecha_nacimiento");
                    fila[5] = rs.getString("Rol");
                    fila[6] = rs.getString("Accion");
                    fila[7] = rs.getString("Fecha");

                    movimientos.add(fila);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movimientos;
    }

    public boolean insertarHorarioMedico(String medico, String lunes, String martes, String miercoles,
            String jueves, String viernes, String sabado, String domingo) {
        String query = "{ CALL InsertarHorarioMedico(?, ?, ?, ?, ?, ?, ?, ?) }";

        try (Connection con = ConexionDB.conectar(); CallableStatement stmt = con.prepareCall(query)) {

            stmt.setString(1, medico);
            stmt.setString(2, lunes);
            stmt.setString(3, martes);
            stmt.setString(4, miercoles);
            stmt.setString(5, jueves);
            stmt.setString(6, viernes);
            stmt.setString(7, sabado);
            stmt.setString(8, domingo);

            stmt.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
