INSERT INTO wsndb.devices (mac)
SELECT device FROM appdb.data_records GROUP BY device;

INSERT INTO wsndb.records (quantity, timestamp, value, device_id)
SELECT quantity, timestamp, value, 4 FROM appdb.data_records;