package pe.edu.cibertec.reportaya.model

data class IncidentUiModel(
    val id: Long,
    val code: String,
    val type: String,
    val description: String,
    val location: String,
    val state: IncidentState,
    val registeredAt: String,
    val user: String,
    val photoUri: String? = null,
    val attentionDate: String? = null,
    val attentionObservation: String? = null
)

enum class IncidentState(val label: String) {
    PENDING("Pendiente"),
    IN_PROGRESS("En proceso"),
    ATTENDED("Atendido"),
    REJECTED("Rechazado");

    companion object {
        fun fromLabel(value: String?): IncidentState =
            entries.firstOrNull { it.label.equals(value, ignoreCase = true) } ?: PENDING
    }
}
