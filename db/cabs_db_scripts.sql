-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cabs
-- ------------------------------------------------------
-- Server version	8.0.35

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

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `ApptId` bigint NOT NULL AUTO_INCREMENT,
  `DoctorId` int NOT NULL,
  `PatientId` int NOT NULL,
  `StartUtc` datetime NOT NULL,
  `EndUtc` datetime NOT NULL,
  `Status` enum('Booked','Cancelled','Completed') NOT NULL DEFAULT 'Booked',
  `CreatedBy` int DEFAULT NULL,
  `CreatedAt` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `CancelledAt` datetime(3) DEFAULT NULL,
  `UpdatedAt` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ApptId`),
  UNIQUE KEY `UX_Appointments_Doctor_Time` (`DoctorId`,`StartUtc`,`EndUtc`),
  KEY `IX_Appointments_DoctorId` (`DoctorId`),
  KEY `IX_Appointments_PatientId` (`PatientId`),
  CONSTRAINT `FK_Appointments_Doctors` FOREIGN KEY (`DoctorId`) REFERENCES `doctors` (`DoctorId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Appointments_Patients` FOREIGN KEY (`PatientId`) REFERENCES `patients` (`PatientId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `CK_Appointments_Range` CHECK ((`EndUtc` > `StartUtc`))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auditlog`
--

DROP TABLE IF EXISTS `auditlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditlog` (
  `LogId` bigint NOT NULL AUTO_INCREMENT,
  `UserId` int DEFAULT NULL,
  `Action` varchar(50) NOT NULL,
  `Entity` varchar(50) NOT NULL,
  `EntityId` varchar(64) DEFAULT NULL,
  `Payload` text,
  `CreatedAt` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`LogId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
  `DoctorId` int NOT NULL AUTO_INCREMENT,
  `UserId` int NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Specialty` varchar(100) DEFAULT NULL,
  `Email` varchar(320) DEFAULT NULL,
  `Phone` varchar(30) DEFAULT NULL,
  `IsActive` tinyint(1) NOT NULL DEFAULT '1',
  `IsDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `CreatedAt` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `UpdatedAt` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`DoctorId`),
  UNIQUE KEY `UX_Doctors_UserId` (`UserId`),
  CONSTRAINT `FK_Doctors_Users` FOREIGN KEY (`UserId`) REFERENCES `users` (`UserId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `emailoutbox`
--

DROP TABLE IF EXISTS `emailoutbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emailoutbox` (
  `EmailId` bigint NOT NULL AUTO_INCREMENT,
  `NotificationId` bigint DEFAULT NULL,
  `ToEmail` varchar(320) NOT NULL,
  `Subject` varchar(200) NOT NULL,
  `Body` text NOT NULL,
  `Status` tinyint NOT NULL DEFAULT '0',
  `Attempts` tinyint NOT NULL DEFAULT '0',
  `NextAttemptUtc` datetime DEFAULT NULL,
  `CreatedUtc` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `SentUtc` datetime DEFAULT NULL,
  PRIMARY KEY (`EmailId`),
  KEY `FK_EmailOutbox_Notifications` (`NotificationId`),
  KEY `IX_EmailOutbox_Status` (`Status`,`NextAttemptUtc`),
  CONSTRAINT `FK_EmailOutbox_Notifications` FOREIGN KEY (`NotificationId`) REFERENCES `notifications` (`NotificationId`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notificationrecipients`
--

DROP TABLE IF EXISTS `notificationrecipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notificationrecipients` (
  `NotificationId` bigint NOT NULL,
  `UserId` int NOT NULL,
  `IsRead` tinyint(1) NOT NULL DEFAULT '0',
  `ReadUtc` datetime DEFAULT NULL,
  `IsDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `DeletedUtc` datetime DEFAULT NULL,
  PRIMARY KEY (`NotificationId`,`UserId`),
  KEY `IX_NotificationRecipients_User` (`UserId`,`IsRead`,`IsDeleted`),
  CONSTRAINT `FK_NotificationRecipients_Notifications` FOREIGN KEY (`NotificationId`) REFERENCES `notifications` (`NotificationId`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `NotificationId` bigint NOT NULL AUTO_INCREMENT,
  `Type` varchar(40) NOT NULL,
  `Title` varchar(200) NOT NULL,
  `Body` varchar(1000) NOT NULL,
  `Severity` tinyint NOT NULL DEFAULT '0',
  `RelatedEntityType` varchar(40) DEFAULT NULL,
  `RelatedEntityId` bigint DEFAULT NULL,
  `CreatedUtc` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreatedByUserId` int DEFAULT NULL,
  `IsSystem` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`NotificationId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `PatientId` int NOT NULL AUTO_INCREMENT,
  `UserId` int NOT NULL,
  `FullName` varchar(100) NOT NULL,
  `Phone` varchar(30) DEFAULT NULL,
  `IsDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `CreatedAt` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `UpdatedAt` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`PatientId`),
  UNIQUE KEY `UX_Patients_UserId` (`UserId`),
  CONSTRAINT `FK_Patients_Users` FOREIGN KEY (`UserId`) REFERENCES `users` (`UserId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `slots`
--

DROP TABLE IF EXISTS `slots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slots` (
  `SlotId` bigint NOT NULL AUTO_INCREMENT,
  `DoctorId` int NOT NULL,
  `StartUtc` datetime NOT NULL,
  `EndUtc` datetime NOT NULL,
  `IsAvailable` tinyint(1) NOT NULL DEFAULT '1',
  `Source` enum('Admin','Doctor') NOT NULL DEFAULT 'Doctor',
  `CreatedBy` int DEFAULT NULL,
  `CreatedAt` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`SlotId`),
  UNIQUE KEY `UX_Slots_Doctor_Time` (`DoctorId`,`StartUtc`,`EndUtc`),
  CONSTRAINT `FK_Slots_Doctors` FOREIGN KEY (`DoctorId`) REFERENCES `doctors` (`DoctorId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `CK_Slots_Range` CHECK ((`EndUtc` > `StartUtc`))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `UserId` int NOT NULL AUTO_INCREMENT,
  `Email` varchar(320) NOT NULL,
  `PasswordHash` varbinary(64) NOT NULL,
  `Salt` varbinary(32) NOT NULL,
  `Role` enum('Doctor','Patient','Admin') NOT NULL,
  `IsActive` tinyint(1) NOT NULL DEFAULT '0',
  `CreatedAt` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `UpdatedAt` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `UX_Users_Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'cabs'
--

--
-- Dumping routines for database 'cabs'
--
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_ActivateUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_ActivateUser`(IN p_UserId INT, IN p_IsActive TINYINT)
BEGIN
  IF NOT EXISTS (SELECT 1 FROM Users WHERE UserId = p_UserId) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'User not found.';
  END IF;

  UPDATE Users
     SET IsActive = p_IsActive,
         UpdatedAt = CURRENT_TIMESTAMP(3)
   WHERE UserId = p_UserId;

  SELECT UserId, IsActive FROM Users WHERE UserId = p_UserId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_AdminCancelAppointment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_AdminCancelAppointment`(
  IN p_ApptId BIGINT,
  IN p_AdminUserId INT,
  IN p_Reason VARCHAR(200)
)
BEGIN
  DECLARE v_DoctorId INT;
  DECLARE v_StartUtc DATETIME(0);
  DECLARE v_EndUtc   DATETIME(0);
  DECLARE v_Status   VARCHAR(12);
  DECLARE v_DoctorUserId  INT;
  DECLARE v_PatientUserId INT;

  SELECT DoctorId, StartUtc, EndUtc, Status
    INTO v_DoctorId, v_StartUtc, v_EndUtc, v_Status
  FROM Appointments
  WHERE ApptId = p_ApptId;

  IF v_DoctorId IS NULL THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Appointment not found.';
  END IF;

  IF v_Status <> 'Booked' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Only booked appointments can be cancelled.';
  END IF;

  START TRANSACTION;

    UPDATE Appointments
       SET Status='Cancelled',
           CancelledAt=CURRENT_TIMESTAMP(3),
           UpdatedAt=CURRENT_TIMESTAMP(3)
     WHERE ApptId = p_ApptId;

    UPDATE Slots
       SET IsAvailable=1
     WHERE DoctorId=v_DoctorId AND StartUtc=v_StartUtc AND EndUtc=v_EndUtc;

    INSERT INTO AuditLog(UserId, Action, Entity, EntityId, Payload)
    VALUES(p_AdminUserId, 'AdminCancel', 'Appointments', CAST(p_ApptId AS CHAR(64)), p_Reason);

  COMMIT;

  SELECT UserId INTO v_DoctorUserId FROM Doctors WHERE DoctorId=v_DoctorId;
  SELECT u.UserId INTO v_PatientUserId
    FROM Appointments a
    JOIN Patients p ON p.PatientId=a.PatientId
    JOIN Users u ON u.UserId=p.UserId
   WHERE a.ApptId=p_ApptId;

  CALL DABS_sp_Notify_Create(
    'AppointmentCancelled',
    'Appointment cancelled by admin/doctor',
    COALESCE(p_Reason, 'Your appointment was cancelled by admin/doctor.'),
    0,
    'Appointment',
    p_ApptId,
    p_AdminUserId,
    JSON_ARRAY(v_DoctorUserId, v_PatientUserId)
  );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_AdminGenerateSlots` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_AdminGenerateSlots`(
  IN p_DoctorId INT,
  IN p_WorkDate DATE,
  IN p_StartHour TINYINT,
  IN p_EndHour   TINYINT,
  IN p_AdminUserId INT
)
BEGIN
  CALL DABS_sp_DoctorGenerateSlots(p_DoctorId, p_WorkDate, p_StartHour, p_EndHour, p_AdminUserId, 'Admin');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_AdminGenerateSlotsRange` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_AdminGenerateSlotsRange`(
  IN p_DoctorId INT,
  IN p_FromDate DATE,
  IN p_ToDate   DATE,
  IN p_StartHour TINYINT,
  IN p_EndHour   TINYINT,
  IN p_AdminUserId INT
)
BEGIN
  DECLARE v_d DATE;
  IF p_ToDate < p_FromDate THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Invalid date range.';
  END IF;
  SET v_d = p_FromDate;
  WHILE v_d <= p_ToDate DO
    CALL DABS_sp_DoctorGenerateSlots(p_DoctorId, v_d, p_StartHour, p_EndHour, p_AdminUserId, 'Admin');
    SET v_d = DATE_ADD(v_d, INTERVAL 1 DAY);
  END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_BookAppointment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_BookAppointment`(
  IN p_DoctorId  INT,
  IN p_PatientId INT,
  IN p_StartUtc  DATETIME(0),
  IN p_EndUtc    DATETIME(0),
  IN p_ByUserId  INT
)
BEGIN
  DECLARE v_ApptId BIGINT;
  DECLARE v_DoctorUserId INT;
  DECLARE v_PatientUserId INT;

  IF p_EndUtc <= p_StartUtc THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Invalid time range.';
  END IF;

  START TRANSACTION;
    -- Claim slot if available
    UPDATE Slots
       SET IsAvailable = 0
     WHERE DoctorId = p_DoctorId
       AND StartUtc = p_StartUtc
       AND EndUtc   = p_EndUtc
       AND IsAvailable = 1;
    IF ROW_COUNT() = 0 THEN
      ROLLBACK;
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Slot unavailable or does not exist.';
    END IF;

    INSERT INTO Appointments(DoctorId, PatientId, StartUtc, EndUtc, Status, CreatedBy)
    VALUES(p_DoctorId, p_PatientId, p_StartUtc, p_EndUtc, 'Booked', p_ByUserId);
    SET v_ApptId = LAST_INSERT_ID();
  COMMIT;

  SELECT UserId INTO v_DoctorUserId FROM Doctors WHERE DoctorId=p_DoctorId;
  SELECT u.UserId INTO v_PatientUserId
    FROM Patients p JOIN Users u ON u.UserId=p.UserId
   WHERE p.PatientId=p_PatientId;

  -- Notify (best-effort)
  CALL DABS_sp_Notify_Create(
    'AppointmentBooked',
    'Appointment booked',
    'Your appointment has been booked successfully.',
    0,
    'Appointment',
    v_ApptId,
    p_ByUserId,
    JSON_ARRAY(v_DoctorUserId, v_PatientUserId)
  );

  SELECT v_ApptId AS ApptId, 'Booked' AS Status;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_CancelAppointment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_CancelAppointment`(
  IN p_ApptId BIGINT,
  IN p_PatientId INT,
  IN p_ByUserId INT
)
BEGIN
  DECLARE v_DoctorId INT;
  DECLARE v_StartUtc DATETIME(0);
  DECLARE v_EndUtc   DATETIME(0);
  DECLARE v_OwnerPatientId INT;
  DECLARE v_Status VARCHAR(12);
  DECLARE v_DoctorUserId INT;
  DECLARE v_PatientUserId INT;

  SELECT DoctorId, StartUtc, EndUtc, PatientId, Status
    INTO v_DoctorId, v_StartUtc, v_EndUtc, v_OwnerPatientId, v_Status
  FROM Appointments WHERE ApptId=p_ApptId;

  IF v_DoctorId IS NULL THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Appointment not found.';
  END IF;
  IF v_OwnerPatientId <> p_PatientId THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Not owner of appointment.';
  END IF;
  IF v_Status <> 'Booked' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Only booked appointments can be cancelled.';
  END IF;

  START TRANSACTION;
    UPDATE Appointments
       SET Status='Cancelled', CancelledAt=CURRENT_TIMESTAMP(3), UpdatedAt=CURRENT_TIMESTAMP(3)
     WHERE ApptId=p_ApptId;

    UPDATE Slots
       SET IsAvailable=1
     WHERE DoctorId=v_DoctorId AND StartUtc=v_StartUtc AND EndUtc=v_EndUtc;
  COMMIT;

  SELECT UserId INTO v_DoctorUserId FROM Doctors WHERE DoctorId=v_DoctorId;
  SELECT u.UserId INTO v_PatientUserId
    FROM Patients p JOIN Users u ON u.UserId=p.UserId
   WHERE p.PatientId=v_OwnerPatientId;

  CALL DABS_sp_Notify_Create(
    'AppointmentCancelled',
    'Appointment cancelled',
    'Your appointment has been cancelled and the slot is available again.',
    0,
    'Appointment',
    p_ApptId,
    p_ByUserId,
    JSON_ARRAY(v_DoctorUserId, v_PatientUserId)
  );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_ClearSlotsRange` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_ClearSlotsRange`(
  IN p_DoctorId INT,
  IN p_FromDate DATE,
  IN p_ToDate   DATE
)
BEGIN
  IF p_ToDate < p_FromDate THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Invalid date range.';
  END IF;

  DELETE s
    FROM Slots s
   WHERE s.DoctorId = p_DoctorId
     AND s.StartUtc >= CAST(p_FromDate AS DATETIME)
     AND s.EndUtc   <  DATE_ADD(CAST(p_ToDate AS DATETIME), INTERVAL 1 DAY)
     AND s.IsAvailable = 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_CreateDoctor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_CreateDoctor`(
  IN p_Email VARCHAR(320),
  IN p_PasswordHash VARBINARY(64),
  IN p_Salt VARBINARY(32),
  IN p_Name VARCHAR(100),
  IN p_Specialty VARCHAR(100),
  IN p_Phone VARCHAR(30),
  IN p_IsActive TINYINT
)
BEGIN
  DECLARE v_UserId INT;
  IF EXISTS (SELECT 1 FROM Users WHERE Email=p_Email) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Email already exists.';
  END IF;

  INSERT INTO Users(Email, PasswordHash, Salt, Role, IsActive)
  VALUES(p_Email, p_PasswordHash, p_Salt, 'Doctor', COALESCE(p_IsActive,1));
  SET v_UserId = LAST_INSERT_ID();

  INSERT INTO Doctors(UserId, Name, Specialty, Email, Phone, IsActive)
  VALUES(v_UserId, p_Name, p_Specialty, p_Email, p_Phone, COALESCE(p_IsActive,1));

  SELECT v_UserId AS UserId, LAST_INSERT_ID() AS DoctorId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_DeleteDoctorSoft` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_DeleteDoctorSoft`(IN p_DoctorId INT)
BEGIN
  DECLARE v_UserId INT;
  SELECT UserId INTO v_UserId FROM Doctors WHERE DoctorId=p_DoctorId;
  IF v_UserId IS NULL THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Doctor not found.';
  END IF;

  UPDATE Doctors SET IsDeleted=1, IsActive=0, UpdatedAt=CURRENT_TIMESTAMP(3) WHERE DoctorId=p_DoctorId;
  UPDATE Users   SET IsActive=0, UpdatedAt=CURRENT_TIMESTAMP(3) WHERE UserId=v_UserId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_DeletePatientSoft` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_DeletePatientSoft`(IN p_PatientId INT)
BEGIN
  DECLARE v_UserId INT;
  SELECT UserId INTO v_UserId FROM Patients WHERE PatientId=p_PatientId;
  IF v_UserId IS NULL THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Patient not found.';
  END IF;

  UPDATE Patients SET IsDeleted=1, UpdatedAt=CURRENT_TIMESTAMP(3) WHERE PatientId=p_PatientId;
  UPDATE Users    SET IsActive=0, UpdatedAt=CURRENT_TIMESTAMP(3) WHERE UserId=v_UserId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_DoctorGenerateSlots` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_DoctorGenerateSlots`(
  IN p_DoctorId INT,
  IN p_WorkDate DATE,
  IN p_StartHour TINYINT,
  IN p_EndHour   TINYINT,
  IN p_ByUserId  INT,
  IN p_Source    VARCHAR(10)
)
BEGIN
  DECLARE v_start DATETIME(0);
  DECLARE v_stop  DATETIME(0);
  DECLARE v_slotStart DATETIME(0);
  DECLARE v_slotEnd   DATETIME(0);

  IF (p_StartHour < 0 OR p_StartHour > 23 OR p_EndHour < 1 OR p_EndHour > 24 OR p_EndHour <= p_StartHour) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Invalid hour window.';
  END IF;

  SET v_start = DATE_ADD(CAST(p_WorkDate AS DATETIME), INTERVAL p_StartHour HOUR);
  SET v_stop  = DATE_ADD(CAST(p_WorkDate AS DATETIME), INTERVAL p_EndHour   HOUR);
  SET v_slotStart = v_start;

  WHILE v_slotStart < v_stop DO
    SET v_slotEnd = DATE_ADD(v_slotStart, INTERVAL 30 MINUTE);

    -- idempotent insert
    INSERT INTO Slots(DoctorId, StartUtc, EndUtc, IsAvailable, Source, CreatedBy)
    SELECT p_DoctorId, v_slotStart, v_slotEnd, 1, COALESCE(p_Source,'Doctor'), p_ByUserId
    WHERE NOT EXISTS (
      SELECT 1 FROM Slots WHERE DoctorId=p_DoctorId AND StartUtc=v_slotStart AND EndUtc=v_slotEnd
    );

    SET v_slotStart = v_slotEnd;
  END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_GetDoctorIdByUserId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_GetDoctorIdByUserId`(IN p_UserId INT)
BEGIN
  SELECT d.DoctorId
  FROM Doctors d
  WHERE d.UserId = p_UserId AND (d.IsDeleted = 0 OR d.IsDeleted IS NULL)
  LIMIT 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_GetPatientById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_GetPatientById`(IN p_PatientId INT)
BEGIN
  SELECT p.PatientId, p.FullName, p.Phone
  FROM Patients p
  WHERE p.PatientId = p_PatientId AND COALESCE(p.IsDeleted,0) = 0
  LIMIT 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_GetPatientIdByUserId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_GetPatientIdByUserId`(IN p_UserId INT)
BEGIN
  SELECT p.PatientId
  FROM Patients p
  WHERE p.UserId = p_UserId AND (p.IsDeleted = 0 OR p.IsDeleted IS NULL)
  LIMIT 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_GetUserSaltByEmail` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_GetUserSaltByEmail`(IN p_Email VARCHAR(320))
BEGIN
  SELECT Salt FROM Users WHERE Email=p_Email;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_ListAppointments` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_ListAppointments`(
  IN p_DoctorId INT,
  IN p_PatientId INT,
  IN p_FromUtc DATETIME(0),
  IN p_ToUtc   DATETIME(0)
)
BEGIN
  SELECT a.ApptId, a.DoctorId, a.PatientId, a.StartUtc, a.EndUtc, a.Status,
         d.Name AS DoctorName, d.Specialty,
         p.FullName AS PatientName, p.Phone AS PatientPhone
  FROM Appointments a
  JOIN Doctors  d ON d.DoctorId  = a.DoctorId
  JOIN Patients p ON p.PatientId = a.PatientId
  WHERE (p_DoctorId IS NULL OR a.DoctorId = p_DoctorId)
    AND (p_PatientId IS NULL OR a.PatientId = p_PatientId)
    AND a.StartUtc >= p_FromUtc
    AND a.EndUtc   <= p_ToUtc
  ORDER BY a.StartUtc DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_ListAppointmentsByDoctor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_ListAppointmentsByDoctor`(
  IN p_DoctorId INT,
  IN p_FromUtc DATETIME(0),
  IN p_ToUtc   DATETIME(0)
)
BEGIN
  SELECT a.ApptId, a.DoctorId, a.PatientId, a.StartUtc, a.EndUtc, a.Status,
         a.CreatedBy, a.CreatedAt, a.CancelledAt, a.UpdatedAt,
         p.PatientId AS PatKey, p.PatientId, p.FullName, p.Phone
  FROM Appointments a
  JOIN Patients p ON p.PatientId = a.PatientId
  WHERE a.DoctorId = p_DoctorId
    AND a.StartUtc >= p_FromUtc AND a.EndUtc <= p_ToUtc
  ORDER BY a.StartUtc DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_ListAppointmentsByPatient` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_ListAppointmentsByPatient`(
  IN p_PatientId INT,
  IN p_FromUtc DATETIME(0),
  IN p_ToUtc   DATETIME(0)
)
BEGIN
  SELECT a.ApptId, a.DoctorId, a.PatientId, a.StartUtc, a.EndUtc, a.Status,
         a.CreatedBy, a.CreatedAt, a.CancelledAt, a.UpdatedAt,
         d.DoctorId AS DocKey, d.DoctorId, d.Name, d.Specialty, d.Email, d.Phone
  FROM Appointments a
  JOIN Doctors d ON d.DoctorId = a.DoctorId
  WHERE a.PatientId = p_PatientId
    AND a.StartUtc >= p_FromUtc AND a.EndUtc <= p_ToUtc
  ORDER BY a.StartUtc DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_ListAvailableSlots` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_ListAvailableSlots`(
  IN p_DoctorId INT,
  IN p_WorkDate DATE
)
BEGIN
  SELECT SlotId, DoctorId, StartUtc, EndUtc
  FROM Slots
  WHERE DoctorId = p_DoctorId
    AND IsAvailable = 1
    AND StartUtc >= CAST(p_WorkDate AS DATETIME)
    AND EndUtc   <  DATE_ADD(CAST(p_WorkDate AS DATETIME), INTERVAL 1 DAY)
  ORDER BY StartUtc;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_ListDoctorsBasic` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_ListDoctorsBasic`()
BEGIN
  SELECT DoctorId, Name, Specialty, Email, Phone
  FROM Doctors
  WHERE IsActive = 1 AND IsDeleted = 0
  ORDER BY Name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_ListPatientsPendingActivation` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_ListPatientsPendingActivation`()
BEGIN
  SELECT p.PatientId, p.UserId, p.FullName, p.Phone, u.Email, p.IsDeleted, p.CreatedAt, p.UpdatedAt
  FROM Patients p
  JOIN Users u ON u.UserId = p.UserId
  WHERE u.Role = 'Patient' AND u.IsActive = 0 AND p.IsDeleted = 0
  ORDER BY p.FullName;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_Login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_Login`(
  IN p_Email VARCHAR(320),
  IN p_PasswordHash VARBINARY(64)
)
BEGIN
  DECLARE v_UserId INT;
  DECLARE v_Role   VARCHAR(16);
  DECLARE v_IsActive TINYINT;
  DECLARE v_Hash VARBINARY(64);

  SELECT UserId, Role, IsActive, PasswordHash
    INTO v_UserId, v_Role, v_IsActive, v_Hash
  FROM Users WHERE Email=p_Email;

  IF v_UserId IS NULL THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Invalid credentials.';
  END IF;
  IF v_IsActive = 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Account not active.';
  END IF;
  IF v_Hash <> p_PasswordHash THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Invalid credentials.';
  END IF;

  SELECT v_UserId AS UserId, v_Role AS Role, v_IsActive AS IsActive;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_Notifications_CountUnread` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_Notifications_CountUnread`(IN p_UserId INT)
BEGIN
  SELECT COUNT(*) AS Unread
  FROM NotificationRecipients
  WHERE UserId=p_UserId AND IsDeleted=0 AND IsRead=0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_Notifications_Delete` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_Notifications_Delete`(
  IN p_UserId INT,
  IN p_NotificationId BIGINT
)
BEGIN
  UPDATE NotificationRecipients
     SET IsDeleted=1, DeletedUtc=CURRENT_TIMESTAMP
   WHERE UserId=p_UserId AND NotificationId=p_NotificationId AND IsDeleted=0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_Notifications_ListForUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_Notifications_ListForUser`(
  IN p_UserId INT,
  IN p_OnlyUnread TINYINT,
  IN p_Top INT
)
BEGIN
  SELECT n.NotificationId, n.Type, n.Title, n.Body, n.Severity,
         n.RelatedEntityType, n.RelatedEntityId, n.CreatedUtc,
         r.IsRead, r.ReadUtc, r.IsDeleted, r.DeletedUtc
  FROM NotificationRecipients r
  JOIN Notifications n ON n.NotificationId = r.NotificationId
  WHERE r.UserId = p_UserId
    AND r.IsDeleted = 0
    AND (p_OnlyUnread = 0 OR r.IsRead = 0)
  ORDER BY n.CreatedUtc DESC
  LIMIT p_Top;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_Notifications_MarkAll` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_Notifications_MarkAll`(IN p_UserId INT)
BEGIN
  UPDATE NotificationRecipients
     SET IsRead = 1, ReadUtc = CURRENT_TIMESTAMP
   WHERE UserId = p_UserId AND IsRead = 0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_Notifications_MarkRead` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_Notifications_MarkRead`(
  IN p_UserId INT,
  IN p_NotificationId BIGINT
)
BEGIN
  UPDATE NotificationRecipients
     SET IsRead=1, ReadUtc=CURRENT_TIMESTAMP
   WHERE UserId=p_UserId AND NotificationId=p_NotificationId AND IsDeleted=0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_Notify_Create` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_Notify_Create`(
  IN p_Type VARCHAR(40),
  IN p_Title VARCHAR(200),
  IN p_Body  VARCHAR(1000),
  IN p_Severity TINYINT,
  IN p_RelatedEntityType VARCHAR(40),
  IN p_RelatedEntityId BIGINT,
  IN p_CreatedByUserId INT,
  IN p_RecipientUserIdsJson JSON
)
BEGIN
  DECLARE v_NId BIGINT;
  INSERT INTO Notifications(Type, Title, Body, Severity, RelatedEntityType, RelatedEntityId, CreatedByUserId)
  VALUES(p_Type, p_Title, p_Body, COALESCE(p_Severity,0), p_RelatedEntityType, p_RelatedEntityId, p_CreatedByUserId);
  SET v_NId = LAST_INSERT_ID();

  INSERT INTO NotificationRecipients(NotificationId, UserId)
  SELECT v_NId, jt.id
  FROM JSON_TABLE(p_RecipientUserIdsJson, '$[*]' COLUMNS (id INT PATH '$')) AS jt;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_RegisterPatient` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_RegisterPatient`(
  IN p_Email VARCHAR(320),
  IN p_PasswordHash VARBINARY(64),
  IN p_Salt VARBINARY(32),
  IN p_FullName VARCHAR(100),
  IN p_Phone VARCHAR(30)
)
BEGIN
  DECLARE v_UserId INT;
  IF EXISTS (SELECT 1 FROM Users WHERE Email=p_Email) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Email already exists.';
  END IF;

  INSERT INTO Users(Email, PasswordHash, Salt, Role, IsActive)
  VALUES(p_Email, p_PasswordHash, p_Salt, 'Patient', 0);
  SET v_UserId = LAST_INSERT_ID();

  INSERT INTO Patients(UserId, FullName, Phone)
  VALUES(v_UserId, p_FullName, p_Phone);

  SELECT v_UserId AS UserId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_Report_DoctorAppointments` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_Report_DoctorAppointments`(
  IN p_DoctorId INT,
  IN p_FromUtc  DATETIME(0),
  IN p_ToUtc    DATETIME(0)
)
BEGIN
  SELECT a.ApptId,
         a.StartUtc,
         a.EndUtc,
         TIMESTAMPDIFF(MINUTE, a.StartUtc, a.EndUtc) AS DurationMinutes,
         a.Status,
         p.FullName AS PatientName,
         CONCAT(LEFT(COALESCE(p.Phone,''),3),'****',RIGHT(COALESCE(p.Phone,''),2)) AS PatientPhoneMasked
  FROM Appointments a
  JOIN Patients p ON p.PatientId=a.PatientId
  WHERE a.DoctorId=p_DoctorId
    AND a.StartUtc >= p_FromUtc AND a.EndUtc <= p_ToUtc
  ORDER BY a.StartUtc;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_Report_DoctorAppointments_Csv` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_Report_DoctorAppointments_Csv`(
  IN p_DoctorId INT,
  IN p_FromUtc  DATETIME(0),
  IN p_ToUtc    DATETIME(0)
)
BEGIN
  SELECT a.ApptId, a.StartUtc, a.EndUtc,
         TIMESTAMPDIFF(MINUTE, a.StartUtc, a.EndUtc) AS DurationMinutes,
         a.Status, p.FullName, p.Phone
  FROM Appointments a
  JOIN Patients p ON p.PatientId=a.PatientId
  WHERE a.DoctorId=p_DoctorId
    AND a.StartUtc >= p_FromUtc AND a.EndUtc <= p_ToUtc
  ORDER BY a.StartUtc;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_Report_DoctorTotals` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_Report_DoctorTotals`(
  IN p_DoctorId INT,
  IN p_FromUtc  DATETIME(0),
  IN p_ToUtc    DATETIME(0)
)
BEGIN
  SELECT COUNT(*) AS TotalAppointments,
         SUM(TIMESTAMPDIFF(MINUTE, StartUtc, EndUtc)) AS TotalMinutes,
         CAST(SUM(TIMESTAMPDIFF(MINUTE, StartUtc, EndUtc))/60.0 AS DECIMAL(10,2)) AS TotalHours
  FROM Appointments
  WHERE DoctorId=p_DoctorId
    AND StartUtc >= p_FromUtc AND EndUtc <= p_ToUtc
    AND Status='Booked';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_UpdateDoctor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_UpdateDoctor`(
  IN p_DoctorId INT,
  IN p_Name VARCHAR(100),
  IN p_Specialty VARCHAR(100),
  IN p_Email VARCHAR(320),
  IN p_Phone VARCHAR(30),
  IN p_IsActive TINYINT
)
BEGIN
  IF NOT EXISTS (SELECT 1 FROM Doctors WHERE DoctorId=p_DoctorId) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Doctor not found.';
  END IF;

  UPDATE Doctors
  SET Name      = p_Name,
      Specialty = p_Specialty,
      Email     = p_Email,
      Phone     = p_Phone,
      IsActive  = COALESCE(p_IsActive, IsActive),
      UpdatedAt = CURRENT_TIMESTAMP(3)
  WHERE DoctorId=p_DoctorId;

  SELECT * FROM Doctors WHERE DoctorId=p_DoctorId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DABS_sp_UpdatePatient` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cabs_dba`@`localhost` PROCEDURE `DABS_sp_UpdatePatient`(
  IN p_PatientId INT,
  IN p_FullName  VARCHAR(100),
  IN p_Phone     VARCHAR(30)
)
BEGIN
  IF NOT EXISTS (SELECT 1 FROM Patients WHERE PatientId=p_PatientId) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Patient not found.';
  END IF;

  UPDATE Patients
  SET FullName=p_FullName, Phone=p_Phone, UpdatedAt=CURRENT_TIMESTAMP(3)
  WHERE PatientId=p_PatientId;

  SELECT * FROM Patients WHERE PatientId=p_PatientId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-18 12:25:12
