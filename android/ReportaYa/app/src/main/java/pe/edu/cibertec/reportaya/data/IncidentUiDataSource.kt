package pe.edu.cibertec.reportaya.data

import pe.edu.cibertec.reportaya.model.IncidentUiModel


interface IncidentUiDataSource {
    fun listIncidents(): List<IncidentUiModel>
    fun findIncidentById(id: Long): IncidentUiModel?
}

object IncidentDataSourceRegistry {
    var current: IncidentUiDataSource = PreviewIncidentDataSource
}
