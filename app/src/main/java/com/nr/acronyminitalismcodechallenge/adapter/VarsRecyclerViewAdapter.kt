package com.nr.acronyminitalismcodechallenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nr.acronyminitalismcodechallenge.databinding.VarsItemBinding
import com.nr.acronyminitalismcodechallenge.model.VarsModel


class VarsRecyclerViewAdapter(private val varsItem: List<VarsModel>): RecyclerView.Adapter<VarsRecyclerViewAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: VarsItemBinding): RecyclerView.ViewHolder(binding.root)

    // Inflate the RecyclerVie Item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VarsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // This callback is used to get the ViewHolder with the position in order to simplify the databind using with
    // Instead of just calling holder.bind.<view>.text = xxxxxxxx
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder){
            with(varsItem[position]){
                binding.lfTextView.text = this.lf
                binding.freqTextView.text = this.freq.toString()
                binding.sinceTextView.text = this.since.toString()

            }
        }
    }

    // Return the size of the variable list
    override fun getItemCount(): Int {
        return varsItem.size
    }

}