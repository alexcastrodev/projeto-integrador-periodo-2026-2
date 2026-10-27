package br.catalogo.dao;

import br.catalogo.model.Item;
import br.catalogo.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private static final String COLS = "id, titulo, autor, ano, genero, sinopse, tipo, nota, capa_url";

    public void inserir(Item i) throws SQLException {
        String sql = "INSERT INTO item (titulo, autor, ano, genero, sinopse, tipo, nota, capa_url) "
                + "VALUES (?,?,?,?,?,?,?,?) RETURNING id";
        try (Connection c = Conexao.abrir(); PreparedStatement ps = c.prepareStatement(sql)) {
            preencher(ps, i);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                i.setId(rs.getInt(1));
            }
        }
    }

    public boolean atualizar(Item i) throws SQLException {
        String sql = "UPDATE item SET titulo=?, autor=?, ano=?, genero=?, sinopse=?, tipo=?, nota=?, capa_url=? WHERE id=?";
        try (Connection c = Conexao.abrir(); PreparedStatement ps = c.prepareStatement(sql)) {
            preencher(ps, i);
            ps.setInt(9, i.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        try (Connection c = Conexao.abrir(); PreparedStatement ps = c.prepareStatement("DELETE FROM item WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public Item buscarPorId(int id) throws SQLException {
        try (Connection c = Conexao.abrir();
             PreparedStatement ps = c.prepareStatement("SELECT " + COLS + " FROM item WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Item> listar(String busca, String genero) throws SQLException {
        String sql = "SELECT " + COLS + " FROM item WHERE (titulo ILIKE ? OR autor ILIKE ?) AND (? = '' OR genero = ?) ORDER BY titulo";
        String like = "%" + (busca == null ? "" : busca.trim()) + "%";
        String g = genero == null ? "" : genero;
        List<Item> itens = new ArrayList<>();
        try (Connection c = Conexao.abrir(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, g);
            ps.setString(4, g);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) itens.add(mapear(rs));
            }
        }
        return itens;
    }

    public List<String> generos() throws SQLException {
        List<String> r = new ArrayList<>();
        try (Connection c = Conexao.abrir(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT DISTINCT genero FROM item ORDER BY genero")) {
            while (rs.next()) r.add(rs.getString(1));
        }
        return r;
    }

    private void preencher(PreparedStatement ps, Item i) throws SQLException {
        ps.setString(1, i.getTitulo());
        ps.setString(2, i.getAutor());
        ps.setInt(3, i.getAno());
        ps.setString(4, i.getGenero());
        ps.setString(5, i.getSinopse());
        ps.setString(6, i.getTipo());
        ps.setObject(7, i.getNota(), Types.INTEGER);
        ps.setString(8, i.getCapaUrl() == null || i.getCapaUrl().isBlank() ? null : i.getCapaUrl());
    }

    private Item mapear(ResultSet rs) throws SQLException {
        Item i = new Item();
        i.setId(rs.getInt("id"));
        i.setTitulo(rs.getString("titulo"));
        i.setAutor(rs.getString("autor"));
        i.setAno(rs.getInt("ano"));
        i.setGenero(rs.getString("genero"));
        i.setSinopse(rs.getString("sinopse"));
        i.setTipo(rs.getString("tipo"));
        i.setNota((Integer) rs.getObject("nota"));
        i.setCapaUrl(rs.getString("capa_url"));
        return i;
    }
}
