package com.example.android.android_kkbox_assignment.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.android_kkbox_assignment.databinding.ItemEpisodeBinding
import com.example.android.android_kkbox_assignment.logic.model.Episode
import java.text.SimpleDateFormat

class EpisodeAdapter(
    private val onClickListener: OnClickListener
) : ListAdapter<Episode, EpisodeAdapter.ItemVH>(DiffUtil()){
    
    
    class ItemVH(private val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root){
    
        private val rawDate = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")
        private val castDate = SimpleDateFormat("yyyy/MM/dd")
        
        fun bind(episode: Episode){
            
            
            Glide.with(itemView.context).load(episode.image?.url).into(binding.episodeImage)
            binding.episodeTitleTv.text = episode.title
            
            val milliSec = episode.pubDate?.let { rawDate.parse(it)?.time }
            binding.episodeDateTv.text = castDate.format(milliSec).toString()
        }
    }
    
    class OnClickListener(val clickListener: (position: Int) -> Unit){
        fun onClick(position: Int) = clickListener(position)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        return ItemVH(ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val episode = getItem(position)
        holder.bind(episode = episode)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position = position)
        }
    }
    
    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldEpisode: Episode, newEpisode: Episode): Boolean {
                return oldEpisode === newEpisode
        }
    
        override fun areContentsTheSame(oldEpisode: Episode, newEpisode: Episode): Boolean {
            return oldEpisode == newEpisode
        }
    
    }
}