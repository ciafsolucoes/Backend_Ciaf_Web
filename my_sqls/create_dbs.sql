-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: ciaf_id
-- ------------------------------------------------------
-- Server version       8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


DROP PROCEDURE IF EXISTS `ciaf_id`.`create_emitente_1_database_if_needed`;
DELIMITER $$
CREATE PROCEDURE `ciaf_id`.`create_emitente_1_database_if_needed`() 
BEGIN
    IF EXISTS (
        SELECT 1
        FROM INFORMATION_SCHEMA.SCHEMATA
        WHERE SCHEMA_NAME = 'emitente_1'
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Banco emitente_1 ja existe. Execucao abortada.';
    END IF;

    CREATE DATABASE `emitente_1` DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci;
END$$
DELIMITER ;

CALL `ciaf_id`.`create_emitente_1_database_if_needed`();
DROP PROCEDURE IF EXISTS `ciaf_id`.`create_emitente_1_database_if_needed`;

USE emitente_1;

SET @emitente_id := 1;

DROP TABLE IF EXISTS `apagar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apagar` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `emitente_id` bigint NOT NULL,
                          `addition` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `creditor` varchar(77) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `dateofoperation` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `description` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `discount` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `due_date` datetime DEFAULT NULL,
                          `duedate` varchar(15) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `efetive` bit(1) DEFAULT NULL,
                          `final_value` varchar(22) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `final_value_real` double DEFAULT NULL,
                          `fixedcost` bit(1) DEFAULT NULL,
                          `observation` text COLLATE utf8mb3_unicode_ci,
                          `paid_value` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `type` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `value` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `vencimento` date DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `areceber`
--

DROP TABLE IF EXISTS `areceber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `areceber` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `emitente_id` bigint NOT NULL,
                            `addition` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dateofoperation` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `description` varchar(90) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `devedor` varchar(77) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `discount` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `duedate` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `efetive` bit(1) NOT NULL,
                            `final_value` varchar(22) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `final_value_real` double DEFAULT NULL,
                            `fixed_cost` bit(1) NOT NULL,
                            `nr_nfce` int DEFAULT NULL,
                            `nr_venda` int DEFAULT NULL,
                            `observation` text COLLATE utf8mb3_unicode_ci,
                            `paid_value` varchar(22) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `type` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `value` varchar(22) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `vencimento` date DEFAULT NULL,
                            `venda_id` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `balance`
--

DROP TABLE IF EXISTS `balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `balance` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `emitente_id` bigint NOT NULL,
                           `balance` double NOT NULL,
                           `balanceforecast` double NOT NULL,
                           `data_saldo` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `budgets`
--

DROP TABLE IF EXISTS `budgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budgets` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `emitente_id` bigint NOT NULL,
                           `aprovado_por` varchar(90) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `client` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `client_cell` varchar(30) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `client_email` varchar(66) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `client_id` bigint DEFAULT NULL,
                           `data` varchar(33) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `data_aprovacao` varchar(35) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `created_at` datetime DEFAULT NULL,
                           `desconto` double DEFAULT NULL,
                           `nr_budget` int DEFAULT NULL,
                           `observacao` text COLLATE utf8mb3_unicode_ci,
                           `parcelado` bit(1) DEFAULT NULL,
                           `pdf` bit(1) NOT NULL,
                           `prazo` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `receipts_id` bigint DEFAULT NULL,
                           `saved_b` bit(1) DEFAULT NULL,
                           `status` varchar(66) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `updated_at` datetime DEFAULT NULL,
                           `validade` varchar(35) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `valor` double DEFAULT NULL,
                           `vendedor` varchar(66) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `budgets_pg`
--

DROP TABLE IF EXISTS `budgets_pg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budgets_pg` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `emitente_id` bigint NOT NULL,
                              `budgets_id` bigint DEFAULT NULL,
                              `condicoes` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `convenio` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `data_pg` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `forma` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `value` double DEFAULT NULL,
                              `troco` double DEFAULT NULL,
                              `valor` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `valor_pg` double DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `budgetsitems`
--

DROP TABLE IF EXISTS `budgetsitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budgetsitems` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `brand` varchar(55) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `description` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_cean` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_cfop` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_cprod` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
--                                 `prod_desconto` double DEFAULT NULL,
                                `prod_ncm` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_qcom` int DEFAULT NULL,
                                `prod_ucom` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_vprod` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_vun_com` double NOT NULL,
                                `prod_xprod` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `product_id` bigint DEFAULT NULL,
                                `ref` bigint DEFAULT NULL,
                                `total_price` double NOT NULL,
                                `budget_id` bigint DEFAULT NULL,
                                `cst_csosn` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_ceantrib` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_cest` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_cnpjfab` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_cst` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_extipi` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_ind_escala` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_ind_tot` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_nve` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_pcicms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_picms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_qtrib` varchar(11) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_red_icms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_utrib` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_vbcicms` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_vicms` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_vipi` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_vun_trib` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_c_benef` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_modbc` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_orig` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_pbc_icms` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_p_credsn` varchar(5) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_pfcp` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_pipi` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_p_redbc_icms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_pesob` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_pesol` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_v_credicmssn` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_v_desc` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_vfcp` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_v_frete` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_vicmsdeson` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_v_outro` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `prod_v_seg` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FKr1b7ake2ra6uj8h2bjxsfvjax` (`budget_id`),
                                CONSTRAINT `FKr1b7ake2ra6uj8h2bjxsfvjax` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `caixadiario`
--


DROP TABLE IF EXISTS `cartanfe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartanfe` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `emitente_id` bigint NOT NULL,
                            `chave` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cnpjcpf` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `codigouf` int NOT NULL,
                            `created_at` datetime DEFAULT NULL,
                            `estado` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `motivo` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `nrcarta` int NOT NULL,
                            `nrnfe` int NOT NULL,
                            `nrseq` int NOT NULL,
                            `pathxml` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `protocolo` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `serie` int NOT NULL,
                            `status` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cashflow`
--

DROP TABLE IF EXISTS `cashflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cashflow` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `emitente_id` bigint NOT NULL,
                            `balance` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `creditor` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `devedor` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `due_date` datetime DEFAULT NULL,
                            `duedate` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `forecast` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `paid` bit(1) DEFAULT NULL,
                            `type` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cfop`
--


DROP TABLE IF EXISTS `condicaopg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `condicaopg` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `emitente_id` bigint NOT NULL,
                              `description` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dias` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `incoming` bit(1) NOT NULL,
                              `vezes` int NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `emitente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emitente` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `emit_cep` varchar(12) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_cmun` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_crt` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_ie` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_nro` varchar(35) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_uf` varchar(6) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_xbairro` varchar(70) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_xcpl` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_xlgr` varchar(99) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_xmun` varchar(77) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_xnome` varchar(99) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_cnpj` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_cpf` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_email` varchar(99) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_rg` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `image` longblob,
                            `is_active` bit(1) DEFAULT NULL,
                            `is_paid` bit(1) DEFAULT NULL,
                            `payment_date` date DEFAULT NULL,
                            `start` date DEFAULT NULL,
                            `senha_cert` varchar(99) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cellnumber` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `phonenumber` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entrega`
--

DROP TABLE IF EXISTS `forma_pg_nfce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forma_pg_nfce` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `emitente_id` bigint NOT NULL,
                                 `condicoes` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                 `convenio` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                 `data_pg` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                 `forma` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                 `nota_number` int DEFAULT NULL,
                                 `nota_pg_id` bigint DEFAULT NULL,
                                 `value` double DEFAULT NULL,
                                 `troco` double DEFAULT NULL,
                                 `valor` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                 `valor_pg` double DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forma_pg_nfe`
--

DROP TABLE IF EXISTS `forma_pg_nfe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forma_pg_nfe` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `condicoes` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `convenio` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `data_pg` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `forma` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `nota_number` int DEFAULT NULL,
                                `nota_pg_id` bigint DEFAULT NULL,
                                `value` double DEFAULT NULL,
                                `troco` double DEFAULT NULL,
                                `valor` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `valor_pg` double DEFAULT NULL,
                                `emitente_id` bigint NOT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fornecedores`
--

DROP TABLE IF EXISTS `fornecedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornecedores` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `cmun` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `address` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `cellnumber` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `cep` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `city` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `compl` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `contact_phone1` varchar(70) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `contact_phone2` varchar(70) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `contact_phone3` varchar(70) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `contato1` varchar(70) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `contato2` varchar(70) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `contato3` varchar(70) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `corporate` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `cpf_cnpj` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `district` varchar(50) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `email` varchar(70) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `emitente_id` bigint DEFAULT NULL,
                                `indiedest` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `name` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `nickname` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `number` varchar(12) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `observation` text COLLATE utf8mb3_unicode_ci,
                                `phonenumber` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `register_date` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `rg_insc_estadual` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `state` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `tipo` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `ibpt_mg`
--

DROP TABLE IF EXISTS `movimentacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimentacao` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `emitente_id` bigint NOT NULL,
                                `data_mov` varchar(12) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `date_mov` date DEFAULT NULL,
                                `nr_entrada` int NOT NULL,
                                `nr_nfe` int NOT NULL,
                                `nr_saida` int NOT NULL,
                                `nr_venda` int NOT NULL,
                                `observacao` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `product_id` bigint DEFAULT NULL,
                                `quantidade` double DEFAULT NULL,
                                `tipo_mov` int DEFAULT NULL,
                                `user` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nf_e`
--

DROP TABLE IF EXISTS `notas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notas` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `emitente_id` bigint NOT NULL,
                         `number` int DEFAULT NULL,
                         `cfop` int DEFAULT NULL,
                         `chave` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `client` varchar(95) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `clientcpfcnpj` varchar(24) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `clientmail` varchar(90) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `data` varchar(35) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `freight` bit(1) DEFAULT NULL,
                         `freightvalue` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `idclient` bigint DEFAULT NULL,
                         `modelo` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `path` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `protocolo` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `salvo` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `seller` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `serie` int DEFAULT NULL,
                         `status` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         `total` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notas_consumidor`
--

DROP TABLE IF EXISTS `notas_consumidor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notas_consumidor` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `emitente_id` bigint NOT NULL,
                                    `cfop` int DEFAULT NULL,
                                    `chave` varchar(50) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `client` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `client_id` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `clientcpfcnpj` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `clientmail` varchar(90) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `data` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `freight` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `freightvalue` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `modelo` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `number` int NOT NULL,
                                    `path` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `salvo` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `seller` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `serie` int NOT NULL,
                                    `status` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    `total` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orc_areceber`
--

DROP TABLE IF EXISTS `orc_areceber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orc_areceber` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `emitente_id` bigint NOT NULL,
                                `addition` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `dateofoperation` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `description` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `devedor` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `discount` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `due_date` datetime DEFAULT NULL,
                                `duedate` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `efetive` bit(1) NOT NULL,
                                `final_value` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `observation` text COLLATE utf8mb3_unicode_ci,
                                `paid_value` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `type` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `value` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pagamentos`
--

DROP TABLE IF EXISTS `pagamentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagamentos` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `emitente_id` bigint NOT NULL,
                              `condicoes` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `convenio` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `data_pg` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `forma` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `value` double DEFAULT NULL,
                              `troco` double DEFAULT NULL,
                              `valor` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `valor_pg` double DEFAULT NULL,
                              `venda_id` bigint DEFAULT NULL,
                              `venda_pg_id` bigint DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permitir`
--


DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persons` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `emitente_id` bigint NOT NULL,
                           `address` varchar(84) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `birthday_date` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `c_mun` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `cellnumber` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `cep` varchar(12) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `city` varchar(70) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `cod_pais` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `cod_uf` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `compl` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `corporate` varchar(77) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `cpf_cnpj` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `district` varchar(40) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `email` varchar(70) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `id_estrangeiro` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `ind_ie_dest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `indfinal` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `insc_munic` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `name` varchar(77) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `nickname` varchar(77) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `number` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `observation` text COLLATE utf8mb3_unicode_ci,
                           `p_rural` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `phonenumber` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `register_date` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `rg_insc_estadual` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `state` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `suframa` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `tipo` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `pais` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `emitente_id` bigint NOT NULL,
                            `brand` varchar(55) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cean` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cest` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cfop` varchar(6) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cfop_fora` varchar(6) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cnpj_fabricante` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cofins` double DEFAULT NULL,
                            `commission` double DEFAULT NULL,
                            `cprod` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cst_cofins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cst_csosn` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cst_ipi` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cst_pis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cus_div` double DEFAULT NULL,
                            `description` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ean_valido_nfe` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `factory_name` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `fcp` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `gain_percentage` double DEFAULT NULL,
                            `gtin_valid` varchar(35) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms` double DEFAULT NULL,
                            `indescala` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ipi` double DEFAULT NULL,
                            `liquid_weight` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `m_obra` double DEFAULT NULL,
                            `mva` double DEFAULT NULL,
                            `ncm` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `observations` text COLLATE utf8mb3_unicode_ci,
                            `origem` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `peso_bruto` double DEFAULT NULL,
                            `peso_liquido` double DEFAULT NULL,
                            `pis` double DEFAULT NULL,
                            `porcento_lucro` double DEFAULT NULL,
                            `prod_ceantrib` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_cnpjfab` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_codif` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_cst` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_extipi` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_ind_tot` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_nve` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_pcicms` varchar(5) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_qcom` double DEFAULT NULL,
                            `prod_qtrib` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_ucom` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_ufcomb` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_utrib` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vbcicms` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vcofins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vicms` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vipi` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vpis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vun_com` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vun_trib` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_c_benef` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_c_prodanp` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_descanp` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_modbc` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_pglp` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_pgni` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_pgnn` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_qbcprod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_q_temp` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_v_aliq_prod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_v_desc` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vfcp` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_v_frete` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_v_outro` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_v_part` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_v_seg` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `purchasevalue` double DEFAULT NULL,
                            `red_base_icms` double DEFAULT NULL,
                            `register_date` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `salevalue` double DEFAULT NULL,
                            `status` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `stock` double DEFAULT NULL,
                            `stock_minimum` double DEFAULT NULL,
                            `tag_sub_trib_imp` bit(1) DEFAULT NULL,
                            `total_p_value` double DEFAULT NULL,
                            `total_s_value` double DEFAULT NULL,
                            `unit` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `valordecompra` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `valordevenda` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `weight` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_cst_cofins` varchar(6) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_cst_pis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_ppis` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_uf_destino` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_uf_origem` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vprod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_pfcpufdest` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_picmsinter` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_picmsinter_part` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_picmsufdest` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_qbcofins` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_qbpis` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_v_aliqstcofins` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_v_aliqstpis` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vbcfcpufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vbcst_pis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vbcst_confins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vbcufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vbc_pis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vbc_confins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vfcpufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vicmsufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vicmsufreme` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vstcofins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vstpis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_p_credsn` varchar(5) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_v_credicmssn` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                        `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `services` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `description` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dispatchdate` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `service_value` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `serviceorder` tinyblob,
                            `startdate` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `servicos`
--

DROP TABLE IF EXISTS `servicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
/* CREATE TABLE `servicos` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `description` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dispatchdate` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `service_value` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `serviceorder` tinyblob,
                            `startdate` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `aliquota` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cdlista` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cfop` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `codigo` int NOT NULL,
                            `codtribu` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cofins` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `comissao` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `descricao` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `infiscal` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `natoiss` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ncm` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `obs` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `pis` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `unidade` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `valor` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci; */
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transportadoras`
--

DROP TABLE IF EXISTS `transportadoras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transportadoras` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `emitente_id` bigint NOT NULL,
                                   `cep` varchar(12) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `cidade` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `cnpj` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `codigo` int DEFAULT NULL,
                                   `contato1` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `contato2` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `endereco` varchar(150) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `ie` varchar(20) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `nome` varchar(120) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `obs` text COLLATE utf8mb3_unicode_ci,
                                   `placa` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `rntc` varchar(20) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `telefone1` varchar(20) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `telefone2` varchar(20) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `uf` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   `ufveiculo` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `vendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendas` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `emitente_id` bigint NOT NULL,
                          `cfop_geral` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `client` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `client_cell` varchar(30) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `client_email` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `data` varchar(30) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `created_at` datetime DEFAULT NULL,
                          `id_client` bigint DEFAULT NULL,
                          `nr_venda` int DEFAULT NULL,
                          `pdf` bit(1) NOT NULL,
                          `updated_at` datetime DEFAULT NULL,
                          `valor` double DEFAULT NULL,
                          `venda_salva` bit(1) DEFAULT NULL,
                          `vendedor` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                          `client_id` bigint DEFAULT NULL,
                          `observacao` text COLLATE utf8mb3_unicode_ci,
                          PRIMARY KEY (`id`),
                          KEY `FKle5lvsny6sak9250schu1dfv8` (`client_id`),
                          CONSTRAINT `FKle5lvsny6sak9250schu1dfv8` FOREIGN KEY (`client_id`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vendasitems`
--

DROP TABLE IF EXISTS `vendasitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendasitems` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `brand` varchar(55) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `cst_csosn` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cean` varchar(40) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ucom` varchar(40) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cest` varchar(40) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cfop` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_desc` double NOT NULL,
                               `prod_ind_escala` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ncm` varchar(40) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_picms` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ppis` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_qcom` int DEFAULT NULL,
                               `prod_vprod` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vcofins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vicms` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vpis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vun_com` double NOT NULL,
                               `prod_xprod` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `product_id` bigint DEFAULT NULL,
                               `ref` bigint DEFAULT NULL,
                               `total_price` double NOT NULL,
                               `vendaid` bigint DEFAULT NULL,
                               `venda_id` bigint DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK3g212l5198fx6nsfvu0g02gyl` (`venda_id`),
                               CONSTRAINT `FK3g212l5198fx6nsfvu0g02gyl` FOREIGN KEY (`venda_id`) REFERENCES `vendas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vendaspg`
--

DROP TABLE IF EXISTS `vendaspg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendaspg` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `condicoes` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `convenio` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `data_pg` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `descont` double NOT NULL,
                            `entrada` double NOT NULL,
                            `forma` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `receive_value` double NOT NULL,
                            `troco` double NOT NULL,
                            `type` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `venda_id` bigint DEFAULT NULL,
                            `xentrada` int NOT NULL,
                            `xparcels` int NOT NULL,
                            `vendas_id` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FKfo99yx22lap8j4m625pqbp3tr` (`vendas_id`),
                            CONSTRAINT `FKfo99yx22lap8j4m625pqbp3tr` FOREIGN KEY (`vendas_id`) REFERENCES `vendas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vendedores`
--

DROP TABLE IF EXISTS `vendedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendedores` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `emitente_id` bigint NOT NULL,
                              `address` varchar(50) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `birthday_date` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `cellnumber` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `cep` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `city` varchar(42) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `commission` float NOT NULL,
                              `compl` varchar(30) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `cpf_cnpj` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `district` varchar(35) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `email` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `name` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `number` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `observation` text COLLATE utf8mb3_unicode_ci,
                              `phonenumber` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `register_date` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `state` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

DROP TABLE IF EXISTS `caixadiario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caixadiario` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `emitente_id` bigint NOT NULL,
                               `cellphone` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `codigo` int NOT NULL,
                               `cpf_cnpj` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `datamov_caixa` datetime DEFAULT NULL,
                               `created_at` datetime DEFAULT NULL,
                               `documento` varchar(90) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `emitente` varchar(90) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `fone` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `mov_type` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `nr_nfce` int DEFAULT NULL,
                               `nr_venda` int DEFAULT NULL,
                               `observacao` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `opened` bit(1) NOT NULL,
                               `saldo` double NOT NULL,
                               `updated_at` datetime DEFAULT NULL,
                               `valor` double NOT NULL,
                               `caixa_id` bigint DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK5r8lvef029pdngsqm8xvky48d` (`caixa_id`),
                               CONSTRAINT `FK5r8lvef029pdngsqm8xvky48d` FOREIGN KEY (`caixa_id`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `historico`
--

DROP TABLE IF EXISTS `historico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historico` (
                             `codigo` bigint NOT NULL,
                             `nome` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `id` bigint NOT NULL,
                             `caixa_id` bigint DEFAULT NULL,
                             PRIMARY KEY (`codigo`),
                             KEY `FKfwlhuuxgn4ed2pev470cd2te0` (`caixa_id`),
                             CONSTRAINT `FKfwlhuuxgn4ed2pev470cd2te0` FOREIGN KEY (`caixa_id`) REFERENCES `caixadiario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `dados_nfce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dados_nfce` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `valorcofins` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `valornf` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `valorpis` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `c_aut` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `cnpj_transp` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `cpf_transp` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `chave_nfce` varchar(49) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `cnpj_op_cartao` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `cofins_aliq_pcofins` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `cofins_aliq_vcofins` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `data_contingencia` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_cep` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_cmun` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_cnpj` varchar(55) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_ie` varchar(45) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_cpf` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_email` varchar(68) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_indiedest` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_nro` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_uf` int DEFAULT NULL,
                              `dest_xbairro` varchar(75) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_xcpl` varchar(155) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_xlgr` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_xmun` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_xnome` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_c_pais` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_fone` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_id_estrangeiro` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dest_x_pais` varchar(49) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `det_pag_tpag` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `det_pag_vpag` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_cep` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_cmun` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_cnpj` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_crt` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_ie` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_nro` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_uf` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_xbairro` varchar(35) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_xcpl` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_xlgr` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_xmun` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `emit_xnome` varchar(90) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `esp` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `finnfe` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `finalidade` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `hora_contingencia` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vbc` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vbcst` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vcofins` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vfcp` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vfcpst` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vicms` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vicmsdeson` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vipi` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vipidevol` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vnf` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_voutro` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vpis` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vprod` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vst` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vtot_trib` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_v_desc` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vfcpst_ret` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_v_frete` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_vii` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icms_tot_v_seg` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icmsvicms` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `icmsvicmsdeson` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_cdv` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_cmunfg` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_cnf` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_cuf` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_dh_emi` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_finnfe` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_id_dest` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_ind_final` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_ind_pres` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_mod` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_nnf` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_nat_op` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_proc_emi` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_serie` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_tp_amb` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_tp_emis` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_tp_imp` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_tpnf` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ide_ver_proc` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ie_transp` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ind_final` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ind_pag` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `ind_pres` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `inf_ad_fisco` varchar(244) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `inf_adic` varchar(244) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `inf_ad_imp` varchar(244) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `marca` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `mod_frete` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `modelo` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `n_vol` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `nfce_ref` varchar(45) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `nome_transp` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `numero` int NOT NULL,
                              `pesob` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `pesol` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `pis_aliq_cst` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `pis_aliq_ppis` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `pis_aliq_vbc` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `pis_aliq_vpis` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `prod_vprod` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `protocolo` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `q_vol` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `seller` varchar(40) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `serie` int NOT NULL,
                              `t_band` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `t_pag` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `tipo_emissao` int NOT NULL,
                              `tipo_danfe` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `tp_amb` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `tp_emis` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `tp_integra` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `transp_valor_frete` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `transp_valor_seguro` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `uf_transp` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `v_pag` double NOT NULL,
                              `v_troco` double NOT NULL,
                              `valor_basevbc` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `veiculo_placa` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `veiculo_uf` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `x_ender_transp` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `x_mun_transp` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `notas_id` bigint DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              KEY `FKrnu39ir64xirs5lbv23xws193` (`notas_id`),
                              CONSTRAINT `FKrnu39ir64xirs5lbv23xws193` FOREIGN KEY (`notas_id`) REFERENCES `notas_consumidor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dadosnfe`
--

DROP TABLE IF EXISTS `dadosnfe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dadosnfe` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `notas_id` bigint DEFAULT NULL,
                            `nota_id` bigint DEFAULT NULL,
                            `c_aut` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `c_munfg` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cnpj_transp` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cpf_transp` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `chave_nfe` varchar(49) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cnpj_op_cartao` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cofins_aliq_pcofins` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cofins_aliq_vcofins` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `data_emissao` date DEFAULT NULL,
                            `data_saida` date DEFAULT NULL,
                            `data_contingencia` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_cep` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_cmun` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_cnpj` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_cpf` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_email` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_indiedest` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_nro` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_uf` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_xbairro` varchar(49) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_xcpl` varchar(20) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_xlgr` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_xmun` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_xnome` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_c_pais` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_fone` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_id_estrangeiro` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_ie` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `dest_x_pais` varchar(49) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `det_pag_tpag` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `det_pag_vpag` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_cep` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_cmun` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_cnpj` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_crt` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_ie` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_nro` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_uf` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_xbairro` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_xcpl` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_xlgr` varchar(38) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `emit_xmun` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `esp` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `finnfe` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `finalidade` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `hora_contingencia` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vbc` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vbcst` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vcofins` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vfcp` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vfcpst` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vicms` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vicmsdeson` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vipi` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vipidevol` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vnf` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_voutro` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vpis` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vprod` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vst` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vtot_trib` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_v_desc` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vfcpst_ret` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_v_frete` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_vii` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `icms_tot_v_seg` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `id_dest` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_cdv` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_cmunfg` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_cnf` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_cuf` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_dh_emi` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_finnfe` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_id_dest` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_ind_final` varchar(9) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_ind_pres` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_mod` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_nnf` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_nat_op` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_proc_emi` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_serie` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_tp_amb` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_tp_emis` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_tp_imp` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_tpnf` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ide_ver_proc` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ie_transp` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ind_final` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ind_pag` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ind_pres` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `inf_ad_fisco` varchar(244) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `inf_adic` varchar(244) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `inf_ad_imp` varchar(244) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `marca` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `mod_frete` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `modelo` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `n_vol` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `nat_op` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `nfce_ref` varchar(45) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `nome_transp` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `pesob` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `pesol` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `pis_aliq_cst` varchar(11) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `pis_aliq_ppis` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `pis_aliq_vbc` varchar(11) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `pis_aliq_vpis` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `proc_emi` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `prod_vprod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `protocolo` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `q_vol` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `seller` varchar(44) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `setvprod` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `t_band` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `t_pag` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `tipo_emissao` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `tipo_danfe` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `tp_emis` varchar(11) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `tp_imp` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `tp_integra` varchar(2) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `tpnf` varchar(11) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `transp_mod_frete` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `transp_valor_frete` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `transp_valor_seguro` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ufsaida_pais` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `uf_transp` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `v_pag` double DEFAULT NULL,
                            `v_troco` double DEFAULT NULL,
                            `valor_basevbc` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `valorcofins` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `valornf` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `valorpis` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `veiculo_placa` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `veiculo_uf` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `ver_proc` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `x_ender_transp` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `x_loc_exporta` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `x_mun_transp` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FK8nj3bcorl907cfv6yev94hovl` (`notas_id`),
                            CONSTRAINT `FK8nj3bcorl907cfv6yev94hovl` FOREIGN KEY (`notas_id`) REFERENCES `notas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `emitente`
--

DROP TABLE IF EXISTS `entrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrega` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `dadosentrega_id` bigint DEFAULT NULL,
                           `c_mun` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `c_pais` varchar(11) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `cep` varchar(12) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `cpf_cnpj` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `email` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `fone` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `ie` varchar(15) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `nro` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `uf` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `x_bairro` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `x_cpl` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `x_lgr` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `x_mun` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `x_nome` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           `x_pais` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FKrunaevxrhys4p8vb1fym34vpp` (`dadosentrega_id`),
                           CONSTRAINT `FKrunaevxrhys4p8vb1fym34vpp` FOREIGN KEY (`dadosentrega_id`) REFERENCES `dadosnfe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entrega_nfce`
--

DROP TABLE IF EXISTS `entrega_nfce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrega_nfce` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `c_mun` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `c_pais` varchar(11) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `cep` varchar(12) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `cpf_cnpj` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `email` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `fone` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `ie` varchar(15) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `nro` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `uf` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `x_bairro` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `x_cpl` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `x_lgr` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `x_mun` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `x_nome` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `x_pais` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                                `dadosentrega_id` bigint DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FKc6xhh7u23b3jcsj3m7kwfo765` (`dadosentrega_id`),
                                CONSTRAINT `FKc6xhh7u23b3jcsj3m7kwfo765` FOREIGN KEY (`dadosentrega_id`) REFERENCES `dados_nfce` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forma_pg_nfce`
--

DROP TABLE IF EXISTS `itemsdanota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemsdanota` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `dados_id` bigint DEFAULT NULL,
                               `cofins` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `brand` varchar(55) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `cst_csosn` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cean` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ceantrib` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cest` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cfop` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cnpjfab` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cnpj_encomendante` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_codif` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cprod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cst` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cst_cofins` varchar(6) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_cst_pis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_desconto` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_extipi` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_iof` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ind_escala` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ind_tot` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ncm` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_nve` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_num_fci` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_pcicms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_picms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ppis` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_qcom` double DEFAULT NULL,
                               `prod_qtrib` varchar(11) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_red_icms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ucom` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ufcons` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ufdesemb` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ufterceiro` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_utrib` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_uf_destino` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_uf_origem` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vbcicms` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vcofins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vicms` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vipi` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vpis` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vprod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vun_com` double DEFAULT NULL,
                               `prod_vun_trib` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_xprod` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_c_benef` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_c_exportador` varchar(63) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_c_fabricante` varchar(63) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_c_prodanp` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ddi` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_d_desemb` varchar(6) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_descanp` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_desconto_adic` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_desp_aduaneira` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_modbc` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_n_adicao` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_ndi` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_n_draw` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_n_draw_imp` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_n_seq_adic` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_orig` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_pbc_icms` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_pfcp` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_pfcpufdest` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_pglp` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_pgni` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_pgnn` varchar(5) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_picmsinter` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_picmsinter_part` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_picmsufdest` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_pipi` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_p_redbc_icms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_pesob` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_pesol` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_qbcprod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_qbcofins` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_qbpis` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_q_temp` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_tp_intermedio` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_tp_via_transp` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vafrim` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_aliq_prod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_aliqstcofins` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_aliqstpis` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vbcfcpufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vbcst_pis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vbcst_confins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vbcufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vbc_pis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vbc_confins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_comb` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_desc` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_descdi` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vfcp` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vfcpufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_frete` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vicmsdeson` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vicmsufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vicmsufreme` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_outro` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_part` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vstcofins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vstpis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_seg` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_vl_imp_import` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_x_loc_desemb` varchar(63) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `product_id` bigint DEFAULT NULL,
                               `total_price` double DEFAULT NULL,
                               `prod_p_credsn` varchar(5) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               `prod_v_credicmssn` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKr8oqkgvqdhqudpic1055yuhn5` (`dados_id`),
                               CONSTRAINT `FKr8oqkgvqdhqudpic1055yuhn5` FOREIGN KEY (`dados_id`) REFERENCES `dadosnfe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `itemsnfce`
--

DROP TABLE IF EXISTS `itemsnfce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemsnfce` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `cofins` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `cst_csosn` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `brand` varchar(55) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_cean` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ceantrib` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_cest` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_cfop` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_cnpjfab` varchar(16) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_cnpj_encomendante` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_codif` varchar(28) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_cprod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_cst` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_cst_cofins` varchar(6) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_cst_pis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_desconto` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_extipi` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_iof` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ind_escala` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ind_tot` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ncm` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_nve` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_num_fci` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_pcicms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_picms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ppis` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_qcom` double DEFAULT NULL,
                             `prod_qtrib` varchar(11) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_red_icms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ucom` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ufcons` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ufdesemb` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ufterceiro` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_utrib` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_uf_destino` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_uf_origem` varchar(3) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vbcicms` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vcofins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vicms` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vipi` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vpis` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vprod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vun_com` double DEFAULT NULL,
                             `prod_vun_trib` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_xprod` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_c_benef` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_c_exportador` varchar(63) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_c_fabricante` varchar(63) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_c_prodanp` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ddi` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_d_desemb` varchar(6) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_descanp` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_desconto_adic` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_desp_aduaneira` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_modbc` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_n_adicao` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_ndi` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_n_draw` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_n_draw_imp` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_n_seq_adic` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_orig` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_pbc_icms` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_p_credsn` varchar(5) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_pfcp` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_pfcpufdest` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_pglp` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_pgni` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_pgnn` varchar(5) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_picmsinter` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_picmsinter_part` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_picmsufdest` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_pipi` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_p_redbc_icms` varchar(7) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_pesob` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_pesol` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_qbcprod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_qbcofins` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_qbpis` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_q_temp` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_tp_intermedio` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_tp_via_transp` varchar(4) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vafrim` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_aliq_prod` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_aliqstcofins` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_aliqstpis` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vbcfcpufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vbcst_pis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vbcst_confins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vbcufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vbc_pis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vbc_confins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_comb` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_credicmssn` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_desc` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_descdi` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vfcp` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vfcpufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_frete` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vicmsdeson` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vicmsufdest` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vicmsufreme` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_outro` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_part` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vstcofins` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vstpis` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_v_seg` varchar(21) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_vl_imp_import` varchar(14) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `prod_x_loc_desemb` varchar(63) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                             `product_id` bigint DEFAULT NULL,
                             `total_price` double DEFAULT NULL,
                             `dados_nfce_id` bigint DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             KEY `FKgjumjmvc2erx17jo2h1199qh9` (`dados_nfce_id`),
                             CONSTRAINT `FKgjumjmvc2erx17jo2h1199qh9` FOREIGN KEY (`dados_nfce_id`) REFERENCES `dados_nfce` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `condicaopg`
--

DROP TABLE IF EXISTS `condicaopg`;

CREATE TABLE `condicaopg` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `emitente_id` bigint NOT NULL,
                              `description` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `dias` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                              `incoming` bit(1) NOT NULL,
                              `vezes` int NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

INSERT INTO `condicaopg` (`emitente_id`, `description`, `dias`, `incoming`, `vezes`)
VALUES
    (@emitente_id, 'À VISTA - 30 DIAS', '0-30', b'1', 2),
    (@emitente_id, '3X SEM ENTRADA', '30-60-90', b'0', 3),
    (@emitente_id, '3X COM ENTRADA', '0-30-60', b'1', 3),
    (@emitente_id, '4X SEM ENTRADA', '30-60-90-120', b'0', 4),
    (@emitente_id, '4X COM ENTRADA', '0-30-60-90', b'1', 4),
    (@emitente_id, '5X SEM ENTRADA', '30-60-90-120-150', b'0', 5),
    (@emitente_id, '5X COM ENTRADA', '0-30-60-90-120', b'1', 5),
    (@emitente_id, '6X SEM ENTRADA', '30-60-90-120-150-180', b'0', 6),
    (@emitente_id, '6X COM ENTRADA', '0-30-60-90-120-150', b'1', 6),
    (@emitente_id, '10X SEM ENTRADA', '30-60-90-120-150-180-210-240-270-300', b'0', 10),
    (@emitente_id, '10X COM ENTRADA', '0-30-60-90-120-150-180-210-240-270', b'1', 10);

--
-- Table structure for table `ord_serv`
--

DROP TABLE IF EXISTS `ord_serv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ord_serv` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `emitente_id` bigint NOT NULL,
                            `aliquota` varchar(6) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `c_trib_nac` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `client_cell` varchar(30) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `client_email` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `client_id` bigint DEFAULT NULL,
                            `cliente` varchar(80) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `codigo` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `condicoes` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `cpf_cnpj` varchar(30) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `data_emissao` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `data_fechamento` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `descricao` varchar(244) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `equipamento` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `iss_retido` bit(1) DEFAULT NULL,
                            `obs` text COLLATE utf8mb3_unicode_ci,
                            `previsao_entrega` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `status` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `tecnico` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `tipo` varchar(30) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `unidade` varchar(6) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `valor_pecas` double DEFAULT NULL,
                            `valor_servico` double DEFAULT NULL,
                            `valor_total` double DEFAULT NULL,
                            `vendedor` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `vendedor_cell` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `serie` varchar(18) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            `marca` varchar(60) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-30 14:10:02
