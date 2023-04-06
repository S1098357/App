package com.example.myapplication

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.myapplication.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.GoogleApiClient
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchView = findViewById(R.id.idSearchView)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                val location=searchView.query.toString()
                var addressList : List<Address> = mutableListOf()
                if (location == null ||location == "") {}
                else{
                    val geocoder=Geocoder(this@MapsActivity)
                    try {
                        addressList= geocoder.getFromLocationName(location,1) as List<Address>
                        println(addressList.size)

                    }catch (e: IOException){
                        e.printStackTrace()
                    }
                    val address:Address= addressList[0]
                    val latLng=LatLng(address.latitude,address.longitude)
                    mMap.addMarker(MarkerOptions().position(latLng).title(location))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

}