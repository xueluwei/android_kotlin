package com.example.xlwapp.fragment.gdg

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentGdgListBinding
import com.example.xlwapp.recyclerview.gdg.GdgClickListener
import com.example.xlwapp.recyclerview.gdg.GdgListAdapter
import com.example.xlwapp.viewmodel.gdg.GdgListViewModel
import com.google.android.gms.location.*
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar

private const val LOCATION_PERMISSION_REQUEST = 1

private const val LOCATION_PERMISSION = "android.permission.ACCESS_FINE_LOCATION"

class GdgListFragment : Fragment() {

    private val viewModel: GdgListViewModel by lazy {
        ViewModelProvider(this).get(GdgListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentGdgListBinding>(inflater, R.layout.fragment_gdg_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = GdgListAdapter(GdgClickListener {
            val destination = Uri.parse(it.url)
            startActivity(Intent(Intent.ACTION_VIEW, destination))
        })
        binding.gdgChapterList.adapter = adapter
        viewModel.showNeedLocation.observe(viewLifecycleOwner, object: Observer<Boolean> {
            override fun onChanged(show: Boolean?) {
                // Snackbar is like Toast but it lets us show forever
                if (show == true) {
                    Snackbar.make(
                            binding.root,
                            "No location. Enable location in settings (hint: test with Maps) then check app permissions!",
                            Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })
        viewModel.regionList.observe(viewLifecycleOwner, object: Observer<List<String>>{
            override fun onChanged(t: List<String>?) {
                t ?: return
                val chipGrop = binding.regionList
                val inf = LayoutInflater.from(chipGrop.context)
                val children = t.map {
                    val chip = inf.inflate(R.layout.region, chipGrop, false) as Chip
                    chip.text = it
                    chip.tag = it
                    chip.setOnCheckedChangeListener{ button, ischecked ->
                        viewModel.onFilterChanged(button.tag as String, ischecked)
                    }
                    chip
                }
                chipGrop.removeAllViews()
                for (chip in children) {
                    chipGrop.addView(chip)
                }
            }
        })

        setHasOptionsMenu(true)
        return  binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestLastLocationOrStartLocationUpdates()
    }

    /**
     * Show the user a dialog asking for permission to use location.
     */
    private fun requestLocationPermission() {
        requestPermissions(arrayOf(LOCATION_PERMISSION), LOCATION_PERMISSION_REQUEST)
    }

    /**
     * Request the last location of this device, if known, otherwise start location updates.
     *
     * The last location is cached from the last application to request location.
     */
    private fun requestLastLocationOrStartLocationUpdates() {
        // if we don't have permission ask for it and wait until the user grants it
        if (ContextCompat.checkSelfPermission(requireContext(), LOCATION_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location == null) {
                startLocationUpdates(fusedLocationClient)
            } else {
                viewModel.onLocationUpdated(location)
            }
        }
    }

    /**
     * Start location updates, this will ask the operating system to figure out the devices location.
     */
    private fun startLocationUpdates(fusedLocationClient: FusedLocationProviderClient) {
        // if we don't have permission ask for it and wait until the user grants it
        if (ContextCompat.checkSelfPermission(requireContext(), LOCATION_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
            return
        }


        val request = LocationRequest().setPriority(LocationRequest.PRIORITY_LOW_POWER)
        val callback = object: LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                val location = locationResult?.lastLocation ?: return
                viewModel.onLocationUpdated(location)
            }
        }
        fusedLocationClient.requestLocationUpdates(request, callback, null)
    }

    /**
     * This will be called by Android when the user responds to the permission request.
     *
     * If granted, continue with the operation that the user gave us permission to do.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            LOCATION_PERMISSION_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLastLocationOrStartLocationUpdates()
                }
            }
        }
    }

}
