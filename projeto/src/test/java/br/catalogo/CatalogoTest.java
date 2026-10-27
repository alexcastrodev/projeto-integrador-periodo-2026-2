package br.catalogo;

import br.catalogo.dao.ItemDAO;
import br.catalogo.model.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class CatalogoTest {
    private Item valido() {
        Item i = new Item();
        i.setTitulo("Matrix"); i.setAutor("Wachowski"); i.setAno(1999); i.setGenero("Ficção"); i.setTipo("FILME");
        return i;
    }

    @Test
    void validacao() {
        assertTrue(valido().validar().isEmpty());
        Item i = valido(); i.setTitulo(" "); i.setAno(0); i.setTipo("X"); i.setNota(9);
        assertEquals(4, i.validar().size());
    }

    @Test
    void daoCrudEInjection() throws Exception {
        ItemDAO dao = new ItemDAO();
        try { dao.generos(); } catch (Exception e) { assumeTrue(false, "sem banco"); }
        Item i = valido(); i.setTitulo("Teste Unico 123");
        dao.inserir(i);
        assertTrue(i.getId() > 0);
        assertEquals(1, dao.listar("teste unico", "").size());
        assertEquals(0, dao.listar("'; DROP TABLE item; --", "").size());
        assertEquals(0, dao.listar("' OR '1'='1", "").size());
        i.setAno(2000); assertTrue(dao.atualizar(i));
        assertEquals(2000, dao.buscarPorId(i.getId()).getAno());
        assertTrue(dao.excluir(i.getId()));
        assertNull(dao.buscarPorId(i.getId()));
        assertFalse(dao.generos().isEmpty());
    }
}
