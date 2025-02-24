CREATE TABLE public.tasks (
  id bigint NOT NULL,
  description character varying(255),
  priority character varying(255),
  status character varying(255),
  title character varying(255),
  author_id bigint,
  performer_id bigint,
  CONSTRAINT tasks_priority_check CHECK (((priority)::text = ANY ((ARRAY['HIGH'::character varying, 'MEDIUM'::character varying, 'LOW'::character varying])::text[]))),
    CONSTRAINT tasks_status_check CHECK (((status)::text = ANY ((ARRAY['NEW'::character varying, 'IN_PROGRESS'::character varying, 'IN_REVIEW'::character varying, 'TESTING'::character varying, 'CLOSED'::character varying])::text[])))
);