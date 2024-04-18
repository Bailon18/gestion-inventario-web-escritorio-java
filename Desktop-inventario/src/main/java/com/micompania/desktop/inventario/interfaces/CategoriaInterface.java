package com.micompania.desktop.inventario.interfaces;

import com.micompania.desktop.inventario.entidad.Categoria;

import java.util.List;

public interface CategoriaInterface {
    
    Categoria buscarCategoriaPorId(int idProducto);
    List<Categoria> obtenerTodasLasCategorias();
}
