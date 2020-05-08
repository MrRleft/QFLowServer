CREATE TABLE  if not exists public.active_period (
    date_activation date,
    date_deactivation date,
    id_queue_ap_fk character varying(255)
);


--
-- TOC entry 202 (class 1259 OID 8023629)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE if not exists public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


--
-- TOC entry 204 (class 1259 OID 8075823)
-- Name: info_user_queue; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE if not exists public.info_user_queue (
    id_queue_iuq_fk character varying(255),
    id_user_iqu_fk character varying(255),
    is_rate_iqu_fk character varying(255),
    date_access date,
    date_success date,
    unattended boolean
);


--
-- TOC entry 205 (class 1259 OID 8075829)
-- Name: queue; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE if not exists public.queue (
    id_queue character varying(255) NOT NULL,
    join_id integer NOT NULL,
    name character varying(255),
    description character varying(255),
    business_associated character varying(255),
    date_finished date,
    date_created date,
    capacity integer,
    current_position integer,
    is_locked boolean
);


--
-- TOC entry 209 (class 1259 OID 8603115)
-- Name: queue_join_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE  if not exists public.queue_join_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3894 (class 0 OID 0)
-- Dependencies: 209
-- Name: queue_join_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.queue_join_id_seq OWNED BY public.queue.join_id;


--
-- TOC entry 206 (class 1259 OID 8075835)
-- Name: queue_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE if not exists public.queue_user (
    is_active boolean,
    is_admin boolean,
    id_queue_qu_fk character varying(255),
    id_user_qu_fk character varying(255)
);


--
-- TOC entry 207 (class 1259 OID 8075841)
-- Name: rate_queue; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE if not exists public.rate_queue (
    id_rating character varying(255) NOT NULL,
    rate integer,
    comment character varying(255)
);


--
-- TOC entry 208 (class 1259 OID 8075847)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE if not exists public.users (
    id_user character varying(255) NOT NULL,
    token character varying(255),
    email character varying(255),
    is_admin boolean,
    name_lastname character varying(255),
    password character varying(255),
    profile_picture character varying(255),
    username character varying(255)
);

