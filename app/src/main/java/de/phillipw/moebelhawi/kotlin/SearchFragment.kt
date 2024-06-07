package de.phillipw.moebelhawi.kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.phillipw.moebelhawi.kotlin.adapter.SearchAdapter
import de.phillipw.moebelhawi.kotlin.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewModel.resultInput.observe(
            viewLifecycleOwner
        ) {
            viewModel.loadResultInput(it)
        }
        viewModel.results.observe(
            viewLifecycleOwner
        ) {

            binding.rvSearchResult.adapter = SearchAdapter(it) {
                viewModel.setSelectedProduct(it)
                findNavController().navigate(R.id.productDetailFragment)
            }
        }

        binding.textInputEditText.doAfterTextChanged { query ->
            viewModel.updateInputText(query.toString())
            if (query.isNullOrEmpty()) {
                binding.rvSearchResult.visibility = View.GONE
                binding.categoriesLayout.visibility = View.VISIBLE
            } else {
                binding.rvSearchResult.visibility = View.VISIBLE
                binding.categoriesLayout.visibility = View.GONE
            }
        }
        binding.categorie1.setOnClickListener {
            val action = SearchFragmentDirections.toListFragment("kitchen")
            findNavController().navigate(action)
        }
        binding.categorie2.setOnClickListener {
            val action = SearchFragmentDirections.toListFragment("bedroom")
            findNavController().navigate(action)
        }
        binding.categorie3.setOnClickListener {
            val action = SearchFragmentDirections.toListFragment("bathroom")
            findNavController().navigate(action)
        }
        binding.categorie4.setOnClickListener {
            val action = SearchFragmentDirections.toListFragment("diningroom")
            findNavController().navigate(action)
        }
        binding.categorie5.setOnClickListener {
            val action = SearchFragmentDirections.toListFragment("couch")
            findNavController().navigate(action)
        }

    }
}