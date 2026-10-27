package br.catalogo.servlet;

import br.catalogo.dao.ItemDAO;
import br.catalogo.model.Item;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/itens")
public class ItemServlet extends HttpServlet {
    private final ItemDAO dao = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String acao = req.getParameter("acao") == null ? "listar" : req.getParameter("acao");
        try {
            switch (acao) {
                case "novo" -> {
                    req.setAttribute("item", new Item());
                    req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
                }
                case "editar", "ver" -> {
                    Item i = dao.buscarPorId(inteiro(req.getParameter("id")));
                    if (i == null) { resp.sendError(404, "Item não encontrado"); return; }
                    req.setAttribute("item", i);
                    req.getRequestDispatcher("/WEB-INF/jsp/" + (acao.equals("ver") ? "detalhe" : "form") + ".jsp").forward(req, resp);
                }
                default -> {
                    String q = req.getParameter("q"), g = req.getParameter("genero");
                    req.setAttribute("itens", dao.listar(q, g));
                    req.setAttribute("generos", dao.generos());
                    req.setAttribute("q", q);
                    req.setAttribute("genero", g);
                    req.getRequestDispatcher("/WEB-INF/jsp/lista.jsp").forward(req, resp);
                }
            }
        } catch (SQLException e) {
            erroBanco(e, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            if ("excluir".equals(req.getParameter("acao"))) {
                dao.excluir(inteiro(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/itens");
                return;
            }
            Item i = lerFormulario(req);
            List<String> erros = i.validar();
            if (!erros.isEmpty()) {
                req.setAttribute("erros", erros);
                req.setAttribute("item", i);
                req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
                return;
            }
            if (i.getId() == 0) dao.inserir(i); else dao.atualizar(i);
            resp.sendRedirect(req.getContextPath() + "/itens?acao=ver&id=" + i.getId());
        } catch (SQLException e) {
            erroBanco(e, resp);
        }
    }

    private Item lerFormulario(HttpServletRequest req) {
        Item i = new Item();
        i.setId(inteiro(req.getParameter("id")));
        i.setTitulo(req.getParameter("titulo"));
        i.setAutor(req.getParameter("autor"));
        i.setAno(inteiro(req.getParameter("ano")));
        i.setGenero(req.getParameter("genero"));
        i.setSinopse(req.getParameter("sinopse"));
        i.setTipo(req.getParameter("tipo"));
        i.setCapaUrl(req.getParameter("capaUrl"));
        String n = req.getParameter("nota");
        i.setNota(n == null || n.isBlank() ? null : inteiro(n));
        return i;
    }

    private static int inteiro(String s) {
        try { return Integer.parseInt(s.trim()); } catch (RuntimeException e) { return 0; }
    }

    private void erroBanco(SQLException e, HttpServletResponse resp) throws IOException {
        log("Erro de banco", e);
        resp.sendError(500, "Erro ao acessar o banco de dados.");
    }
}
