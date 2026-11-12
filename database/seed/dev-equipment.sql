START TRANSACTION;

INSERT INTO locations (building, room)
SELECT 'Engineering Building', '101'
WHERE NOT EXISTS (
    SELECT 1 FROM locations
    WHERE building = 'Engineering Building' AND room = '101'
);

INSERT INTO equipment (name, category, location_id, status)
SELECT 'Demo Laptop 001', 'Laptop', l.id, 'AVAILABLE'
FROM locations l
WHERE l.building = 'Engineering Building'
  AND l.room = '101'
  AND NOT EXISTS (
      SELECT 1 FROM equipment e
      WHERE e.name = 'Demo Laptop 001'
        AND e.location_id = l.id
  );

INSERT INTO equipment (name, category, location_id, status)
SELECT 'Demo Projector 001', 'Projector', l.id, 'AVAILABLE'
FROM locations l
WHERE l.building = 'Engineering Building'
  AND l.room = '101'
  AND NOT EXISTS (
      SELECT 1 FROM equipment e
      WHERE e.name = 'Demo Projector 001'
        AND e.location_id = l.id
  );

COMMIT;