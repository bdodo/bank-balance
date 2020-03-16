-- 4.2.4 Reporting - Find the transactional account per client with the highest balance
WITH  HIGHESTBALANCECLIENT
 AS

 (SELECT CLIENT_ID
              , MAX(DISPLAY_BALANCE) AS HIGHESTBALANCE
           FROM CLIENT_ACCOUNT
         GROUP
             BY CLIENT_ID
			 )

SELECT c.CLIENT_ID AS [Client Id],
       SURNAME AS [Client Surname],
	   MAX(DISPLAY_BALANCE) AS [Display Balance],
	   ca.CLIENT_ACCOUNT_NUMBER AS [Client Account Number],
	   at.DESCRIPTION AS [Account Description]
	      FROM  CLIENT  c
		  JOIN  HIGHESTBALANCECLIENT hbc
		  ON hbc.CLIENT_ID= c.CLIENT_ID
           JOIN CLIENT_ACCOUNT ca
		   ON  hbc.CLIENT_ID=ca.CLIENT_ID
		   AND hbc.HIGHESTBALANCE=ca.DISPLAY_BALANCE
		   JOIN ACCOUNT_TYPE at
		   ON at.ACCOUNT_TYPE_CODE=ca.ACCOUNT_TYPE_CODE

		   GROUP BY c.CLIENT_ID,
		             SURNAME,
					 ca.CLIENT_ACCOUNT_NUMBER,
					 at.DESCRIPTION