
package com.micompania.desktop.inventario.negocio.Services;

import com.micompania.desktop.inventario.entidad.SalidaInventario;
import com.micompania.desktop.inventario.interfaces.SalidaInventarioInterface;
import com.micompania.desktop.inventario.negocio.DAO.SalidaInventarioDAO;
import java.util.Date;
import java.util.List;


public class SalidaInventarioService implements SalidaInventarioInterface{
    
    private final SalidaInventarioDAO salidaDao = new SalidaInventarioDAO();

    @Override
    public SalidaInventario realizarSalidaInventario(SalidaInventario salidaInventario) {
        return salidaDao.realizarSalidaInventario(salidaInventario);
    }

    @Override
    public SalidaInventario buscarSalidaInventario(int idSalida) {
        return salidaDao.buscarSalidaInventario(idSalida);
    }

    @Override
    public List<SalidaInventario> obtenerTodasLasSalidas() {
        return salidaDao.obtenerTodasLasSalidas();
    }

    @Override
    public List<SalidaInventario> buscarSalidasPorFecha(String fechaInicio, String fechaFin) {
        return salidaDao.buscarSalidasPorFecha(fechaInicio, fechaFin);
    }
    
}
