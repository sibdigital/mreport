select 
    ROW_NUMBER() OVER() AS ID,
    r.*
from(
    select
        resource,
        min(res_month) as res_month, 
        min(res_dayweek ) as res_dayweek, 
        min(res_hour) as res_hour, 
        min(period) as period, 
        min(raion) as raion, 
        max(avg_duration) as avg_duration
    from(
        SELECT resource, 
        abs(res_month - ?) as res_month, 
        abs(res_dayweek - ?) as res_dayweek, 
        abs(res_hour - ?) as res_hour, 
        abs(period - ?) as period, 
        abs(raion - ?) as raion, 
        avg_duration 
        FROM MAKPSB.STAT_AVG
        WHERE resource = ? 
    ) 
    group by resource
)as r;


