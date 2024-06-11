package de.phillipw.moebelhawi.kotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import de.phillipw.moebelhawi.kotlin.HomeViewModel
import de.phillipw.moebelhawi.kotlin.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

}