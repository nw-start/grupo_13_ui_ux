package pe.edu.cibertec.reportaya.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import pe.edu.cibertec.reportaya.R
import pe.edu.cibertec.reportaya.data.IncidentDataSourceRegistry
import pe.edu.cibertec.reportaya.databinding.ActivityIncidentListBinding
import pe.edu.cibertec.reportaya.model.IncidentState
import pe.edu.cibertec.reportaya.model.IncidentUiModel

class IncidentListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIncidentListBinding
    private lateinit var adapter: IncidentAdapter
    private var source: List<IncidentUiModel> = emptyList()
    private var selectedState: IncidentState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncidentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbarAndDrawer()
        setupRecyclerView()
        setupSearchAndFilters()
        binding.fabNewIncident.setOnClickListener {
            startActivity(Intent(this, RegisterIncidentActivity::class.java))
        }
        loadIncidents()
    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) loadIncidents()
    }

    private fun setupToolbarAndDrawer() {
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.navigationView.setNavigationItemSelectedListener(
            NavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_incidents -> binding.drawerLayout.closeDrawer(GravityCompat.START)
                    R.id.menu_logout -> {
                        startActivity(Intent(this, LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        })
                    }
                }
                true
            }
        )
    }

    private fun setupRecyclerView() {
        adapter = IncidentAdapter { incident ->
            startActivity(Intent(this, IncidentDetailActivity::class.java).apply {
                putExtra(EXTRA_INCIDENT_ID, incident.id)
            })
        }
        binding.rvIncidents.layoutManager = LinearLayoutManager(this)
        binding.rvIncidents.adapter = adapter
        binding.rvIncidents.setHasFixedSize(true)
    }

    private fun setupSearchAndFilters() {
        binding.etSearch.doAfterTextChanged { applyFilters() }
        binding.chipAll.setOnClickListener { selectedState = null; applyFilters() }
        binding.chipPending.setOnClickListener { selectedState = IncidentState.PENDING; applyFilters() }
        binding.chipInProgress.setOnClickListener { selectedState = IncidentState.IN_PROGRESS; applyFilters() }
        binding.chipAttended.setOnClickListener { selectedState = IncidentState.ATTENDED; applyFilters() }
        binding.chipRejected.setOnClickListener { selectedState = IncidentState.REJECTED; applyFilters() }
    }

    private fun loadIncidents() {
        showLoading(true)
        source = IncidentDataSourceRegistry.current.listIncidents()
        showLoading(false)
        applyFilters()
    }

    private fun applyFilters() {
        val filtered = IncidentFilter.apply(
            source,
            binding.etSearch.text?.toString().orEmpty(),
            selectedState
        )
        adapter.submitList(filtered)
        val isEmpty = filtered.isEmpty()
        binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.rvIncidents.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun showLoading(visible: Boolean) {
        binding.loadingState.visibility = if (visible) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_INCIDENT_ID = "selected_incident_id"
    }
}
