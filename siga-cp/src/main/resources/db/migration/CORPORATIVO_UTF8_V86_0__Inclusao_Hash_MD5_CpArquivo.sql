/* Inclusão de Hash MD5 na CP_ARQUIVO para validar a alteração no documento */

ALTER TABLE CORPORATIVO.CP_ARQUIVO ADD HASH_MD5 VARCHAR(50);
COMMENT ON COLUMN "CORPORATIVO"."CP_ARQUIVO"."HASH_MD5" IS 'Hash MD5 dos binários do arquivo';
UPDATE CORPORATIVO.CP_ARQUIVO SET HASH_MD5='0';
ALTER TABLE CORPORATIVO.CP_ARQUIVO MODIFY HASH_MD5 NOT NULL;