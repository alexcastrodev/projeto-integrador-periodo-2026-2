<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="pt-BR"><head><meta charset="UTF-8"><title>${item.id == 0 ? 'Novo item' : 'Editar item'}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"></head><body>
<h1>${item.id == 0 ? 'Novo item' : 'Editar item'}</h1>
<c:if test="${not empty erros}"><div class="erro"><ul><c:forEach var="e" items="${erros}"><li><c:out value="${e}"/></li></c:forEach></ul></div></c:if>
<form class="campo" method="post" action="${pageContext.request.contextPath}/itens">
  <input type="hidden" name="id" value="${item.id}">
  <label>Tipo</label>
  <select name="tipo" required>
    <option value="LIVRO" ${item.tipo == 'LIVRO' ? 'selected' : ''}>Livro</option>
    <option value="SERIE" ${item.tipo == 'SERIE' ? 'selected' : ''}>Série</option>
    <option value="FILME" ${item.tipo == 'FILME' ? 'selected' : ''}>Filme</option>
  </select>
  <label>Título</label><input name="titulo" required maxlength="150" value="<c:out value='${item.titulo}'/>">
  <label>Autor / Diretor</label><input name="autor" required maxlength="100" value="<c:out value='${item.autor}'/>">
  <label>Ano</label><input name="ano" type="number" required min="1800" max="2100" value="${item.ano == 0 ? '' : item.ano}">
  <label>Gênero</label><input name="genero" required maxlength="50" value="<c:out value='${item.genero}'/>">
  <label>Sinopse</label><textarea name="sinopse" rows="5"><c:out value="${item.sinopse}"/></textarea>
  <label>Nota (1 a 5, opcional)</label><input name="nota" type="number" min="1" max="5" value="${item.nota}">
  <label>Link da capa (opcional)</label><input name="capaUrl" maxlength="300" value="<c:out value='${item.capaUrl}'/>">
  <p><button class="btn">Salvar</button> <a href="${pageContext.request.contextPath}/itens">Cancelar</a></p>
</form></body></html>
