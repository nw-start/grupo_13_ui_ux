package pe.edu.cibertec.reportaya.ui

import org.junit.Assert.assertEquals
import org.junit.Test
import pe.edu.cibertec.reportaya.model.IncidentState
import pe.edu.cibertec.reportaya.model.IncidentUiModel

class IncidentFilterTest {
    private val source = listOf(
        IncidentUiModel(1, "INC-0001", "Fuga de agua", "Goteo en tubería", "Torre A", IncidentState.PENDING, "09/10/2026", "u1"),
        IncidentUiModel(2, "INC-0002", "Ascensor", "Puerta se demora", "Torre B", IncidentState.IN_PROGRESS, "09/10/2026", "u2"),
        IncidentUiModel(3, "INC-0003", "Ruido", "Ruido en terraza", "Área común", IncidentState.ATTENDED, "08/10/2026", "u3")
    )

    @Test
    fun filterByTextSearchesCodeTypeDescriptionLocationStateAndDate() {
        assertEquals(listOf(2L), IncidentFilter.apply(source, "Ascensor", null).map { it.id })
        assertEquals(listOf(3L), IncidentFilter.apply(source, "terraza", null).map { it.id })
        assertEquals(listOf(1L), IncidentFilter.apply(source, "Torre A", null).map { it.id })
    }

    @Test
    fun filterByStateReturnsOnlySelectedState() {
        assertEquals(listOf(1L), IncidentFilter.apply(source, "", IncidentState.PENDING).map { it.id })
    }

    @Test
    fun textAndStateFiltersAreCombined() {
        assertEquals(emptyList<Long>(), IncidentFilter.apply(source, "INC-0001", IncidentState.ATTENDED).map { it.id })
    }
}
