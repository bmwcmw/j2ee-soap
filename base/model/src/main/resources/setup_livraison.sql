DROP TABLE commande;
DROP TABLE commande_articles;

CREATE TABLE commande (
    id bigint NOT NULL,
    confirmee date,
    effectuee date
);

GRANT ALL ON TABLE commande TO etudiant;

ALTER TABLE ONLY commande
    ADD CONSTRAINT commande_pkey PRIMARY KEY (id);


CREATE TABLE commande_articles (
    id bigint NOT NULL,
    commande_id bigint NOT NULL,
    wine_id character varying(15) NOT NULL,
    quantite integer NOT NULL
);

GRANT ALL ON TABLE commande_articles TO etudiant;

ALTER TABLE ONLY commande_articles
    ADD CONSTRAINT commande_articles_pkey PRIMARY KEY (id);

ALTER TABLE ONLY commande_articles
    ADD CONSTRAINT commande_articles_fkey FOREIGN KEY (wine_id) REFERENCES wine(id);

-- ALTER TABLE ONLY commande_articles
--     ADD CONSTRAINT commande_articles_ukey UNIQUE (commande_id, wine_id, quantite);

