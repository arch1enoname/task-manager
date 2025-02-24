CREATE TABLE comments (
             id bigint NOT NULL,
             date timestamp(6) without time zone,
             text character varying(255),
             author_id bigint
);