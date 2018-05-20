select count(id) from application;

select
  transfer_log.id,
  transfer_log.obj_id,
  --transfer_log.msg,
  company_units.company_id,
  company.name,
  transport_unit.vin,
  transport_unit.msisdn,
  transport_unit.grn
from transfer_log, company_units, transport_unit, company
where
  transfer_log.obj_id = company_units.units_id
  and
    company_units.units_id = transport_unit.id
  and
  transfer_log.result = 1 and transfer_log.type = 1
  and
  company_units.company_id = company.id
order by
  company.name, transport_unit.grn