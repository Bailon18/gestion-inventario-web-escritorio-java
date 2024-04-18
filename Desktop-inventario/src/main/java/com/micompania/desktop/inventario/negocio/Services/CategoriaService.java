package com.micompania.desktop.inventario.negocio.Services;

import com.micompania.desktop.inventario.negocio.DAO.CategoriaDAO;
import com.micompania.desktop.inventario.entidad.Categoria;
import com.micompania.desktop.inventario.interfaces.CategoriaInterface;

import java.util.List;

public class CategoriaService implements CategoriaInterface{

    private final CategoriaInterface categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    @Override
    public Categoria buscarCategoriaPorId(int idCategoria) {
        return categoriaDAO.buscarCategoriaPorId(idCategoria);
    }

    @Override
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaDAO.obtenerTodasLasCategorias();
    }
}
