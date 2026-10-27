<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="pt-BR"><head><meta charset="UTF-8"><title>Catálogo</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"></head><body>
<h1>Catálogo de livros, séries e filmes</h1>
<form class="busca" method="get" action="${pageContext.request.contextPath}/itens">
  <input name="q" value="<c:out value='${q}'/>" placeholder="Buscar por título ou autor/diretor">
  <select name="genero" style="width:12rem">
    <option value="">Todos os gêneros</option>
    <c:forEach var="g" items="${generos}"><option value="<c:out value='${g}'/>" ${g == genero ? 'selected' : ''}><c:out value="${g}"/></option></c:forEach>
  </select>
  <button class="btn">Buscar</button>
  <a class="btn" href="${pageContext.request.contextPath}/itens?acao=novo">Novo item</a>
</form>
<c:if test="${empty itens}"><p>Nenhum item encontrado.</p></c:if>
<c:if test="${not empty itens}">
<table><tr><th>Título</th><th>Autor/Diretor</th><th>Ano</th><th>Tipo</th><th>Nota</th><th></th></tr>
<c:forEach var="i" items="${itens}"><tr>
  <td><a href="${pageContext.request.contextPath}/itens?acao=ver&id=${i.id}"><c:out value="${i.titulo}"/></a></td>
  <td><c:out value="${i.autor}"/></td><td>${i.ano}</td><td>${i.tipo}</td>
  <td class="estrelas"><c:if test="${not empty i.nota}"><c:forEach begin="1" end="${i.nota}">★</c:forEach></c:if></td>
  <td><a href="${pageContext.request.contextPath}/itens?acao=editar&id=${i.id}">Editar</a></td>
</tr></c:forEach></table></c:if>
</body></html>
