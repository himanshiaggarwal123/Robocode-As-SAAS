CREATE TABLE `robot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(100) DEFAULT NULL,
  `packageId` varchar(100) DEFAULT NULL,
  `robotId` varchar(100) DEFAULT NULL,
  `dataAccess` varchar(45) DEFAULT 'y',
  `filepath` varchar(100) DEFAULT NULL,
  `createdDate` varchar(45) DEFAULT NULL,
  `updatedDate` varchar(45) DEFAULT NULL,
  `robotcode` blob,
  `file` blob,
  `compiledcode` blob,
  `role` varchar(45) DEFAULT 'viewer',
  `StorageDirPath` varchar(100) DEFAULT NULL,
  `Games_played` int(11) DEFAULT '0',
  `Games_win` int(11) DEFAULT '0',
  `totalscores` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `robot_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=big5

CREATE TABLE `robot_role` (
  `role` varchar(45) DEFAULT NULL,
  `robotId` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=big5

CREATE TABLE `role` (
  `role_name` varchar(50) NOT NULL,
  `gae_role` varchar(45) DEFAULT NULL,
  `new_robot` varchar(5) DEFAULT NULL,
  `edit_robot` varchar(45) DEFAULT NULL,
  `view_robot` varchar(45) DEFAULT NULL,
  `edit_role` varchar(45) DEFAULT NULL,
  `edit_user` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=big5

CREATE TABLE `users` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `role` varchar(45) NOT NULL DEFAULT 'viewer',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=big5