package cesur.dam.inventarioappg1.data;

import cesur.dam.inventarioappg1.models.Equipo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de {@link DAO} para {@link Equipo} basada en JDBC.
 * <p>
 * Accede a la base de datos MySQL a través del {@link DataSource} recibido en
 * el constructor, ejecutando consultas SQL sobre la tabla {@code Equipo}.
 * </p>
 */
public class EquipoJdbcDAO implements DAO<Equipo> {

    /** Origen de datos utilizado para obtener las conexiones JDBC. */
    private DataSource ds;

    /**
     * Crea una instancia del DAO.
     *
     * @param ds {@link DataSource} del que se obtendrán las conexiones
     */
    public EquipoJdbcDAO(DataSource ds){
        this.ds = ds;
    }

    /**
     * Busca un equipo por su identificador.
     *
     * @param id identificador del equipo
     * @return un {@link Optional} con el equipo encontrado, o vacío si no existe
     * @throws RuntimeException si ocurre un error de SQL
     */
    @Override
    public Optional<Equipo> findById(Long id) {
        String sql = "select * from Equipo where id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setLong(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(mapper(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    /**
     * Recupera todos los equipos de la base de datos.
     *
     * @return lista con todos los equipos
     * @throws RuntimeException si ocurre un error de SQL
     */
    @Override
    public List<Equipo> findAll() {
        String sql = "select * from Equipo";
        List<Equipo> equipos = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                equipos.add(mapper(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equipos;
    }



    /**
     * Inserta un nuevo equipo en la base de datos y le asigna el identificador
     * generado.
     *
     * @param obj equipo a guardar
     * @return el equipo guardado con su identificador actualizado
     * @throws RuntimeException si ocurre un error de SQL
     */
    @Override
    public Equipo save(Equipo obj) {
        String sql = "insert into Equipo (nombre, categoria, disponible) " +
                     "VALUES (?,?,?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, obj.getNombre());
            pst.setString(2, obj.getCategoria());
            pst.setBoolean(3, obj.getDisponible());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    /**
     * Actualiza en la base de datos los datos de un equipo existente.
     *
     * @param entity equipo con los datos actualizados; debe tener identificador
     * @throws RuntimeException si ocurre un error de SQL
     */
    @Override
    public void update(Equipo entity) {
        String sql = "update Equipo set nombre=?, categoria=?, disponible=? " +
                     "where id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, entity.getNombre());
            pst.setString(2, entity.getCategoria());
            pst.setBoolean(3, entity.getDisponible());
            pst.setLong(4, entity.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina de la base de datos el equipo con el identificador indicado.
     *
     * @param id identificador del equipo a eliminar
     * @throws RuntimeException si ocurre un error de SQL
     */
    @Override
    public void deleteById(Long id) {
        String sql = "delete from Equipo where id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /* Métodos auxiliares */

    /**
     * Convierte la fila actual de un {@link ResultSet} en un objeto
     * {@link Equipo}.
     *
     * @param rs resultado posicionado en la fila a mapear
     * @return el equipo construido a partir de la fila actual
     * @throws SQLException si ocurre un error al leer las columnas
     */
    public static Equipo mapper(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo();
        equipo.setId(rs.getLong("id"));
        equipo.setNombre(rs.getString("nombre"));
        equipo.setCategoria(rs.getString("categoria"));
        equipo.setDisponible(rs.getBoolean("disponible"));
        return equipo;
    }

}
