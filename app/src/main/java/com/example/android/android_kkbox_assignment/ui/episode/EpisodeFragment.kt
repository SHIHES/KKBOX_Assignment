package com.example.android.android_kkbox_assignment.ui.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android.android_kkbox_assignment.databinding.FragmentEpisodeBinding

class EpisodeFragment : Fragment() {
    
    lateinit var binding: FragmentEpisodeBinding
    
    private val arg: EpisodeFragmentArgs by navArgs()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        
        binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        
        setView()
        setButton()
        
        return binding.root
    }
    
    
    private fun setView(){
        Glide.with(requireContext())
            .load(arg.episode?.image?.url)
            .centerCrop()
            .into(binding.fragmentEpisodeImage)
        binding.fragmentEpisodeSummary.text = arg.episode?.summary
        binding.fragmentEpisodeTitle.text = arg.episode?.title
    }
    
    private fun setButton(){
        binding.playButtonLayout.setOnClickListener {
            findNavController().navigate(EpisodeFragmentDirections.actionEpisodeFragmentToPlayerFragment(arg.episode))
        }
    }
}