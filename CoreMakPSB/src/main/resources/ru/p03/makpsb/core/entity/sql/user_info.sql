select 
ur.id_role as role_id, ur.id as use_role_id, cv.id as cvitantion_id, re.id as resource_id,
id_depart, ur.name as username, operation, re."path" as resource_path, re.code as resource_code, rr."name" as role_name
from makpsb.use_role as ur
inner join makpsb.cvitantion as cv
on ur.id_role = cv.id_role
inner join makpsb.resource as re
on cv.id_resource = re.id
inner join makpsb.role as rr
on cv.id_role = rr.id
where ur.is_deleted = 0 and cv.is_deleted = 0 and re.is_deleted = 0 and ur.name like ?