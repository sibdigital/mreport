/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altmf
 * Created: 17.03.2017
 */

select sched_name,
trigger_name,
trigger_group,
cron_expression,
time_zone_id from makpsb.qrtz_cron_triggers as ct
where ct."trigger_name" like ?


