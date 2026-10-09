package pe.edu.cibertec.reportaya.data

import pe.edu.cibertec.reportaya.model.IncidentState
import pe.edu.cibertec.reportaya.model.IncidentUiModel


object PreviewIncidentDataSource : IncidentUiDataSource {
    private val items = listOf(
        IncidentUiModel(
            1, "INC-0001", "Fuga de agua",
            "Goteo constante en la tubería del pasadizo; el piso se humedece durante la noche.",
            "Torre A · Piso 4", IncidentState.IN_PROGRESS,
            "09/10/2026 · 08:32", "residente01"
        ),
        IncidentUiModel(
            2, "INC-0002", "Ascensor",
            "El ascensor de la torre B se detiene unos segundos antes de abrir la puerta.",
            "Torre B · Ascensor 2", IncidentState.PENDING,
            "09/10/2026 · 07:15", "residente02"
        ),
        IncidentUiModel(
            3, "INC-0003", "Ruido",
            "Ruido recurrente en horario de descanso proveniente del área social.",
            "Área común · Terraza", IncidentState.ATTENDED,
            "08/10/2026 · 22:04", "residente01",
            attentionDate = "09/10/2026 · 09:10",
            attentionObservation = "Se coordinó con seguridad y se notificó a los residentes."
        ),
        IncidentUiModel(
            4, "INC-0004", "Seguridad",
            "La luminaria del acceso lateral no enciende y reduce la visibilidad del ingreso.",
            "Acceso lateral", IncidentState.REJECTED,
            "08/10/2026 · 18:47", "residente03",
            attentionDate = "08/10/2026 · 20:00",
            attentionObservation = "Corresponde a mantenimiento programado ya registrado."
        ),
        IncidentUiModel(
            5, "INC-0005", "Limpieza",
            "Se requiere retirar residuos acumulados después del mantenimiento del jardín.",
            "Jardín central", IncidentState.PENDING,
            "07/10/2026 · 16:21", "residente04"
        )
    )

    override fun listIncidents() = items
    override fun findIncidentById(id: Long) = items.firstOrNull { it.id == id }
}
