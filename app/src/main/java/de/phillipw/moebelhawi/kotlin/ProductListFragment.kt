package de.phillipw.moebelhawi.kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.phillipw.moebelhawi.kotlin.adapter.ProductListAdapter
import de.phillipw.moebelhawi.kotlin.databinding.FragmentProductListBinding


class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding

    private val viewModel: HomeViewModel by activityViewModels()

    private val args: ProductListFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductListBinding.inflate(layoutInflater)

        viewModel.loadByCategory(args.title)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvResults.text = args.title
        viewModel.categoryResults.observe(viewLifecycleOwner) {
            binding.rvProductlist.adapter = ProductListAdapter(it){
                viewModel.setSelectedProduct(it)
                findNavController().navigate(R.id.productDetailFragment)
            }
        }

    }


}