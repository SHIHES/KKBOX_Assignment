package com.example.android.android_kkbox_assignment.ui.player

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android.android_kkbox_assignment.R
import com.example.android.android_kkbox_assignment.databinding.FragmentPlayerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayerFragment : Fragment() {
    
    lateinit var binding: FragmentPlayerBinding
    lateinit var customMediaPlayer: CustomMediaPlayer
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    
    private val arg: PlayerFragmentArgs by navArgs()
    private var isSeeking = false
    private val TAG = "PlayerFragment"
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        
        if (arg.episode?.sound?.url != null){
            customMediaPlayer = CustomMediaPlayer(arg.episode?.sound?.url.toString())
        } else {
            Toast.makeText(requireContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        }
        
        customMediaPlayer.initialMediaPlayer()
        setSeekBar()
        setView()
        setButton()
        asyncProgressBar()
        
        return binding.root
    }
    
    private fun setSeekBar(){
        binding.seekBar.max = customMediaPlayer.getDuration()
    
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if(isSeeking){
                    customMediaPlayer.seekToProgress(progress)
                }
            }
        
            override fun onStartTrackingTouch(p0: SeekBar?) {
                isSeeking = true
            }
        
            override fun onStopTrackingTouch(p0: SeekBar?) {
                isSeeking = false
            }
        
        })
    }
    
    private fun setView(){
        Glide.with(requireContext())
            .load(arg.episode?.image?.url)
            .centerCrop()
            .into(binding.FragmentPlayerImage)
        
        binding.fragmentPlayerTitle.text = arg.episode?.title
    }
    
    private fun setButton(){
        binding.fragmentPlayerPlayButton.setOnClickListener {
            
            if(customMediaPlayer.isPlayingMusic){
                customMediaPlayer.playOrPauseMusic()
                binding.fragmentPlayerIconButton.setImageResource(R.drawable.ic_baseline_pause_24)
            } else{
                customMediaPlayer.playOrPauseMusic()
                binding.fragmentPlayerIconButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
        }
        binding.fragmentPlayerBackward.setOnClickListener {
        
        }
        binding.fragmentPlayerForward.setOnClickListener {
        
        }
    }
    
    private fun updateProgressBar(){
        lifecycleScope.launch(Dispatchers.IO){
            
            withContext(Dispatchers.Main){
            
            }
        }
    }
    
    private fun asyncProgressBar() {
        handler = Handler()
        runnable = Runnable {
            binding.seekBar.progress = customMediaPlayer.getCurrentEpisodeProgress()
            handler.postDelayed(runnable, 500)
        }
        runnable.run()
    }
    
    override fun onStart() {
        super.onStart()
        customMediaPlayer.initialMediaPlayer()
        asyncProgressBar()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        customMediaPlayer.releaseMediaPlayer()
        handler.removeCallbacks(runnable)
    }
    
    override fun onStop() {
        super.onStop()
        customMediaPlayer.releaseMediaPlayer()
        handler.removeCallbacks(runnable)
    }
}