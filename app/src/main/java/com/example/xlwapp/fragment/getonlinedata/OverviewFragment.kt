package com.example.xlwapp.fragment.getonlinedata

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentOverviewBinding
import com.example.xlwapp.network.MarsApiFilter
import com.example.xlwapp.recyclerview.onlineData.OnClickListener
import com.example.xlwapp.recyclerview.onlineData.PhotoGridAdapter
import com.example.xlwapp.viewmodel.onlinedata.OverviewViewModel

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment() {

    val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentOverviewBinding>(inflater, R.layout.fragment_overview, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        binding.photosGrid.adapter = PhotoGridAdapter(OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
                when(item.itemId){
                    R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
                    R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
                    else -> MarsApiFilter.SHOW_ALL
                }
        )
        return super.onOptionsItemSelected(item)
    }
}
