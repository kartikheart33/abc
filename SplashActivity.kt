package com.alexios.android.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexios.android.R
import com.alexios.android.RoomDB.AlexiousDataBase
import com.alexios.android.databinding.ActivitySplashBinding
import com.alexios.android.firestore.FirestoreClass
import com.alexios.android.models.Filter
import com.alexios.android.models.Products
import com.alexios.android.models.Wine
import com.alexios.android.utils.Constants
import com.alexios.android.utils.SharedPreferenceUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlin.math.log


class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySplashBinding
    val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 1
    }

    companion object {
        private const val TAG = "SplashActivity"
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            signInAnonymously()
        } else {
            validate(true)
        }
    }

    private fun signInAnonymously() {
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInAnonymously:success")
                    validate(false)
                } else {
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    validate(false)
                }
            }
    }

    private fun validate(isAwait: Boolean) {
        if (isAwait) {
//            Handler(Looper.getMainLooper()).postDelayed(
//                {
//                    redirectToHome()
//                },
//                3000
//            )
        } else {
//            Handler(Looper.getMainLooper()).postDelayed(
//                {
//                    redirectToHome()
//                },
//                3000
//            )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferenceUtil = SharedPreferenceUtil(this)
        Log.d(TAG, "firsttimeFlag"+sharedPreferenceUtil.getBooleanPreferences(Constants.ISFIRSTTIME).toString())

        if (!sharedPreferenceUtil.getBooleanPreferences(Constants.ISFIRSTTIME)){
            sharedPreferenceUtil.setBooleanPreferences(Constants.ISFIRSTTIME,true)
            FirestoreClass().getWines(this)
            Log.d(TAG, "exicute firsttime ")
            return
        }




        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
//        var onlineVersion: Long =
//            FirebaseRemoteConfig.getInstance().getLong("current_version") // empty string
//        Log.d(TAG, "KEYY:" + onlineVersion.toString())


        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Config params updated: $updated")
                    var onlineVersion: Long =
                        FirebaseRemoteConfig.getInstance().getLong("current_version") // empty string
                    Log.d(TAG, "NewKEYY:" + onlineVersion.toString())
                    val pref = SharedPreferenceUtil(this)
                    var currentPrefVersion: Int = pref.getIntPreferences(Constants.LOCALREMOTECONFIGVERSION)
                    if (currentPrefVersion < onlineVersion) {
                        pref.setIntPreferences(Constants.LOCALREMOTECONFIGVERSION, onlineVersion.toInt())
                        FirestoreClass().getWines(this)
                        Log.d(TAG, "newww")
                    }else{
                        Log.d(TAG, "old")
                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                redirectToHome()
                            },
                            3000
                        )

                    }

                } else {
                    Toast.makeText(
                        this,
                        "Fetch failed",
                        Toast.LENGTH_SHORT,
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            redirectToHome()
                        },
                        3000
                    )
                }

            }
    }

    fun redirectToHome() {
        Log.d(TAG, "redirectToHome: ")
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun winesFromFirestore(wines: MutableList<Wine>) {
        if (wines.size > 0) {
            val db = AlexiousDataBase.getDbInstance(this)
            db.wineDao().clearRecords()
             db.wineDao().insertAll(wines)
        }
        FirestoreClass().getFilter(this)
        FirestoreClass().getProducts(this)

        Toast.makeText(
            this@SplashActivity,
            "Wines from Firebase : " + wines.size,
            Toast.LENGTH_LONG
        ).show()
    }

    fun productsFromFirestore(products: MutableList<Products>) {
        val db = AlexiousDataBase.getDbInstance(this)
        db.productDao().clearRecords()
        db.productDao().insertAll(products)
        Toast.makeText(
            this@SplashActivity,
            "Products from Fire : " + products.size,
            Toast.LENGTH_LONG
        ).show()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                redirectToHome()
            },
            3000
        )
    }

    fun filterFromFirestore(filter: MutableList<Filter>) {
        if (filter.size > 0) {
            val db = AlexiousDataBase.getDbInstance(this)
            db.filterDao().clearRecords()
            db.filterDao().insertAll(filter)
        }
        Toast.makeText(
            this@SplashActivity,
            "Products from Fire : " + filter.size,
            Toast.LENGTH_LONG
        ).show()
    }
}