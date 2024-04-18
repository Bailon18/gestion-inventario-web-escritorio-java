
package com.micompania.desktop.inventario.interfaces;

import com.micompania.desktop.inventario.entidad.SalidaInventario;
import java.util.List;

public interface SalidaInventarioInterface {

    SalidaInventario realizarSalidaInventario(SalidaInventario salidaInventario);
    SalidaInventario buscarSalidaInventario(int idSalida);
    List<SalidaInventario> obtenerTodasLasSalidas();
    List<SalidaInventario> buscarSalidasPorFecha(String fechaInicio, String fechaFin);
 
}
