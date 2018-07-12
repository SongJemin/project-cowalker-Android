package com.jemcom.cowalker.Nuri.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Nuri.Holder.ProjectViewHolder
import com.jemcom.cowalker.Nuri.Item.ProjectItem
import com.jemcom.cowalker.R

class ProjectApplyAdapter (private var projectItems : ArrayList<ProjectItem>, var requestManager: RequestManager) : RecyclerView.Adapter<ProjectViewHolder>() {


    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.projectlist_item, parent, false)
        return ProjectViewHolder(mainView)
    }

    override fun getItemCount(): Int = projectItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        requestManager.load(projectItems[position].img).into(holder.img)
        holder.title.text = projectItems[position].title
        holder.sub.text = projectItems[position].sub

    }
}