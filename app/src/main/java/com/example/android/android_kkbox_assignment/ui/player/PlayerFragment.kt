package com.example.android.android_kkbox_assignment.ui.player

import android.os.Bundle
import android.os.Handler
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
import kotlin.properties.Delegates

class PlayerFragment : Fragment() {
    
    lateinit var binding: FragmentPlayerBinding
    lateinit var customMediaPlayer: CustomMediaPlayer
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    var playListPosition by Delegates.notNull<Int>()
    
    private val arg: PlayerFragmentArgs by navArgs()
    private var isSeeking = false
    private val TAG = "PlayerFragment"

    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        
        
        playListPosition = arg.adapterPosition
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        customMediaPlayer = CustomMediaPlayer()
        
        customMediaPlayer.initialMediaPlayer(arg.channel?.episodes?.get(playListPosition)?.sound?.url.toString())
        setSeekBar()
        setView()
        setButton()
        asyncProgressBar()
        continuePlaying()
        
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
            .load(playListPosition.let { arg.channel?.episodes?.get(it)?.image?.url })
            .centerCrop()
            .into(binding.FragmentPlayerImage)
        
        binding.fragmentPlayerTitle.text = arg.channel?.episodes?.get(playListPosition)?.title
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
            //資料從最新到最舊排序，因此position位置越小集數越新
            try {
                playListPosition++
                customMediaPlayer.playForwardOrBackwardEpisode(
                    arg.channel?.episodes?.get(playListPosition)?.sound?.url.toString()
                )
                setView()
            } catch (e: Exception){
                Toast.makeText(requireContext(),"This is the first episode", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.fragmentPlayerForward.setOnClickListener {
            try {
                playListPosition--
                customMediaPlayer.playForwardOrBackwardEpisode(
                    arg.channel?.episodes?.get(playListPosition)?.sound?.url.toString()
                )
                setView()
            } catch (e: Exception) {
                Toast.makeText(requireContext(),"This is the last episode", Toast.LENGTH_SHORT).show()
            }

        }
    }
    
    private fun continuePlaying(){
        if (customMediaPlayer.isPlayingToEnd){
            try {
                playListPosition--
                customMediaPlayer.playForwardOrBackwardEpisode(
                    arg.channel?.episodes?.get(playListPosition)?.sound?.url.toString()
                )
                setView()
            } catch (e: Exception) {
                Toast.makeText(requireContext(),"This is the last episode", Toast.LENGTH_SHORT).show()
            }
        } else {
        
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
        customMediaPlayer.initialMediaPlayer(arg.channel?.episodes?.get(playListPosition)?.sound?.url.toString())
        continuePlaying()
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