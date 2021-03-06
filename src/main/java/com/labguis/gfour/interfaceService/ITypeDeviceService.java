package com.labguis.gfour.interfaceService;

import com.labguis.gfour.modelo.TypeDevice;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nicolas Castillo
 */
public interface ITypeDeviceService {
    public int save(TypeDevice td);
    public List<TypeDevice>listar();
    TypeDevice findByName(String name);
    Optional<TypeDevice> findByID(int id);

    public void delete(int id);
}
