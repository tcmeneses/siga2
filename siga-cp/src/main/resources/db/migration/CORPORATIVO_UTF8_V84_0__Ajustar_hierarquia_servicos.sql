-------------------------------------------
--	SCRIPT: AJUSTA A HIERARQUIA DAS PERMISSÕES CAD_UNIDADE: Pai E TIPO_UNIDADE: Unidade com Acesso Externo
--	TRELLO 1243 
-------------------------------------------
ALTER SESSION SET CURRENT_SCHEMA=corporativo;

UPDATE CP_SERVICO SET ID_SERVICO_PAI = 369 -- SIGA-GI-CAD_LOTACAO: Cadastrar Lotação
WHERE id_servico IN (
    372, -- SIGA-GI-CAD_UNIDADE: Pai
    373  -- SIGA-GI-TIPO_UNIDADE: Unidade com Acesso Externo 
);