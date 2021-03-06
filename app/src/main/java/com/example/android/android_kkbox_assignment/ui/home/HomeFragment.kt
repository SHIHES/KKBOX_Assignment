package com.example.android.android_kkbox_assignment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android.android_kkbox_assignment.databinding.FragmentHomeBinding
import com.example.android.android_kkbox_assignment.ext.getVmFactory
import com.example.android.android_kkbox_assignment.logic.model.LoadApiStatus
import com.example.android.android_kkbox_assignment.logic.model.Result

class HomeFragment : Fragment() {
    
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }
    private val TAG = "HomeFragment"
    
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        
        val episodeRv = binding.episodeRv
        val adapter = EpisodeAdapter(
            EpisodeAdapter.OnClickListener{
                viewModel.navigateToEpisode(it)
            }
        )
        episodeRv.adapter = adapter
        episodeRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        
        viewModel.channelData.observe(viewLifecycleOwner){
            it?.let {
                Glide.with(requireContext()).load(it.channel?.image?.url)
                    .centerCrop()
                    .into(binding.podcastImage)
                
                adapter.submitList(it.channel?.episodes)
            }
        }
        
        viewModel.selectedEpisodePosition.observe(viewLifecycleOwner){
            it?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToEpisodeFragment(
                        channel = viewModel.channelData.value?.channel,
                        it
                    )
                )
                viewModel.onEpisodeNavigated()
            }
        }
        
        viewModel.status.observe(viewLifecycleOwner) {
            it?.let {
            
            }
        }
        
        return binding.root
    }
}