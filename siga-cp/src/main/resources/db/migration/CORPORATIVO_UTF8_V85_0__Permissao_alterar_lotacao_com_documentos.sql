/* Permissões para alterar nome e sigla da lotação após cadastro de documento */

INSERT INTO "CORPORATIVO"."CP_SERVICO" (ID_SERVICO, SIGLA_SERVICO, DESC_SERVICO, ID_SERVICO_PAI, ID_TP_SERVICO) 
    VALUES ("CORPORATIVO".CP_SERVICO_SEQ.nextval, 'SIGA-GI-CAD_LOTACAO-ALT', 'Alterar Lotação com Documentos', (SELECT max(id_servico) FROM "CORPORATIVO"."CP_SERVICO" WHERE SIGLA_SERVICO = 'SIGA-GI-CAD_LOTACAO') , '2');
ALTER TABLE CORPORATIVO.DP_LOTACAO ADD (HIS_IDC_INI NUMBER);
COMMENT ON COLUMN "CORPORATIVO"."DP_LOTACAO"."HIS_IDC_INI" IS 'Id identidade da pessoa que cadastrou';
ALTER TABLE CORPORATIVO.DP_LOTACAO ADD (HIS_IDC_FIM NUMBER);
COMMENT ON COLUMN "CORPORATIVO"."DP_LOTACAO"."HIS_IDC_FIM" IS 'Id identidade da pessoa que finalizou';