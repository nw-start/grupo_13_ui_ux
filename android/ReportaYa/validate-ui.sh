#!/usr/bin/env bash
set -euo pipefail

required=(
  app/src/main/res/layout/activity_login.xml
  app/src/main/res/layout/activity_incident_form.xml
  app/src/main/res/layout/activity_incident_list.xml
  app/src/main/res/layout/activity_incident_detail.xml
  app/src/main/res/layout/activity_incident_edit.xml
  app/src/main/res/layout/item_incident.xml
  app/src/main/res/layout/nav_header.xml
  app/src/main/res/layout-land/activity_login.xml
  app/src/main/res/layout-land/activity_incident_form.xml
  app/src/main/res/layout-land/activity_incident_detail.xml
  app/src/main/res/layout-land/activity_incident_edit.xml
  app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentAdapter.kt
  app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentFilter.kt
  app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentListActivity.kt
  app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentDetailActivity.kt
  app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentEditActivity.kt
  app/src/main/java/pe/edu/cibertec/reportaya/ui/RegisterIncidentActivity.kt
  app/src/main/java/pe/edu/cibertec/reportaya/data/IncidentUiDataSource.kt
)

for f in "${required[@]}"; do
  [[ -f "$f" ]] || { echo "FALTA: $f" >&2; exit 1; }
done

grep -q 'RecyclerView' app/src/main/res/layout/activity_incident_list.xml
grep -q 'MaterialCardView' app/src/main/res/layout/item_incident.xml
grep -q 'ExtendedFloatingActionButton' app/src/main/res/layout/activity_incident_list.xml
grep -q 'TextInputLayout' app/src/main/res/layout/activity_login.xml
grep -q 'ListAdapter' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentAdapter.kt
grep -q 'DiffUtil.ItemCallback' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentAdapter.kt
grep -q 'tvCode.text' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentAdapter.kt
grep -q 'tvType.text' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentAdapter.kt
grep -q 'tvDescription.text' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentAdapter.kt
grep -q 'tvLocation.text' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentAdapter.kt
grep -q 'tvState.text' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentAdapter.kt
grep -q 'tvDate.text' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentAdapter.kt
grep -q 'putExtra(EXTRA_INCIDENT_ID' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentListActivity.kt
grep -q 'putExtra(IncidentListActivity.EXTRA_INCIDENT_ID' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentDetailActivity.kt
grep -q 'doAfterTextChanged' app/src/main/java/pe/edu/cibertec/reportaya/ui/IncidentListActivity.kt
grep -q 'loadingState' app/src/main/res/layout/activity_incident_list.xml
grep -q 'emptyState' app/src/main/res/layout/activity_incident_list.xml
grep -q 'IncidentUiDataSource' app/src/main/java/pe/edu/cibertec/reportaya/data/IncidentUiDataSource.kt

if grep -R -n -E 'TODO|FIXME|Not yet implemented' app/src/main README_INTEGRANTE_4.md INTEGRATION_MEMBER5.md; then
  echo 'ERROR: quedan marcadores pendientes.' >&2
  exit 1
fi

echo 'OK: layouts completos + Material + RecyclerView personalizado + navegación + búsqueda/filtros + estados + integración visual.'
