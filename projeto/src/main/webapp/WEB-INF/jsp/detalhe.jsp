<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="pt-BR"><head><meta charset="UTF-8"><title><c:out value="${item.titulo}"/></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"></head><body>
<p><a href="${pageContext.request.contextPath}/itens">&larr; Voltar</a></p>
<c:if test="${not empty item.capaUrl}"><img class="capa" src="<c:out value='${item.capaUrl}'/>" alt="Capa"></c:if>
<h1><c:out value="${item.titulo}"/> <small>(${item.ano})</small></h1>
<p><b>${item.tipo}</b> · <c:out value="${item.autor}"/> · <c:out value="${item.genero}"/></p>
<c:if test="${not empty item.nota}"><p class="estrelas"><c:forEach begin="1" end="${item.nota}">★</c:forEach></p></c:if>
<p><c:out value="${item.sinopse}"/></p>
<p style="clear:both">
  <a class="btn" href="${pageContext.request.contextPath}/itens?acao=editar&id=${item.id}">Editar</a>
  <form method="post" action="${pageContext.request.contextPath}/itens" style="display:inline" onsubmit="return confirm('Excluir este item?')">
    <input type="hidden" name="acao" value="excluir"><input type="hidden" name="id" value="${item.id}">
    <button class="btn perigo">Excluir</button></form>
</p></body></html>
