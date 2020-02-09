with
temp_max_worker_position_dates (id_worker, id_position, id_depart, max_date_begin) as( 
    SELECT id_worker, id_position, id_depart, max(date_begin)
    FROM MAKPSB.WORKER_POSITION
    where date_end is null and is_deleted = 0
    group by id_depart, id_worker, id_position 
),
last_worker_position (id, id_worker, id_position, id_depart, date_begin, date_end, is_leader) as( 
    SELECT wp.id, wp.id_worker, wp.id_position, wp.id_depart, wp.date_begin, wp.date_end, wp.is_leader 
    FROM temp_max_worker_position_dates as mwpd
    inner join MAKPSB.WORKER_POSITION as wp
    on mwpd.max_date_begin = wp.date_begin and mwpd.id_worker = wp.id_worker
    and mwpd.id_position = wp.id_position and mwpd.id_depart = wp.id_depart
),
all_info (id, id_worker, id_position, id_depart, id_raion, date_begin, date_end, is_leader,
          fam, im, otc, position_name, depart_name, raion_name, nomer) as(
    select lwp.id, lwp.id_worker, lwp.id_position, lwp.id_depart, d.id_raion, lwp.date_begin, lwp.date_end, is_leader,
    w.fam, w.im, w.otc, p.name as position_name, d.name as depart_name, d.rname as raion_name, d.nomer 
    from last_worker_position as lwp
    left join makpsb.worker as w
    on lwp.id_worker = w.id
    left join makpsb.position as p
    on lwp.id_position = p.id
    left join (
        select md.id, md.name, r.name as rname, r.nomer, r.id as id_raion 
        from makpsb.depart as md
        left join makpsb.raion as r on md.id_raion = r.id
    ) as d
    on lwp.id_depart = d.id
)
select * from all_info
