package pe.edu.cibertec.reportaya.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pe.edu.cibertec.reportaya.R
import pe.edu.cibertec.reportaya.data.IncidentDataSourceRegistry
import pe.edu.cibertec.reportaya.databinding.ActivityIncidentEditBinding

class IncidentEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIncidentEditBinding
    private var incidentId: Long = -1
    private var selectedPhoto: Uri? = null

    private val photoPicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedPhoto = uri
        binding.ivPhotoPreview.setImageURI(uri)
        binding.ivPhotoPreview.visibility = if (uri == null) View.GONE else View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncidentEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }

        incidentId = intent.getLongExtra(IncidentListActivity.EXTRA_INCIDENT_ID, -1)
        val types = resources.getStringArray(R.array.incident_types)
        binding.actvType.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, types))
        binding.btnChangePhoto.setOnClickListener { photoPicker.launch("image/*") }
        binding.btnSaveChanges.setOnClickListener { validateAndSubmit() }
        populate()
    }

    private fun populate() {
        IncidentDataSourceRegistry.current.findIncidentById(incidentId)?.let { item ->
            binding.tvCode.text = item.code
            binding.tvState.text = item.state.label
            binding.actvType.setText(item.type, false)
            binding.etDescription.setText(item.description)
            binding.etLocation.setText(item.location)
        }
    }

    private fun validateAndSubmit() {
        val type = binding.actvType.text?.toString()?.trim().orEmpty()
        val description = binding.etDescription.text?.toString()?.trim().orEmpty()
        val location = binding.etLocation.text?.toString()?.trim().orEmpty()
        binding.tilType.error = if (type.isBlank()) getString(R.string.error_type_required) else null
        binding.tilDescription.error = if (description.length !in 10..250) getString(R.string.error_description_range) else null
        binding.tilLocation.error = if (location.isBlank()) getString(R.string.error_location_required) else null
        if (type.isNotBlank() && description.length in 10..250 && location.isNotBlank()) {
            Snackbar.make(binding.root, R.string.ui_updated_for_member5, Snackbar.LENGTH_LONG)
                .setAction(R.string.close) { finish() }
                .show()
        }
    }
}
