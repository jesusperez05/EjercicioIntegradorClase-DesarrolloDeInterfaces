package cesur.dam.inventarioappg1.data;

import cesur.dam.inventarioappg1.models.Equipo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Enunciado del ejercicio: implementar el DAO en memoria usando una lista.
 * <p>
 * Implementación de {@link DAO} para {@link Equipo} que almacena los datos
 * en memoria dentro de un {@link ArrayList}, sin persistencia en base de datos.
 * Los métodos {@code findByNombre} y {@code findByCategoria} son adicionales
 * al contrato de la interfaz.
 * </p>
 */
public class EquipoListDAO implements  DAO<Equipo> {

    /** Lista en memoria que almacena los equipos. */
    private List<Equipo> equipoList = new ArrayList<>();

    /**
     * Busca un equipo por su identificador.
     *
     * @param id identificador del equipo
     * @return un {@link Optional} con el equipo encontrado, o vacío si no existe
     */
    @Override
    public Optional<Equipo> findById(Long id) {
        return equipoList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    /**
     * Obtiene todos los equipos almacenados.
     *
     * @return lista con todos los equipos
     */
    @Override
    public List<Equipo> findAll() {
        return equipoList;
    }

    /**
     * Guarda un nuevo equipo en la lista.
     *
     * @param obj equipo a guardar
     * @return el equipo guardado, posiblemente con el identificador asignado
     */
    @Override
    public Equipo save(Equipo obj) {

        long nextId = equipoList.stream()
                .mapToLong(Equipo::getId)
                .max()
                .orElse(0L) + 1L;
        obj.setId(nextId);

        equipoList.add(obj);
        return obj;
    }



    /**
     * Actualiza un equipo existente de la lista.
     *
     * @param entity equipo con los datos actualizados
     */
    @Override
    public void update(Equipo entity) {
        equipoList.stream()
                .filter(e -> e.getId().equals(entity.getId()))
                .findFirst()
                .ifPresent(e -> {
                    e.setNombre(entity.getNombre());
                    e.setCategoria(entity.getCategoria());
                    e.setDisponible(entity.getDisponible());
                });
    }

    /**
     * Elimina un equipo por su identificador.
     *
     * @param id identificador del equipo a eliminar
     */
    @Override
    public void deleteById(Long id) {
        equipoList.removeIf(e -> e.getId().equals(id));
    }

    /**
     * Busca equipos cuyo nombre coincida con el indicado.
     *
     * @param nombre nombre a buscar
     * @return lista de equipos que coinciden con el nombre
     */
    public List<Equipo> findByNombre(String nombre){
        return equipoList.stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre))
                .toList();
    }

    /**
     * Busca equipos cuya categoría coincida con la indicada.
     *
     * @param categoria categoría a buscar
     * @return lista de equipos que coinciden con la categoría
     */
    public List<Equipo> findByCategoria(String categoria){
        return equipoList.stream()
                .filter(e -> e.getCategoria().equalsIgnoreCase(categoria))
                .toList();
    }

}
