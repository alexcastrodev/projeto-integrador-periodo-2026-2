CREATE TABLE IF NOT EXISTS item (
    id       SERIAL PRIMARY KEY,
    titulo   VARCHAR(150) NOT NULL,
    autor    VARCHAR(100) NOT NULL,
    ano      INT          NOT NULL CHECK (ano BETWEEN 1800 AND 2100),
    genero   VARCHAR(50)  NOT NULL,
    sinopse  TEXT,
    tipo     VARCHAR(10)  NOT NULL CHECK (tipo IN ('LIVRO','SERIE','FILME')),
    nota     INT          CHECK (nota BETWEEN 1 AND 5),
    capa_url VARCHAR(300)
);
CREATE INDEX IF NOT EXISTS idx_item_titulo ON item (lower(titulo));
CREATE INDEX IF NOT EXISTS idx_item_autor  ON item (lower(autor));

INSERT INTO item (titulo, autor, ano, genero, sinopse, tipo, nota) VALUES
 ('Dom Casmurro','Machado de Assis',1899,'Romance','Bentinho e a duvida sobre Capitu.','LIVRO',5),
 ('Interestelar','Christopher Nolan',2014,'Ficcao cientifica','Exploradores procuram um novo lar para a humanidade.','FILME',5),
 ('Breaking Bad','Vince Gilligan',2008,'Drama','Um professor de quimica entra para o mundo do crime.','SERIE',4);
