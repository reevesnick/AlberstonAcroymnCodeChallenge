package com.nr.acronyminitalismcodechallenge

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nr.acronyminitalismcodechallenge.adapter.VarsRecyclerViewAdapter
import com.nr.acronyminitalismcodechallenge.api.ApiInterface
import com.nr.acronyminitalismcodechallenge.databinding.ActivityMainBinding
import com.nr.acronyminitalismcodechallenge.repo.SfRepo
import com.nr.acronyminitalismcodechallenge.viewmodel.SfViewModel
import com.nr.acronyminitalismcodechallenge.viewmodel.SfViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val job by lazy { Job() } // Create a lazy instead of lateinit since we do not know the non-null values at this time

    val mainActivityJob: CoroutineContext
        get() = Dispatchers.Main





    @SuppressLint("SetTextI18n") // Should use String Resource but for now will come back later
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get all the repo, interface, and view model to fetch the data
        val sfApiInterface = ApiInterface.getInstance()
        val sfRepo = SfRepo(sfApiInterface)
        val viewModel = ViewModelProvider(this, SfViewModelFactory(sfRepo))[SfViewModel::class.java]

        // Search Item with Listener. Tried to use observable but the ViewModel will take care of it
        binding.searchView.addTextChangedListener(object : TextWatcher {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.getAbbrivatedData(s.toString())
            }
        })

        // Progress Bar Observable Value
        viewModel.loading.observe(this) { loading ->
            if (loading)  binding.indeterminateBar.visibility = View.VISIBLE else View.GONE
        }

        // Setup RecyclerView Adapter
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // Observe any SF Data from View  model for every response
        viewModel.sfData.observe(this) { data ->
            try {
                // Bind the recyclerview adapter if data is there
                binding.recyclerView.adapter = VarsRecyclerViewAdapter(data[0].lfs[0].vars)

            } catch (ex: java.lang.IndexOutOfBoundsException){
                ex.localizedMessage
            }
        }

        // Error Message
        viewModel.errorMessage.observe(this){ error ->
            showDefaultDialog(error)
        }

    }

    // Was using an inline but Kotlin Lint suggests it would not have an impact
    private fun showDefaultDialog(error: String) {
        val alertDialog = AlertDialog.Builder(this)

        alertDialog.apply {
            setTitle("Error")
            setMessage(error)
            setPositiveButton("OK") { _, _ ->

            }

        }.create().show()
    }


}