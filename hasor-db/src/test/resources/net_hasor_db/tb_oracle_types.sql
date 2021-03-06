create table tb_oracle_types
(
    c_char                       CHAR,
    c_char_n                     CHAR(12),
    c_char_nb                    CHAR(12 BYTE),
    c_char_nc                    CHAR(12 CHAR),
    c_character                  CHARACTER(10),
    c_character_b                CHARACTER(10 BYTE),
    c_character_c                CHARACTER(10 CHAR),

    c_nchar                      NCHAR,
    c_nchar_n                    NCHAR(12),
    c_national_character         NATIONAL CHARACTER(12),
    c_national_char              NATIONAL CHAR(12),

    c_varchar2                   VARCHAR2(24),
    c_varchar2_b                 VARCHAR2(24 BYTE),
    c_varchar2_c                 VARCHAR2(24 CHAR),
    c_character_varying          CHARACTER VARYING(24),
    c_character_varying_b        CHARACTER VARYING(24 BYTE),
    c_character_varying_c        CHARACTER VARYING(24 CHAR),
    c_char_varying               CHAR VARYING(24),
    c_char_varying_b             CHAR VARYING(24 BYTE),
    c_char_varying_c             CHAR VARYING(24 CHAR),
    c_national_char_varying      NATIONAL CHAR VARYING(24),

    c_nvarchar2                  NVARCHAR2(24),
    c_national_character_varying NATIONAL CHARACTER VARYING(24),
    c_nchar_varying              NCHAR VARYING(24),

    c_clob                       CLOB,
    c_nclob                      NCLOB,

    c_number                     NUMBER,
    c_number_n                   NUMBER(10),
    c_number_n_n                 NUMBER(10, 2),
    c_numeric                    NUMERIC,
    c_numeric_n                  NUMERIC(10),
    c_numeric_n_n                NUMERIC(*, 2),
    c_decimal                    DECIMAL,
    c_decimal_n                  DECIMAL(10),
    c_decimal_n_n                DECIMAL(10, 2),

    c_float                      FLOAT,
    c_float_n                    FLOAT(10),
    c_integer                    INTEGER,
    c_int                        INT,
    c_smallint                   SMALLINT,
    c_double_p                   DOUBLE PRECISION,
    c_real                       REAL,

    c_bfloat                     BINARY_FLOAT,
    c_bdouble                    BINARY_DOUBLE,

    c_date                       DATE,

    c_timestamp                  TIMESTAMP,
    c_timestamp_n                TIMESTAMP(9),
    c_timestamp_z                TIMESTAMP WITH TIME ZONE,
    c_timestamp_n_z              TIMESTAMP(9) WITH TIME ZONE,
    c_timestamp_lz               TIMESTAMP WITH LOCAL TIME ZONE,
    c_timestamp_n_lz             TIMESTAMP(9) WITH LOCAL TIME ZONE,

    c_interval_year              INTERVAL YEAR TO MONTH,
    c_interval_year_n            INTERVAL YEAR (9) TO MONTH,
    c_interval_day               INTERVAL DAY TO SECOND,
    c_interval_day_n1            INTERVAL DAY (9) TO SECOND,
    c_interval_day_n2            INTERVAL DAY TO SECOND (9),
    c_interval_day_n1n2          INTERVAL DAY (9) TO SECOND (9),

    c_raw                        RAW(10),
    c_long_raw                   LONG RAW,
    c_blob                       BLOB,
    c_bfile                      BFILE,
    --c_long                     LONG,
    c_xml                        XMLTYPE,

    c_rowid                      ROWID,
    c_urowid                     UROWID
)