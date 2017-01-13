declare
  str varchar2(32767);
begin
str :='
{
	"accessSpeeds": {
		"ETHERNET": {
			"displayValue" : "Ethernet",
			"accessSpeedImagePath": "images/ethernet.png",
			"range": [
				{ 
				  "speed": "2000", 
				  "MRC": "1550-8702/site",
				  "NRC": "1296-9525/site"
				},
				{ 
				  "speed": "10000", 
				  "MRC": "1688-7924/site",
				  "NRC": "4428-8063/site"
				},
				{ 
				  "speed": "50000", 
				  "MRC": "1496-9255/site",
				  "NRC": "1348-6417/site"
				},
				{ 
				  "speed": "150000", 
				  "MRC": "2199-9543/site",
				  "NRC": "1499-7507/site"
				},
				{ 
				  "speed": "500000", 
				  "MRC": "4767-8961/site",
				  "NRC": "1811-9933/site"
				}				
			],
			"physicalInterferenceOptions": [{
					"key": "ethernetlxBase100",
					"value": "Ethernet 100 Base LX"
				}, {
					"key": "ethernetlxBase200",
					"value": "Ethernet 200 Base LX"
				}, {
					"key": "ethernetlxBase300",
					"value": "Ethernet 300 Base LX"
				}
			],
			"accessInterconnectTechnology": [{
					"key": "ethernetdirectConnect",
					"value": "Ethernet Direct Connect"
				}, {
					"key": "ethernetindirectConnect",
					"value": "Ethernet Indirect Connect"
				}
			],
			"localAccessManagementOptions": [{
					"key": "ethernet200",
					"value": "Ethernet 200"
				}, {
					"key": "ethernet500",
					"value": "Ethernet 500"
				}
			],	
			"accessTailTechnologyOptions": [{
					"key": "ethernetshared",
					"value": "Ethernet ESP ETH Shared"
				}, {
					"key": "ethernetdedicated",
					"value": "Ethernet ESP ETH Dedicated"
				}
			],	
			"accessArchitectureOptions": [{
					"key": "ethernetswitched",
					"value": "Ethernet Switched"
				}, {
					"key": "ethernetstandalone",
					"value": "Ethernet Standalone"
				}
			]				
		},
		"IP": {
			"displayValue" : "IP",
			"accessSpeedImagePath": "images/private_line.png",
			"range": [
				{ 
				  "speed": "1544", 
				  "MRC": "1209-9761/site",
				  "NRC": "1663-9517/site"
				},
				{ 
				  "speed": "44736", 
				  "MRC": "1595-9885/site",
				  "NRC": "1978-9384/site"
				},
				{ 
				  "speed": "155520", 
				  "MRC": "5725-9007/site",
				  "NRC": "1575-8481/site"
				},
				{ 
				  "speed": "622080", 
				  "MRC": "2046-9704/site",
				  "NRC": "6487-8297/site"
				},
				{ 
				  "speed": "2488320", 
				  "MRC": "1315-8309/site",
				  "NRC": "1298-9815/site"
				}
			],			
			"physicalInterferenceOptions": [{
					"key": "privatelinelxBase100",
					"value": "Private Line 100 Base LX"
				}, {
					"key": "privatelinelxBase200",
					"value": "Private Line 200 Base LX"
				}, {
					"key": "privatelinelxBase300",
					"value": "Private Line 300 Base LX"
				}
			],
			"accessInterconnectTechnology": [{
					"key": "privatelinedirectConnect",
					"value": "Private Line Direct Connect"
				}, {
					"key": "privatelineindirectConnect",
					"value": "Private Line Indirect Connect"
				}
			],
			"localAccessManagementOptions": [{
					"key": "privateline200",
					"value": "Private Line 200"
				}, {
					"key": "privateline500",
					"value": "Private Line 500"
				}
			],	
			"accessTailTechnologyOptions": [{
					"key": "privatelineshared",
					"value": "Private Line ESP ETH Shared"
				}, {
					"key": "privatelinededicated",
					"value": "Private Line ESP ETH Dedicated"
				}
			],	
			"accessArchitectureOptions": [{
					"key": "privatelineswitched",
					"value": "Private Line Switched"
				}, {
					"key": "privatelinestandalone",
					"value": "Private Line Standalone"
				}
			]			
		}
	},
	"portSpeeds": {
		"ETHERNET": {
			"displayValue" : "Ethernet",
			"portOptionImagePath": "images/scdapb.png",	
			"range": [
				{ 
				  "speed": "1500", 
				  "MRC": "$0-$100/site",
				  "NRC": "$110-$120/site"
				},
				{ 
				  "speed": "2000", 
				  "MRC": "$200-$210/site",
				  "NRC": "$220-$230/site"
				},
				{ 
				  "speed": "3000", 
				  "MRC": "$300-$310/site",
				  "NRC": "$320-$330/site"
				},
				{ 
				  "speed": "4000", 
				  "MRC": "$400-$410/site",
				  "NRC": "$420-$430/site"
				},
				{ 
				  "speed": "5000", 
				  "MRC": "$500-$510/site",
				  "NRC": "$520-$530/site"
				},
				{ 
				  "speed": "6000", 
				  "MRC": "$600-$510/site",
				  "NRC": "$620-$530/site"
				},
				{ 
				  "speed": "7000", 
				  "MRC": "$700-$510/site",
				  "NRC": "$720-$530/site"
				},
				{ 
				  "speed": "8000", 
				  "MRC": "$800-$510/site",
				  "NRC": "$820-$530/site"
				},
				{ 
				  "speed": "9000", 
				  "MRC": "$900-$510/site",
				  "NRC": "$920-$530/site"
				}				
			],
			"portType": [{
					"key": "ethernetporttyp1",
					"value": "Port 1"
				}, {
					"key": "ethernetporttyp3",
					"value": "Port 3"
				}, {
					"key": "ethernetporttyp2",
					"value": "Port 2"
				}
			],
			"routingProtocol": [{
					"key": "bgp1ethernet",
					"value": "ethernetBGP 1"
				}, {
					"key": "ethernetbgp2",
					"value": "ethernetBGP 2"
				}
			],
			"portProtocol": [{
					"key": "ethernetportProtocol1",
					"value": "ethernetPort Protocol 1"
				}, {
					"key": "ethernetportProtocol2",
					"value": "ethernetPort Protocol 2"
				}
			],	
			"ipVersion": [{
					"key": "ethernetipv4",
					"value": "ethernetIPV4"
				}, {
					"key": "ethernetipv6",
					"value": "ethernetIPV6"
				}
			],	
			"ratePlan": [{
					"key": "ethernetfixed",
					"value": "ethernetFixed"
				}, {
					"key": "ethernetfloating",
					"value": "ethernetFloating"
				}
			],
			"resiliencyOptions": [
				{
					"key": "SCDAPB",
					"value" : "Single CE, Dual Access, Primary/Backup"
				}, {
					"key": "DCDAPB",
					"value" : "Dual CE, Dual Access, Primary/Backup"
				}
			]							
		},
		"IP": {
			"displayValue" : "IP",
			"portOptionImagePath": "images/scdapb.png",
			"range": [
				{ 
				  "speed": "40000", 
				  "MRC": "$10-$190/site",
				  "NRC": "$1190-$1290/site"
				},
				{ 
				  "speed": "44736", 
				  "MRC": "$2090-$2190/site",
				  "NRC": "$2290-$2390/site"
				},
				{ 
				  "speed": "155520", 
				  "MRC": "$3090-$3190/site",
				  "NRC": "$3290-$3390/site"
				},
				{ 
				  "speed": "622080", 
				  "MRC": "$4090-$4190/site",
				  "NRC": "$4290-$4390/site"
				},
				{ 
				  "speed": "2488320", 
				  "MRC": "$5090-$5190/site",
				  "NRC": "$5290-$5390/site"
				}
			],			
			"portType": [{
					"key": "private_lineporttyp1",
					"value": "Port 1"
				}, {
					"key": "private_lineporttyp3",
					"value": "Port 3"
				}, {
					"key": "private_lineporttyp2",
					"value": "Port 2"
				}
			],
			"routingProtocol": [{
					"key": "bgp1private_line",
					"value": "private_lineBGP 1"
				}, {
					"key": "private_linebgp2",
					"value": "private_lineBGP 2"
				}
			],
			"portProtocol": [{
					"key": "private_lineportProtocol1",
					"value": "private_linePort Protocol 1"
				}, {
					"key": "private_lineportProtocol2",
					"value": "private_linePort Protocol 2"
				}
			],	
			"ipVersion": [{
					"key": "private_lineipv4",
					"value": "private_lineIPV4"
				}, {
					"key": "private_lineipv6",
					"value": "private_lineIPV6"
				}
			],	
			"ratePlan": [{
					"key": "private_linefixed",
					"value": "private_lineFixed"
				}, {
					"key": "private_linefloating",
					"value": "private_lineFloating"
				}
			],
			"resiliencyOptions": [
				{
					"key" : "NORES",
					"value" : "No Resiliency"
				}, {
					"key": "SCDAPB",
					"value" : "Single CE, Dual Access, Primary/Backup"
				}, {
					"key": "DCDAPB",
					"value" : "Dual CE, Dual Access, Primary/Backup"
				}
			]
		}
	}
}';
update SLEXP_SITE_CONFIG set SITE_DATA=str;
end;
/
