package pe.edu.cibertec.reportaya.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pe.edu.cibertec.reportaya.R
import pe.edu.cibertec.reportaya.databinding.ActivityIncidentFormBinding

class RegisterIncidentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIncidentFormBinding
    private var selectedPhoto: Uri? = null

    private val photoPicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedPhoto = uri
        binding.ivPhotoPreview.setImageURI(uri)
        binding.ivPhotoPreview.visibility = if (uri == null) View.GONE else View.VISIBLE
        binding.tvPhotoHint.text = if (uri == null) getString(R.string.photo_optional) else getString(R.string.photo_selected)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncidentFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }
        val types = resources.getStringArray(R.array.incident_types)
        binding.actvType.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, types))
        binding.btnAddPhoto.setOnClickListener { photoPicker.launch("image/*") }
        binding.btnSave.setOnClickListener { validateAndSubmit() }
    }

    private fun validateAndSubmit() {
        val type = binding.actvType.text?.toString()?.trim().orEmpty()
        val description = binding.etDescription.text?.toString()?.trim().orEmpty()
        val location = binding.etLocation.text?.toString()?.trim().orEmpty()

        binding.tilType.error = if (type.isBlank()) getString(R.string.error_type_required) else null
        binding.tilDescription.error = when {
            description.length < 10 -> getString(R.string.error_description_min)
            description.length > 250 -> getString(R.string.error_description_max)
            else -> null
        }
        binding.tilLocation.error = if (location.isBlank()) getString(R.string.error_location_required) else null

        if (type.isNotBlank() && description.length in 10..250 && location.isNotBlank()) {
            Snackbar.make(binding.root, R.string.ui_saved_for_member5, Snackbar.LENGTH_LONG)
                .setAction(R.string.back_to_list) { finish() }
                .show()
        }
    }
}
