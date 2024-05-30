package de.phillipw.moebelhawi.kotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.phillipw.moebelhawi.kotlin.HomeViewModel
import de.phillipw.moebelhawi.kotlin.R
import de.phillipw.moebelhawi.kotlin.adapter.HomeAdapter
import de.phillipw.moebelhawi.kotlin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel.loadTopSellers()
        viewModel.loadTopRated()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.topSellers.observe(viewLifecycleOwner) { products ->
            binding.rvTopSellers.adapter = HomeAdapter(products){
                viewModel.setSelectedProduct(it)
                findNavController().navigate(R.id.productDetailFragment)
            }
        }
        viewModel.topRated.observe(viewLifecycleOwner) { products ->
            binding.rvTopRated.adapter = HomeAdapter(products){
                viewModel.setSelectedProduct(it)
                findNavController().navigate(R.id.productDetailFragment)
            }
        }
    }
}