package br.catalogo.model;

import java.util.ArrayList;
import java.util.List;

public class Item {
    public static final List<String> TIPOS = List.of("LIVRO", "SERIE", "FILME");

    private int id;
    private String titulo, autor, genero, sinopse, tipo, capaUrl;
    private int ano;
    private Integer nota;

    public List<String> validar() {
        List<String> erros = new ArrayList<>();
        if (vazio(titulo) || titulo.length() > 150) erros.add("Título é obrigatório (até 150 caracteres).");
        if (vazio(autor) || autor.length() > 100) erros.add("Autor/diretor é obrigatório (até 100 caracteres).");
        if (vazio(genero) || genero.length() > 50) erros.add("Gênero é obrigatório (até 50 caracteres).");
        if (ano < 1800 || ano > 2100) erros.add("Ano deve ser um número entre 1800 e 2100.");
        if (!TIPOS.contains(tipo)) erros.add("Tipo deve ser livro, série ou filme.");
        if (nota != null && (nota < 1 || nota > 5)) erros.add("Nota deve ser de 1 a 5.");
        if (capaUrl != null && capaUrl.length() > 300) erros.add("Link da capa muito longo (até 300).");
        if (capaUrl != null && !capaUrl.isBlank() && !capaUrl.matches("(?i)https?://.+"))
            erros.add("Link da capa deve começar com http:// ou https://.");
        return erros;
    }

    private static boolean vazio(String s) { return s == null || s.isBlank(); }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public String getCapaUrl() { return capaUrl; }
    public void setCapaUrl(String capaUrl) { this.capaUrl = capaUrl; }
}
