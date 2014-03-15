--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.2
-- Dumped by pg_dump version 9.2.2
-- Started on 2013-09-11 21:19:30

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 168 (class 1259 OID 16461)
-- Name: bottle; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE bottle (
    id bigint NOT NULL,
    wine_id character varying(15)
);


--
-- TOC entry 169 (class 1259 OID 16464)
-- Name: composition; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE composition (
    wine_id character varying(15) NOT NULL,
    percentage integer,
    variety character varying(255) NOT NULL
);


--
-- TOC entry 170 (class 1259 OID 16467)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 171 (class 1259 OID 16469)
-- Name: location; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE location (
    id bigint NOT NULL,
    country character varying(255),
    region character varying(255),
    subregion character varying(255)
);


--
-- TOC entry 172 (class 1259 OID 16475)
-- Name: wine; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE wine (
    id character varying(15) NOT NULL,
    appellation character varying(255),
    color character varying(6),
    location bigint,
    percentage double precision,
    producer character varying(255),
    vineyard character varying(255),
    vintage date,
    style character varying(255),
    winetype character varying(20),
    price double precision
);


--
-- TOC entry 1932 (class 2606 OID 16565)
-- Name: bottle_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bottle
    ADD CONSTRAINT bottle_pkey PRIMARY KEY (id);


--
-- TOC entry 1934 (class 2606 OID 16484)
-- Name: composition_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY composition
    ADD CONSTRAINT composition_pkey PRIMARY KEY (wine_id, variety);


--
-- TOC entry 1936 (class 2606 OID 16486)
-- Name: location_country_region_subregion_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY location
    ADD CONSTRAINT location_country_region_subregion_key UNIQUE (country, region, subregion);


--
-- TOC entry 1938 (class 2606 OID 16532)
-- Name: location_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- TOC entry 1940 (class 2606 OID 16490)
-- Name: wine_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY wine
    ADD CONSTRAINT wine_pkey PRIMARY KEY (id);


--
-- TOC entry 1941 (class 2606 OID 16570)
-- Name: bottle_wine_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bottle
    ADD CONSTRAINT bottle_wine_id_fkey FOREIGN KEY (wine_id) REFERENCES wine(id);


--
-- TOC entry 1942 (class 2606 OID 16575)
-- Name: composition_wine_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY composition
    ADD CONSTRAINT composition_wine_id_fkey FOREIGN KEY (wine_id) REFERENCES wine(id);


--
-- TOC entry 1943 (class 2606 OID 16580)
-- Name: wine_location_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY wine
    ADD CONSTRAINT wine_location_fkey FOREIGN KEY (location) REFERENCES location(id);


-- Completed on 2013-09-11 21:19:30

--
-- PostgreSQL database dump complete
--

