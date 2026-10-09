package pe.edu.cibertec.reportaya.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import pe.edu.cibertec.reportaya.R
import pe.edu.cibertec.reportaya.data.IncidentDataSourceRegistry
import pe.edu.cibertec.reportaya.databinding.ActivityIncidentDetailBinding
import pe.edu.cibertec.reportaya.model.IncidentState

class IncidentDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIncidentDetailBinding
    private var incidentId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncidentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }

        incidentId = intent.getLongExtra(IncidentListActivity.EXTRA_INCIDENT_ID, -1)
        binding.btnEdit.setOnClickListener {
            startActivity(Intent(this, IncidentEditActivity::class.java).apply {
                putExtra(IncidentListActivity.EXTRA_INCIDENT_ID, incidentId)
            })
        }
        render()
    }

    override fun onResume() {
        super.onResume()
        if (::binding.isInitialized) render()
    }

    private fun render() {
        val item = IncidentDataSourceRegistry.current.findIncidentById(incidentId)
        if (item == null) {
            binding.tvDescription.text = getString(R.string.incident_not_found)
            binding.btnEdit.isEnabled = false
            return
        }
        binding.tvCode.text = item.code
        binding.tvType.text = item.type
        binding.tvDescription.text = item.description
        binding.tvLocation.text = item.location
        binding.tvDate.text = item.registeredAt
        binding.tvUser.text = item.user
        binding.tvState.text = item.state.label
        val (badgeBackground, badgeTextColor) = when (item.state) {
            IncidentState.PENDING -> R.drawable.badge_pending to R.color.state_pending_fg
            IncidentState.IN_PROGRESS -> R.drawable.badge_in_progress to R.color.state_progress_fg
            IncidentState.ATTENDED -> R.drawable.badge_attended to R.color.state_attended_fg
            IncidentState.REJECTED -> R.drawable.badge_rejected to R.color.state_rejected_fg
        }
        binding.tvState.setBackgroundResource(badgeBackground)
        binding.tvState.setTextColor(ContextCompat.getColor(this, badgeTextColor))
        binding.groupAttention.visibility = if (item.attentionDate != null || item.attentionObservation != null) View.VISIBLE else View.GONE
        binding.tvAttentionDate.text = item.attentionDate ?: getString(R.string.not_available)
        binding.tvObservation.text = item.attentionObservation ?: getString(R.string.not_available)
        val hasPhoto = !item.photoUri.isNullOrBlank()
        binding.photoContainer.visibility = if (hasPhoto) View.VISIBLE else View.GONE
        item.photoUri?.takeIf { it.isNotBlank() }?.let { uri ->
            binding.ivPhotoEvidence.setImageURI(Uri.parse(uri))
        }
    }
}
