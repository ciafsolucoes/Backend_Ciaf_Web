-- Cria a tabela texto_padrao em TODOS os bancos de empresa que já existem (ciaf_id e emitente_*).
-- Pode rodar mais de uma vez (CREATE TABLE IF NOT EXISTS). Empresas novas já recebem a tabela pelo create_dbs.sql.
--   mysql -uroot -p < texto_padrao_empresas_existentes.sql
DROP PROCEDURE IF EXISTS `ciaf_id`.`criar_texto_padrao_em_todas`;
DELIMITER $$
CREATE PROCEDURE `ciaf_id`.`criar_texto_padrao_em_todas`()
BEGIN
    DECLARE fim INT DEFAULT 0;
    DECLARE esquema VARCHAR(64);
    DECLARE cur CURSOR FOR
        SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA
        WHERE SCHEMA_NAME = 'ciaf_id' OR SCHEMA_NAME LIKE 'emitente\_%';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET fim = 1;
    OPEN cur;
    laco: LOOP
        FETCH cur INTO esquema;
        IF fim = 1 THEN LEAVE laco; END IF;
        SET @ddl = CONCAT('CREATE TABLE IF NOT EXISTS `', esquema, '`.`texto_padrao` (',
            '`id` bigint NOT NULL AUTO_INCREMENT,',
            '`emitente_id` bigint NOT NULL,',
            '`titulo` varchar(80) COLLATE utf8mb3_unicode_ci NOT NULL,',
            '`texto` text COLLATE utf8mb3_unicode_ci NOT NULL,',
            'PRIMARY KEY (`id`)',
            ') ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci');
        PREPARE stmt FROM @ddl;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    CLOSE cur;
END$$
DELIMITER ;
CALL `ciaf_id`.`criar_texto_padrao_em_todas`();
DROP PROCEDURE `ciaf_id`.`criar_texto_padrao_em_todas`;
-- conferência: lista onde a tabela existe
SELECT TABLE_SCHEMA FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'texto_padrao' ORDER BY TABLE_SCHEMA;
